/*
 * @(#)AuthDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;
 

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午11:09:58
 * @version V1.0.0
 * description：
 * 
 */
public class AuthDto  extends RoleDto{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** web权限  资源之间用逗号隔开  **/
    private   String   authWeb;
    /** app权限  资源之间用逗号隔开  **/
    private   String   authApp;
    /** 用户数 **/
    private   int      num;
    
    public String getAuthWeb() {
        return authWeb;
    }
    public void setAuthWeb(String authWeb) {
        this.authWeb = authWeb;
    }
    public String getAuthApp() {
        return authApp;
    }
    public void setAuthApp(String authApp) {
        this.authApp = authApp;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    
}
