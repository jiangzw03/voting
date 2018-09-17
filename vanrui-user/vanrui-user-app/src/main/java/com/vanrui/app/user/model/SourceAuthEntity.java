/*
 * @(#)SourceAuthEntity.java 2016年10月13日
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
 * @date 2016年10月13日 下午9:06:56
 * @version V1.0.0
 * description：
 * 
 */
public class SourceAuthEntity  extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private   Long  sId;
    /** url **/
    private   String   sUrl;
    /** 角色列表 ，逗号隔开 **/
    private   String   rIds;
    public Long getsId() {
        return sId;
    }
    public void setsId(Long sId) {
        this.sId = sId;
    }
    public String getsUrl() {
        return sUrl;
    }
    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }
    public String getrIds() {
        return rIds;
    }
    public void setrIds(String rIds) {
        this.rIds = rIds;
    }
}
