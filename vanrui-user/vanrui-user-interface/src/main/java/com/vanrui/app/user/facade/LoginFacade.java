/*
 * @(#)UserLoginFacade.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import com.vanrui.app.user.dto.AuthenticationDTO;
import com.vanrui.app.user.dto.LoginUserDTO;
import com.vanrui.app.user.dto.fm.FmLoginUserDTO;

import ooh.bravo.core.dto.ResponseDTO; 

/**
 *
 * @author maji01
 * @date 2016年10月17日 上午11:43:13
 * @version V1.0.0
 * description：
 * 
 */
public interface LoginFacade {

    /***
     * 登录校验
     * @param authDTO
     * @return
     */
    public ResponseDTO<LoginUserDTO>  loginAuthenticate(AuthenticationDTO authDTO);
    
    /**
     * fm登录校验
     * @param keyWord
     * @return
     */
    public ResponseDTO<FmLoginUserDTO> fmLoginAuthenticate(String keyWord);
}
