/*
 * @(#)FMLoginValidateUtil.java 2017年5月22日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;
 
import org.apache.commons.codec.digest.DigestUtils;

import com.vanrui.app.user.util.rsa.RsaEncrypt;
import com.vanrui.app.user.util.rsa.RsaEncryptDecryptUtils;

import ooh.bravo.core.util.PropertiesLoader;
import ooh.bravo.util.StringUtils; 

/**
 *
 * @author maji01
 * @date 2017年5月22日 下午2:29:31
 * @version V1.0.0
 * description：
 * 
 */
public class FMLoginValidateUtil {

    private static final String TENANTE_ID = "T000001";
    private static final String PRE = "0000000000";
    private static final String KEY = PropertiesLoader.getProperty("FM.LOGIN");
    
    public static Long getUIdFromKeyword(String keyword,boolean needDecrypt){
        String temp = keyword;
        if(needDecrypt){
            temp = RsaEncryptDecryptUtils.decryptString(keyword);
        }
        if(temp==null||temp.length()<=(PRE.length()+KEY.length())){
            return null;
        }
        String fmUserId = temp.substring(PRE.length(),temp.length()-KEY.length());
        Long userId = null;
        try{
            userId = Long.parseLong(fmUserId);
        }catch(Exception e){
        }   
        return userId;
    }
    
    /**
     * 创建 token
     * @param userId
     * @return
     */
    public static String createToken(Long userId){
        try{
            String token =  RsaEncrypt.encryptString(TENANTE_ID+","+userId); 
            return token;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * 校验token
     * @param userId
     * @return
     */
    public static Long validateToken(String token){
        String result = RsaEncrypt.decryptStringAndBack(token);
        if(StringUtils.isNotBlank(result)&&result.contains(",")){
            String [] temp = result.split(",");
            if(temp.length==2&&StringUtils.isNotBlank(temp[1]) &&TENANTE_ID.equals(temp[0])){
                return Long.valueOf(temp[1]);        
            }
        }
        return null;
    }
     
   
}
