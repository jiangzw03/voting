/*
 * @(#)UserOrgRefEntity.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import ooh.bravo.core.model.BaseObject;

/**
 * 用户组织机构编关系表实体类
 * @author maji01
 * @date 2016年10月10日 下午1:59:07
 * @version V1.0.0
 * description：
 * 
 */
public class UserOrgRefDto extends BaseObject{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** 组织机构ID,必填字段  **/
    private  Long  orId; 
    /** 用户ID,必填字段 **/
    private  Long  uId; 
    /** 机构组合编码,必填字段  **/
    private  String  combinationCode;
    /** 机构名称,必填字段  **/
    private  String  orName;
    /** 用户名称  **/
    private  String  uName;
    
    public Long getOrId() {
        return orId;
    }
    public void setOrId(Long orId) {
        this.orId = orId;
    }
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    }
    public String getCombinationCode() {
        return combinationCode;
    }
    public void setCombinationCode(String combinationCode) {
        this.combinationCode = combinationCode;
    }
    public String getOrName() {
        return orName;
    }
    public void setOrName(String orName) {
        this.orName = orName;
    }
    public String getuName() {
        return uName;
    }
    public void setuName(String uName) {
        this.uName = uName;
    } 
    
    
}
