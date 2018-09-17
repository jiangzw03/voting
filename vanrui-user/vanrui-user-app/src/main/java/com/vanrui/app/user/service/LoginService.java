/*
 * @(#)LoginService.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vanrui.app.user.dao.UserDao;
import com.vanrui.app.user.dto.AuthenticationDTO;
import com.vanrui.app.user.dto.LoginUserDTO;
import com.vanrui.app.user.dto.fm.FmLoginUserDTO;
import com.vanrui.app.user.model.UserEntity;
import com.vanrui.app.user.service.cache.UserCacheService;
import com.vanrui.app.user.service.cache.UserOrgCacheService;
import com.vanrui.app.user.service.cache.UserRoleCacheService;
import com.vanrui.app.user.service.cache.UserSkillCacheService;
import com.vanrui.app.user.util.FMLoginValidateUtil;
import com.vanrui.app.user.util.UCException;
import com.vanrui.app.user.util.rsa.RsaEncrypt;
import com.vanrui.app.user.util.rsa.RsaEncryptDecryptUtils;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.crypto.util.EncryptionUtils;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;

/**
 *
 * @author maji01
 * @date 2016年10月17日 上午11:48:18
 * @version V1.0.0 description：
 * 
 */
@Service("loginService")
@TransactionalMark
public class LoginService extends BaseService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserCacheService uCache;
	
	@Autowired
	UserOrgCacheService uoCache;
	
	@Autowired
	UserSkillCacheService usCache;
	
	@Autowired
	UserRoleCacheService urCache;

    @Autowired
	UserRelationService  userRelationService;

	/**
	 * 如果用户名或者密码为空，抛出异常，异常代码："user-error-001", 并提示："非空校验失败: 用户名/密码为空"
	 * 如果校验密码失败，抛出异常，异常代码："user-error-004", 并提示："密码错误,登录失败"
	 * 如果根据账号，未能找到对应的用户信息时，抛出异常，异常代码："user-error-005", 并提示："账户不存在,登录失败"
	 * 
	 * @param authDTO
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 8)
	public ResponseDTO<LoginUserDTO> loginAuthenticate(AuthenticationDTO authDTO) {
		ResponseDTO<LoginUserDTO> logDTO = new ResponseDTO<LoginUserDTO>();
		
		if(authDTO.getDeviceType().intValue() > 0){
			 //RSA解密
			 String password = RsaEncrypt.decryptStringAndBack(authDTO.getPassword());
			 logger.info("password={}", password);
			 authDTO.setPassword(password);
		}
		logger.info("RSA解密后：={}", authDTO);

		
		
		if (authDTO.getUsername() == null || authDTO.getPassword() == null) {
			logDTO.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
			logDTO.setMessage("账号或密码为空");
			logDTO.setStatus(ResponseStatus.FAIL.getCode());
		}
		UserEntity user = userDao.queryInfoByAccountorMobilePhone(authDTO.getUsername());
		if (user != null) {
			// 校验密码
			if (user.getStatus() == null) {
				logDTO.setErrorCode(UCException.ERROR_ACCOUNT_EXCEPTION.getCode());
				logDTO.setStatus(ResponseStatus.FAIL.getCode());
				logDTO.setMessage("账号异常，请联系你的上级管理人员");
				return logDTO;
			} else if (user.getStatus().equals(0)) {
				logDTO.setErrorCode(UCException.ERROR_ACCOUNT_FREEZE.getCode());
				logDTO.setStatus(ResponseStatus.FAIL.getCode());
				logDTO.setMessage("账号已被冻结，如需开通请联系你的上级管理人员");
				return logDTO;
			} else if (!EncryptionUtils.verifyPassword(authDTO.getPassword(), user.getPassword())) {
				logDTO.setErrorCode(UCException.ERROR_COMPARE_PASSWORD.getCode());
				logDTO.setStatus(ResponseStatus.FAIL.getCode());
				logDTO.setMessage("账号或密码输入错误");
				return logDTO;
			}
			// 登录成功 刷新缓存
			refreshCacheByUId(user.getuId());
			// ，准备返回
			LoginUserDTO data = new LoginUserDTO();
			data.setUserID(user.getuId());
			data.setMobilePhone(user.getMobilePhone());
			data.setEmployeeCode(user.getEmployeeCode());
			data.setUsername(user.getAccount());
			data.setRealName(user.getUserName());
			data.setTenantID("T000001");
			// 配置上下文
			SystemContextUtils.setUserAndTenantID(user.getuId(), "T000001");
			logDTO.setData(data);
			logDTO.setStatus(ResponseStatus.SUCCESS.getCode());

		} else {
			logDTO.setErrorCode(UCException.ERROR_COMPARE_ACCOUNT.getCode());
			logDTO.setStatus(ResponseStatus.FAIL.getCode());
			logDTO.setMessage("账户不存在,登录失败");
		}
		logger.info("登录结果:{}",logDTO);
		return logDTO;
	}

	public static void main(String[] args) {
		Integer i = new Integer(10);
		if (i == 10) {
			System.out.println("result:true");
		} else {
			System.out.println("result:false");
		}
	}

	/**
	 * 根据用户ID刷新用户缓存
	 * 
	 * @param uId
	 */
	public void refreshCacheByUId(Long uId) {

		uCache.refreshUserInfoCacheByUId(uId);
		uoCache.refreshUserOrgCacheByUId(uId);
		usCache.refreshUserRoleCacheByUId(uId);
		urCache.refreshUserRoleCacheByUId(uId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 8)
    public ResponseDTO<FmLoginUserDTO> fmLoginAuthenticate(String keyWord) {
	    ResponseDTO<FmLoginUserDTO> responseDTO = new ResponseDTO<FmLoginUserDTO>();
	    Long fmUserId = FMLoginValidateUtil.getUIdFromKeyword(keyWord,false);
	    if(fmUserId==null||fmUserId.equals(0L)){
	        // 校验不通过
	        responseDTO.setStatus(ResponseStatus.FAIL.getCode());
	        responseDTO.setMessage("校验失败，登录的key无效");
	        return responseDTO;
	    }
	    Long uId = userRelationService.selectEbaByFMUId(fmUserId);
	    if(uId == null){
	        responseDTO.setErrorCode(UCException.ERROR_ID_IS_NOT_EXISTS.getCode());
            responseDTO.setStatus(ResponseStatus.FAIL.getCode());
            responseDTO.setMessage("FM账户未绑定，登录失败");
	    } 
	    UserEntity entity = uCache.selectDetailByUId(uId);
	    if(entity!=null){
	        // 用户状态
            if (entity.getStatus() == null) {
                responseDTO.setErrorCode(UCException.ERROR_ACCOUNT_EXCEPTION.getCode());
                responseDTO.setStatus(ResponseStatus.FAIL.getCode());
                responseDTO.setMessage("账号异常，请联系你的上级管理人员");
                return responseDTO;
            } else if (entity.getStatus().equals(0)) {
                responseDTO.setErrorCode(UCException.ERROR_ACCOUNT_FREEZE.getCode());
                responseDTO.setStatus(ResponseStatus.FAIL.getCode());
                responseDTO.setMessage("账号已被冻结，如需开通请联系你的上级管理人员");
                return responseDTO;
            }
            // 准备返回
            final String tenantID = "T000001";
            FmLoginUserDTO data = new FmLoginUserDTO();
            data.setUserID(entity.getuId());
            data.setMobilePhone(entity.getMobilePhone());
            data.setEmployeeCode(entity.getEmployeeCode());
            data.setUsername(entity.getAccount());
            data.setRealName(entity.getUserName());
            data.setTenantID(tenantID);
            // 通过RSA生成token
            data.setToken(RsaEncryptDecryptUtils.encryptString(keyWord));
//            data.setToken(keyWord);
            // 配置上下文
            SystemContextUtils.setUserAndTenantID(entity.getuId(),tenantID);
            responseDTO.setData(data);
            responseDTO.setStatus(ResponseStatus.SUCCESS.getCode());
            logger.info("登录用户信息："+data);
	    }else{
	        responseDTO.setErrorCode(UCException.ERROR_ID_IS_NOT_EXISTS.getCode());
            responseDTO.setStatus(ResponseStatus.FAIL.getCode());
            responseDTO.setMessage("请联系您的主管在saas平台关联你的账号才可以访问");
	    }
	    return responseDTO;
    }
	 
}
