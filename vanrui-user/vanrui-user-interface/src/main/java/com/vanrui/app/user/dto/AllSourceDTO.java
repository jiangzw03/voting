/*
 * @(#)AllSourceDTO.java 2016年10月23日
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
 * @date 2016年10月23日 上午9:43:37
 * @version V1.0.0
 * description：
 * 
 */
public class AllSourceDTO extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** web资源   **/
    private   List<WebAllSourceDTO>   webSource;
    /** app资源   **/
    private   List<SourceDto>    appSource;
    
    
    public List<SourceDto> getAppSource() {
        return appSource;
    }
    public void setAppSource(List<SourceDto> appSource) {
        this.appSource = appSource;
    }
    public List<WebAllSourceDTO> getWebSource() {
        return webSource;
    }
    public void setWebSource(List<WebAllSourceDTO> webSource) {
        this.webSource = webSource;
    }
     
}
