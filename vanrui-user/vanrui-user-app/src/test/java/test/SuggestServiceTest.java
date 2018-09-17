/*
 * @(#)SuggestServiceTest.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.user.dto.SuggestDTO;
import com.vanrui.app.user.service.SuggestService;

import base.BaseJUnitTest;

/**
 *
 * @author fanhuajun
 * @date 2016年10月18日 下午6:34:23
 * @version V1.0.0 description：+
 * 
 */
public class SuggestServiceTest extends BaseJUnitTest {

    @Autowired
    SuggestService suggestService;

    // @Test
    public void test() {
        SuggestDTO suggestDTO = new SuggestDTO();
        suggestDTO.setuId((long) 1);
        suggestDTO.setContent("很好");
        suggestDTO.setCreateTime(new Date());
        suggestService.insert(suggestDTO);

    }

    @Test
    public void testContent() {

        String line = "This order was placed for QT3000! OK?";

        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        if (m.find()) {
            /*
             * System.out.println("Found value: " + m.group(0) );
             * System.out.println("Found value: " + m.group(1) );
             * System.out.println("Found value: " + m.group(2) );
             */
            System.out.println("find");
        } else {
            System.out.println("NO MATCH");
        }

    }

    public static void main(String[] args) {
        int count = 0;
        String regEx = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5 ，。？：；‘’！“”—……、,.?:;''!\"\"—……、|(－{2})|(（）)|(\\(\\))|(【】)|({})|(《》]";
        String str = "弄好呀";
        
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        
        System.out.println("字符长度"+str.length()); 
        while (m.find()) {
            count = count + 1;
            System.out.print(m.groupCount());
            System.out.print(m.group());
            System.out.print("  ");
        }
        System.out.println("共有 " + count + "个 ");
    }

}
