/*
 * @(#)Snippet.java 2016年10月27日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.List;

public class SourceBaseDTO extends SourceDto{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** 菜单资源图片编码 **/
    private  String  sCode; 
    /** 菜单资源URL **/
    private  String  sUrl;
    /** 儿子节点 **/
    private   List<SourceBaseDTO>    sonDTOList;
    
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
    public List<SourceBaseDTO> getSonDTOList() {
        return sonDTOList;
    }
    public void setSonDTOList(List<SourceBaseDTO> sonDTOList) {
        this.sonDTOList = sonDTOList;
    } 
}

 
