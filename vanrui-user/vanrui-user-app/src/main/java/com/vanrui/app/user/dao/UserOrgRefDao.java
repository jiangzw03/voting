/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.dto.UserOrgRefDto;
import com.vanrui.app.user.model.UserOrgRefEntity;

/**
 * 待定
 * 
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0 description：
 * 
 */
@Repository
public interface UserOrgRefDao {

    /**
     * 新增用户角色关系
     * 
     * @param userId
     */
    public void insert(@Param("uoRef") List<UserOrgRefEntity> uoRef);

    /**
     * 根据机构ID修改，机构组合编码
     */
    public void updateByOrgId(@Param("orgId") Long orgId, @Param("combineCode") String combineCode, @Param("orgName") String orgName);

    /**
     * 删除用户角色关系，1-1关系
     * 
     * @param userId
     */
    public void delete(Long uId);

    public List<UserOrgRefEntity> selectUserOrgRefsByUId(Long uId);

    /**
     * 关系包含：用户ID，用户姓名，角色ID，机构ID，组合编号
     * 
     * @param uIds
     * @return
     */
    public List<UserOrgRefDto> selectRefsByUIds(@Param("uIds") List<Long> uIds);

    /**
     * 根据用户ID，组合编号查询用户类型
     * 
     * @param uIds
     * @return
     */
    public List<Long> selectuIdList(@Param("uId") Long uId, @Param("combineCode") String combineCode);

    /**
     * 根据用户姓名，组合编号查询用户类型
     * 
     * @param userName
     *            支持全模糊
     * @param combineCode
     *            支持后模糊
     * @return
     */
    public List<Long> selectUserIdList(@Param("userName") String userName, @Param("combineCode") String combineCode);
    
    /**
     * 根据用户姓名，组合编号列表查询用户类型
     * 
     * @param userName
     *            支持全模糊
     * @param combineCode
     *            支持后模糊
     * @return
     */
    List<Long> selectUserIdListByCombineCodeList(@Param("userName") String userName, @Param("combineCodeList") List<String> combineCodeList);

    /**
     * 判断机构下是否存在用户
     * 
     * @param orgId
     * @return
     */
    public Integer hasUserUnderOrganization(@Param("orgId") Long orgId);

    /**
     * 根据机构查询用户列表，
     * 
     * @param combineCode
     *            必填，并且全匹配
     * @return
     */
    public List<Long> selectUserIds(@Param("combineCode") String combineCode);
    
    /**
     * 根据机构列表查询用户列表，
     * 
     * @param combineCode
     *            必填，并且全匹配
     * @return
     */
    public List<Long> selectUserIdsByCombineCodeList(@Param("combineCodeList") List<String> combineCodeList);
    
    /**
     * 根据机构查询用户列表，
     * 模糊查询
     * @param combineCode
     *            必填，并且全匹配
     * @return
     */
    public List<Long> selectLikeUserIds(@Param("combineCode") String combineCode);

    /**
     * 根据机构ID查询APP工单管理者列表
     */
    public Set<Long> selectWorkOrderManagers(Long orgId);
    
    /**
     * 查询管辖内，所属项目内的人员
     */
    public Integer selectTotalUser(@Param("orgCodes")String[] orgCodes);
    
    /**
     * 查询用户手机号码列表，不包含用户本人
     * @param uId
     * @param orgCodes
     * @return
     */
    public  List<String>  selectUserPhoneNums(@Param("uId")Long uId,@Param("orgCodes")String[] orgCodes);
}
