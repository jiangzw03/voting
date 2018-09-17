/*
 * @(#)RoleFacadeImpl.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dao;

import java.util.List;
 



import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
 






import com.vanrui.app.user.dto.AuthDto;
import com.vanrui.app.user.model.RoleEntity;
 

/**
 *
 * @author maji01
 * @date 2016年10月8日 下午4:28:57
 * @version V1.0.0
 * description：
 * 
 */
@Repository
public interface RoleDao  {

    
    int insert(RoleEntity role);

    
    int update(RoleEntity role);

    
    int deleteByRoleId(Long roleId);

    
    List<RoleEntity> selectAllRole();

    
    RoleEntity selectRoleInfoByRoleId(Long roleId);
 
    
    List<AuthDto> selectAllAuthority(RowBounds  bound);

    String  isExistsForRoleName(@Param("roleName")String roleName,@Param("roleId")Long roleId);
}
