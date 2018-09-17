/*
 * @(#)UserCenterFacadeImpl.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.org.constant.OrgLevleType;
import com.vanrui.app.org.dto.OrgTreeNodeDTO;
import com.vanrui.app.org.facade.OrganizationTreeFacade;
import com.vanrui.app.user.constant.Constant;
import com.vanrui.app.user.dao.FreezeDao;
import com.vanrui.app.user.dao.UserDao;
import com.vanrui.app.user.dao.UserOrgRefDao;
import com.vanrui.app.user.dao.UserSkillRefDao;
import com.vanrui.app.user.dto.FreezeDto;
import com.vanrui.app.user.dto.RoleDto;
import com.vanrui.app.user.dto.UserBaseDto;
import com.vanrui.app.user.dto.UserDetailDto;
import com.vanrui.app.user.dto.UserInertDto;
import com.vanrui.app.user.dto.UserInfoDto;
import com.vanrui.app.user.dto.UserListCondition;
import com.vanrui.app.user.dto.UserSkillDto;
import com.vanrui.app.user.dto.UserUpdateDto;
import com.vanrui.app.user.model.EbaFmRefEntity;
import com.vanrui.app.user.model.RoleEntity;
import com.vanrui.app.user.model.SkillDictionaryEntity;
import com.vanrui.app.user.model.UserEntity;
import com.vanrui.app.user.model.UserOrgRefEntity;
import com.vanrui.app.user.model.UserRoleAuthEntity;
import com.vanrui.app.user.model.UserSkillRefEntity;
import com.vanrui.app.user.service.cache.UserCacheService;
import com.vanrui.app.user.service.cache.UserOrgCacheService;
import com.vanrui.app.user.service.cache.UserRoleCacheService;
import com.vanrui.app.user.service.cache.UserSkillCacheService;
import com.vanrui.app.user.util.Constants;
import com.vanrui.app.user.util.ObjectConverter;
import com.vanrui.app.user.util.RowBoundsUtil;
import com.vanrui.app.user.util.RowBoundsUtils;
import com.vanrui.app.user.util.StringUtil;
import com.vanrui.app.user.util.UCException;
import com.vanrui.app.user.util.rsa.RsaEncrypt;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.PageRequestDTO;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.crypto.util.EncryptionUtils;
import ooh.bravo.mybatis.util.CountHelper;
import ooh.bravo.redis.service.ObjectRedisService;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;
import ooh.bravo.util.StringUtils;

/**
 *
 * @author maji01
 * @date 2016年10月10日 下午7:34:59
 * @version V1.0.0 description：
 */

@Service("userCenterService")
@TransactionalMark
public class UserCenterService extends BaseService {

	@Autowired
	UserDao userDao;
	@Autowired
	UserCacheService userCache;

	@Autowired
	UserRoleCacheService userRoleCache;

	@Autowired
	UserSkillCacheService userSkillCache;
	@Autowired
	UserSkillRefDao userSkillDao;

	@Autowired
	UserOrgRefDao userOrgRefDao;
	@Autowired
	UserOrgCacheService userOrgCache;
	@Autowired
	UserRelationService  userRelationService;

	@Autowired
	FreezeDao fDao;

	@Autowired
	ObjectRedisService<UserUpdateDto> objectRedisService;

	@Autowired
	OrganizationTreeFacade organizationTreeFacade;
	
	/**
	 * 新增用户信息
	 * 
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public ResponseDTO<Integer> insert(UserInertDto user) {
		
		//rsa解密
		user.setPassword(RsaEncrypt.decryptStringAndBack(user.getPassword()));
		
		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		// 统一对user对象非空校验
		if (this.isValidateForInsert(user, response) != null) {
			return response;
		}
		// 新增用户信息
		UserEntity entity = ObjectConverter.transferUserInfo(user);
		entity.setCreateTime(user.getCreateTime());
		entity.setCreator(user.getCreator());
		userDao.insert(entity);
		Long uId = entity.getuId();
		// 新增用户角色关系
		List<UserRoleAuthEntity> authList = ObjectConverter.transferUserRoleRef(user.getrId(), uId);
		userRoleCache.insertAuth(authList);
		// 新增用户与机构关系
		List<UserOrgRefEntity> uoRef = ObjectConverter.transferUserOrgRef(user.getRefjg(), uId);
		userOrgCache.insert(uoRef);
		// 新增用户与技能的关系
		List<UserSkillRefEntity> usRef = ObjectConverter.transferUserSkillRef(user.getSkills(), uId);
		if (usRef != null && usRef.size() > 0) {
			userSkillCache.insert(usRef);
		}
		response.setData(1);
		response.setStatus(ResponseStatus.SUCCESS.getCode());
		return response;
	}

	/**
	 * 修改用户信息,不能修改密码,不能冻结或解冻用户，以及不能修改registID 不能操作的字段都应该有专门的接口提供更新操作。
	 * 
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public ResponseDTO<Integer> update(UserUpdateDto user) {
		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		// 全面验证user对象
		if (this.isValidateForUpdate(user, response) != null) {
			return response;
		}
		boolean rIdNotNull = false;
		if (user.getRole() != null && user.getRole().getrId() != null) {
			rIdNotNull = true;
			if (isNotExistForRoleId(user.getRole().getrId())) {
				response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
				response.setStatus(ResponseStatus.FAIL.getCode());
				response.setMessage(UCException.ERROR_NULLPOINT_FIELD.getMessage() + ": 无效角色ID[ rId=" + user.getRole().getrId() + " ]，修改用户失败 ！");
				return response;
			}
		}
		// 员工编号 重复校验
		if (StringUtils.isNotBlank(user.getEmployeeCode()) && userDao.isExistsForEmployeeCode(user.getEmployeeCode(), user.getuId()) != null) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setStatus(ResponseStatus.FAIL.getCode());
			response.setMessage("编号已存在，请检查是否重复录入");
			return response;
		}
		// 员工手机号 重复校验
		if (!StringUtil.validateString(user.getMobilePhone(), "[1-9][0-9]{10,10}")) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setStatus(ResponseStatus.FAIL.getCode());
			response.setMessage("手机号应为11位数字");
			return response;
		} else if (userDao.isExistsForPhoneNum(user.getMobilePhone(), user.getuId()) != null) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setStatus(ResponseStatus.FAIL.getCode());
			response.setMessage("手机号已被使用，请检查是否输入错误，或重复录入");
			return response;
		}
		List<UserSkillDto> skills = user.getSkills();
		List<Long> list = null;
		if (skills != null && skills.size() > 0) {
			list = new ArrayList<Long>();
			int len = skills.size();
			for (int i = 0; i < len; i++) {
				list.add(skills.get(i).getSkillId());
			}
			// 技能不为空时，不能存在父子重叠的情况，否则报错
			if (user.getSkills() != null && !user.getSkills().isEmpty() && userSkillDao.isExistParentSonRef(list, list) != null) {
				response.setMessage("子类与父类技能不能重叠提交");
				response.setStatus(ResponseStatus.FAIL.getCode());
				return response;
			}
		}
		// 验证全部通过后执行更新
		UserEntity entity = ObjectConverter.transferUserInfo(user);
		entity.setPassword(null);
		entity.setStatus(null);
		entity.setuId(user.getuId());
		entity.setUpdator(user.getUpdator());
		entity.setUpdateTime(user.getUpdateTime());
		// 先查缓存，没有再查数据库
		userCache.update(entity);

		if (user.getRefjg() != null && user.getRefjg().size() > 0) {
			// 修改机构关系
			List<UserOrgRefEntity> uoRef = ObjectConverter.transferUserOrgRef(user.getRefjg(), user.getuId());
			userOrgCache.update(uoRef, user.getuId());
		}
		if (rIdNotNull) {
			// 修改用户与角色的关系,会刷缓存
			List<UserRoleAuthEntity> authList = ObjectConverter.transferUserRoleRef(user.getRole().getrId(), user.getuId());
			userRoleCache.update(authList, user.getuId());
		}

		List<UserSkillRefEntity> usRef = null;
		usRef = ObjectConverter.transferUserSkillRef(list, user.getuId());
		userSkillCache.update(usRef, user.getuId());
		response.setData(1);
		response.setStatus(ResponseStatus.SUCCESS.getCode());
		return response;
	}

	/**
	 * 修改密码
	 * 
	 * @param uId
	 * @param newPassword
	 * @param oldPassword
	 */
	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public ResponseDTO<Integer> updatePassword(Long uId, String newPassword, String oldPassword) {

		// TODO 解密
		newPassword = RsaEncrypt.decryptStringAndBack(newPassword);
		oldPassword = RsaEncrypt.decryptStringAndBack(oldPassword);
		logger.info("修改密码解密后newPassword={},oldPassword={}", newPassword, oldPassword);

		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		// 查询密码
		String password = userDao.selectPassword(uId);
		if (!isValidatePassword(password, oldPassword, newPassword, response)) {
			// 0表示失败,1表示成功
			response.setData(0);
			response.setStatus(ResponseStatus.FAIL.getCode());
			return response;
		}
		// 修改密码
		String newPass = EncryptionUtils.encodePassword(newPassword);
		UserEntity entity = new UserEntity();
		entity.setuId(uId);
		entity.setPassword(newPass);
		entity.setUpdateTime(new Date());
		entity.setUpdator(uId);
		userCache.update(entity);

		response.setData(1);
		response.setStatus(ResponseStatus.SUCCESS.getCode());
		return response;
	}

	/**
	 * 用户重置密码
	 * 
	 * @param uId
	 * @param newPassword
	 * @param oldPassword
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public ResponseDTO<Integer> resetPassword(Long uId) {
		// 权限校验
		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		Long userID = SystemContextUtils.getUserID();
		// 重置密码
		String newPass = EncryptionUtils.encodePassword(Constant.defaultPwd);
		UserEntity entity = new UserEntity();
		entity.setuId(uId);
		entity.setPassword(newPass);
		entity.setUpdateTime(new Date());
		entity.setUpdator(userID);
		userCache.update(entity);
		response.setData(1);
		response.setStatus(ResponseStatus.SUCCESS.getCode());
		return response;

	}

	/**
	 * 解冻/冻结操作
	 * 
	 * @param freeze
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public ResponseDTO<Integer> freeze(FreezeDto freeze) {
		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		// 1、验证冻结对象
		if (!this.isValidateForFreeze(freeze, response)) {
			response.setData(0);
			response.setStatus(ResponseStatus.FAIL.getCode());
			return response;
		}
		UserEntity entity = new UserEntity();
		entity.setuId(freeze.getfUId());
		entity.setStatus(freeze.getfType());
		entity.setfSolution(freeze.getSolution());
		entity.setUpdateTime(freeze.getOperateTime());
		entity.setUpdator(freeze.getOperator());
		userCache.update(entity);
		// 添加冻结历史记录,待定补充
		fDao.insert(freeze);
		response.setData(1);
		response.setStatus(ResponseStatus.SUCCESS.getCode());
		return response;
	}

	/**
	 * 查询用户详情，只展示用户基本信息，不会展示密码信息。
	 * 
	 * @param uId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public UserDetailDto selectInDetailByUId(Long uId) {
		if (uId == null || SystemContextUtils.getTenantID() == null) {
			logger.error("[查询用户详情]：失败原因：uId={},TenantId={}", uId, SystemContextUtils.getTenantID());
			return null;
		}
		// 查询用户基本信息
		UserDetailDto dto = ObjectConverter.transferUserDetailInfo(userDao.queryInfoByUId(uId));
		if (dto == null) {
			logger.error("[查询用户详情]：失败原因：[uId={}]用户不存在", uId);
			return null;
		}
		// 查询用户与角色的关系
		List<RoleEntity> role = userRoleCache.selectByUId(uId);
		if (role != null && role.size() > 0) {
			dto.setRoleName(role.get(0).getrName());
		}
		// 查询用户与机构的关系列表List<Long>
		dto.setJgs(ObjectConverter.transferUserOrgRefBack(userOrgCache.selectByUId(uId)));
		// 查询用户与技能的关系列表List<Object>
		List<UserSkillRefEntity> skills = userSkillCache.selectByUId(uId);
		if (skills != null && skills.size() > 0) {
			int len = skills.size();
			StringBuffer temp = new StringBuffer(skills.get(0).getSkillName());
			for (int i = 1; i < len; i++) {
				temp.append(",").append(skills.get(i).getSkillName());
			}
			// 设置技能
			dto.setSkills(temp.toString());
		}
		return dto;
	}

	/**
	 * 查询用户详情,修改中的查询使用
	 * 
	 * @param uId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
	public UserUpdateDto selectInUpdateByUId(Long uId) {
		if (uId == null || SystemContextUtils.getTenantID() == null) {
			logger.error("[查询用户详情]：失败原因：uId={},TenantId={}", uId, SystemContextUtils.getTenantID());
			return null;
		}
		// 查询用户基本信息 ， 先查缓存，没有再查数据库
		UserEntity entity = userCache.selectDetailByUId(uId);
		if (entity == null) {
			logger.error("[查询用户详情]：失败原因：[uId={}]用户不存在", uId);
			return null;
		}
		UserUpdateDto dto = ObjectConverter.transferToUpdateDto(entity);
		// 查询用户与角色的关系
		List<RoleEntity> role = userRoleCache.selectByUId(uId);
		if (role != null && role.size() > 0) {
			RoleDto rDto = new RoleDto();
			rDto.setrId(role.get(0).getrId());
			rDto.setrName(role.get(0).getrName());
			dto.setRole(rDto);
		}
		// TODO 查询用户绑定的FM账号的手机号码
		List<Long> userIdSet = new ArrayList<>();
		userIdSet.add(uId);
		EbaFmRefEntity refEntity = userRelationService.selecOneByUIds(userIdSet);
		if(refEntity!=null){
		    dto.setRelateFmMobile(refEntity.getFmUserPhone());
		}
		// 查询用户与机构的关系列表
		dto.setRefjg(ObjectConverter.transferUserOrgRefBack(userOrgCache.selectByUId(uId)));
		// 查询用户与技能的关系列表
		dto.setSkills(ObjectConverter.transferUserSkillRefBack(userSkillCache.selectByUId(uId)));

		return dto;
	}

	/**
	 * 根据条件查询用户列表,不包括自己
	 * 
	 * @param containMine
	 *            true；包含自己，false 不包含自己
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
	public PageResponseDTO<UserInfoDto> selectListByCondition(boolean containMine, UserListCondition condition, int pageNum, int pageSize) {
		// 安分页查询用户信息
		RowBounds bound = RowBoundsUtil.getRowBounds(pageNum, pageSize);
		if (condition == null || condition.getCombineCode() == null) {
			logger.error("机构组合编号不能为空，查询失败 :[{}]", condition);
			return null;
		}
		List<Long> skills = null;
		if (condition.getSkillId() != null) {
			skills = getSkillsFromCache(condition.getSkillId());
		}
		Long uId = SystemContextUtils.getUserID();
		if (uId == null) {
			logger.error("检查到您未登录，查询失败 ！ [ userId = null ] ");
			return null;
		}
		/*
		 * if( containMine ){ uId = null; }
		 */
		// 根据分页条件以及查询条件查询用户ID列表
		List<Long> uIds = userDao.queryUidsByCondition(null, condition, skills, bound);
		PageResponseDTO<UserInfoDto> responseDto = new PageResponseDTO<UserInfoDto>();
		responseDto.setTotalRow(CountHelper.getTotalRow());
		if (uIds != null && uIds.size() > 0) {
			// 存在符合条件的ID列表，则根据ID列表查询用户信息列表并按照时间排序(逆序)
			List<UserInfoDto> list = userDao.selectUsersByUids(uIds, condition.getSort());
			responseDto.setData(list);
		}
		return responseDto;
	}

	// 去缓存中匹配，并获取父级id
	private List<Long> getSkillsFromCache(Long skillId) {
		List<Long> list = new ArrayList<Long>();

		list.add(skillId);
		for (SkillDictionaryEntity en : userSkillCache.selectAllSkills()) {
			if (en.getpSkillId().equals(skillId)) {
				// 分析，并将机构的路径编号进行差分，并转成long类型
				list.add(en.getSkillId());
			} else if (en.getSkillId().equals(skillId) && !en.getpSkillId().equals(-1l)) {
				list.add(en.getpSkillId());
			}
		}
		return list;
	}

	/**
	 * 根据用户ID列表查询用户基本信息列表
	 * 
	 * @param uIds
	 * @return Map<UId,UserInfoDto>
	 */
	public Map<Long, UserInfoDto> selectUserBaseInfoList(Set<Long> uIds) {

		if (uIds == null || uIds.size() == 0) {
			logger.error("[查询用户基本信息列表] 入口参数 [uIds == null ] ,查询失败 ！ ");
			return null;
		}
		List<Long> list = new ArrayList<Long>();
		for (Long id : uIds) {
			list.add(id);
		}
		List<UserInfoDto> res = userDao.selectUserBaseInfoList(list);
		if (res != null && res.size() > 0) {
			Map<Long, UserInfoDto> map = new HashMap<Long, UserInfoDto>();
			for (UserInfoDto dto : res) {
				map.put(dto.getuId(), dto);
			}
			return map;
		}
		logger.error("[查询用户基本信息列表] 入口参数 Set<Long> uIds=[{}] ,查询结果:null", uIds);
		return null;
	}

	/**
	 * 根据用户ID列表查询用户基本信息
	 * 
	 * @param uId
	 * @return
	 */
	public UserInfoDto selectUserBaseInfo(Long uId) {
		if (uId == null) {
			logger.error("[查询用户基本信息 ] 入口参数 [uId == null ] ,查询失败 ！ ");
			return null;
		}
		/*
		 * Long id = SystemContextUtils.getUserID(); if (id == null) {
		 * logger.error("[查询用户基本信息 ] 未登录,无权执行本操作 ,查询失败 ！ "); return null; }
		 */
		UserEntity entity = userCache.selectDetailByUId(uId);
		UserInfoDto dto = new UserInfoDto();
		dto.setuId(uId);
		dto.setUserName(entity.getUserName());
		dto.setAccount(entity.getAccount());
		dto.setEmployeeCode(entity.getEmployeeCode());
		dto.setMobilePhone(entity.getMobilePhone());
		return dto;
	}

	public List<UserBaseDto> selectAssignOrderUsers(PageRequestDTO<SearchUserCondition> param) {

		RowBounds boundsPage = RowBoundsUtils.getRowBounds(param);
		if (boundsPage == null) {
			return null;
		}

		Long orgId = param.getArgument().getOrgId();
		List<Long> skillIds = param.getArgument().getSkillIdList();
		List<Long> roleIds = param.getArgument().getRoleIdList();
		String userName = param.getArgument().getUserName();

		/*
		 * if (orgId == null || skillIds == null || roleIds == null ||
		 * skillIds.isEmpty() || roleIds.isEmpty()) { logger.error(
		 * "[查询可接单用户信息] 请求参数为空，查询失败！"); return null; }
		 */

		List<UserBaseDto> userList = new ArrayList<UserBaseDto>();
		List<UserEntity> userEntityList = userDao.selectAssignOrderUsers(orgId, skillIds, roleIds, userName, boundsPage);
		if (userEntityList == null) {
			return null;
		}
		for (UserEntity user : userEntityList) {
			userList.add(ObjectConverter.transferUserInfoBack(user));
		}
		return userList;
	}

	/**
	 * 解冻、冻结用户的对象的非空校验
	 * 
	 * @param freeze
	 * @return
	 */
	private boolean isValidateForFreeze(FreezeDto freeze, ResponseDTO<Integer> response) {

		if (freeze == null || freeze.getfType() == null || freeze.getfUId() == null || freeze.getSolution() == null) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FREEZE.getCode());
			response.setMessage(UCException.ERROR_NULLPOINT_FREEZE.getMessage());
			return false;
		}
		// 操作类型 : 0冻结，1解冻 **/
		if (!(freeze.getfType().equals(0) || freeze.getfType().equals(1))) {
			response.setErrorCode(UCException.ERROR_OPERATETYPE_FREEZE.getCode());
			response.setMessage(UCException.ERROR_OPERATETYPE_FREEZE.getMessage());
			return false;
		}
		if (freeze.getOperateTime() == null) {
			freeze.setOperateTime(new Date());
		}
		Long uId = SystemContextUtils.getUserID();
		if (uId == null) {
			response.setErrorCode(UCException.ERROR_SYSTEM_CONTEXT_NULL.getCode());
			response.setMessage(UCException.ERROR_SYSTEM_CONTEXT_NULL.getMessage() + ": 你没有登录，该操作无效 ！");
			return false;
		}
		// 冻结/解冻，无法修改自己的状态
		if (freeze.getfUId().equals(uId)) {
			response.setMessage("用户无法冻结/解冻自己");
			return false;
		}
		if (freeze.getOperator() == null) {
			freeze.setOperator(uId);
		}
		return true;
	}

	/**
	 * 新增用户的对象的非空校验
	 * 
	 * @param freeze
	 * @return
	 */
	public ResponseDTO<Integer> isValidateForInsert(UserInertDto user, ResponseDTO<Integer> response) {
	    response.setStatus(ResponseStatus.FAIL.getCode());
	    if (user == null || StringUtils.isBlank(user.getUserName())) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage("用户姓名不能为空，新增用户失败");
			return response;
		}
		if (user.getMobilePhone() == null || !StringUtil.validateString(user.getMobilePhone(), "[1-9][0-9]{10,10}")) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setMessage("手机号应为11位数字");
			return response;
		} else if (userDao.isExistsForPhoneNum(user.getMobilePhone(), null) != null) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setMessage("手机号已被使用，请检查是否输入错误，或重复录入");
			return response;
		}
		if (StringUtils.isNotBlank(user.getEmployeeCode()) && userDao.isExistsForEmployeeCode(user.getEmployeeCode(), null) != null) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setMessage("编号已存在，请检查是否重复录入");
			return response;
		}
        if (user.getAccount() == null || StringUtil.validateString(user.getAccount(), "[0-9]{6,20}")) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage("用户名应为 6-20位数字、字母、符号，区分大小写，且不支持纯数字的账号");
			return response;
		} else if (userDao.isExistsForAccount(user.getAccount()) != null) {
			response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
			response.setMessage("用户名已被使用");
			return response;
		}
		if (user.getPassword() == null) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage("密码应为6-20位数字、字母或符号，不支持空格");
			return response;
		}
		if (isNotExistForRoleId(user.getrId())) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage(UCException.ERROR_NULLPOINT_FIELD.getMessage() + ": 无效角色ID[ rId=" + user.getrId() + " ]，新增用户失败 ！");
			return response;
		}
		if (user.getRefjg() == null || user.getRefjg().size() == 0) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage(UCException.ERROR_NULLPOINT_FIELD.getMessage() + ": 机构不能为空，新增用户失败 ！");
			return response;
		}
		if (user.getCreator() == null) {
			Long uId = SystemContextUtils.getUserID();
			if (uId == null) {
				response.setErrorCode(UCException.ERROR_SYSTEM_CONTEXT_NULL.getCode());
				response.setMessage(UCException.ERROR_SYSTEM_CONTEXT_NULL.getMessage() + ": 你没有登录，该操作无效 ！");
				return response;
			}
			user.setCreator(uId);
		}
		// 技能不为空时，不能存在父子重叠的情况，否则报错
		if (user.getSkills() != null && !user.getSkills().isEmpty() && userSkillDao.isExistParentSonRef(user.getSkills(), user.getSkills()) != null) {
			response.setMessage("子类与父类技能不能重叠提交");
			return response;
		}
		if (user.getCreateTime() == null) {
			user.setCreateTime(new Date());
		}
		if (user.getStatus() == null) {
			user.setStatus(Constants.USER_FREEZE_NO);
		}
		user.setPassword(EncryptionUtils.encodePassword(user.getPassword()));
		return null;
	}

	/**
	 * 新增用户的对象的非空校验
	 * 
	 * @param freeze
	 * @return
	 */
	public ResponseDTO<Integer> isValidateForUpdate(UserUpdateDto user, ResponseDTO<Integer> response) {
		if (user == null || user.getuId() == null || user.getuId().equals(0l)) {
			logger.error(": 更新内容为空,更新失败[  {} ]", user);
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage("更新内容为空,更新失败");
			return response;
		}
		Long uId = SystemContextUtils.getUserID();
		if (uId == null) {
			logger.error(": 当前用户ID[uId={}],查询失败", uId);
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage(UCException.ERROR_NULLPOINT_FIELD.getMessage() + ": 当前用户未登录,查询失败");
			return response;
		}
		//20170626经与需求确认用户可修改自己除账号以外的个人信息--huhuiqian
/*		if (uId.equals(user.getuId())) {
			logger.error("修改失败，目前暂不支持修改自己的信息");
			response.setMessage("修改失败，目前暂不支持修改自己的信息");
			return response;
		}*/
		if (user.getUpdateTime() == null) {
			user.setUpdateTime(new Date());
		}

		if (user.getEmployeeCode() == null) {
		    user.setEmployeeCode(Constant.EMPTY_STR);
		}
		user.setUpdator(uId);
		return null;
	}

	public boolean isValidatePassword(String password, String oldPd, String newPd, ResponseDTO<Integer> response) {
		if (oldPd == null || newPd == null || password == null) {
			response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			response.setMessage(UCException.ERROR_NULLPOINT_FIELD.getMessage() + ": 用户ID无效,修改密码失败");
			return false;
		}
		// 1、验证旧密码是否输出正确
		if (!EncryptionUtils.verifyPassword(oldPd, password)) {
			response.setErrorCode(UCException.ERROR_COMPARE_PASSWORD_OLD.getCode());
			response.setMessage(UCException.ERROR_COMPARE_PASSWORD_OLD.getMessage());
			return false;
		}
		// 2、验证新密码与旧密码是否相等
		if (EncryptionUtils.verifyPassword(newPd, password)) {
			response.setErrorCode(UCException.ERROR_COMPARE_PASSWORD_NEW.getCode());
			response.setMessage(UCException.ERROR_COMPARE_PASSWORD_NEW.getMessage());
			return false;
		}
		return true;
	}

	private boolean isNotExistForRoleId(Long rId) {
		// 0表示无效角色
		if (rId == null || rId.equals(0l)) {
			return true;
		}
		List<RoleEntity> allRoles = userRoleCache.selectAllRoles();
		for (RoleEntity r : allRoles) {
			if (rId.equals(r.getrId())) {
				return false;
			}
		}
		return true;
	}

	public Set<Long> selectWorkOrderManagers(Long orgId) {
		Set<Long> result = userOrgRefDao.selectWorkOrderManagers(orgId);
		if (result == null || result.size() == 0) {
			OrgTreeNodeDTO orgTreeNodeDTO = organizationTreeFacade.getTreeNode4AllParent(orgId);
			result = this.selectWorkOrderManagersAscend(orgTreeNodeDTO);
		}
		return result;
	}

	/***
	 * 校验 制定用户是否具备管理员的目录权限
	 * 
	 * @param userId
	 * @return
	 */
	public boolean validateIsManagers(Long userId) {
		if (userId == null) {
			return false;
		}
		Integer result = userDao.validateIsManagers(userId);
		// 1表示具有管理员权限,0表示不具有
		if (result != null && result.intValue() == 1) {
			return true;
		}
		return false;
	}

	public Boolean hasWorkOrderRepairPermission(Long userId) {
		if (userId == null) {
			userId = SystemContextUtils.getUserID();
		}
		Integer result = userDao.hasWorkOrderRepairPermission(userId);
		if (result != null && result.intValue() == 1) {
			return true;
		}
		return false;
	}

	private Set<Long> selectWorkOrderManagersAscend(OrgTreeNodeDTO orgTreeNodeDTO) {
		if (orgTreeNodeDTO == null || orgTreeNodeDTO.getLevelType().intValue() == OrgLevleType.CITY.getCode()) {
			return null;
		}
		if (orgTreeNodeDTO.getParentOrg() == null) {
			return null;
		}
		Set<Long> result = userOrgRefDao.selectWorkOrderManagers(orgTreeNodeDTO.getParentOrg().getId());
		if (result != null && result.size() > 0) {
			return result;
		}
		return this.selectWorkOrderManagersAscend(orgTreeNodeDTO.getParentOrg());
	}

	/**
	 * 获取所有用户ID列表
	 * 
	 * @return
	 */
	public List<Long> selectAllUserIdList() {
		return userDao.selectAllUserIdList();
	}

	/**
	 * 获取用户权限id列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Long> selectAllRIdList(Long userId) {
		if (userId == null) {
			return null;
		}
		List<Long> response = new ArrayList<Long>();
		response = userDao.selectAllRIdList(userId);

		return response;
	}

	/***
	 * 跟据电话号码查询用户Id
	 * 
	 * @param phoneNum
	 * @return
	 */
	public Long selectUserIdByPhoneNum(String phoneNum) {
		if (StringUtils.isBlank(phoneNum)) {
			return null;
		}
		return userDao.queryUIdByPhoneNum(phoneNum);
	}
	
	/**
	 * 根据用户Id查询用户的技能列表
	 * @param userId
	 * @return
	 */
	public List<Long> selectSubSkillListByUserId(Long userId){
        if(userId == null||userId==0){
            return new ArrayList<Long>();
        }
        return userSkillDao.selectSubSkillListByUserId(userId);
    }
}
