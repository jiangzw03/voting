/*
 * @(#)UserForOrderTransfer.java 2016年12月22日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.transfer;

import java.util.ArrayList;
import java.util.List;

import com.vanrui.app.user.dto.SkillDTO;
import com.vanrui.app.user.dto.UserForOrderDTO;
import com.vanrui.app.user.model.SkillDictionaryEntity;
import com.vanrui.app.user.model.UserForOrdersDTO;

/**
 *
 * @author maji
 * @date 2018年03月02日 下午10:10:18
 * @version V3.0.0
 * description：
 * 
 */
public class SkillEntityRransfer {
    private static final int NORMAL_STATUS = 1;
    
    public static SkillDictionaryEntity transferToSkillDictionaryEntity(SkillDTO dto){
    	SkillDictionaryEntity entity = null;
        if(dto != null){
        	entity = new SkillDictionaryEntity();
        	entity.setSkillId(dto.getSkillId());
        	entity.setpSkillId(dto.getpSkillId());
        	entity.setSkillCode(dto.getSkillCode());
        	entity.setSkillName(dto.getSkillName());
        	entity.setStatus(NORMAL_STATUS);
        }
        return entity;
        
    }

}
