/*
 * @(#)UserCenterException.java 2016年10月14日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

 
/**
 * 
 * @author maji01
 * @date 2016年10月14日 上午8:53:07
 * @version V1.0.0
 * description：
 * 
 */
public enum UCException {

    ERROR_NULLPOINT_FIELD("user-error-001","非空校验失败"), 
    ERROR_COMPARE_PASSWORD_OLD("user-error-002","旧密码输入错误,修改密码失败"), 
    ERROR_COMPARE_PASSWORD_NEW("user-error-003","新密码不能与旧密码相同,修改密码失败"),
    ERROR_COMPARE_PASSWORD("user-error-004","密码错误,登录失败"),
    ERROR_COMPARE_ACCOUNT("user-error-005","账户不存在,登录失败"),
    ERROR_HAVED_BIND_ROLE("user-error-006","该角色下有关联成员，无法删除"),
    ERROR_ACCOUNT_FREEZE("user-error-007","账号已被冻结，如需开通请联系你的上级管理人员"),
    ERROR_SYSTEM_CONTEXT_NULL("user-error-008","上下文[userId=null],请登录后再试 "),
    
    ERROR_OBJECT_NULLPOINT("user-error-009","对象中必填项的非空校验，校验不通过"),
    ERROR_NULLPOINT_FREEZE("user-error-010","解冻/冻结对象，全是必填项，非空校验失败，操作失败"),
    ERROR_OPERATETYPE_FREEZE("user-error-011","解冻/冻结对象，类型异常,只能是 0冻结 ,1解冻"),
    ERROR_ID_IS_NOT_EXISTS("user-error-012","存在未知ID"),
    ERROR_ACCOUNT_EXCEPTION("user-error-013","用户账号异常，请联系管理员"),
    ERROR_REPEAT_EXCEPTION("user-error-014","已存在，请检查是否重复录入")
    ;
    private String   code;
    private String   message;
    
    UCException(String co,String  mess){
        this.code = co;
        this.message = mess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    } 
}
