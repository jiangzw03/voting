/*
 * @(#)MenuFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;
 

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.dto.SkillDTO;
import com.vanrui.app.user.model.SkillDictionaryEntity;
 

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午10:52:20
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface SkillDao {
  
    /**
     * 查询所有技能
     * @param entity
     */
    public  List<SkillDictionaryEntity>  selectAllSkills( );
  
    /**
     * 查询技能信息
     * @param entity
     */
    public SkillDictionaryEntity  selectById( Long  skillId );
     
    public List<SkillDictionaryEntity> selectSkillsBySkillIds(@Param("idSet") Set<Long> idSet);
    
    /***
     * 新增技能
     * @param dto
     */
    public void insert(@Param("entity")SkillDictionaryEntity  entity);
    
    /***
     * 更新信号点名称
     * @param id
     * @param skillName
     */
    public void updateSkillName(@Param("id")Long id,@Param("skillName")String skillName);
    
    /**
     * 删除技能
     * @param id
     */
    public void delete(Long id);
}
