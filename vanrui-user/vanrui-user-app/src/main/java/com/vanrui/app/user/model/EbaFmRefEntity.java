/*
 * @(#)EbaFmRefEntity.java 2017年5月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import ooh.bravo.core.model.BaseObject;

/**
 *
 * @author maji01
 * @date 2017年5月17日 下午9:53:00
 * @version V1.0.0
 * description：
 * 
 */
public class EbaFmRefEntity extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** EBA用户ID **/
    private Long userId;
    /** FM用户ID **/
    private String fmUserId;
    /** FM用户姓名 **/
    private String fmUserName;
    /** FM用户手机号 **/
    private String fmUserPhone;
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    } 
    public String getFmUserName() {
        return fmUserName;
    }
    public void setFmUserName(String fmUserName) {
        this.fmUserName = fmUserName;
    }
    public String getFmUserPhone() {
        return fmUserPhone;
    }
    public void setFmUserPhone(String fmUserPhone) {
        this.fmUserPhone = fmUserPhone;
    }
    public String getFmUserId() {
        return fmUserId;
    }
    public void setFmUserId(String fmUserId) {
        this.fmUserId = fmUserId;
    } 
}
