/*
 * @(#)FreezeDto.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ooh.bravo.core.model.BaseObject;

/**
 *  冻结操作对象
 * @author maji01
 * @date 2016年10月10日 下午4:13:00
 * @version V1.0.0
 * description：
 * 
 */
public class FreezeDto extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private   Long  id;
    
    /** 被操作的用户ID **/
    @NotNull(message = "用户ID不能为空")
    private  Long   fUId;
    
    /** 操作类型 : 0冻结，1解冻 **/
    @NotNull(message = "操作类型不能为空")
    @Max(value = 1, message = "操作类型错误")
    @Min(value = 0, message = "操作类型错误")
    private  Integer    fType;
    
    /** 操作原因 **/
    @NotEmpty(message = "原因不能为空")
    @Size(max = 100, message = "内容最大长度为100个字符")
    private  String  solution;
    /** 操作人 ID **/
    private  Long    operator;
    /** 操作时间  **/
    private  Date    operateTime;
    
    public Long getfUId() {
        return fUId;
    }
    public void setfUId(Long fUId) {
        this.fUId = fUId;
    }
    public Integer getfType() {
        return fType;
    }
    public void setfType(Integer fType) {
        this.fType = fType;
    }
    public String getSolution() {
        return solution;
    }
    public void setSolution(String solution) {
        this.solution = solution;
    }
    public Long getOperator() {
        return operator;
    }
    public void setOperator(Long operator) {
        this.operator = operator;
    }
    public Date getOperateTime() {
        return operateTime;
    }
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
}
