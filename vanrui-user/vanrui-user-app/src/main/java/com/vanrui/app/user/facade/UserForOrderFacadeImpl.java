/*
 * @(#)UserForOrderFacadeImpl.java 2016年12月13日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.user.dto.UserForOrderDTO;
import com.vanrui.app.user.model.UserForOrdersDTO;
import com.vanrui.app.user.service.UserForOrderService;
import com.vanrui.app.user.transfer.UserForOrderTransfer;

import ooh.bravo.core.dto.PageRequestDTO;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.service.BaseService;

/**
 *
 * @author fanhuajun
 * @date 2016年12月13日 下午4:21:27
 * @version V1.0.0
 * description：
 * 
 */
@Service("userForOrderFacade")
public class UserForOrderFacadeImpl extends BaseService implements UserForOrderFacade {
    
    @Autowired
    private UserForOrderService userForOrderService;
    
    
    @Override
    public ResponseDTO<PageResponseDTO<UserForOrderDTO>> getUserForOrder(PageRequestDTO<SearchUserCondition> param){
        
        logger.info("查询待派/转派工单列表 查询参数={}",param);
        PageResponseDTO<UserForOrderDTO> userForOrderWebPage = new PageResponseDTO<UserForOrderDTO>();
        ResponseDTO<PageResponseDTO<UserForOrderDTO>> responseDTO = new ResponseDTO<PageResponseDTO<UserForOrderDTO>>();
        userForOrderWebPage = userForOrderService.getUserForOrder(param);
        
        responseDTO.setData(userForOrderWebPage);
        
        return responseDTO;
    }
    
    @Override
    public PageResponseDTO<UserForOrderDTO> getUserForOrderN(PageRequestDTO<SearchUserCondition> param){
        
        logger.info("获取工单人员列表 查询参数={}",param);
        PageResponseDTO<UserForOrderDTO> pageResponseDTO = new PageResponseDTO<UserForOrderDTO>();
        PageResponseDTO<UserForOrdersDTO> PageOrdersDTO = null;
        List<UserForOrderDTO> orderDTOListN = null;
        
        PageOrdersDTO = userForOrderService.getUserForOrderN(param);
        orderDTOListN = UserForOrderTransfer.orderTransfer(PageOrdersDTO.getData());
        
        pageResponseDTO.setData(orderDTOListN);
        pageResponseDTO.setTotalRow(PageOrdersDTO.getTotalRow());
        
        logger.info("获取工单人员列表[输出]={}",pageResponseDTO);
        
        return pageResponseDTO;
    }
    
    @Override
    public PageResponseDTO<UserForOrderDTO> getUserForOrderAccept(PageRequestDTO<SearchUserCondition> param){
        
        logger.info("获取工单人员列表 查询参数={}",param);
        PageResponseDTO<UserForOrderDTO> pageResponseDTO = new PageResponseDTO<UserForOrderDTO>();
        PageResponseDTO<UserForOrdersDTO> PageOrdersDTO = null;
        List<UserForOrderDTO> orderDTOListN = null;
        
        PageOrdersDTO = userForOrderService.getUserForOrderAccept(param);
        orderDTOListN = UserForOrderTransfer.orderTransfer(PageOrdersDTO.getData());
        
        pageResponseDTO.setData(orderDTOListN);
        pageResponseDTO.setTotalRow(PageOrdersDTO.getTotalRow());
        
        logger.info("获取工单人员列表[输出]={}",pageResponseDTO);
        
        return pageResponseDTO;
    }

}
