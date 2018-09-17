/*
 * @(#)SuggestService.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dao.SuggestDao;
import com.vanrui.app.user.dto.SuggestDTO;
import com.vanrui.app.user.model.SuggestEntity;
import com.vanrui.app.user.util.ObjectConverter;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;

/**
 *
 * @author fanhuajun
 * @date 2016年10月18日 下午5:39:27
 * @version V1.0.0
 * description：
 * 
 */
@Service("suggestService") 
@TransactionalMark
public class SuggestService extends BaseService {
    
    @Autowired
    SuggestDao suggestDao;
    /**
     * 保存意见反馈
     * @param suggestDTO
     * @return
     */
    public ResponseDTO<Integer> insert(SuggestDTO suggestDTO){
        ResponseDTO<Integer> response = new ResponseDTO<>();
        SuggestEntity entity = ObjectConverter.transferSuggest(suggestDTO);
        entity.setuId(SystemContextUtils.getUserID());
        
        try{
            suggestDao.insert(entity);
            logger.info("[添加意见反馈]正常:{}",entity);
        } catch (Exception e) {
            logger.error("[添加意见反馈]异常:{}", e);
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage(ResponseStatus.FAIL.getMsg());
        }
        
        return response;
    } 

}
