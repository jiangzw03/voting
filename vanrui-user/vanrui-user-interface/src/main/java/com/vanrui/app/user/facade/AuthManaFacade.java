/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

  
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;

import java.util.List;

import com.vanrui.app.user.dto.AuthDetailsDto;
import com.vanrui.app.user.dto.AuthDto;
import com.vanrui.app.user.dto.AuthInfoDto;

/**
 * 权限管理，侧重系统角色管理
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0
 * description：
 * 
 */
public interface AuthManaFacade {

    /**
     *  新增菜单权限 
     * @param authObj
     */
    public   ResponseDTO<Integer>   insert(AuthDetailsDto authObj);
 

    /**
     * 修改菜单权限
     * @param authObj
     */
    public   ResponseDTO<Integer>   update(AuthDetailsDto authObj);
    
    /**
     * 根据用户ID查询角色名称，和权限信息
     * @param userId
     */
    public   ResponseDTO<Integer>   deleteByRoleId(Long  roleId);
     
    /**
     * 根据用户ID查询角色名称，和权限信息  
     * @param pageNum     第几页（从第1开始计数）
     * @param pageSize    每页查询数量
     * @return
     */
    public   PageResponseDTO<AuthDto>   selectAllAuthority( int pageNum,int pageSize);
    
    /**
     * 查询web资源，app资源，若rId非空，还会查询角色拥有哪些资源
     * @param rId
     * @return
     */
    public   AuthInfoDto    selectAuthorityByRId( Long rId );
    
    /**
     * 
     * @param roleId
     * @return
     */
    List<Long> selectUserIdsByRoleId(Long roleId);
 
    /***
     * 校验web端访问权限
     * @param url
     * @return
     */
    public  ResponseDTO<Long> webVisitValidate(String url);
}
