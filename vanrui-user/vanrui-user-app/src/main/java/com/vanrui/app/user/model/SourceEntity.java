/*
 * @(#)MenuDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import ooh.bravo.core.model.BaseObject;

/**
 * 资源实体类
 * @author maji01
 * @date 2016年10月8日 上午11:09:27
 * @version V1.0.0
 * description：
 * 
 */
public class SourceEntity  extends BaseObject{

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
    /** 菜单资源图片编码 **/
    private  String  sCode;
    /** 排序  **/
    private  int  sOrder;
    /** 菜单资源URL **/
    private  String  sUrl;
    /** 菜单资源状态 0:无权限 ，1:可用  **/
    private  int  status;
    /** 菜单资源关系路径  **/
    private  String  sTrace;
     
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
    public String getsCode() {
        return sCode;
    }
    public void setsCode(String sCode) {
        this.sCode = sCode;
    }
    public int getsOrder() {
        return sOrder;
    }
    public void setsOrder(int sOrder) {
        this.sOrder = sOrder;
    }
    public String getsUrl() {
        return sUrl;
    }
    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getsTrace() {
        return sTrace;
    }
    public void setsTrace(String sTrace) {
        this.sTrace = sTrace;
    }
     
}
