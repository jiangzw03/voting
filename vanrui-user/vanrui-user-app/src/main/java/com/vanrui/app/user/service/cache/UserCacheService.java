/*
 * @(#)UserCacheService.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service.cache;
 
 
 
import ooh.bravo.redis.service.ObjectRedisService;
import ooh.bravo.service.BaseService; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dao.UserDao;
import com.vanrui.app.user.model.UserEntity; 
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
@Service("userCacheService") 
public class UserCacheService extends BaseService {

    @Autowired
    UserDao  uDao;

    @Autowired
    ObjectRedisService<UserEntity>    objectRedisService;
    
    /**
     * 根据用户ID查询用户详情
     * 先查缓存，没找到再查数据库 
     */
    public   UserEntity   selectDetailByUId(Long  uId){
        String key =   RedisCacheTools.getUserKeyByType(Constants.REDIS_KEY_USER_BASE_INFO);
        String hashKey = String.valueOf(uId);
        // 从缓存中取数据
        UserEntity entity = objectRedisService.get(key, hashKey);
        if(entity == null){  
            // 缓存中不存在，去数据库中查
            entity = uDao.queryInfoByUId(uId); 
            if(entity != null){
                // 加入缓存
                objectRedisService.put(key,hashKey, entity);
            }
        } 
        return  entity;
    }
    
    public   int   update(UserEntity  entity){
        
        if(entity == null || entity.getuId() == null ){
            return 0;
        }
     // 修改数据库
        uDao.update(entity);
     // 修改缓存
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_BASE_INFO ); 
        String hashKey = String.valueOf(entity.getuId());
        UserEntity  user = uDao.queryInfoByUId(entity.getuId());
        if(user != null){
         // 加入缓存 
            objectRedisService.put(key,hashKey, user);
        }else{
            objectRedisService.delete(key,hashKey);
        }
        return 1;
    }
    
   
     
    /**
     * 刷新<用户基本信息缓存>
     * @param uId
     */
    public  void   refreshUserInfoCacheByUId( Long uId ){
        logger.info("开始刷新<用户基本信息缓存>[uId={}]",uId);
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_BASE_INFO ); 
        String hashKey = String.valueOf(uId); 
        UserEntity  user = uDao.queryInfoByUId( uId );
        if(user != null){
         // 加入缓存 
            objectRedisService.put(key,hashKey, user);
        }else{
            objectRedisService.delete(key,hashKey);
        }
        logger.info("成功刷新<用户基本信息缓存>[uId={}]",uId);
    }
}
