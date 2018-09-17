/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vanrui.app.user.dto.FreezeDto;
import com.vanrui.app.user.dto.UserBaseDto;
import com.vanrui.app.user.dto.UserDetailDto;
import com.vanrui.app.user.dto.UserInertDto;
import com.vanrui.app.user.dto.UserInfoDto;
import com.vanrui.app.user.dto.UserListCondition;
import com.vanrui.app.user.dto.UserUpdateDto;

import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;

/**
 * 用户中心服务
 * 
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0 description：
 * 
 */
public interface UserCenterFacade {

    /**
     * 新增用户
     * 
     * @param uId
     * @return
     */
    public ResponseDTO<Integer> insert(UserInertDto user);

    /**
     * 修改用户信息
     * 
     * @param uId
     * @return
     */
    public ResponseDTO<Integer> update(UserUpdateDto user);

    /**
     * 修改密码
     * 
     * @param uId
     * @return
     */
    public ResponseDTO<Integer> updatePassword(Long uId, String newPassword, String oldPassword);

    /**
     * 冻结用户 0
     * 
     * @param uId
     * @return
     */
    public ResponseDTO<Integer> freeze(FreezeDto freeze);

    /**
     * 查询用户详情,修改中的查询使用
     * 
     * @param uId
     * @return
     */
    public UserDetailDto selectInDetailByUId(Long uId);

    /**
     * 查询用户详情，只展示用户基本信息，不会展示密码信息。
     * 
     * @param uId
     * @return
     */
    public UserUpdateDto selectInUpdateByUId(Long uId);

    /**
     * 根据条件查询用户列表，不包括自己
     * 
     * @param containMine
     *            true；包含自己，false 不包含自己
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResponseDTO<UserInfoDto> selectListByCondition(boolean containMine, UserListCondition condition, int pageNum, int pageSize);

    /**
     * 根据用户ID列表查询用户基本信息列表 ,其中不包括《机构信息，技能信息，角色信息》，其它信息，数据库中有就能查出来，
     * 
     * @param uIds
     * @return Map<UId,UserInfoDto>
     */
    public Map<Long, UserInfoDto> selectUserBaseInfoList(Set<Long> uIds);

    /**
     * 根据用户ID列表查询用户基本信息列表 ,其中不包括《机构信息，技能信息，角色信息》，其它信息，数据库中有就能查出来，
     * 
     * @param uId
     * @return
     */
    public UserInfoDto selectUserBaseInfo(Long uId);

    /**
     * 根据组织机构ID、技能ID 列表、角色ID列表筛选人员
     * 
     * @param orgId
     * @param skillIds
     * @param roleIds
     * @return
     */
    public List<UserBaseDto> selectAssignOrderUsers(Long orgId, List<Long> skillIds, List<Long> roleIds, String userName);

    /**
     * 根据机构ID查询工单管理者列表
     * 
     * @param orgId
     * @return
     */
    public Set<Long> selectWorkOrderManagers(Long orgId);

    /**
     * 获取所有用户ID列表
     * @return
     */
    public List<Long> selectAllUserIdList();

    /**
     * 用户ID是否具备工单管理者权限
     * 
     * @param userId
     * @return
     */
    public  boolean  validateIsManagers(Long userId);

    /**
     * 用户ID是否具备工单维修维保操作权限
     * 
     * @param userId
     * @return
     */
    public  Boolean  hasWorkOrderRepairPermission(Long userId);

    /**
     * 根据用户id查找权限id集合
     * @param userId
     * @return List<Long>
     */
    public List<Long> selectRIds(Long userId);
    
    /**
     * 根据用户id查找权限id集合
     * @param userId
     * @return List<Long>
     */
    public ResponseDTO<Long> selectUserIdByPhoneNum(String phoneNum);
    
    /**
     * 重置密码
     * @return
     */
    public ResponseDTO<Integer> resetPassword(Long ModifiedUserId);
    
    /**
     * 根据用户Id查询用户子技能Id列表
     * @param userId
     * @return
     */
    public List<Long> selectSubSkillListByUserId(Long userId);

}
