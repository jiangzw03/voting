/*
 * @(#)UserRelationTest.java 2017年5月23日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.user.dto.EBAandFMRefDTO;
import com.vanrui.app.user.facade.UserRelationFacadeImpl;

import base.BaseJUnitTest;
import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.dto.ResponseDTO;

/**
 *
 * @author maji01
 * @date 2017年5月23日 下午8:37:49
 * @version V1.0.0
 * description：
 * 
 */
public class UserRelationTest  extends BaseJUnitTest {
    @Autowired
    UserRelationFacadeImpl  service;
    
    @Test
    public  void testRelateFMAccount(){
        EBAandFMRefDTO ebaFMRefDto = new EBAandFMRefDTO();
        ebaFMRefDto.setFmUserId("6");
        ebaFMRefDto.setFmUserName("cece");
        ebaFMRefDto.setFmUserPhone("121212121");
        ebaFMRefDto.setUserId(1L);
        ResponseDTO<Integer> response = service.relateFMAccount(ebaFMRefDto);
        System.out.println("result:"+response);
    }
    
    @Test
    public  void testSelectFMAccount(){
        SystemContextUtils.setTenantID("T000001");
        ResponseDTO<EBAandFMRefDTO> response = null;
        Long userId = 10170L;
        Long orgId = 1397L;
        String projectCode="000001-001069-001070-001397";
        response = service.selectByUserIdSet(userId, orgId, projectCode);
        System.out.println("result:"+response);
    }
     
}
