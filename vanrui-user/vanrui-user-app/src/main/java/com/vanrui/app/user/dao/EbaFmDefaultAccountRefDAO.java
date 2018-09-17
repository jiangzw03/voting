/*
 * @(#)MenuFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;
   
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vanrui.app.user.model.EbaFmRefEntity;
 
/**
 * 
 * @author maji01
 * @date 2017年7月19日 下午3:38:06
 * @version V1.0.0
 * description：
 */
@Repository
public interface EbaFmDefaultAccountRefDAO {
   
     /**
      * 查询城市默认账号：同步时调用
      * @param cityId
      * @return
      */
    public EbaFmRefEntity selecOneByCityId(@Param("cityId")Long cityId);
    
}
