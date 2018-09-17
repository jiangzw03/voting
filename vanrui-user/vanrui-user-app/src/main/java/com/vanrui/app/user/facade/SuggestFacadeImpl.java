/*
 * @(#)SuggestFacade.java 2016年10月19日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.SuggestDTO;
import com.vanrui.app.user.service.SuggestService;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;

/**
 *
 * @author fanhuajun
 * @date 2016年10月19日 下午2:41:28
 * @version V1.0.0
 * description：
 * 
 */
@Service("suggestFacade")
public class SuggestFacadeImpl extends BaseService implements SuggestFacade{
    @Autowired
    SuggestService suggestService;
    
    @Override
    public ResponseDTO<Integer>  insert(SuggestDTO suggest) {
        logger.info("[新增意见反馈] 入参：suggest = {}",suggest);
        ResponseDTO<Integer> response = new ResponseDTO<Integer>();
        
        
        String regEx = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5 ，。？：；‘’！“”—……、,.?:;''!\"\"—……、|(－{2})|(（）)|(\\(\\))|(【】)|({})|(《》]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(suggest.getContent());
        int count = 0;
        while (m.find()) {
            count = count + 1;
        }
        
        if(suggest.getContent().length() != count ){
            logger.error("picture container!");
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage("不能包含特殊字符，图片");
        }
        try {
            response =  suggestService.insert(suggest);
            logger.info("[新增意见反馈]成功.");
        } catch (Exception ex) {
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage(ResponseStatus.FAIL.getMsg());
            logger.error("[新增意见反馈]异常: {}", ex);
        }
        return  response ;
    }

}
