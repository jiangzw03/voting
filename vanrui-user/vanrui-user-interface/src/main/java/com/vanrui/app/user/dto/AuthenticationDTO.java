package com.vanrui.app.user.dto;

import ooh.bravo.core.model.BaseObject;

public class AuthenticationDTO extends BaseObject {
    private static final long serialVersionUID = -2551143224304910760L;

    private String username;// 登录用户名
    private String password;// 登录密码 
    /**
     * 1: web
     * 2: Android
     * 3: iphone
     * 4: Android、iphone
     */
    private Integer deviceType;//访问设备类型

    public AuthenticationDTO() {
    }

    public AuthenticationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

}
