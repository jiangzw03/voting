/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import com.vanrui.app.user.dto.UserOrgRefDto;

/**
 * 用户机构关系表
 * 
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0 description：
 * 
 */
public interface UserOrgRefFacade {

    /**
     * 根据用户ID列表查询用户与机构的关系列表： 包含：用户ID，用户姓名，机构ID，机构组合编号
     * 
     * @param uIds
     * @return
     */
    public List<UserOrgRefDto> selectUserOrgRefListByUIds(List<Long> uIds);
    
    /**
     * 校验指定用户是否拥有该地区有权限
     * @param userId 用户ID
     * @param orgId 地区ID
     * @return 是否有权限
     */
    public boolean validateUserOrgRef(Long userId, Long orgId);

    /**
     * 查询用户与机构的ID列表：
     * 
     * @param uId
     *            不包含的用户ID
     * @param combineCode
     *            符合该条件的组合编号
     * @return
     */
    public List<Long> selectUserOrgRefList(Long uId, String combineCode);
    
    /**
     * 查询用户所属区域范围内的用户手机号码：
     * 
     * @param uId
     *            不包含的用户ID
     * @param combineCode
     *            符合该条件的组合编号
     * @return
     */
    public List<String> selectUserPhoneNumList(Long uId,String[] combineCode);
    
    /**
     * 根据用户用户姓名与机构组合编码，查询用户列表
     * 
     * @param userName
     *            可选参数
     * @param combineCode
     *            必填参数
     * @return 返回null:发生异常，返回 [ ]没有符合条件的数据
     */
    public List<Long> selectUserIdList(String userName, String combineCode);
    
    /**
     * 根据用户用户姓名与机构组合编码列表，查询用户列表
     * 
     * @param userName
     *            可选参数
     * @param combineCodeList
     *            必填参数
     * @return 返回null:发生异常，返回 [ ]没有符合条件的数据
     */
    public List<Long> selectUserIdList(String userName, List<String> combineCodeList);

    /**
     * 根据机构组合编码，查询用户列表
     * 
     * @param combineCode
     *            必填参数 ,全匹配
     * @return 返回null:发生异常，返回 [ ]没有符合条件的数据
     */
    public List<Long> selectUserIds(String combineCode);
    
    /**
     * 根据多个机构组合编码，查询用户列表
     * 
     * @param combineCode
     *            必填参数 ,全匹配
     * @return 返回null:发生异常，返回 [ ]没有符合条件的数据
     */
    public List<Long> selectUserIds(List<String> combineCodeList);

    /**
     * 根据用户ID查询用户的机构关系对象列表 ，不包含用户名字
     * 
     * @param uId
     * @return
     */
    public List<UserOrgRefDto> selectUserOrgRefByUId(Long uId);

    /**
     * 根据用户ID查询用户的组织机构 组合编码 列表 , 如果参数为空，默认取当前上下文用户ID作为参数
     * 
     * @param uId
     * @return
     */
    public List<String> selectUserCombinationCodeList(Long uId);

    /**
     * 查询当前用户的机构关系对象列表 ，不包含用户名字
     * 
     * @return
     */
    public List<UserOrgRefDto> selectCurUserOrgRefByUId();

    /**
     * 判断机构是否存在用户
     * 
     * @return
     */
    public Boolean hasUserUnderOrganization(Long orgId);

    /***
     * 校验 是否在权限范围内
     * @param linkCode
     * @return 
     */
    public  boolean  validateUserOrg(String linkCode);

    /**
     * 查询人员总数
     * @param orgCodes
     * @return
     */
    public Integer getTotalUser(String[] orgCodes);
}
