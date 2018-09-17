/*
 * @(#)UserInfoDto.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author maji01
 * @date 2016年10月10日 下午4:01:31
 * @version V1.0.0
 * description：
 * 
 */
public class UserUpdateDto extends UserBaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** 所属机构 **/
    private List<UserOrgRefDto>     refjg; 

    /** 技能关系列表   **/
    private List<UserSkillDto>      Skills;
    
    private RoleDto      role;
    /** 关联FM的手机号码 **/
    private String relateFmMobile; 

    /** 修改时间  **/
    private Date  updateTime;
    /** 修改人  **/
    private Long  updator;
 
    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public List<UserOrgRefDto> getRefjg() {
        return refjg;
    }

    public void setRefjg(List<UserOrgRefDto> refjg) {
        this.refjg = refjg;
    }

    public List<UserSkillDto> getSkills() {
        return Skills;
    }

    public void setSkills(List<UserSkillDto> skills) {
        Skills = skills;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public String getRelateFmMobile() {
        return relateFmMobile;
    }

    public void setRelateFmMobile(String relateFmMobile) {
        this.relateFmMobile = relateFmMobile;
    } 
    
    
}
