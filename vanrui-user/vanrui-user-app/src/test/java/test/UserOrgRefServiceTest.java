/*
 * @(#)AuthManaFacade.java 2016年10月8日
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
import org.testng.Assert;

import com.vanrui.app.org.msg.OrgChangeMsg;
import com.vanrui.app.user.dto.UserOrgRefDto;
import com.vanrui.app.user.service.UserOrgRefService;

import base.BaseJUnitTest;
import ooh.bravo.context.util.SystemContextUtils;

/**
 * 用户机构关系表
 * 
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0 description：
 * 
 */
public class UserOrgRefServiceTest extends BaseJUnitTest {

    @Autowired
    UserOrgRefService uorgRefService;

    @Test
    public  void   batchUdpateOrg(){
        OrgChangeMsg  org = new OrgChangeMsg();
        org.setId( 7l );
        org.setLinkCode("000001-000002-000007");
        org.setName("宝安");
        uorgRefService.batchUdpateOrg(org);
        System.out.println("\n\n @___finish:" + "  \n\n");
    }
    
    
    /**
     * 根据用户ID列表查询用户与机构的关系列表： 包含：用户ID，用户姓名，机构ID，机构组合编号
     * 
     * @param uIds
     * @return
     */
    @Test
    public void selectUserOrgRefListByUIds() {
        List<Long> uIds = new ArrayList<Long>();
        uIds.add(1L);
        uIds.add(10077L);
        List<UserOrgRefDto> list = uorgRefService.selectUserOrgRefListByUIds(uIds);
        System.out.println("\n\n @___result:" + list + "  \n\n");
        System.out.println("\n\n @___finish:" + list.size() + "  \n\n");
    }

//    @Test
    public void hasUserUnderOrganization() {
        System.out.println("\n\n @___finish:" + uorgRefService.hasUserUnderOrganization(1111L) + "  \n\n");
    }
    
//    @Test
    public  void selectUserIdList( ) {
        // 入参校验，不能为空
        List<Long>  list = uorgRefService.selectUserIdList("asfdasdfasdf","0000");
        System.out.println("\n\n @___finish:" + list  + "  \n\n");
    }
    
    @Test
    public void selectCombinationCodeList() {
        logger.info("查询结果={}", uorgRefService.selectUserCombinationCodeList(1L));
    }
    
    @Test
    public void getTotalUser(){
    	String[] orgCodes = {"000001-000021-000022"};
    	
    	
    	logger.info("查询结果={}", uorgRefService.getTotalUser(orgCodes));
    }
    
    /**
     * 测试该人员是否管理该机构
     */
    @Test
    public void validateUserManageOnOrgTest() {
    	SystemContextUtils.setUserAndTenantID(1L, "T000001");
    	/* select * from t_ref_user_organization t; */
    	
    	boolean rs = this.uorgRefService.validateUserOrgRef(30L, 131L);
    	Assert.assertTrue(rs);
    }
    
}




