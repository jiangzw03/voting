/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.dto.UserForOrderDTO;
import com.vanrui.app.user.model.UserForOrdersDTO;
import com.vanrui.app.user.model.UserSkillRefEntity;

/**
 *  待定
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface UserSkillRefDao {

     
    /**
     * 新增用户角色关系
     * @param userId
     */
    public   void   insert(@Param("usRef")List<UserSkillRefEntity>  usRef);

    /**
     * 删除用户角色关系 
     * @param userId
     */
    public   void   delete(Long  uId);
    
    /**
     * 查询用户与技能的关系
     * @param userId
     */
    public    List<UserSkillRefEntity>      selectSkillsByUId(Long  uId);
    
    /***
     * 检查集合中是否存在父子关系
     * @param skillIds
     * @return
     */
    public   String    isExistParentSonRef(@Param("pSkillIds")List<Long> pSkillIds,@Param("skillIds")List<Long> skillIds  );
    /**
     * 查询派单用户列表
     * @param skills
     * @param userName
     * @param orgId
     * @return
     */
    public List<UserForOrdersDTO> selectUserForOrder(@Param("bounds") RowBounds bounds, @Param("orgUserList") List<Long> orgUserList);
    
    /**
     * 根据技能ID查询用户ID列表
     * @param skillId
     * @return
     */
    public List<Long> selectUserIdsByCondition(@Param("combineCode") String combineCode, @Param("skillId") Long skillId,
    		@Param("userName") String userName, @Param("privilege") Integer privilege, @Param("projectType") Integer projectType);
    

    /**
     * 根据用户Id查询用户技能的子技能列表
     * @param userId
     * @return
     */
    public List<Long>  selectSubSkillListByUserId(@Param("userId")Long  userId);
    
    /**
     * 根据技能Id校验是否已经被用户使用
     * @param skillId
     * @return
     */
    public Integer validateSkillUsedById(@Param("skillId")Long  skillId);
}




