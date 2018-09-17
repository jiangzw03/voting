/*
 * @(#)SuggestDao.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import org.springframework.stereotype.Repository;

import com.vanrui.app.user.model.SuggestEntity;

/**
 *
 * @author fanhuajun
 * @date 2016年10月18日 下午4:52:11
 * @version V1.0.0 description：
 * 
 */
@Repository
public interface SuggestDao {

    /**
     * 保存意见反馈
     * @param entity
     */
    public void insert(SuggestEntity entity);

}
