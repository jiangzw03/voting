/*
 * @(#)AuthDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import ooh.bravo.core.model.BaseObject;

/**
 * 用户角色关系表实体类
 * @author maji01
 * @date 2016年10月8日 上午11:09:58
 * @version V1.0.0
 * description：
 * 
 */
public class UserSkillDto  extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 用户ID **/
    private   Long   uId;
    /** 技能ID **/
    private   Long   skillId;
    /** 技能名称  **/
    private   String   skillName;
    
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    }
    public Long getSkillId() {
        return skillId;
    }
    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
    public String getSkillName() {
        return skillName;
    }
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    } 
    
}
