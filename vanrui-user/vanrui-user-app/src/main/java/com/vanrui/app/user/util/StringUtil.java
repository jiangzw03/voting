/*
 * @(#)StringUtil.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatType;

import ooh.bravo.context.util.SystemContextUtils;

/**
 *
 * @author maji01
 * @date 2016年10月8日 下午5:34:16
 * @version V1.0.0
 * description：
 * 
 */
public class StringUtil {

    public  static  String   getUserDetailObjectRedisKey(){
        // 租户ID_系统名称_主题
        return  "USER_"+SystemContextUtils.getTenantID()+"_USERDETAIL";
    }
    
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher("142513132s");
        boolean b= matcher.matches();
        System.out.println("result: "+b);
        
    }
    
    public  static   boolean   validateString(String context,String pattern){

        return  Pattern.compile(pattern).matcher(context).matches(); 
    }
    
}
