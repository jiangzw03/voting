/*
 * @(#)Constants.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

/**
 *
 * @author maji01
 * @date 2016年10月10日 下午8:04:12
 * @version V1.0.0
 * description：
 * 
 */
public class Constants {

    /** 用户冻结   **/
    public  final   static   Integer  USER_FREEZE_YES = 0;

    /** 用户可用   **/
    public  final   static   Integer  USER_FREEZE_NO = 1;
    
    /***  缓存KEY的名称 *****/ 
    /** 用户基本信息Key   **/
    public  final   static   String  REDIS_KEY_USER_BASE_INFO = "BASEINFO"; 
    /** 用户与机构关系对象列表Key   **/
    public  final   static   String  REDIS_KEY_USER_REF_ORG = "REFORG";
    /** 用户与技能关系Key   **/
    public  final   static   String  REDIS_KEY_USER_REF_SKILL = "REFSKILL";
    /** 用户与角色关系Key   **/
    public  final   static   String  REDIS_KEY_USER_REF_ROLE = "REFROLE";
    /** 所有的角色Key   **/
    public  final   static   String  REDIS_KEY_ROLE_ALL = "ALLROLE";
    /** 角色与web资源关系Key   **/
    public  final   static   String  REDIS_KEY_ROLE_REF_WEB = "WEBROLEREF";
    /** 角色与app资源关系Key   **/
    public  final   static   String  REDIS_KEY_ROLE_REF_APP = "APPROLEREF";
    /** 所有的web资源Key   **/
    public  final   static   String  REDIS_KEY_WEBSOURCE_ALL = "ALLWEB";
    /** 所有的app资源Key   **/
    public  final   static   String  REDIS_KEY_APPSOURCE_ALL = "ALLAPP";
    /** 所有的技能Key   **/
    public  final   static   String  REDIS_KEY_SKILL_ALL = "ALLSKILL"; 
}
