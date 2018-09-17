/*
 * @(#)MenuFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;
 

import org.springframework.stereotype.Repository;

import com.vanrui.app.user.dto.FreezeDto;
 

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午10:52:20
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface FreezeDao {
 
    /**
     * 将解冻或冻结信息写入冻结操作历史表中
     * @param entity
     */
    public  void  insert( FreezeDto   entity );
     
}
