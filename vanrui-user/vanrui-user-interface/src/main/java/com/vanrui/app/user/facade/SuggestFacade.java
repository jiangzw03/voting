/*
 * @(#)SuggestFacade.java 2016年10月19日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import com.vanrui.app.user.dto.SuggestDTO;

import ooh.bravo.core.dto.ResponseDTO;

/**
 *
 * @author fanhuajun
 * @date 2016年10月19日 下午2:46:35
 * @version V1.0.0 description：
 * 
 */
public interface SuggestFacade {

    /**
     * 保存意见反馈
     * 
     * @param user
     * @return
     */
    public ResponseDTO<Integer> insert(SuggestDTO suggest);

}
