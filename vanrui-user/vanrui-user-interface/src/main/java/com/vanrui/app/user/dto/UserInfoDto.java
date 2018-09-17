/*
 * @(#)UserInfoDto.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.Date;

import ooh.bravo.core.model.BaseObject;

/**
 * 
 * @author maji01
 * @date 2016年10月10日 下午4:01:31
 * @version V1.0.0
 * description：
 * 
 */
public class UserInfoDto extends BaseObject {

    /**
     *  
     */
    private static final long serialVersionUID = 1L;
    
    private  Long    uId;
    /** 角色id **/
    private  Long    roleId;
    /** 角色名称 **/
    private  String    roleName;
    /** 用户姓名 **/
    private  String  userName;
    /** 用户账号  **/
    private  String  account; 
    /** 员工编号  **/
    private  String  employeeCode;
    /** 电话号码  **/
    private  String  mobilePhone; 
    /** 是否被冻结   0:冻结，1:可用 **/
    private  int   status;
    /** 所属城市 **/
    private  String  jgs; 
    /** 技能  id:技能名称   eg  1000:供配电,3000:消防  **/
    private  String  skills;
    /** 创建时间  **/
    private     Date  createTime;
    
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    } 
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }  
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getJgs() {
        return jgs;
    }
    public void setJgs(String jgs) {
        this.jgs = jgs;
    }
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

      
}
