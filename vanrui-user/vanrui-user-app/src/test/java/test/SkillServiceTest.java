/*
 * @(#)LoginServiceTest.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseJUnitTest;

import com.vanrui.app.user.dto.SkillDTO;
import com.vanrui.app.user.service.SkillService;

/**
 *
 * @author maji01
 * @date 2016年10月18日 下午2:03:59
 * @version V1.0.0
 * description：
 * 
 */
public class SkillServiceTest  extends BaseJUnitTest {

    @Autowired
    SkillService   service;
    
    @Test
    public   void   test(){
        List<SkillDTO>  DTOS = service.selectAllSkills();
        logger.info("@@@@@@@@@@@@@ 查询成功[{}]",DTOS);
    }
}
