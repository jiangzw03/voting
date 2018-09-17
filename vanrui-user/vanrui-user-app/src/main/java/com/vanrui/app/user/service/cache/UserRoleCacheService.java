/*
 * @(#)UserCacheService.java 2016年10月17日
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

import com.vanrui.app.user.dao.RoleDao;
import com.vanrui.app.user.dao.UserRoleDao;
import com.vanrui.app.user.model.RoleEntity; 
import com.vanrui.app.user.model.UserRoleAuthEntity; 
import com.vanrui.app.user.util.Constants;
import com.vanrui.app.user.util.RedisCacheTools;

/**
 *
 * @author maji01
 * @date 2016年10月17日 下午1:03:41
 * @version V1.0.0
 * description：
 * 
 */
@Service("userRoleCacheService") 
public class UserRoleCacheService extends BaseService {
 
    @Autowired
    UserRoleDao   urDao;
    @Autowired
    RoleDao    roleDao;

    @Autowired
    ObjectRedisService<List<RoleEntity>>    objectRedisService;
   
    
    public  int  insertAuth(List<UserRoleAuthEntity> authList){
        
        urDao.insertAuth(authList);
        return 1;
    }
    /**
     * 根据用户ID查询 
     * 先查缓存，没找到再查数据库 
     */
    public   List<RoleEntity>    selectByUId( Long  uId ){
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ROLE );
        String hashKey = String.valueOf(uId);
        // 从缓存中取数据
        List<RoleEntity> list = objectRedisService.get(key, hashKey);
        if(list == null ||list.isEmpty() ){ 
            // 缓存中不存在，去数据库中查
            list = urDao.selectRolesByUId(uId); 
            if(list != null){
                // 加入缓存
                objectRedisService.put(key,hashKey, list);
            }else{
                objectRedisService.delete(key,hashKey);
            }
        } 
        return  list;
    }
    
    public   int   update(List<UserRoleAuthEntity> list,Long uId){
        
        if(list == null || uId == null ){
            return 0;
        }
     // 修改数据库
        urDao.deleteAuth(uId);
        urDao.insertAuth(list); 
     // 修改缓存
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ROLE );
        String hashKey = String.valueOf( uId );
        List<RoleEntity>  refs = urDao.selectRolesByUId(uId); 
        if(refs != null&&refs.size()>0){
            // 加入缓存
            objectRedisService.put(key,hashKey, refs);
        }else{
            objectRedisService.delete(key,hashKey);
        }
        return 1;
    }
     
    /**
     * 缓存角色列表
     */
    public   List<RoleEntity>   selectAllRoles(){ 
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_ROLE_ALL );
        // 从缓存中取数据
        List<RoleEntity> list = objectRedisService.get(key, key);
        if(list == null ){ 
            // 缓存中不存在，去数据库中查
            list = roleDao.selectAllRole();
            if(list != null&& list.size() > 0){
                // 加入缓存
                objectRedisService.put(key,key, list);
            }else{
                objectRedisService.delete(key,key);
            }
        } 
        return  list;
    }
    
    
    /**
     * 缓存角色列表
     */
    public   void  refreshAllRolesCache(){ 
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_ROLE_ALL );
        // 从缓存中取数据
        List<RoleEntity> list =  roleDao.selectAllRole();
        if(list == null || list.isEmpty() ){ 
            // 缓存中不存在，去数据库中查
            objectRedisService.delete(key,key);
        }else{
            objectRedisService.put(key,key, list);
        }
        // 刷新个人角色关系缓存
        String keyPerson = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ROLE );
        objectRedisService.delete( keyPerson );
    }
    
    
    /**
     * 刷新用户角色缓存
     * @param uId
     */
    public  void   refreshUserRoleCacheByUId( Long uId ){
        logger.info("开始刷新<用户角色缓存>[uId={}]",uId);
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ROLE );
        String hashKey = String.valueOf(uId); 
        List<RoleEntity>  refs = urDao.selectRolesByUId(uId); 
        if(refs != null&&refs.size()>0){
            // 加入缓存
            objectRedisService.put(key,hashKey, refs);
        }else{
            objectRedisService.delete(key,hashKey);
        }
        logger.info("成功刷新<用户角色缓存>[uId={}]",uId);
    }
}
