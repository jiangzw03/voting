/*
 * @(#)exportSkill.java 2016年10月19日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.user.dto.SkillDTO;
import com.vanrui.app.user.service.SkillService;

import base.BaseJUnitTest;
import util.ExportExcel;

/**
 *
 * @author fanhuajun
 * @date 2016年10月19日 上午11:54:42
 * @version V1.0.0 description：
 * 
 */
public class exportExcelSkill extends BaseJUnitTest {

    @Autowired
    SkillService service;

    @Test
    public void test() {
        List<SkillDTO> skillDTO = service.selectAllSkills();

        // logger.info("........... 查询成功[{}]", skillDTO);
        ExportExcel<SkillDTO> exportExcel = new ExportExcel<SkillDTO>();
        Map<String, String> headerMap = new HashMap<String, String>();

        /*
         * 按headerMap的输入顺序输出excel key值存入get方法名,value存入excel首行显示字段
         */
        headerMap.put("getSkillId", "技能id");
        headerMap.put("getpSkillId", "技能父级id");
        headerMap.put("getSkillName", "技能名称");
        headerMap.put("getSkillCode", "技能编码");
        

        try {
            OutputStream out = new FileOutputStream("E://a.xls");
            exportExcel.exportExcel("测试POI导出EXCEL文档", headerMap, skillDTO, out,
                    "yyyy-MM-dd");
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
