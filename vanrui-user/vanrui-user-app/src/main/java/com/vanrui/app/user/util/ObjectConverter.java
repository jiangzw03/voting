/*
 * @(#)StringUtil.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

import java.util.ArrayList;
import java.util.List;

import ooh.bravo.util.StringUtils;

import com.vanrui.app.user.dto.SourceDto;
import com.vanrui.app.user.dto.SourceInfoDto;
import com.vanrui.app.user.dto.SuggestDTO;
import com.vanrui.app.user.dto.UserBaseDto; 
import com.vanrui.app.user.dto.UserDetailDto;
import com.vanrui.app.user.dto.UserOrgRefDto;
import com.vanrui.app.user.dto.UserSkillDto;
import com.vanrui.app.user.dto.UserUpdateDto;
import com.vanrui.app.user.model.SourceEntity;
import com.vanrui.app.user.model.SuggestEntity;
import com.vanrui.app.user.model.UserEntity;
import com.vanrui.app.user.model.UserOrgRefEntity;
import com.vanrui.app.user.model.UserRoleAuthEntity;
import com.vanrui.app.user.model.UserSkillRefEntity; 

/**
 * 对象转换器
 * @author maji01
 * @date 2016年10月8日 下午5:34:16
 * @version V1.0.0
 * description：
 * 
 */
public class ObjectConverter {

    /**
     * 
     * @param entitys
     * @return
     */
    public static List<SourceDto>  transferSouceObjs(List<SourceEntity>  entitys){
        if( entitys == null || entitys.size() == 0 ){
            return null;
        }
        int len = entitys.size();
        List<SourceDto>  list = new ArrayList<SourceDto>();
        SourceEntity  temp = null;
        for(int i=0;i<len;i++){
            temp = entitys.get(i);
            if( temp.getsType() > 2 ){
                // 过滤掉页面请求资源
                continue;
            }
            SourceDto  dto = new  SourceDto();
            dto.setsId(temp.getsId());
            dto.setPsId(temp.getPsId());
            dto.setsName(temp.getsName());
            dto.setsType(temp.getsType());
            dto.setStatus(temp.getStatus());
            list.add(dto);
        }
        return list;
    } 
    
    
    /**
     * 
     * @param entitys
     * @return
     */
    public static List<SourceInfoDto>  transferSouceInfos(List<SourceEntity>  entitys){
        if( entitys == null || entitys.size() == 0 ){
            return null;
        }
        int len = entitys.size();
        List<SourceInfoDto>  list = new ArrayList<SourceInfoDto>();
        SourceEntity  temp = null;
        for(int i=0;i<len;i++){
            temp = entitys.get(i);
            SourceInfoDto  dto = new  SourceInfoDto();
            dto.setsId(temp.getsId());
            dto.setPsId(temp.getPsId());
            dto.setsName(temp.getsName());
            dto.setsType(temp.getsType());
            dto.setStatus(temp.getStatus());
            dto.setsCode(temp.getsCode());
            dto.setsOrder(temp.getsOrder());
            dto.setsTrace(temp.getsTrace());
            dto.setsUrl(temp.getsUrl()); 
            list.add(dto);
        }
        return list;
    }
    
    public static SuggestEntity transferSuggest(SuggestDTO suggest){
        if( suggest == null){
            return null;
        }
        
        SuggestEntity entity = new SuggestEntity();
        entity.setuId(suggest.getuId());
        entity.setContent(suggest.getContent());
        entity.setCreateTime(suggest.getCreateTime());
        return entity;
    }
    
    
    /**
     * 用户dto转换成用户实体类
     * @param entitys
     * @return
     */
    public static UserEntity  transferUserInfo(UserBaseDto user){
        if( user == null ){
            return  null;
        }
        if( StringUtils.isBlank( user.getUserName() ) ){
            user.setUserName(null);
        }
        UserEntity  entity  =  new  UserEntity();
        entity.setUserName(user.getUserName());
        entity.setAccount(user.getAccount());
        entity.setPassword(user.getPassword());
        entity.setAddress(user.getAddress());
        entity.setEmail(user.getEmail());
        entity.setEmployeeCode(user.getEmployeeCode());
        entity.setIdCard(user.getIdCard());
        entity.setMobilePhone(user.getMobilePhone());
        entity.setStatus(user.getStatus()); 
        return entity;
    }
    
    /**
     * 用户实体类转换成用户Basedto
     * @param entitys
     * @return
     */
    public static UserBaseDto  transferUserInfoBack(UserEntity user){
        if( user == null ){
            return  null;
        }
        
        UserBaseDto  entity  =  new  UserBaseDto();
        entity.setuId(user.getuId());
        entity.setUserName(user.getUserName());
        entity.setAccount(user.getAccount());
        entity.setPassword(user.getPassword());
        entity.setAddress(user.getAddress());
        entity.setEmail(user.getEmail());
        entity.setEmployeeCode(user.getEmployeeCode());
        entity.setIdCard(user.getIdCard());
        entity.setMobilePhone(user.getMobilePhone());
        entity.setStatus(user.getStatus());
        entity.setfSolution(user.getfSolution());
        
        return entity;
    }
    
    /**
     * 用户实体类转换成用户Basedto
     * @param entitys
     * @return
     */
    public static UserDetailDto  transferUserDetailInfo(UserEntity user){
        if( user == null ){
            return  null;
        } 
        UserDetailDto  entity  =  new  UserDetailDto();
        entity.setuId(user.getuId());
        entity.setUserName(user.getUserName());
        entity.setAccount(user.getAccount());
        user.setPassword(null);
        entity.setAddress(user.getAddress());
        entity.setEmail(user.getEmail());
        entity.setEmployeeCode(user.getEmployeeCode());
        entity.setIdCard(user.getIdCard());
        entity.setMobilePhone(user.getMobilePhone());
        entity.setStatus(user.getStatus());
        entity.setfSolution(user.getfSolution());
        entity.setCreateTime(user.getCreateTime());
        return entity;
    }
    
    /**
     * 用户实体类 转换成用户更新dto对象
     * @param entitys
     * @return
     */
    public static UserUpdateDto  transferToUpdateDto(UserEntity user){
        if( user == null ){
            return  null;
        } 
        UserUpdateDto  entity  =  new  UserUpdateDto();
        entity.setuId(user.getuId());
        entity.setAccount(user.getAccount());
        entity.setUserName(user.getUserName());
        entity.setPassword(user.getPassword());
        entity.setAddress(user.getAddress());
        entity.setEmail(user.getEmail());
        entity.setEmployeeCode(user.getEmployeeCode());
        entity.setIdCard(user.getIdCard());
        entity.setMobilePhone(user.getMobilePhone());
        entity.setStatus(user.getStatus());
        entity.setfSolution(user.getfSolution()); 
        entity.setCreateTime(user.getCreateTime());
        return entity;
    }
    
      
    /**
     * 用户dto转换成用户角色关系实体类
     * @param entitys
     * @return
     */
    public static List<UserRoleAuthEntity>  transferUserRoleRef( Long rId,Long uId){
        if( rId == null  || uId == null ){
            return  null;
        }
        List<UserRoleAuthEntity> authList =new ArrayList<UserRoleAuthEntity>();
        UserRoleAuthEntity  urEntity = new UserRoleAuthEntity();
        urEntity.setrId(rId);
        urEntity.setuId(uId);
        authList.add(urEntity);
        return authList;
    }
    
    /**
     * 技能列表 转换成用户技能关系实体类
     * @param entitys
     * @return
     */
    public static List<UserSkillRefEntity>  transferUserSkillRef( List<Long> skills ,Long uId){
        if(skills == null || uId == null ){
            return null;
        }
        int len = skills.size();
        List<UserSkillRefEntity> usRefs = new ArrayList<UserSkillRefEntity>(); 
        UserSkillRefEntity  uskill = null;
        for(int i=0;i<len;i++){
            uskill = new  UserSkillRefEntity();
            uskill.setSkillId(skills.get(i));
            uskill.setuId(uId);
            usRefs.add(uskill);
        }
        return usRefs;
    }
    
    /**
     * 技能列表 转换成用户技能关系实体类
     * @param entitys
     * @return
     */
    public static List<UserSkillDto>  transferUserSkillRefBack( List<UserSkillRefEntity> skills){
        if(skills == null ){
            return null;
        }
        int len = skills.size();
        List<UserSkillDto> usRefs = new ArrayList<UserSkillDto>(); 
        UserSkillDto  uskill = null;
        for(int i=0;i<len;i++){
            uskill = new  UserSkillDto();
            uskill.setSkillId(skills.get(i).getSkillId());
            uskill.setuId(skills.get(i).getuId());
            uskill.setSkillName(skills.get(i).getSkillName());
            usRefs.add(uskill);
        } 
        return usRefs;
    }
    
    
    /**
     * 技能列表 转换成用户技能关系实体类
     * @param entitys
     * @return
     */
    public static List<UserOrgRefEntity>  transferUserOrgRef( List<UserOrgRefDto> refjg,Long uId){
        if(refjg == null || uId == null ){
            return null;
        }
        int len = refjg.size();
        List<UserOrgRefEntity> uorgRefs = new ArrayList<UserOrgRefEntity>(); 
        UserOrgRefEntity  uOrg = null;
        for(int i=0;i<len;i++){
            uOrg = new  UserOrgRefEntity();
            uOrg.setOrId(refjg.get(i).getOrId());
            uOrg.setCombinationCode(refjg.get(i).getCombinationCode());
            uOrg.setOrName(refjg.get(i).getOrName());
            uOrg.setuId(uId);
            uorgRefs.add(uOrg);
        }
        return  uorgRefs;
    }
    
    /**
     * 用户技能关系实体类 转换成技能列表
     * @param entitys
     * @return
     */
    public static List<UserOrgRefDto>  transferUserOrgRefBack( List<UserOrgRefEntity> refjg){
        if(refjg == null  ){
            return null;
        }
        int len = refjg.size();
        List<UserOrgRefDto> uorgRefs = new ArrayList<UserOrgRefDto>(); 
        UserOrgRefDto  uOrg = null;
        for(int i=0;i<len;i++){
            uOrg = new  UserOrgRefDto();
            uOrg.setOrId(refjg.get(i).getOrId());
            uOrg.setCombinationCode(refjg.get(i).getCombinationCode());
            uOrg.setOrName(refjg.get(i).getOrName());
            uOrg.setuId(refjg.get(i).getuId());
            uorgRefs.add(uOrg);
        }
        return  uorgRefs;
    }
}
