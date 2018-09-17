/*
 * @(#)OperateDTO.java 2016年10月24日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.Date;

import ooh.bravo.core.model.BaseObject;
import ooh.bravo.core.model.MessageObject;

/**
 *
 * @author maji01
 * @date 2016年10月24日 下午2:02:58
 * @version V1.0.0
 * description：
 * 
 */
public class WorkRecordMsg extends MessageObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private   Long   id;
    /** 操作类型  
     * {@link com.vanrui.app.man.constant.WorkRecordType } 
     * **/
    private   String  workType;
    /** 工单名称/知会内容/项目名称/停止接单的离职原因等/null  **/
    private   String  content; 
    /** 工单类型日志时，保存对应工单的ID ，否则为空即可 **/
    private   Long    refId;
    /** 记录操作时间，新增时无效，内部会以当前时间重新设置，主要用于查询使用   **/
    private   Date   workTime;
    /** 用户ID，用于查询使用，新增中无效   **/
    private   Long   uId;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    } 
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }   
    public String getWorkType() {
        return workType;
    }
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    public Long getRefId() {
        return refId;
    }
    public void setRefId(Long refId) {
        this.refId = refId;
    }
    public Date getWorkTime() {
        return workTime;
    }
    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    } 

}
