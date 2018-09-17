/*
 * @(#)RowBoundsUtil.java 2016年10月20日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

import org.apache.ibatis.session.RowBounds;

/**
 *
 * @author maji01
 * @date 2016年10月20日 上午11:14:55
 * @version V1.0.0
 * description：
 * 
 */
public class RowBoundsUtil {

    
    public   static   RowBounds      getRowBounds(int pageNum,int pageSize){
       
        if( pageNum < 1 ){
            pageNum = 1;
        }
        if( pageSize < 1 ){
            pageSize = 10;
        }
        int offset = (pageNum - 1)*pageSize;
        return  new RowBounds(offset, pageSize);
    }
}
