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
public class UserInertDto extends UserBaseDto {

    private static final long serialVersionUID = 1L;
    
    /** 所属机构 ,必填字段**/
    private   List<UserOrgRefDto>     refjg; 

    /** 技能关系列表   **/
    private   List<Long>      Skills;
    
    /** 必填字段 **/
    private   Long       rId;
 
    /** 创建时间  **/
    private     Date  createTime;
    /** 创建人  **/
    private  Long  creator;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public List<UserOrgRefDto> getRefjg() {
        return refjg;
    }

    public void setRefjg(List<UserOrgRefDto> refjg) {
        this.refjg = refjg;
    }

    public List<Long> getSkills() {
        return Skills;
    }

    public void setSkills(List<Long> skills) {
        Skills = skills;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    } 
    
    
}
