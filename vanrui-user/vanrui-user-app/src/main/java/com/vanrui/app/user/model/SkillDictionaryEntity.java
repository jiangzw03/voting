/*
 * @(#)KillDictionaryEntity.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import ooh.bravo.core.model.BaseObject;

/**
 *  技能字典表实体类
 * @author maji01
 * @date 2016年10月10日 下午1:50:11
 * @version V1.0.0
 * description：
 * 
 */
public class SkillDictionaryEntity extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** 技能id **/
    private  Long  skillId; 
    /** 技能父级id **/
    private  Long  pSkillId; 
    /** 技能名称 **/
    private  String  skillName; 
    /** 技能编码，用来标示层级关系000-000-000-000 **/
    private  String  skillCode; 
    /** 是否可用，0不可用，1可用 **/
    private  int     status;
    
    public Long getSkillId() {
        return skillId;
    }
    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
    public Long getpSkillId() {
        return pSkillId;
    }
    public void setpSkillId(Long pSkillId) {
        this.pSkillId = pSkillId;
    }
    public String getSkillName() {
        return skillName;
    }
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
    public String getSkillCode() {
        return skillCode;
    }
    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    } 

}
