/*
 * @(#)WebAllSourceDTO.java 2016年10月28日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.List;

import ooh.bravo.core.model.BaseObject;

/**
 *
 * @author maji01
 * @date 2016年10月28日 下午7:39:26
 * @version V1.0.0
 * description：
 * 
 */
public class WebAllSourceDTO extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private  String  sourceName;
    /** 子级问资源  **/
    private  List<SourceDto>   sonSource;
    
    public String getSourceName() {
        return sourceName;
    }
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
    public List<SourceDto> getSonSource() {
        return sonSource;
    }
    public void setSonSource(List<SourceDto> sonSource) {
        this.sonSource = sonSource;
    }
}
