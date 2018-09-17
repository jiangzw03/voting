/*
 * @(#)RoleDto.java 2016年10月8日
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
 * @date 2016年10月8日 上午11:08:19
 * @version V1.0.0
 * description：
 * 
 */
public class RoleDto  extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 角色ID **/
    private   Long  rId;
    /** 角色名 **/
    private   String  rName;
    /** 创建时间  **/
    private   Date  createTime;
    /** 创建人  **/
    private   Long   creator;
    /** 修改时间 **/
    private   Date  updateTime;
    /** 修改人 **/
    private   Long   updator;
    
    public Long getrId() {
        return rId;
    }
    public void setrId(Long rId) {
        this.rId = rId;
    }
    public String getrName() {
        return rName;
    }
    public void setrName(String rName) {
        this.rName = rName;
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
