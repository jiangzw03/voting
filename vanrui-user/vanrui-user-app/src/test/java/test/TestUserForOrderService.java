/*
 * @(#)TestUserForOrderService.java 2016年12月13日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.user.facade.UserForOrderFacade;
import com.vanrui.app.user.service.UserForOrderService;

import base.BaseJUnitTest;
import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.dto.PageRequestDTO;

/**
 *
 * @author fanhuajun
 * @date 2016年12月13日 下午4:32:27
 * @version V1.0.0
 * description：
 * 
 */
public class TestUserForOrderService extends BaseJUnitTest {
    
    @Autowired
    UserForOrderService userForOrderService;
    
    @Autowired
    UserForOrderFacade userForOrderFacade;
    
    
    
    
    /**
     * 
     */
    @Test
    public void getUserForOrder(){
        SystemContextUtils.setUserAndTenantID(1L,"T000001");
        
        List<Long> roleIds = new ArrayList<Long>();
        roleIds.add(1L);
        roleIds.add(100L);
        PageRequestDTO<SearchUserCondition> pageParam = new PageRequestDTO<SearchUserCondition>();
        SearchUserCondition searchUserCondition = new SearchUserCondition();
        pageParam.setArgument(searchUserCondition);
        
        pageParam.getArgument().setProjectType(3);
        pageParam.getArgument().setOrderId(1296L);
        pageParam.getArgument().setOrgId(1772L);
        pageParam.getArgument().setAssignType(2);
        pageParam.getArgument().setPrivilege(2);
        //pageParam.getArgument().setUserName("解");
//        pageParam.getArgument().setSkillIdList(skillIds);
//        pageParam.getArgument().setSkillIdList(null);
        pageParam.getArgument().setRoleIdList(null);
        pageParam.getArgument().setCombineCode("000001-001069-001075-001772");
        pageParam.setPageNum(1);
        pageParam.setPageSize(100);
        
        logger.info("[入参]={}",pageParam);
        logger.info("查询信息={}",userForOrderFacade.getUserForOrderN(pageParam));
        
    }

}
