/*
 * @(#)LoginFacadeService.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.AuthenticationDTO;
import com.vanrui.app.user.dto.LoginUserDTO;
import com.vanrui.app.user.dto.fm.FmLoginUserDTO;
import com.vanrui.app.user.service.LoginService;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;

/**
 *
 * @author maji01
 * @date 2016年10月17日 上午11:47:35
 * @version V1.0.0
 * description：
 * 
 */
@Service("loginFacade")
public class LoginFacadeService extends BaseService implements LoginFacade {

    @Autowired
    LoginService  service;
    
    @Override
    public ResponseDTO<LoginUserDTO> loginAuthenticate(AuthenticationDTO authDTO) {
    	
        logger.info("[登录验证] 入参: authDTO={}", authDTO);
        ResponseDTO<LoginUserDTO>    response = null;
        try{ 
            response = service.loginAuthenticate(authDTO);
            logger.info("[登录验证结果] {}",response);
        }catch(Exception ex){  
             response = new   ResponseDTO<LoginUserDTO>();
             response.setStatus(ResponseStatus.FAIL.getCode());
             response.setMessage("系统异常，请联系你的上级管理人员");
             logger.error("[登录验证] 异常: {}", ex);
        }
        return response;
    }
     
    /**
     * 根据关键字登录
     * @param keyWord 
     * @param ts 时间戳
     * @param fmUserId
     * @return
     */
    public ResponseDTO<FmLoginUserDTO> fmLoginAuthenticate(String keyWord){ 
        logger.info("[登录验证] 入参: keyWord={}", keyWord);
        ResponseDTO<FmLoginUserDTO>    response = null;
        try{ 
            response = service.fmLoginAuthenticate(keyWord);
            logger.info("[登录验证结果] {}",response);
        }catch(Exception ex){  
             response = new   ResponseDTO<FmLoginUserDTO>();
             response.setStatus(ResponseStatus.FAIL.getCode());
             response.setMessage("系统异常，请联系你的上级管理人员");
             logger.error("[登录验证] 异常: {}", ex);
        }
        return response;
    }
} 
