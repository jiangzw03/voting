/*
 * @(#)MenuDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;
 

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午11:09:27
 * @version V1.0.0
 * description：
 * 
 */
public class SourceInfoDto  extends SourceDto{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
     
    /** 菜单资源图片编码 **/
    private  String  sCode;
    /** 排序  **/
    private  int  sOrder;
    /** 菜单资源URL **/
    private  String  sUrl; 
    
    /** 菜单资源关系路径  **/
    private  String  sTrace;
     
    public String getsCode() {
        return sCode;
    }
    public void setsCode(String sCode) {
        this.sCode = sCode;
    }
    public String getsUrl() {
        return sUrl;
    }
    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    } 
    public String getsTrace() {
        return sTrace;
    }
    public void setsTrace(String sTrace) {
        this.sTrace = sTrace;
    }
    public int getsOrder() {
        return sOrder;
    }
    public void setsOrder(int sOrder) {
        this.sOrder = sOrder;
    } 
     
}
