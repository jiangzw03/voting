/*
 * @(#)SuggestDTO.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ooh.bravo.core.model.BaseObject;

/**
 *
 * @author fanhuajun
 * @date 2016年10月18日 下午5:56:01
 * @version V1.0.0
 * description：
 * 
 */
public class SuggestDTO extends BaseObject {
    
    private static final long serialVersionUID = 1L;
    /*用户ID*/
    public Long uId;
    
    /*意见反馈内容*/
    @NotEmpty(message = "反馈内容不能为空")
    @Size(max = 2000, message = "提交内容最多2000个字符")
    public String content;
    
    /*意见创建时间*/
    public Date createTime;

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
