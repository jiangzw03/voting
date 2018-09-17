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
public interface RoleWebmenuDao {

    /**
     * 新增web端菜单权限
     * @param userId
     */
    public   void   insertAuth(@Param("authList")List<RoleMenuAuthEntity>  authList);

    /**
     * 删除web端菜单权限
     * @param userId
     */
    public   void   deleteAuth(Long  roleId);
    
    

     
}
