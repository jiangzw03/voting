/*
 * @(#)UserCacheService.java 2016年10月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service.cache;

import ooh.bravo.service.BaseService;

import com.vanrui.app.user.model.RoleEntity;

/**
 *
 * @author maji01
 * @date 2016年10月17日 下午1:03:41
 * @version V1.0.0
 * description：
 * 
 */
public class AuthCacheService extends BaseService {

    /** 
     * 1、用户与机构关系
     * 2、用户与技能关系
     * 3、用户与角色关系
     */
    
    
    /**
     * 1、角色与app资源关系
     * 2、角色与web资源关系
     */
    
    public  RoleEntity  selectRoleSourceRefByRId(Long rId){
        
        return   null;
    }
}
