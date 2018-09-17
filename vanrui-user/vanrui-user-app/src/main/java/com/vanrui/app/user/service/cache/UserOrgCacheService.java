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

import com.vanrui.app.user.dao.UserOrgRefDao; 
import com.vanrui.app.user.model.UserOrgRefEntity;
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
@Service("userOrgCacheService") 
public class UserOrgCacheService extends BaseService {


    @Autowired
    UserOrgRefDao     uOrgRefDao;

    @Autowired
    ObjectRedisService<List<UserOrgRefEntity>>    objectRedisService;
    
    public  void   insert(List<UserOrgRefEntity> uoRef){
        uOrgRefDao.insert(uoRef);
    }
    
    /**
     * 根据用户ID查询用户详情
     * 先查缓存，没找到再查数据库 
     */
    public   List<UserOrgRefEntity>    selectByUId( Long  uId ){ 
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ORG );
        String hashKey = String.valueOf(uId);
        // 从缓存中取数据
        List<UserOrgRefEntity> list = objectRedisService.get(key, hashKey);
        if(list == null ){ 
            // 缓存中不存在，去数据库中查
            list = uOrgRefDao.selectUserOrgRefsByUId(uId); 
            if(list != null){
                // 加入缓存
                objectRedisService.put(key,hashKey, list);
            }else{
                objectRedisService.delete(key,hashKey);  
            }
        } 
        return  list;
    }
    
    public   int   update(List<UserOrgRefEntity> list,Long uId){
        
        if(list == null || uId == null ){
            return 0;
        }
     // 修改数据库
        uOrgRefDao.delete(uId);
        uOrgRefDao.insert(list); 
     // 修改缓存
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ORG );
        String hashKey = String.valueOf( uId );
        List<UserOrgRefEntity>  refs = uOrgRefDao.selectUserOrgRefsByUId(uId); 
        if(refs != null){
         // 加入缓存 
            objectRedisService.put(key,hashKey, refs);
        }else{
            objectRedisService.delete(key,hashKey);
        }
        return 1;
    }
     
    
    /**
     * 刷新用户组织机构缓存
     * @param uId
     */
    public  void   refreshUserOrgCacheByUId( Long uId ){
        logger.info("开始刷新<用户组织机构缓存>[uId={}]",uId);
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_ORG );
        String hashKey = String.valueOf(uId); 
        List<UserOrgRefEntity>  refs = uOrgRefDao.selectUserOrgRefsByUId(uId); 
        if(refs != null){
         // 加入缓存
            objectRedisService.put(key,hashKey, refs);
        }else{
            objectRedisService.delete(key,hashKey);
        }
        logger.info("成功刷新<用户组织机构缓存>[uId={}]",uId);
    }
    
    
    /**
     * 批量刷新缓存
     * @param uId
     */
    public  void   refreshBatchCacheByUIds( List<Long> uIds ){
        logger.info("开始批量刷新<用户组织机构缓存>[uIds={}]",uIds); 
        if( uIds != null&& uIds.size() > 0 ){ 
            for(Long uId:uIds){ 
                // 刷新缓存
                this.refreshUserOrgCacheByUId( uId ); 
            } 
        } 
        logger.info("批量刷新<用户组织机构缓存> 完成 ");
    }
}
