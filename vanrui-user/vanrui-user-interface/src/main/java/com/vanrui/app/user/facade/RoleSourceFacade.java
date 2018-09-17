/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import com.vanrui.app.user.dto.AllSourceDTO;
import com.vanrui.app.user.dto.RoleDto;
import com.vanrui.app.user.dto.RoleSourceUrlDto;
import com.vanrui.app.user.dto.SourceBaseDTO;
import com.vanrui.app.user.dto.SourceInfoDto;

import ooh.bravo.core.dto.ResponseDTO;
  

/**
 * 角色资源关系服务 
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0
 * description：
 * 
 */
public interface RoleSourceFacade {

    /**
     * 查询角色ID与资源URL的全量数据
     */
    public  List<RoleSourceUrlDto>   selectAppAllRoleURLs();
    

    /**
     * 查询角色ID与资源URL的全量数据
     */
    public  List<RoleSourceUrlDto>   selectWebAllRoleURLs();
    
    /**
     * 根据角色ID查询app资源权限列表
     */
    public  List<SourceInfoDto>   selectAppSourcesByrId(Long rId);
    
    /**
     * 根据角色ID查询web资源权限列表
     */
    public  List<SourceInfoDto>   selectWebSourcesByrId(Long rId);
    
    /** status 0无权限，1有权限，
     * 当前 uId为空，或rId为空，或角色没有关联的资源，都会返回null
     * 根据查询当前用户的web资源权限列表，主要用于web端菜单栏
     */
    public  List<SourceBaseDTO>   selectWebSourcesBycurUId( );
    
    /**
     * 查询所有角色列表
     * @param rId
     * @return
     */
    public   List<RoleDto>    selectAllROle();
    
    /**
     * 查询所有资源，保护web资源，app资源 
     * @param rId
     * @return
     */
    public   AllSourceDTO    selectAllSource();
    
    /**
     * 根据某一项资源权限，查询拥有该权限的所有的角色
     * @param sId
     * @return
     */
    public List<Long> selectRolesByOneRight(Long sId);
    
    /**
     * 根据userId查询该用户的web权限
     * @param userId
     * @return
     */
    List<SourceInfoDto> selectWebSourcesByUid(Long userId);
    
    /**
     * 校验url是否有访问权限
     * @param userId
     * @param url
     * @return
     */
    ResponseDTO<Long> validateAppSourceAuthByuserId(String keyWord,String url);
}
