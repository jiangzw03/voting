/*
 * @(#)FreezeDto.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import java.util.Date;

import ooh.bravo.core.model.BaseObject;

/**
 *  冻结操作对象
 * @author maji01
 * @date 2016年10月10日 下午4:13:00
 * @version V1.0.0
 * description：
 * 
 */
public class FreezeEntity extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** 被操作的用户ID **/
    private  Long   fUId;
    /** 操作类型  **/
    private  int    fType;
    /** 操作原因 **/
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
    public int getfType() {
        return fType;
    }
    public void setfType(int fType) {
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
    
}
