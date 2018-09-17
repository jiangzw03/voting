/*
 * @(#)UserRelationService.java 2017年5月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;
  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vanrui.app.user.dto.EBAandFMRefDTO;
import com.vanrui.app.user.model.EbaFmRefEntity;
import com.vanrui.app.user.service.UserRelationService;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService; 
/**
 * 用户关联关系服务
 * @author maji01
 * @date 2017年5月17日 下午10:10:40
 * @version V1.0.0
 * description：
 * 
 */ 
@Service("userRelationFacade")
public class UserRelationFacadeImpl extends BaseService implements UserRelationFacade{

    @Autowired
    UserRelationService   userRelationService; 
    /**
     * 关联FM账号
     * @param ebaFMRefDto
     * @return
     */
    public ResponseDTO<Integer> relateFMAccount(EBAandFMRefDTO ebaFMRefDto){
        logger.info("[eba与外部关联关系持久化] 入参：ebaFMRef=[{}] ", ebaFMRefDto);
        ResponseDTO<Integer> response = null;
        try {
            response = userRelationService.relateFMAccount(ebaFMRefDto);
            logger.info("[eba与外部关联关系持久化]成功.");
        } catch (Exception ex) {
            logger.error("[eba与外部关联关系持久化]异常: {}", ex);
            response = new ResponseDTO<Integer>();
            response.setMessage("关联FM账号异常");
            response.setStatus(ResponseStatus.FAIL.getCode());
        }
        return response;
    }
    
    /**
     * 解除关联FM账号
     * @param ebaFMRefDto
     * @return
     */
    public ResponseDTO<Integer> deleteFMRelate(Long userId){
        logger.info("[解除eba与FM的账号关联关系] 入参：userId=[{}] ", userId);
        ResponseDTO<Integer> response = null;
        try {
            response = userRelationService.suspendRelatedFromFMAccount(userId);
            logger.info("[解除eba与FM的账号关联关系]成功.");
        } catch (Exception ex) {
            logger.error("[解除关联FM账号]异常: {}", ex);
            response = new ResponseDTO<Integer>();
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage("解除关联FM账号异常");
        }
        return response;
    }
  
    /**
     * 根据EBA的用户Id集合查询一条符合要求的FM账号信息
     * @param userIdSet
     * @return
     */
    public ResponseDTO<EBAandFMRefDTO> selectByUserIdSet(Long userId,Long orgId,String projectCode){
        logger.info("[查询一条FM账号信息] 入参：(userid,orgid,projectCode)=({},{},{}) ", userId,orgId,projectCode);
        ResponseDTO<EBAandFMRefDTO> response = new ResponseDTO<EBAandFMRefDTO>();
        try {
            EBAandFMRefDTO entity = null;
            entity = this.transforToEBAandFMRefDTO(userRelationService.selectByUserIdSet(userId,orgId,projectCode),orgId);
            if(entity!=null){
                response.setData(entity);
                logger.info("[查询一条FM账号信息]成功.");
            }else{
                response.setStatus(ResponseStatus.FAIL.getCode());
                response.setMessage("未查到符合要求的common FM账号信息");
                logger.info("未查到符合要求的FM账号信息,orgid={},projectCode={}.",orgId,projectCode);
            }
        } catch (Exception ex) {
            logger.error("[查询一条FM账号信息]异常: {}", ex); 
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage("查询一条FM账号信息异常");
        }
        return response;
    }
    
    private EBAandFMRefDTO transforToEBAandFMRefDTO(EbaFmRefEntity entity,Long orgId){
        if(entity==null){
            entity=userRelationService.getCommonInfo(orgId);
            if(entity==null){ 
                return null;
            }
        }
        EBAandFMRefDTO dto = new EBAandFMRefDTO();
        dto.setFmUserId(entity.getFmUserId());
        dto.setFmUserName(entity.getFmUserName());
        dto.setFmUserPhone(entity.getFmUserPhone());
        dto.setUserId(entity.getUserId());
        return dto;
    }
}
