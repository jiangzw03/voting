/*
 * @(#)SkillService.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dao.SkillDao;
import com.vanrui.app.user.dao.UserSkillRefDao;
import com.vanrui.app.user.dto.SkillDTO;
import com.vanrui.app.user.model.SkillDictionaryEntity;
import com.vanrui.app.user.service.cache.UserSkillCacheService;
import com.vanrui.app.user.transfer.SkillEntityRransfer;

/**
 *
 * @author maji01
 * @date 2016年10月18日 下午6:16:44
 * @version V3.0.0
 * description：
 * 
 */
@Service("skillService") 
@TransactionalMark
public class SkillService extends BaseService {

    @Autowired
    UserSkillCacheService   usCache;
    @Autowired
    SkillDao   skillDao;
    @Autowired
    UserSkillRefDao   usDao;
    /**
     * 查询所有技能
     * @return
     */
    public List<SkillDTO> selectAllSkills() {
        List<SkillDTO>  results = null;
        List<SkillDictionaryEntity>  list =   usCache.selectAllSkills();
        SkillDictionaryEntity   skill = null;
        if( list != null && list.size() > 0 ){
            results = new  ArrayList<SkillDTO>();
            SkillDTO  dto = null;
            int len = list.size();
            for(int i=0;i<len;i++){
                skill = list.get(i);
                dto = new  SkillDTO();
                dto.setpSkillId(skill.getpSkillId());
                dto.setSkillCode(skill.getSkillCode());
                dto.setSkillId(skill.getSkillId());
                dto.setSkillName(skill.getSkillName());
                results.add(dto);
            }
        }
        return results;
    }
    
    /***
     * 根据技能id列表查询技能实体对象列表
     * @param skills
     * @return
     */
    public Map<Long,SkillDTO> selectSkillsBySkillIds(Set<Long> skills) {
        
        if(skills == null || skills.size() == 0 ){
            return null;
        }
        Map<Long,SkillDTO>  results = null; 
        SkillDictionaryEntity   skill = null;
        List<SkillDictionaryEntity> list = skillDao.selectSkillsBySkillIds(skills);
        if( list != null && list.size() > 0 ){
            results = new  HashMap<Long,SkillDTO>();
            SkillDTO  dto = null;
            int len = list.size();
            for(int i=0;i<len;i++){
                skill = list.get(i);
                dto = new  SkillDTO();
                dto.setpSkillId(skill.getpSkillId());
                dto.setSkillCode(skill.getSkillCode());
                dto.setSkillId(skill.getSkillId());
                dto.setSkillName(skill.getSkillName());
                results.put(dto.getSkillId(),dto);
            }
        }
        return results;
    }
    
    public SkillDTO selectById(Long id){
        
        SkillDictionaryEntity skillEntity = skillDao.selectById(id);
        
        if(skillEntity == null){
            return null;
        }
        
        SkillDTO skillDto = new SkillDTO();
        skillDto.setSkillId(skillEntity.getSkillId());
        skillDto.setSkillCode(skillEntity.getSkillCode());
        skillDto.setpSkillId(skillEntity.getpSkillId());
        skillDto.setSkillName(skillEntity.getSkillName());
        
        return skillDto;
    }
    
    
    public ResponseDTO<Integer> addNewSkill(SkillDTO dto) {
    	ResponseDTO<Integer> response = new ResponseDTO<Integer>();
    	if(!validateSkillDTO(dto)){
    		response.setStatus(ResponseStatus.FAIL.getCode());
    		response.setMessage("新增入参所有属性必填，新增操作中断");
    		return response;
    	}
    	SkillDictionaryEntity entity=SkillEntityRransfer.transferToSkillDictionaryEntity(dto);
    	skillDao.insert(entity);
    	// 刷新缓存
    	usCache.refreshAllSkillCache();
		return response;
	}
    
    private boolean validateSkillDTO(SkillDTO dto){
    	if(dto==null||dto.getpSkillId()==null||dto.getpSkillId()==null||dto.getSkillName()==null){
    		return false;
    	}
    	return true;
    }
 
	public ResponseDTO<Integer> updateSkillName(Long id, String skillName) {
		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		if(id==null||skillName==null){
			response.setStatus(ResponseStatus.FAIL.getCode());
    		response.setMessage("修改技能名称失败，入参id="+id+",skillName="+skillName);
    		return response;
		}
		skillDao.updateSkillName(id, skillName);
		// 刷新缓存
    	usCache.refreshAllSkillCache();
		return response;
	}
 
	public ResponseDTO<Integer> deleteById(Long id) {
		ResponseDTO<Integer> response = new ResponseDTO<Integer>();
		if(id==null){
			response.setStatus(ResponseStatus.FAIL.getCode());
    		response.setMessage("删除指定技能，入参id不能为空，删除失败");
    		return response;
		}
		skillDao.delete(id);
		// 刷新缓存
    	usCache.refreshAllSkillCache();
		return response;
	}
    
    public ResponseDTO<Boolean> validateSkillUsedBySkillId(Long skillId){ 
    	ResponseDTO<Boolean> response = new ResponseDTO<Boolean>();
    	Integer result = usDao.validateSkillUsedById(skillId);
    	if(result!=null&&result.equals(1)){
    		response.setData(true);
    	}else{
    		response.setData(false);
    	}
    	return response;
    }
}
