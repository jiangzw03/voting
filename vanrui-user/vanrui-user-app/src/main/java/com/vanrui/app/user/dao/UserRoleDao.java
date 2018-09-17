/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.model.RoleEntity;
import com.vanrui.app.user.model.UserRoleAuthEntity;

/**
 *  待定
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface UserRoleDao {

     
    /**
     * 新增用户角色关系
     * @param userId
     */
    public   void   insertAuth(@Param("authList")List<UserRoleAuthEntity>  authList);

    /**
     * 删除用户角色关系，1-1关系
     * @param userId
     */
    public   void   deleteAuth(Long  uId);
    
    /**
     * 根据角色id查询是否存在有绑定的用户
     * @param roleId
     * @return
     */
    public   Integer   isExistBindUsersByRoleId(Long roleId);
    
    /**
     * 根据用户Id查询角色ID列表
     * @param uId
     * @return
     */ 
    public    List<RoleEntity>   selectRolesByUId(@Param("uId")Long  uId);
    
    /**
     * 根据角色ID查询用户ID列表
     * @param roleId
     * @return
     */
    List<Long> selectUserIdsByRoleId(@Param("roleId") Long roleId);
}
