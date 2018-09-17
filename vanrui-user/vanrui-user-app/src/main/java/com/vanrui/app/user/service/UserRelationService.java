/*
 * @(#)UserRelationService.java 2017年5月17日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.org.constant.OrgLevleType;
import com.vanrui.app.org.facade.OrganizationInfoFacade;
import com.vanrui.app.user.dao.EbaFmDefaultAccountRefDAO;
import com.vanrui.app.user.dao.EbaFmRefDAO;
import com.vanrui.app.user.dto.EBAandFMRefDTO;
import com.vanrui.app.user.model.EbaFmRefEntity;

import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.core.util.PropertiesLoader;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;
import ooh.bravo.util.StringUtils;

/**
 * 用户关联关系服务
 * @author maji01
 * @date 2017年5月17日 下午10:10:40
 * @version V1.0.0
 * description：
 * 
 */
@Service
@TransactionalMark
public class UserRelationService extends BaseService{

    @Autowired
    EbaFmRefDAO   ebaFmRefDao;
    @Autowired
    UserOrgRefService   userOrgRefService; 
    @Autowired
    EbaFmDefaultAccountRefDAO defaultAccountDao;

    @Autowired
    OrganizationInfoFacade   organizationInfoFacade;
    /**
     * 关联FM账号
     * @param phoneNum
     * @return
     */
    public ResponseDTO<Integer> relateFMAccount(EBAandFMRefDTO ebaFMRefDto){
        ResponseDTO<Integer> response = new ResponseDTO<Integer>();
        // 1、必填校验
        if(!validateRelation(ebaFMRefDto)){
            logger.warn("关联参数都是必填项,关联失败 ",ebaFMRefDto);
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage("关联参数都是必填项，不能为空");
            return response;
        }  
        // 2、关联校验
        Integer isExist = ebaFmRefDao.checkExistByPhoneNum(ebaFMRefDto.getFmUserPhone(),ebaFMRefDto.getUserId());
        if( isExist != null&&isExist.intValue()>0){
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage("填写的助这儿账号已关联EBA账号，不能重复关联");
            return response; 
        }
        List<Long> userIdSet = new ArrayList<Long>();
        userIdSet.add( ebaFMRefDto.getUserId() );
        EbaFmRefEntity entity = ebaFmRefDao.selecOneByUIds(userIdSet);
        if(entity!=null){
            entity.setFmUserId( ebaFMRefDto.getFmUserId() );
            entity.setFmUserName( ebaFMRefDto.getFmUserName() );
            entity.setFmUserPhone( ebaFMRefDto.getFmUserPhone() );
            // 3、持久化关联信息
            ebaFmRefDao.update(entity);
        }else{ 
            entity = new EbaFmRefEntity(); 
            entity.setFmUserId( ebaFMRefDto.getFmUserId() );
            entity.setFmUserName( ebaFMRefDto.getFmUserName() );
            entity.setFmUserPhone( ebaFMRefDto.getFmUserPhone() );
            entity.setUserId( ebaFMRefDto.getUserId() );
            // 3、持久化关联信息
            ebaFmRefDao.insert(entity);
        }
        return  response;
    }
    private  boolean validateRelation(EBAandFMRefDTO ebaFMRefDto){
        if(ebaFMRefDto == null 
                || ebaFMRefDto.getUserId() ==null
                || ebaFMRefDto.getFmUserId()==null
                || ebaFMRefDto.getFmUserName()==null
                || ebaFMRefDto.getFmUserPhone()==null){
            return false;
        }
        return true;
    }
    /**
     * 关联FM账号
     * @param phoneNum
     * @return
     */
    public Long selectEbaByFMUId(Long fmUId){
        if(fmUId == null){
            logger.error("FM账号不能为空");
            return null;
        } 
        return  ebaFmRefDao.selecOneByFMUId(fmUId); 
    }
    
    /**
     * 解除账号关联
     * @param userId
     * @return
     */
    public ResponseDTO<Integer> suspendRelatedFromFMAccount(Long userId){
        ResponseDTO<Integer> response = new ResponseDTO<Integer>();
        if(userId == null){
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setMessage("用户ID不能为空");
            return response;
        } 
        // 解除关联
        ebaFmRefDao.deleteByUId(userId);
        return  response;
    }
    
    /**
     * 根据用户ID列表查询一条FM账号（向FM反馈状态时使用）
     * @param userId
     * @return
     */
    public ResponseDTO<EbaFmRefEntity> queryOneFmAccountByUserId(Long userId,String cityCode){
        ResponseDTO<EbaFmRefEntity> response = new ResponseDTO<EbaFmRefEntity>();
        if(userId == null ){
            response.setMessage("参数不能为空");
            response.setStatus(ResponseStatus.FAIL.getCode());
            return response;
        } 
        List<Long> userIdSet = new ArrayList<Long>();
        userIdSet.add(userId);
        // 1、当前用户是否有关联FM账号
        EbaFmRefEntity entity = ebaFmRefDao.selecOneByUIds(userIdSet);
        if(entity != null){
            logger.info("查到可用账号：{}",entity);
            response.setData(entity);
            return response;
        }
        // 2、当前账号没有关联，就在工单所属城市查询
        String combineCode = getCityCode(cityCode);
        if( combineCode!=null ){
            entity = ebaFmRefDao.selectFMAccountByCity(combineCode);
            if(entity != null){
                logger.info("{}所在城市查到可用账号：{}",cityCode,entity);
                response.setData(entity);
                return response;
            } 
        }
        // 3、若没有关联账号，返回失败；
        logger.error("在当前城市未找到合适的账号");
        response.setMessage("在当前城市未找到合适的账号");
        response.setStatus(ResponseStatus.FAIL.getCode());
        return  response;
    }
    
    private String getCityCode(String combineCode){
        if(StringUtils.isNotBlank(combineCode)&&combineCode.length()>="000000-000000".length()){
            return combineCode.substring(0,"000000-000000".length());
        }
        return null;
    }
    
    public EbaFmRefEntity selecOneByUIds(List<Long> userIdSet){
        if(userIdSet==null||userIdSet.isEmpty()){
            return null;
        }  
        return ebaFmRefDao.selecOneByUIds(userIdSet);
    }
    
    /**
     * 查询用户的账号，或
     * @param userIdSet
     * @return
     */
    public EbaFmRefEntity selectByUserIdSet(Long userId,Long orgId,String projectCode){
        if(userId==null){
            return null;
        } 
        // 查询单前用户是否有绑定FM账号
        EbaFmRefEntity entity = null;
        List<Long> userIdSet = new ArrayList<Long>();
        userIdSet.add(userId);
        entity = ebaFmRefDao.selecOneByUIds(userIdSet);
        if(entity != null){
            return entity;
        } 
        // 根据范围查询管理员的FM账号，否则返回null
        return queryFMManagerByOrgIdOrProjectCode(orgId,projectCode);
        
    }
    
    /***
     * 根据范围查询管理员的FM账号，否则返回null
     * 向上追溯，若项目级别没有，就找片区，若片区也没有，就返回null
     * @param orgId
     * @param projectCode
     * @return
     */
    private EbaFmRefEntity queryFMManagerByOrgIdOrProjectCode(Long orgId,String projectCode){
        EbaFmRefEntity entity = null;
        Long areaId = organizationInfoFacade.selectParentIdByIdAndLevleType(OrgLevleType.AREA, orgId);
        if(areaId!=null){
            entity = ebaFmRefDao.selecOneByAreaId(areaId);
        }
        if(entity == null){
            logger.warn("未找到片区级别绑定FM账号的管理员");
        }
        return entity;
    }
    
     
    public EbaFmRefEntity getCommonInfo(Long orgId){
        Long cityId = organizationInfoFacade.selectParentIdByIdAndLevleType(OrgLevleType.CITY, orgId);
        if(cityId==null){
            logger.warn("查询默认账户时，查询城市机构Id失败，请联系管理员。");
            return null;
        }
        EbaFmRefEntity entity= defaultAccountDao.selecOneByCityId(cityId);
        if(entity == null|| StringUtils.isBlank(entity.getFmUserName())
                || StringUtils.isBlank(entity.getFmUserId()) ){
            logger.warn("当前城市账号未配置默认FM账户，查询失败。");
            return null;
        }
//        entity.setFmUserId( PropertiesLoader.getProperty("FM.ACCOUNT.USERID") );
//        entity.setFmUserName( PropertiesLoader.getProperty("FM.ACCOUNT.NAME") );
//        entity.setFmUserPhone( PropertiesLoader.getProperty("FM.ACCOUNT.MOBILE") ); 
        return entity;
    }
}
