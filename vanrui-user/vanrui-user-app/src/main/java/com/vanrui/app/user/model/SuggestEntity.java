/*
 * @(#)SuggestEntity.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import java.util.Date;

import ooh.bravo.core.model.BaseObject;

/**
 *
 * @author fanhuajun
 * @date 2016年10月18日 下午4:54:02
 * @version V1.0.0
 * description：
 * 
 */
public class SuggestEntity extends BaseObject {
    
    private static final long serialVersionUID = 1L;
    
    public Long suggestId; 
    /*用户ID*/
    public Long uId;
    
    /*意见反馈内容*/
    public String content;
    
    /*意见创建时间*/
    public Date createTime;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Long suggestId) {
        this.suggestId = suggestId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
