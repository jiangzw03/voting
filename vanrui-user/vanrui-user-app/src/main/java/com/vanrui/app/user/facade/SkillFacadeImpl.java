/*
 * @(#)SkillFacadeImpl.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.SkillDTO;
import com.vanrui.app.user.service.SkillService;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;

/**
 *
 * @author maji01
 * @date 2016年10月18日 下午6:16:08
 * @version V1.0.0
 * description：
 * 
 */
@Service("skillFacade")
public class SkillFacadeImpl extends BaseService implements SkillFacade {

    @Autowired
    SkillService   service;
    
    @Override
    public List<SkillDTO> selectAllSkills() {
        logger.info("[查询所有技能列表] 开始");
        List<SkillDTO>   response = null;
        try {
            response =   service.selectAllSkills();
            logger.info("[查询所有技能列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询所有技能列表]异常: {}", ex);
        }
        return  response;   
    }

    @Override
    public Map<Long,SkillDTO> selectSkillsBySkillIds(Set<Long> skills) {
        logger.info("[根据技能ID列表查询技能列表] 入参：skills = {}",skills);
        Map<Long,SkillDTO>   response = null;
        try {
            response =  service.selectSkillsBySkillIds(skills);
            logger.info("[根据技能ID列表查询技能列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据技能ID列表查询技能列表]异常: {}", ex);
        }
        return  response;   
    }

    @Override
    public SkillDTO selectById(Long id) {
        
        logger.info("[根据技能ID，查询技能详细信息] 入参：skills = {}",id);
        SkillDTO skillDTO = null;
        try{
            skillDTO = service.selectById(id);
            logger.info("[根据技能ID列表查询技能列表]成功.");
        } catch (Exception e) {
            logger.error("[根据技能ID列表查询技能列表]异常: {}", e);
        }
        return skillDTO;
    }

	@Override
	public ResponseDTO<Integer> addNewSkill(SkillDTO entity) {
		 logger.info("[添加新技能] 入参：entity = {}",entity);
		 ResponseDTO<Integer> response = null;
		 try {
			 response =  service.addNewSkill(entity);
			 logger.info("[添加新技能]成功.");
		 } catch (Exception ex) {
			 response = new  ResponseDTO<Integer>(ResponseStatus.FAIL);
			 response.setMessage("添加新技能异常");
			 logger.error("[添加新技能]异常: {}", ex);
		 }
		 return  response; 
	}

	@Override
	public ResponseDTO<Integer> updateSkillName(Long id, String skillName) {
		logger.info("[添加新技能] 入参：[id={},skillName={}]",id,skillName);
		ResponseDTO<Integer> response = null;
		try {
			response =  service.updateSkillName(id, skillName);
			logger.info("[添加新技能]成功.");
		} catch (Exception ex) {
			response = new  ResponseDTO<Integer>(ResponseStatus.FAIL);
			response.setMessage("添加新技能异常");
			logger.error("[添加新技能]异常: {}", ex);
		}
		return  response; 
	}

	@Override
	public ResponseDTO<Integer> deleteById(Long id) {
		logger.info("[删除技能] 入参：id={}",id);
		ResponseDTO<Integer> response = null;
		try {
			response =  service.deleteById(id);
			logger.info("[删除技能]成功.");
		} catch (Exception ex) {
			response = new  ResponseDTO<Integer>(ResponseStatus.FAIL);
			response.setMessage("删除技能异常");
			logger.error("[删除技能]异常: {}", ex);
		}
		return  response; 
	}

	@Override
	public ResponseDTO<Boolean> validateSkillUsedBySkillId(Long skillId) {
		logger.info("[检查技能是否已经被中用户使用] 入参：skillId={}",skillId);
		ResponseDTO<Boolean> response = null;
		try {
			response =  service.validateSkillUsedBySkillId(skillId);
			logger.info("[检查技能是否已经被中用户使用]成功.");
		} catch (Exception ex) {
			response = new  ResponseDTO<Boolean>(ResponseStatus.FAIL);
			response.setMessage("检查技能是否已经被中用户使用异常");
			logger.error("[检查技能是否已经被中用户使用]异常: {}", ex);
		}
		return  response; 
	}
}
