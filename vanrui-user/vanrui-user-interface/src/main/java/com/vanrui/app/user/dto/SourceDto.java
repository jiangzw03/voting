/*
 * @(#)MenuDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import ooh.bravo.core.model.BaseObject;

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午11:09:27
 * @version V1.0.0
 * description：
 * 
 */
public class SourceDto  extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 菜单资源ID **/
    private  Long  sId;
    /** 菜单资源父级ID **/
    private  Long  psId;
    /** 菜单资源名称 **/
    private  String  sName;
    /** 菜单资源类型 **/
    private  int  sType;
    /** 0:无权限，1:有权限 **/
    private  int   status;
    
     
    public Long getsId() {
        return sId;
    }
    public void setsId(Long sId) {
        this.sId = sId;
    }
    public String getsName() {
        return sName;
    }
    public void setsName(String sName) {
        this.sName = sName;
    }
    public int getsType() {
        return sType;
    }
    public void setsType(int sType) {
        this.sType = sType;
    }
    public Long getPsId() {
        return psId;
    }
    public void setPsId(Long psId) {
        this.psId = psId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
     
}
