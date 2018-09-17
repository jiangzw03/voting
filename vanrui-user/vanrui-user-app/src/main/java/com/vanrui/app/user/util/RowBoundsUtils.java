/*
 * @(#)RowBoundsUtils.java 2016年10月10日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.util;

import org.apache.ibatis.session.RowBounds;

import ooh.bravo.core.dto.PageRequestDTO;

/**
 *
 * @author huhuiqian
 * @date 2016年10月10日 下午8:51:47
 * @version V1.0.0
 * description：
 */
public class RowBoundsUtils {

    public static RowBounds getRowBounds(PageRequestDTO<?> pageParam) {
        if (pageParam == null) {
            return null;
        }
        int pageNum = pageParam.getPageNum();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        int pageSize = pageParam.getPageSize();
        if (pageSize <= 0) {
            pageSize = 20;
        }
        return new RowBounds((pageNum - 1) * pageSize, pageSize);
    }
}
