package com.vanrui.app.user.dto;

import ooh.bravo.core.model.BaseObject;

public class LoginUserDTO extends BaseObject {
    private static final long serialVersionUID = -2824821125811454098L;

    private Long userID;// 用户ID
    private String tenantID;// 租户ID
    private String username;// 登录用户名
    private String mobilePhone;// 手机号码
    private String realName;// 真实姓名
    private String employeeCode;// 员工编号

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

}
