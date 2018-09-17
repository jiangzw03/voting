/*
 * @(#)RedisCacheKey.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

import ooh.bravo.context.util.SystemContextUtils;

/**
 *
 * @author maji01
 * @date 2016年10月17日 下午8:03:53
 * @version V1.0.0
 * description：
 * 
 */
public class RedisCacheKey {    
    
    /**
     * 与User相关的rediskey，
     * 包含{用户基本信息，用户与机构关系，用户与技术关系，用户与角色关系}
     * @param keyType
     * @return
     */
    public  static  String  getUserKeyByType(String  keyType){
        
        String key = "USER_"+SystemContextUtils.getTenantID()+"_" ;
        
        if(Constants.REDIS_KEY_USER_BASE_INFO.equals(keyType)){
            return   key+Constants.REDIS_KEY_USER_BASE_INFO;
        }else if(Constants.REDIS_KEY_USER_REF_ORG.equals(keyType)){
            return   key+Constants.REDIS_KEY_USER_REF_ORG;
        }else if(Constants.REDIS_KEY_USER_REF_SKILL.equals(keyType)){
            return   key+Constants.REDIS_KEY_USER_REF_SKILL;
        }else if(Constants.REDIS_KEY_USER_REF_ROLE.equals(keyType)){
            return   key+Constants.REDIS_KEY_USER_REF_ROLE;
        } 
        return null;
    }
    
    /**
     * 与ROLE相关的rediskey
     * 包含：{所有角色，角色与web资源关系，角色与app资源的关系}
     * @param keyType
     * @return
     */
    public  static  String  getRoleKeyByType(String  keyType){
        
        String key = "USER_"+SystemContextUtils.getTenantID()+"_" ;
        
        if(Constants.REDIS_KEY_ROLE_ALL.equals(keyType)){
            return   key+Constants.REDIS_KEY_ROLE_ALL;
        }else if(Constants.REDIS_KEY_ROLE_REF_WEB.equals(keyType)){
            return   key+Constants.REDIS_KEY_ROLE_REF_WEB;
        }else if(Constants.REDIS_KEY_ROLE_REF_APP.equals(keyType)){
            return   key+Constants.REDIS_KEY_ROLE_REF_APP;
        }
        return null; 
    }
    
    /**
     * 与AllSource相关的rediskey
     * 包含：{所有web资源，所有app资源，所有技能}
     * @param keyType
     * @return
     */
    public   static  String  getAllSourceKeyByType(String  keyType){
        
        String key = "USER_"+SystemContextUtils.getTenantID()+"_" ;
        
        if(Constants.REDIS_KEY_WEBSOURCE_ALL.equals(keyType)){
            return   key+Constants.REDIS_KEY_WEBSOURCE_ALL;
        }else if(Constants.REDIS_KEY_APPSOURCE_ALL.equals(keyType)){
            return   key+Constants.REDIS_KEY_APPSOURCE_ALL;
        }else if(Constants.REDIS_KEY_SKILL_ALL.equals(keyType)){
            return   key+Constants.REDIS_KEY_SKILL_ALL;
        }
        return null;
    }
    
}
