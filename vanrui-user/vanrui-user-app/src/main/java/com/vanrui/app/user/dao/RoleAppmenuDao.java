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

import com.vanrui.app.user.model.RoleMenuAuthEntity;

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface RoleAppmenuDao {
 
    /**
     * 新增app端菜单权限
     * @param userId
     */
    public   void   insertAuth(@Param("authList")List<RoleMenuAuthEntity>  authList);

    /**
     * 删除app端菜单权限
     * @param userId
     */
    public   void   deleteAuth(Long  roleId);
 
    /**
     * 根据某一项资源权限，查询拥有该权限的所有的角色
     * @param sId
     * @return
     */
    List<Long> selectRolesByOneRight(Long sId);
}
