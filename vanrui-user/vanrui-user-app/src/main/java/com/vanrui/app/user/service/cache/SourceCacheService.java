/*
 * @(#)SourceCacheService.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service.cache;

import java.util.List;
 
import ooh.bravo.redis.service.ObjectRedisService;
import ooh.bravo.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dao.AppSourceDao;
import com.vanrui.app.user.dao.RoleAppmenuDao;
import com.vanrui.app.user.dao.RoleWebmenuDao;
import com.vanrui.app.user.dao.WebSourceDao;
import com.vanrui.app.user.model.RoleMenuAuthEntity;
import com.vanrui.app.user.model.SourceEntity;
import com.vanrui.app.user.util.Constants;
import com.vanrui.app.user.util.RedisCacheTools;

/**
 *
 * @author maji01
 * @date 2016年10月17日 下午1:09:13
 * @version V1.0.0
 * description：
 * 
 */
@Service("sourceCache")
public class SourceCacheService extends BaseService  {

    @Autowired
    AppSourceDao   asDao;
    @Autowired
    WebSourceDao   wsDao;
     
    @Autowired
    RoleWebmenuDao    rwDao;
    @Autowired
    RoleAppmenuDao    raDao;
    
    @Autowired
    ObjectRedisService<List<SourceEntity>>    objectRedisService;
    /**
     * 根据角色ID查询，有关系的资源信息列表
     * 先查缓存，再查数据库
     * @param rId
     * @param isWebSource  true查web资源，false查app资源
     * @return
     */
     public  List<SourceEntity>  selectRoleSourceRefByRId(Long rId,boolean isWebSource){
        if(rId == null){
            return null;
        }
        List<SourceEntity>   source = null;
        String hashKey = String.valueOf(rId);
        String key = getRoleSourceRefKey(isWebSource); 
        // 查缓存
        source =  objectRedisService.get(key, hashKey);
        if(source == null ){
            // 查数据库
            source = queryRoleSourceRef(rId,isWebSource);
            if(source != null){
                // 写入缓存
                objectRedisService.putIfAbsent(key, hashKey, source);
            }
        } 
        return   source;
    }
     
     /**
      * 刷新角色权限的缓存
      * @param rId
      */
     public void refresh(Long rId){
         String hashKey = String.valueOf(rId);
         objectRedisService.delete(hashKey);
     }
     
     
    /***
     * 修改角色与资源之间的关系
     * @param rId
     * @param roleSource
     * @param isWebSource
     * @return
     */
  /*  public   int   updateRoleSource(Long rId,List<RoleMenuAuthEntity>  roleSource,boolean isWebSource){
        
        String hashKey = String.valueOf(rId);
        String key = getRoleSourceRefKey(isWebSource);  
        // 修改角色与资源之间的关系
        if( isWebSource ){ 
            rwDao.deleteAuth(rId);
            rwDao.insertAuth(roleSource);
        }else{ 
            raDao.deleteAuth(rId);
            raDao.insertAuth(roleSource);
        }
        // 更新缓存 
        List<SourceEntity>  source = queryRoleSourceRef(rId,isWebSource);
        if(source != null){
            // 写入缓存
            objectRedisService.delete(key, hashKey);
            objectRedisService.put(key, hashKey, source);
        }
        return 1;
    }*/
     
    
    /**  
     * 缓存所有web资源或app资源  
     * 
     */
    public   List<SourceEntity>   selectAllSource(boolean isWebSource){
        String key = getAllSourceRefKey(isWebSource); 
        List<SourceEntity>  sources =  objectRedisService.get(key, key);
        if( sources == null ){
            // 查数据库
            if(isWebSource){
                sources = wsDao.selectAll();
            }else{
                sources = asDao.selectAll();
            }
            // 加入缓存
            if(sources != null && sources.size() > 0){
                objectRedisService.put(key, key,sources);
            }
        }
        return  sources;
    }
      
     
    /**
     * 
     * @param isWebSource
     * @return
     */
    private   String      getRoleSourceRefKey(boolean isWebSource){
        if(isWebSource){
            return   RedisCacheTools.getUserKeyByType(Constants.REDIS_KEY_ROLE_REF_WEB);
//            return  "ROLE_"+SystemContextUtils.getTenantID()+"_WEBROLESOURCE"; 
        } 
//        return  "ROLE_"+SystemContextUtils.getTenantID()+"_APPROLESOURCE"; 
        return  RedisCacheTools.getUserKeyByType(Constants.REDIS_KEY_ROLE_REF_APP);
    }
    
    /**
     * 
     * @param isWebSource
     * @return
     */
    private   String      getAllSourceRefKey(boolean isWebSource){
        if(isWebSource){
            return  RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_WEBSOURCE_ALL );
//            return  "ROLE_"+SystemContextUtils.getTenantID()+"_WEBSOURCE"; 
        } 
        return  RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_APPSOURCE_ALL );
//        return  "ROLE_"+SystemContextUtils.getTenantID()+"_APPSOURCE"; 
    }
    
    /***
     * 根据角色ID查询（数据库）
     * @param rId
     * @param isWebSource
     * @return
     */
    private    List<SourceEntity>   queryRoleSourceRef(Long rId,boolean isWebSource){
        if(isWebSource){ 
            return  wsDao.selectDetailListByrId(rId);
        }
        return  asDao.selecDetailListByrId(rId);
    }
}
