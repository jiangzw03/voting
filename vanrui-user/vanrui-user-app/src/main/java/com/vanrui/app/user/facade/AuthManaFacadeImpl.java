/*
 * @(#)AuthManaFacadeImpl.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.AuthDetailsDto;
import com.vanrui.app.user.dto.AuthDto;
import com.vanrui.app.user.dto.AuthInfoDto;
import com.vanrui.app.user.service.AuthManaService;
import com.vanrui.app.user.service.jms.AuthorizationChangeMessageService;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;

/**
 * 
 * @author maji01
 * @date 2016年10月8日 下午10:24:39
 * @version V1.0.0 description：
 * 
 */ 

@Service("authManaFacade")
public class AuthManaFacadeImpl extends BaseService implements AuthManaFacade {

    @Autowired
    AuthorizationChangeMessageService authzChangeMessageService;

    @Autowired
    AuthManaService authService;
    
    @Override
    public ResponseDTO<Integer> insert(AuthDetailsDto authObj) {
        logger.info("[新增角色权限]入参: authObj={}", authObj);
        ResponseDTO<Integer> response = null;
        try {
            response = authService.insert(authObj);
            if (response != null && response.getStatus() == ResponseStatus.SUCCESS.getCode()) {
                authzChangeMessageService.sendMessage(authObj);
            }
            logger.info("[新增角色权限]成功.");
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[新增角色权限]异常: {}", ex);
        }
        return response;
    }

    @Override
    public ResponseDTO<Integer> update(AuthDetailsDto authObj) {
        logger.info("[更新角色权限]入参: authObj={},roleId={}",authObj, authObj.getrId());
        ResponseDTO<Integer> response = null;
        try {
            response = authService.update(authObj);
            
            /* 发送角色授权变更消息 */
            authzChangeMessageService.sendMessage(authObj.getrId());
            
            if (response != null && response.getStatus() == ResponseStatus.SUCCESS.getCode()) {
                List<Long> userIdList = authService.selectUserIdsByRoleId(authObj.getrId());
                authzChangeMessageService.sendForUserIdList(userIdList);
            }
            logger.info("[更新角色权限]成功.");
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[更新角色权限]异常: {}", ex);
        }
        return response;
    }

    @Override
    public ResponseDTO<Integer> deleteByRoleId(Long roleId) {
        logger.info("[根据角色ID删除角色]入参: roleId={}", roleId);
        ResponseDTO<Integer> response = null;
        try {
            response = authService.deleteByRoleId(roleId);
            if (response != null && response.getStatus() == ResponseStatus.SUCCESS.getCode()) {
                authzChangeMessageService.sendMessage(roleId);
            }
            logger.info("[根据角色ID删除角色]成功.");
        } catch (Exception ex) {
            response = new ResponseDTO<Integer>();
            response.setMessage(ResponseStatus.FAIL.getMsg() + ":" + ex.getMessage());
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setData(0);
            logger.error("[根据角色ID删除角色]异常: {}", ex);
        }
        return response;
    }

    @Override
    public PageResponseDTO<AuthDto> selectAllAuthority(int pageNum, int pageSize) {
        logger.info("[查询角色权限列表]入参: pageNum={},pageSize={}", pageNum, pageSize);
        PageResponseDTO<AuthDto> response = null;
        try {
            response = authService.selectAllAuthority(pageNum, pageSize);
            logger.info("[查询角色权限列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询角色权限列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public AuthInfoDto selectAuthorityByRId(Long rId) {
        logger.info("[查询角色权限列表]入参: rId={}", rId);
        AuthInfoDto response = null;
        try {
            response = authService.selectAuthority(rId);
            logger.info("[查询角色权限列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询角色权限列表]异常: {}", ex);
        }
        return response;
    }
    
    @Override
    public List<Long> selectUserIdsByRoleId(Long roleId) {
        logger.info("[根据角色ID查询用户ID列表]入参: roleId={}", roleId);
        List<Long> response = null;
        try {
            response = authService.selectUserIdsByRoleId(roleId);
            logger.info("[根据角色ID查询用户ID列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据角色ID查询用户ID列表]异常: {}", ex);
        }
        return response;
    }

	@Override
	public ResponseDTO<Long> webVisitValidate(String url) {
		logger.info("[校验用户访问是否允许]入参: url={}", url);
		ResponseDTO<Long> response = null;
        try {
        	response = authService.webVisitValidate(url);
            logger.info("[校验用户访问是否允许]成功.");
        } catch (Exception ex) {
            logger.error("[校验用户访问是否允许]异常: {}", ex);
        }
        return response;
	}

}
