/*
 * @(#)UserDao.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.dto.UserInfoDto;
import com.vanrui.app.user.dto.UserListCondition;
import com.vanrui.app.user.model.UserEntity;

/**
 *
 * @author maji01
 * @date 2016年10月10日 下午7:50:30
 * @version V1.0.0 description：
 * 
 */
@Repository
public interface UserDao {

    /**
     * 新增用户
     * @param entity
     */
    public   void   insert( UserEntity   entity );

    /**
     * 修改用户信息 
     * @param entity
     */
    public   void   update( UserEntity   entity );

    /**
     * 验证旧密码是否正确
     * @param entity
     */
    public   void   updatePassword( Long  uId,String password );

    /**
     * 根据用户ID查询用户基本信息 ，不包含密码
     * @param uId
     */
    public   UserEntity   queryInfoByUId( Long  uId );
    
    /**
     * 根据用户ID查询用户基本信息 ，不包含密码
     * @param uId
     */
    public   Long   queryUIdByPhoneNum(@Param("phoneNum")String  phoneNum );

    /**
     * 根据账户名查询用户基本信息，包含密码
     * @param uId
     */
    public   UserEntity   queryInfoByAccountorMobilePhone( String  account );

    /**
     * 根据用户ID查询密码
     * @param uId
     */
    public   String   selectPassword( Long  uId );

    /**
     * 根据条件查询用户ID列表，筛选并分页
     * @param condition
     * @param bounds
     * @return
     */
    public   List<Long>   queryUidsByCondition(@Param("userId")Long userId,
                                                @Param("condition")UserListCondition condition,
                                                @Param("skills")List<Long> skills,
                                                @Param("bounds")RowBounds  bounds);

    /**
     * 根据用户ID列表查询用户列表，查询并按照创建时间排序
     * @param uIds
     * @param sort
     * @return
     */
    public List<UserInfoDto>   selectUsersByUids(@Param("uIds")List<Long> uIds,@Param("sort")Integer sort);

    /**
     * 根据用户ID列表查询用户基本信息列表，不包含角色，机构，技能，密码等信息 
     * @param uIds
     * @param sort
     * @return
     */
    public   List<UserInfoDto>   selectUserBaseInfoList(@Param("uIds")List<Long> uIds);

    /**
     * 校验手机号码是否已经被绑定 
     * @param phoneNum 
     * @return  未被绑定，就返回null，否则返回字符串'1'
     */
    public   String   isExistsForPhoneNum(@Param("phoneNum")String phoneNum,@Param("userId")Long userId);

    /**
     * 校验员工编号是否已经被使用 
     * @param phoneNum 
     * @return  未被绑定，就返回null，否则返回字符串'1'
     */
    public   String   isExistsForEmployeeCode(@Param("employeeCode")String  employeeCode,@Param("userId")Long userId);

    /**
     * 校验员工编号是否已经被使用 
     * @param account 
     * @return  未被绑定，就返回null，否则返回字符串'1'
     */
    public   String   isExistsForAccount(@Param("account")String  account);

    /**
     * 根据组织机构ID、技能ID 列表、角色ID列表筛选人员
     * @param orgId
     * @param skillIds
     * @param roleIds
     * @return
     */
    public List<UserEntity> selectAssignOrderUsers(@Param("orgId") Long orgId, 
            @Param("skillIds") List<Long> skillIds,
            @Param("roleIds") List<Long> roleIds ,
            @Param("userName") String userName,  
            @Param("bounds")RowBounds  bounds);

    /**
     * 获取所有用户ID列表
     */
    List<Long> selectAllUserIdList();

    public  Integer   validateIsManagers(@Param("userId") Long userId);

    public  Integer   hasWorkOrderRepairPermission(@Param("userId") Long userId);

    /**
     * 获取用户权限id列表
     */
	public List<Long> selectAllRIdList(Long userId);
}
