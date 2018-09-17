/*
 * @(#)AuthDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.List;
 

/**
 * 新增或修改权限时的实体类
 * @author maji01
 * @date 2016年10月8日 上午11:09:58
 * @version V1.0.0
 * description：
 * 
 */
public class AuthDetailsDto  extends RoleDto{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
      
    /** web权限   **/
    private   List<Long>   authsWeb;
    /** app权限   **/
    private   List<Long>    authsApp;
    
    public List<Long> getAuthsWeb() {
        return authsWeb;
    }
    public void setAuthsWeb(List<Long> authsWeb) {
        this.authsWeb = authsWeb;
    }
    public List<Long> getAuthsApp() {
        return authsApp;
    }
    public void setAuthsApp(List<Long> authsApp) {
        this.authsApp = authsApp;
    } 
    
   
    
}
