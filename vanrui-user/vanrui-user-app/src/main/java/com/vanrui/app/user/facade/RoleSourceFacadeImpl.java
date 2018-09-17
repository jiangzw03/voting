/*
 * @(#)RoleSourceFacadeImpl.java 2016年10月9日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.AllSourceDTO;
import com.vanrui.app.user.dto.RoleDto;
import com.vanrui.app.user.dto.RoleSourceUrlDto;
import com.vanrui.app.user.dto.SourceBaseDTO;
import com.vanrui.app.user.dto.SourceInfoDto;
import com.vanrui.app.user.service.RoleSourceService;

import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;

/**
 *
 * @author maji01
 * @date 2016年10月9日 下午8:39:07
 * @version V1.0.0
 * description：
 * 
 */
@Service("roleSourceFacade")
public class RoleSourceFacadeImpl extends BaseService implements RoleSourceFacade {

    @Autowired
    RoleSourceService  rsService;

    @Override
    public List<RoleSourceUrlDto> selectAppAllRoleURLs() {
        logger.info("[查询角色与app-url关系列表] 开始");
        List<RoleSourceUrlDto>  response = null;
        try {
            response =  rsService.selectAppAllRoleURLs();  
            logger.info("[查询角色与app-url关系列表]成功.");
        } catch (Exception ex) { 
            logger.error("[查询角色与app-url关系列表]异常: {}", ex);
        }
        return  response ;
    }

    @Override
    public List<RoleSourceUrlDto> selectWebAllRoleURLs() {
        logger.info("[查询角色与web-url关系列表] 开始");
        List<RoleSourceUrlDto>  response = null;
        try {
            response =  rsService.selectWebAllRoleURLs();
            logger.info("[查询角色与web-url关系列表]成功.");
        } catch (Exception ex) { 
            logger.error("[查询角色与web-url关系列表]异常: {}", ex);
        }
        return  response ; 
    }

    @Override
    public List<SourceInfoDto> selectAppSourcesByrId(Long rId) {
        logger.info("[根据角色ID查询app资源列表] : rId={}",rId);
        List<SourceInfoDto>  response = null;
        try {
            response = rsService.selectAppSourcesByrId(rId);
            logger.info("[根据角色ID查询app资源列表]成功.");
        } catch (Exception ex) { 
            logger.error("[根据角色ID查询app资源列表]异常: {}", ex);
        }
        return  response ; 
    }

    @Override
    public List<SourceInfoDto> selectWebSourcesByrId(Long rId) {
        logger.info("[根据角色ID查询web资源列表] : rId={}",rId);
        List<SourceInfoDto>  response = null;
        try {
            response = rsService.selectWebSourcesByrId(rId);
            logger.info("[根据角色ID查询web资源列表]成功.");
        } catch (Exception ex) { 
            logger.error("[根据角色ID查询web资源列表]异常: {}", ex);
        }
        return  response ;  
    }

    @Override
    public List<RoleDto> selectAllROle() {
        logger.info("[查询所有角色] 开始");
        List<RoleDto>  response = null;
        try {
            response = rsService.selectAllROle();
            logger.info("[查询所有角色]成功.");
        } catch (Exception ex) { 
            logger.error("[查询所有角色]异常: {}", ex);
        }
        return  response ;  
    }

    @Override
    public AllSourceDTO selectAllSource() {
        logger.info("[查询所有资源] 开始");
        AllSourceDTO  response = null;
        try {
            response = rsService.selectAllSource();
            logger.info("[查询所有资源]成功.");
        } catch (Exception ex) { 
            logger.error("[查询所有资源]异常: {}", ex);
        }
        return  response ;   
    }

    @Override
    public List<SourceBaseDTO> selectWebSourcesBycurUId() {
        logger.info("[根据当前用户ID查询web菜单资源] 开始");
        List<SourceBaseDTO>   response = null;
        try {
            response =  rsService.selectWebSourcesBycurUId();
            logger.info("[根据当前用户ID查询web菜单资源]成功.");
        } catch (Exception ex) {
            logger.error("[根据当前用户ID查询web菜单资源]异常: {}", ex);
        }
        return  response;  
    }

     @Override
    public List<Long> selectRolesByOneRight(Long sId) {
       
         logger.info("[根据菜单资源，查询拥有该资源的所有角色ID] 开始 sId = {}", sId);
         List<Long>   response = null;
         try {
             response =  rsService.selectRolesByOneRight(sId);
             logger.info("[根据菜单资源，查询拥有该资源的所有角色ID]成功.");
         } catch (Exception ex) {
             logger.error("[根据菜单资源，查询拥有该资源的所有角色ID]异常: {}", ex);
         }
         return  response;  
    }

    @Override
    public List<SourceInfoDto> selectWebSourcesByUid(Long userId) {
        logger.info("[根据userId查询其web权限列表]:userId={}",userId);
        List<SourceInfoDto> response = null;
        try {
            response = rsService.selectWebSourcesByUid(userId);
            logger.info("[根据userId查询其web权限列表]成功.");
        } catch (Exception ex) { 
            logger.error("[根据userId查询其web权限列表]异常.", ex);
        }
        return  response ;  
    }

    @Override
    public ResponseDTO<Long> validateAppSourceAuthByuserId(String keyWord,String url) {
        logger.info("[根据keyWord查询其web权限列表]:keyWord={}",keyWord);
        ResponseDTO<Long> response = new ResponseDTO<Long>();
        try {
            response = rsService.validateAppSourceAuthByuserId(keyWord,url);
            logger.info("[根据keyWord查询其web权限列表]成功.");
        } catch (Exception ex) { 
            logger.error("[根据keyWord查询其web权限列表]异常.", ex);
        }
        return  response ;
    }
}
