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

import com.vanrui.app.user.dto.UserForOrderDTO;
import com.vanrui.app.user.model.UserForOrdersDTO;

/**
 *
 * @author fanhuajun
 * @date 2016年12月22日 下午10:10:18
 * @version V1.0.0
 * description：
 * 
 */
public class UserForOrderTransfer {
    
    public static List<UserForOrderDTO> orderTransfer(List<UserForOrdersDTO> userForOrderList ){
        
        if(userForOrderList == null){
            return null;
        }
        List<UserForOrderDTO> dtos = new ArrayList<UserForOrderDTO>();
         for(UserForOrdersDTO dto : userForOrderList){
             UserForOrderDTO userForOrderDTO = new UserForOrderDTO();
             
             userForOrderDTO.setOrgId(dto.getOrgId());
             userForOrderDTO.setuId(dto.getuId());
             userForOrderDTO.setUserName(dto.getUserName());
             userForOrderDTO.setSkillNames(dto.getSkillNames());
             userForOrderDTO.setAcceptStatus(dto.getAcceptStatus());
             userForOrderDTO.setUserType(dto.getUserType());
             dtos.add(userForOrderDTO);
             
         }
        return dtos;
        
    }

}
