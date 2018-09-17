/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;
 

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vanrui.app.user.dto.SkillDTO;

import ooh.bravo.core.dto.ResponseDTO;
 

  
/**
 * 用户机构关系表
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0
 * description：
 * 
 */
public interface SkillFacade {

    
    /**
     * 查询所有技能 
     * @return
     */
    public   List<SkillDTO>  selectAllSkills( ); 
    
    /**
     * 查询所有技能 
     * @return
     */
    public   Map<Long,SkillDTO>  selectSkillsBySkillIds( Set<Long>  skills ); 
    
    /**
     * 根据技能ID，查询技能详细信息
     * @param id
     * @return
     */
    public SkillDTO selectById(Long id);
    
    /***
     * 添加新技能
     * @param entity
     * @return
     */
    public ResponseDTO<Integer> addNewSkill(SkillDTO entity);
    
    /***
     * 更新技能名称
     * @param id
     * @param skillName
     * @return
     */
    public ResponseDTO<Integer> updateSkillName(Long id,String skillName);
    
    /***
     * 删除无用的技能
     * @param id
     * @return
     */
    public ResponseDTO<Integer> deleteById(Long id);
    
    /**
     * 校验该技能是否已经被占用，false：空闲，        true：已被占用
     * @param skillId
     * @return
     */
    public ResponseDTO<Boolean> validateSkillUsedBySkillId(Long skillId);
}
