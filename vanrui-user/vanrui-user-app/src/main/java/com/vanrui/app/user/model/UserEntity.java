/*
 * @(#)UserEntity.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import java.util.Date;

import ooh.bravo.core.model.BaseObject;

/**
 *  用户基本信息实体类
 * @author maji01
 * @date 2016年10月10日 上午11:56:19
 * @version V1.0.0
 * description：
 * 
 */
public class UserEntity  extends BaseObject{

    private static final long serialVersionUID = -6863872009494299577L;
    private  Long    uId;
    /** 用户名 **/
    private  String  userName;
    /** 用户账号  **/
    private  String  account;
    /** 员工编号  **/
    private  String  employeeCode;
    /** 密码  **/
    private  String  password;
    /** 电话号码  **/
    private  String  mobilePhone;
    /** 邮箱  **/
    private  String  email;
    /** 地址  **/
    private  String  address;
    /** 身份证  **/
    private  String  idCard  ;
    /** app端注册ID  **/
    private  String  registId ;
    /** 是否被冻结   0:冻结，1:可用 **/
    private  Integer   status;
    /** 冻结原因  **/
    private  String  fSolution;
    /** 创建时间  **/
    private  Date  createTime;
    /** 创建人  **/
    private  Long  creator;
    /** 修改时间  **/
    private  Date  updateTime;
    /** 修改人  **/
    private  Long  updator; 
   
    
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
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public String getRegistId() {
        return registId;
    }
    public void setRegistId(String registId) {
        this.registId = registId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getCreator() {
        return creator;
    }
    public String getfSolution() {
        return fSolution;
    }
    public void setfSolution(String fSolution) {
        this.fSolution = fSolution;
    }
    public void setCreator(Long creator) {
        this.creator = creator;
    }
    public Date getUpdateTime() {
        return updateTime;
    } 
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Long getUpdator() {
        return updator;
    }
    public void setUpdator(Long updator) {
        this.updator = updator;
    }

}
