/*
 * @(#)UserListCondition.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import org.hibernate.validator.constraints.NotEmpty;

import ooh.bravo.core.model.BaseObject;

/**
 * 用户列表查询条件
 * 
 * @author maji01
 * @date 2016年10月10日 下午4:42:02
 * @version V1.0.0 description：
 * 
 */
public class UserListCondition extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 机构-组合编号 **/
    @NotEmpty(message = "所属地区不能为空")
    private String combineCode;

    /**
     * 组织机构ID
     */
    private Long orgId;

    /** 角色ID **/
    private Long roleId;
    /** 技能ID **/
    private Long skillId;

    // 订单ID
    private Long orderId;

    /** 账号状态，0:冻结，1:可用 **/
    private Integer status;
    /** 人员姓名，支持模糊匹配 **/
    private String userName;
    /** 排序 ：1按创建时间降序，2按创建时间升序，3按名字升序，4按名字降序 **/
    private int sort;

    /* 派单类型 2:转单 3:派单 */
    private Integer assignType;

    /* 权限控制 2：维修 3：维保 */
    private Integer privilege;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getAssignType() {
        return assignType;
    }

    public void setAssignType(Integer assignType) {
        this.assignType = assignType;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

}
