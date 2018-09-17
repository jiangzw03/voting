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

import com.vanrui.app.user.dao.SkillDao;
import com.vanrui.app.user.dao.UserSkillRefDao; 
import com.vanrui.app.user.model.SkillDictionaryEntity;
import com.vanrui.app.user.model.UserSkillRefEntity;
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
@Service("userSkillCacheService") 
public class UserSkillCacheService extends BaseService {
 
    @Autowired
    UserSkillRefDao   usDao;
    @Autowired
    SkillDao    skillDao;

    @Autowired
    ObjectRedisService<List<UserSkillRefEntity>>    objectRedisService;
    

    @Autowired
    ObjectRedisService<List<SkillDictionaryEntity>>    skillRedisService;
    
    public  void    insert(List<UserSkillRefEntity> usRef){
        usDao.insert(usRef);
    }
    
    /**
     * 根据用户ID查询 
     * 先查缓存，没找到再查数据库 
     */
    public   List<UserSkillRefEntity>    selectByUId( Long  uId ){ 
        String key = RedisCacheTools.getUserKeyByType(Constants.REDIS_KEY_USER_REF_SKILL);
        String hashKey = String.valueOf(uId);
        // 从缓存中取数据
        List<UserSkillRefEntity> list = objectRedisService.get(key, hashKey);
        if(list == null ){ 
            // 缓存中不存在，去数据库中查
            list = usDao.selectSkillsByUId(uId); 
            if(list != null){
                // 加入缓存
                objectRedisService.put(key,hashKey, list);
            }else{
                objectRedisService.delete(key, hashKey);
            }
        } 
        return  list;
    }
    
    public   int   update(List<UserSkillRefEntity> list,Long uId){
        
        if( uId == null ){
            return 0;
        }
     // 修改数据库
        usDao.delete(uId);
        if(list !=null && list.size() >0){
            usDao.insert(list); 
        } 
     // 修改缓存
        String key =RedisCacheTools.getUserKeyByType(Constants.REDIS_KEY_USER_REF_SKILL);
        String hashKey = String.valueOf( uId );
        List<UserSkillRefEntity>  refs = usDao.selectSkillsByUId(uId); 
        if(refs != null){
         // 加入缓存
            objectRedisService.put(key,hashKey, refs);
        }else{
            objectRedisService.delete(key, hashKey);
        }
        return 1;
    }
     
 
    
    /**
     * 缓存技能列表
     */
    public   List<SkillDictionaryEntity>   selectAllSkills(){ 
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_SKILL_ALL );
     // 从缓存中取数据
        List<SkillDictionaryEntity> list = skillRedisService.get(key,key);
        if(list == null ){ 
            // 缓存中不存在，去数据库中查
            list = skillDao.selectAllSkills(); 
            if(list != null){
                // 加入缓存
                skillRedisService.put(key, key,list);
            }
        } 
        return list;
    }
 
    /**
     * 刷新用户技能关系缓存
     * @param uId
     */
    public  void   refreshUserRoleCacheByUId( Long uId ){
        logger.info("开始刷新<用户技能关系缓存>[uId={}]",uId);
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_SKILL_ALL );
        String hashKey = String.valueOf(uId); 
        List<UserSkillRefEntity>  refs = usDao.selectSkillsByUId(uId); 
        if(refs != null){
         // 加入缓存
            objectRedisService.put(key,hashKey, refs);
        }else{
            objectRedisService.delete(key, hashKey);
        }
        logger.info("成功刷新<用户技能关系缓存>[uId={}]",uId);
    }
    
    /**
     * 刷新所有技能关系缓存
     * @param uId
     */
    public  void   refreshAllSkillCache( ){
        logger.info("开始刷新<所有用户技能缓存>");
        String key = RedisCacheTools.getUserKeyByType( Constants.REDIS_KEY_USER_REF_SKILL );
        List<SkillDictionaryEntity> list = skillDao.selectAllSkills(); 
        if(list != null){
            // 加入缓存
            skillRedisService.put(key, key,list);
        }
        logger.info("成功刷新<所有用户技能缓存>");
    }
}
