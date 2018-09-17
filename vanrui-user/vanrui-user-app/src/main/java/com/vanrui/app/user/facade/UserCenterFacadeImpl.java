/*
 * @(#)UserCenterFacadeImpl.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.user.dto.FreezeDto;
import com.vanrui.app.user.dto.UserBaseDto;
import com.vanrui.app.user.dto.UserDetailDto;
import com.vanrui.app.user.dto.UserInertDto;
import com.vanrui.app.user.dto.UserInfoDto;
import com.vanrui.app.user.dto.UserListCondition;
import com.vanrui.app.user.dto.UserUpdateDto;
import com.vanrui.app.user.service.AuthManaService;
import com.vanrui.app.user.service.UserCenterService;
import com.vanrui.app.user.service.jms.AuthorizationChangeMessageService;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.PageRequestDTO;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;
import ooh.bravo.util.StringUtils;

/**
 * 用户中心装饰类
 * 
 * @author maji01
 * @date 2016年10月10日 下午7:34:59
 * @version V1.0.0 description：
 * 
 */
@Service("userCenterFacade")
public class UserCenterFacadeImpl extends BaseService implements UserCenterFacade {

    @Autowired
    UserCenterService ucService;
    
    @Autowired
    AuthorizationChangeMessageService authzChangeMessageService;
    
    @Autowired
    AuthManaService authService;

    @Override
    public ResponseDTO<Integer> insert(UserInertDto user) {
        logger.info("[新增用户] 入参：user = {}", user);
        ResponseDTO<Integer> response = null;
        try {
            response = ucService.insert(user);
            logger.info("[新增用户]成功.");
        } catch (DuplicateKeyException ex) {
            response = new ResponseDTO<Integer>();
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            if (ex.getRootCause() != null && StringUtils.isNotBlank(ex.getRootCause().getMessage())) {
                String error = ex.getRootCause().getMessage();
                if (error.contains("ACCOUNT") || error.contains("account")) {
                    response.setMessage("用户名已经被占用，新增用户失败");
                } else if (error.contains("EMPLOYEE_CODE") || error.contains("employee_code")) {
                    response.setMessage("编号已存在，请检查是否重复录入");
                } else if (error.contains("MOBILEPHONE") || error.contains("mobilephone")) {
                    response.setMessage("手机号已被使用，请检查是否输入错误，或重复录入");
                } else {
                    response.setMessage(error);
                }
            } else {
                response.setMessage("主键或唯一性约束冲突异常，");
            }
            logger.error("主键或唯一性约束冲突异常:{}", ex);
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[新增用户]异常: {}", ex);
        }
        return response;
    }

    @Override
    public ResponseDTO<Integer> update(UserUpdateDto user) {
        logger.info("[更新用户] 入参：user = {}", user);
        ResponseDTO<Integer> response = null;
        try {
            response = ucService.update(user);
            if( response.getStatus() == ResponseStatus.SUCCESS.getCode() 
                    && user.getRole()!=null 
                    && user.getRole().getrId() != null){
                // TODO 修改用户角色，需要进行同步
                authzChangeMessageService.send(user.getuId());
            }
            logger.info("[更新用户]成功.");
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[ 更新用户]异常:", ex);
        }
        return response;
    }

    @Override
    public ResponseDTO<Integer> updatePassword(Long uId, String newPassword, String oldPassword) {
        logger.info("[修改密码] 入参：uId = {},newPassword={},oldPassword={}", uId, newPassword, oldPassword);
        ResponseDTO<Integer> response = null;
        try {
            response = ucService.updatePassword(uId, newPassword, oldPassword);
            logger.info("[修改密码]成功.");
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[修改密码]异常: {}", ex);
        }
        return response;
    }

    @Override
    public ResponseDTO<Integer> freeze(FreezeDto freeze) {
        logger.info("[冻结或解冻用户] 入参：freeze = {} ", freeze);
        ResponseDTO<Integer> response = null;
        try {
            response = ucService.freeze(freeze);
            logger.info("[冻结或解冻用户]成功.");
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[冻结或解冻用户]异常: {}", ex);
        }
        return response;
    }

    @Override
    public PageResponseDTO<UserInfoDto> selectListByCondition(boolean containMine, UserListCondition condition, int pageNum, int pageSize) {
        logger.info("[查询用户列表] 入参：condition = {},pageNum={},pageSize={} ", condition, pageNum, pageSize);
        PageResponseDTO<UserInfoDto> response = null;
        try {
            response = ucService.selectListByCondition(containMine, condition, pageNum, pageSize);
            logger.info("[查询用户列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询用户列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public UserDetailDto selectInDetailByUId(Long uId) {
        logger.info("[查询用户‘详情页’的详情] 入参：uId = {} ", uId);
        UserDetailDto response = null;
        try {
            response = ucService.selectInDetailByUId(uId);
            logger.info("[查询用户‘详情页’的详情]成功.");
        } catch (Exception ex) {
            logger.error("[查询用户‘详情页’的详情]异常: {}", ex);
        }
        return response;
    }

    @Override
    public UserUpdateDto selectInUpdateByUId(Long uId) {
        logger.info("[查询用户‘更新页’的详情] 入参：uId = {} ", uId);
        UserUpdateDto response = null;
        try {
            response = ucService.selectInUpdateByUId(uId);
            logger.info("[查询用户‘更新页’的详情]成功.");
        } catch (Exception ex) {
            logger.error("[查询用户‘更新页’的详情]异常: {}", ex);
        }
        return response;
    }

    @Override
    public Map<Long, UserInfoDto> selectUserBaseInfoList(Set<Long> uIds) {
        logger.info("[根据用户ID集合查询用户基本信息列表] 入参：uIds = {} ", uIds);
        Map<Long, UserInfoDto> response = null;
        try {
            response = ucService.selectUserBaseInfoList(uIds);
            logger.info("[根据用户ID集合查询用户基本信息列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据用户ID集合查询用户基本信息列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public UserInfoDto selectUserBaseInfo(Long uId) {
        logger.info("[根据用户ID查询用户基本信息列表] 入参：uId = {} ", uId);
        UserInfoDto response = null;
        try {
            response = ucService.selectUserBaseInfo(uId);
            logger.info("[根据用户ID查询用户基本信息列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据用户ID查询用户基本信息列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public List<UserBaseDto> selectAssignOrderUsers(Long orgId, List<Long> skillIds, List<Long> roleIds, String userName) {

        PageRequestDTO<SearchUserCondition> param = new PageRequestDTO<SearchUserCondition>();
        SearchUserCondition condition = new SearchUserCondition();
        condition.setOrgId(orgId);
        condition.setRoleIdList(roleIds);
        condition.setSkillIdList(skillIds);
        condition.setUserName(userName);
        param.setArgument(condition);

        logger.info("[查询可接单用户信息]请求参数 orgId = {}, skillIds = {}, roleIds = {}", orgId, skillIds, roleIds);
        List<UserBaseDto> userList = null;
        try {
            userList = ucService.selectAssignOrderUsers(param);
            logger.info("[查询可接单用户信息]成功.");
        } catch (Exception e) {
            logger.error("[查询可接单用户信息]异常", e);
        }
        return userList;
    }

    public Set<Long> selectWorkOrderManagers(Long orgId) {
        logger.info("[根据机构ID查询工单管理者列表] 入参：orgId = {} ", orgId);
        Set<Long> response = null;
        try {
            response = ucService.selectWorkOrderManagers(orgId);
        } catch (Exception ex) {
            logger.error("[根据机构ID查询工单管理者列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public List<Long> selectAllUserIdList() {
        logger.info("[获取所有用户ID列表]开始");
        List<Long> response = null;
        try {
            response = ucService.selectAllUserIdList();
        } catch (Exception ex) {
            logger.error("[获取所有用户ID列表]异常: {}", ex);
        }
        logger.info("[获取所有用户ID列表]结束");
        return response;
    }

    @Override
    public boolean validateIsManagers(Long userId) {
        logger.info("[根据机构ID查询工单管理者列表] 入参：userId = {} ", userId);
        try {
            return ucService.validateIsManagers(userId);
        } catch (Exception ex) {
            logger.error("[根据机构ID查询工单管理者列表]异常: {}", ex);
            return false;
        }
    }

    @Override
    public Boolean hasWorkOrderRepairPermission(Long userId) {
        logger.info("[用户ID是否具备工单维修维保操作权限] 入参：userId = {} ", userId);
        try {
            return ucService.hasWorkOrderRepairPermission(userId);
        } catch (Exception ex) {
            logger.error("[用户ID是否具备工单维修维保操作权限]异常: {}", ex);
            return false;
        }
    }

    @Override
    public List<Long> selectRIds(Long userId) {
        logger.info("[获取用户权限ID列表]开始");
        List<Long> response = null;
        try {
            response = ucService.selectAllRIdList(userId);
        } catch (Exception ex) {
            logger.error("[获取用户权限ID列表]异常: {}", ex);
        }
        logger.info("[获取所有用户ID列表]结束");
        return response;
    }

    @Override
    public ResponseDTO<Long> selectUserIdByPhoneNum(String phoneNum) {
        logger.info("[获取用户权限ID列表]开始");
        ResponseDTO<Long> response = new ResponseDTO<Long>();
        try {
            Long userId = ucService.selectUserIdByPhoneNum(phoneNum);
            if(userId != null){
                response.setData(userId);
            }
        } catch (Exception ex) {
            logger.error("[获取用户权限ID列表]异常: {}", ex);
        }finally{
            if(response.getData() == null ){
                response.setStatus(ResponseStatus.FAIL); 
            }
        }
        logger.info("[获取所有用户ID列表]结束");
        return response;
    }

	@Override
	public ResponseDTO<Integer> resetPassword(Long modifiedUserId) {
		
		 logger.info("[重置密码] 入参：uId = {},newPassword={},oldPassword={}",modifiedUserId);
	        ResponseDTO<Integer> response = null;
	       
	        try {
	            response = ucService.resetPassword(modifiedUserId);
	            
	        } catch (Exception ex) {
	            response = new ResponseDTO<Integer>();
	            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
	            response.setStatus(ResponseStatus.FAIL.getCode());
	            response.setData(0);
	            logger.error("[重置密码]异常: {}", ex);
	        }
	        if(response.getData()==1){
	        	logger.info("[重置密码]成功.");
	        }
	        else{
	        	logger.info("[重置密码]失败.");
	        }
	        return response;
	}
	
	@Override
    public List<Long> selectSubSkillListByUserId(Long userId) {
        logger.info("[获取所有用户子技能ID列表]开始"); 
        try {
            return ucService.selectSubSkillListByUserId(userId);
        } catch (Exception ex) {
            logger.error("[获取所有用户子技能ID列表]异常: {}", ex);
        } 
        logger.info("[获取所有用户子技能ID列表]结束");
        return null;
    }
}
