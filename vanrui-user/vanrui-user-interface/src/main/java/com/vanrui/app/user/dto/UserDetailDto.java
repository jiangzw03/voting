/*
 * @(#)UserDetailDto.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;
 
import java.util.List;
 

/**
 *
 * @author maji01
 * @date 2016年10月10日 下午3:39:17
 * @version V1.0.0
 * description：
 * 
 */
public class UserDetailDto extends UserBaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

      
    /** 所属机构 **/
    private  List<UserOrgRefDto>  jgs; 
    /** 技能  **/
    private  String  skills;
    /** 角色名称 **/
    private  String   roleName;  
   
    public List<UserOrgRefDto> getJgs() {
        return jgs;
    }
    public void setJgs(List<UserOrgRefDto> jgs) {
        this.jgs = jgs;
    }
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    } 
    
}
