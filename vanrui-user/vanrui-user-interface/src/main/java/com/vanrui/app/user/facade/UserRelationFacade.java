package com.vanrui.app.user.facade;
   
import java.util.List;

import com.vanrui.app.user.dto.EBAandFMRefDTO;

import ooh.bravo.core.dto.ResponseDTO; 

/**
 * 用户关联关系服务
 * @author maji01
 * @date 2017年5月17日 下午10:10:40
 * @version V1.0.0
 * description：
 * 
 */ 
public interface UserRelationFacade {
 
    /**
     * 关联FM账号
     * @param ebaFMRefDto
     * @return
     */
    public ResponseDTO<Integer> relateFMAccount(EBAandFMRefDTO ebaFMRefDto);
    
    /**
     * 解除关联
     * @param userId
     * @return
     */
    public ResponseDTO<Integer> deleteFMRelate(Long userId);
  
    /***
     * 查询当前用户对应FM账号，若没有，去项目或片区上找管理员的FM账号;
     * @param userId
     * @param orgId
     * @param projectCode
     * @return
     */
    public ResponseDTO<EBAandFMRefDTO> selectByUserIdSet(Long userId,Long orgId,String projectCode);
}
