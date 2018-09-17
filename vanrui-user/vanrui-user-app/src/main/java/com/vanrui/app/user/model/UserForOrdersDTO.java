/*
 * @(#)UserForOrder.java 2016年12月12日
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
 * @date 2016年12月12日 上午9:40:25
 * @version V1.0.0
 * description：
 * 
 */
public class UserForOrdersDTO extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private  Long    uId;
    // 用户姓名,必填字段 
    
    private  String  userName;
    
    //用户类型
    private Integer userType;
    
    //组织机构Ids
    private String orgId;
    
    //城市名称，多个"、"分开 10个字符末尾加...
    private String cityNames;
    
    //片区名称，多个"、"分开 20个字符末尾加...
    private String areaNames;
    
    //项目名称，多个"、"分开 20个字符末尾加...
    private String projectNames;
    
    //技能名称，多个"、"分开20个字符末尾家...
    private String skillNames;
    
    //最近位置
    private String lastPosition;
   
    //待处理工单数
    private Integer waitingTotal;
    
    //接单状态
    private Integer acceptStatus;
    
    
    
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLastPosition() {
        return lastPosition;
    }
    public void setLastPosition(String lastPosition) {
        this.lastPosition = lastPosition;
    }
    public String getAreaNames() {
        return areaNames;
    }
    public String getCityNames() {
        return cityNames;
    }
    public void setCityNames(String cityNames) {
        this.cityNames = cityNames;
    }
    public String getProjectNames() {
        return projectNames;
    }
    public void setProjectNames(String projectNames) {
        this.projectNames = projectNames;
    }
    public String getSkillNames() {
        return skillNames;
    }
    public void setSkillNames(String skillNames) {
        this.skillNames = skillNames;
    }
    public void setAreaNames(String areaNames) {
        this.areaNames = areaNames;
    }
    public Integer getWaitingTotal() {
        return waitingTotal;
    }
    public void setWaitingTotal(Integer waitingTotal) {
        this.waitingTotal = waitingTotal;
    }
    public Integer getAcceptStatus() {
        return acceptStatus;
    }
    public void setAcceptStatus(Integer acceptStatus) {
        this.acceptStatus = acceptStatus;
    }
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
