/*
 * @(#)MenuFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;
  
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.model.EbaFmRefEntity;
 
/**
 *  
 * @author maji01
 * @date 2017年5月17日 下午9:48:55
 * @version V1.0.0
 * description：
 */
@Repository
public interface EbaFmRefDAO {
   
    /**
     * 将关系持久化
     * @param entity
     */
    public  void  insert(EbaFmRefEntity  entity);
    
    /**
     * 更新关系持久化
     * @param entity
     */
    public  void  update(EbaFmRefEntity  entity);
    /**
     * 根据用户ID集合查询一条FM的用户信息
     * @param userIdSet
     * @return
     */
    public  EbaFmRefEntity  selecOneByUIds(@Param("userIdSet")List<Long> userIdSet);
    /**
     * 根据用户ID删除关系，即结束绑定
     * @param userId
     */
    public  void  deleteByUId(Long  userId);
    
    /**
     * 根据电话号码查询是否已经被其它用户绑定
     * @param phoneNum
     * @param userId  不包含这个用户
     * @return
     */
    public  Integer  checkExistByPhoneNum(@Param("phoneNum")String  phoneNum,@Param("userId")Long userId);
      

    /**
     * 根据机构编号查询，范围内的FM账号（只查询一条账号数据）
     * @param conbineCode
     * @return
     */
    public EbaFmRefEntity  selectFMAccountByCity(String conbineCode);
    /**
     * 根据FM用户ID查询EBA用户ID
     * @param fmUId
     * @return
     */
    public Long selecOneByFMUId(Long fmUId);
    
    /***
     * 查询片区范围内的管理员关联的FM账号
     * @param areaCode
     * @return
     */
    public EbaFmRefEntity selecOneByAreaId(@Param("areaId")Long areaId);
}
