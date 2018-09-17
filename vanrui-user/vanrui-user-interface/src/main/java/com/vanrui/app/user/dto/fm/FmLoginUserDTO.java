/*
 * @(#)FmLoginUserDTO.java 2017年6月20日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto.fm;

import com.vanrui.app.user.dto.LoginUserDTO;

/**
 * FM登录使用的实例
 * @author maji01
 * @date 2017年6月20日 上午8:50:09
 * @version V1.0.0
 * description：
 * 
 */
public class FmLoginUserDTO extends LoginUserDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 单点登录凭证，FMid,userId 通过RSA加密生成  **/
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
