/*
 * @(#)UserDetailDto.java 2016年10月10日
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
 * @date 2016年10月10日 下午3:39:17
 * @version V1.0.0
 * description：
 * 
 */
public class UserBaseDto extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private  Long    uId;
    /** 用户姓名,必填字段 **/
    private  String  userName;
    /** 用户账号 ,必填字段 **/
    private  String  account;
    /** 密码 ，不用于修改操作,必填字段  **/
    private  String  password;
    /** 员工编号  **/
    private  String  employeeCode;
    /** 电话号码  **/
    private  String  mobilePhone;
    /** 邮箱  **/
    private  String  email;
    /** 地址  **/
    private  String  address;
    /** 身份证  **/
    private  String  idCard  ;
    /** 是否被冻结   0:冻结，1:可用      ,必填字段 **/
    private  Integer   status;
    /** 冻结原因  **/
    private  String  fSolution;
    
    private String skillName;
    
    private  Date  createTime;
    
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    } 
    public String getfSolution() {
        return fSolution;
    }
    public void setfSolution(String fSolution) {
        this.fSolution = fSolution;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getSkillName() {
        return skillName;
    }
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    } 
    
}
