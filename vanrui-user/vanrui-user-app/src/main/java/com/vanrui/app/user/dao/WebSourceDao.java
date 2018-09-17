/*
 * @(#)MenuFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vanrui.app.user.dto.RoleSourceUrlDto;
import com.vanrui.app.user.model.SourceAuthEntity;
import com.vanrui.app.user.model.SourceEntity;

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午10:52:20
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface WebSourceDao {

    /**
     * 根据角色ID 查询WEB菜单，展示菜单时使用， 查询有权限的web资源列表
     * @param rId 
     * @return
     */
    public  List<SourceEntity>    selectDetailListByrId(Long  rId);
 
    /**
     * 根据角色ID 查询WEB菜单，展示菜单时使用，
     * 注： 没有权限的资源 状态为0，有权限状态为1
     * @param rId 
     * @return
     */
    public  List<SourceEntity>    selectBaseListByrId(Long  rId);
    /**
     * 查询Web菜单资源,新增角色时使用
     */
    public  List<SourceEntity>     selectAll();
     
    /**
     * 根据角色ID查询Web菜单资源,给修改角色时使用
     * @param roleId
     * @return
     */
    public  List<SourceEntity>   selectAllByRoleId(Long roleId);
    
    /**
     * 查询Web资源url与rId的关系 
     */
    public  List<RoleSourceUrlDto>     selectAllrIdAndUrl();
    
    /**
     * 查询Web资源url与rId列表的关系 
     */
    public   List<SourceAuthEntity>   selectAllWebUrlAndAuths();
}
