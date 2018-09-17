/*
 * @(#)UserForOrderFacadeImpl.java 2016年12月13日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.user.dto.UserForOrderDTO;

import ooh.bravo.core.dto.PageRequestDTO;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;

/**
 *
 * @author fanhuajun
 * @date 2016年12月13日 下午4:21:27
 * @version V1.0.0
 * description：
 * 
 */
public interface UserForOrderFacade {
    
    /**
     * 
     * @param orgId
     * @param skillIds
     * @param roleIds
     * @param userName
     * @return
     */
    public ResponseDTO<PageResponseDTO<UserForOrderDTO>> getUserForOrder(PageRequestDTO<SearchUserCondition> pageParam);
    /**
     * 
     * @param orgId
     * @param skillIds
     * @param roleIds
     * @param userName
     * @return
     */
    public PageResponseDTO<UserForOrderDTO> getUserForOrderN(PageRequestDTO<SearchUserCondition> pageParam);
    
    /**
     * 待验收人员
     * @param pageParam
     * @return
     */
    public PageResponseDTO<UserForOrderDTO> getUserForOrderAccept(PageRequestDTO<SearchUserCondition> pageParam);
    

}
