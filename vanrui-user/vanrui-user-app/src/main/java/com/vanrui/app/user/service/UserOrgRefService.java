/*
 * @(#)UserOrgRefService.java 2016年10月14日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vanrui.app.org.facade.OrganizationInfoFacade;
import com.vanrui.app.org.msg.OrgChangeMsg;
import com.vanrui.app.user.dao.UserOrgRefDao;
import com.vanrui.app.user.dto.UserOrgRefDto;
import com.vanrui.app.user.model.UserOrgRefEntity;
import com.vanrui.app.user.service.cache.UserOrgCacheService;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;
import ooh.bravo.util.StringUtils;

/**
 *
 * @author maji01
 * @date 2016年10月14日 下午6:27:54
 * @version V1.0.0 description：
 * 
 */
@Service("userOrgRefService")
@TransactionalMark
public class UserOrgRefService extends BaseService {

    @Autowired
    UserOrgRefDao uOrgRefDao;
    @Autowired
    UserOrgCacheService userOrgCache;
    @Autowired
    OrganizationInfoFacade organizationInfoFacade;

    public List<UserOrgRefDto> selectUserOrgRefListByUIds(List<Long> uIds) {
        if (uIds == null || uIds.size() == 0) {
            return null;
        }
        return uOrgRefDao.selectRefsByUIds(uIds);
    }
    
    /**
     * 校验指定用户是否在该地区有管理权限
     * @param userId 用户ID
     * @param orgId 地区ID
     * @return 是否有管理权限
     */
    public boolean validateUserOrgRef(Long userId, Long orgId) {
    	if (userId == null || orgId == null) {
    		return false;
    	}
    	
    	// 获取linkcode
    	String linkCode = this.organizationInfoFacade.selectLinkCodeById(orgId);
    	if (linkCode == null) {
    		return false;
    	}
    	
    	List<UserOrgRefDto> list = this.uOrgRefDao.selectRefsByUIds(Arrays.asList(new Long[] {userId}));
    	if (CollectionUtils.isEmpty(list)) {
    		return false;
    	}
    	
    	for (UserOrgRefDto dto : list) {
    		if (dto != null && dto.getCombinationCode() != null) {
    			if (linkCode.startsWith(dto.getCombinationCode())) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }

    /***
     * 批量修改用户组织机构编码
     */
    public void batchUdpateOrg(OrgChangeMsg org) {
        if (org == null || org.getId() == null || org.getId() == 0 || StringUtils.isBlank(org.getLinkCode()) || StringUtils.isBlank(org.getName())) {
            logger.error("组织机构编码，名称，id都不能为空：{}", org);
            return;
        }
        // 批量修改
        uOrgRefDao.updateByOrgId(org.getId(), org.getLinkCode(), org.getName());
        // 批量删除缓存
        userOrgCache.refreshBatchCacheByUIds(selectUserIdList(null, org.getLinkCode()));
    }

    /**
     * 根据用户Id与机构组合编码，查询用户与组合编码列表
     * 
     * @param uId
     * @param combineCode
     * @return
     */
    public List<Long> selectUserOrgRefList(Long uId, String combineCode) {

        return uOrgRefDao.selectuIdList(uId, combineCode);
    }

    /**
     * 根据用户用户姓名与机构组合编码，查询用户列表
     * 
     * @param uId
     * @param combineCode
     * @return
     */
    public List<Long> selectUserIdList(String userName, String combineCode) {
        // 入参校验，不能为空
        if (StringUtils.isBlank(combineCode)) {
            logger.error("[ 根据组合编号和用户姓名查询用户ID列表 ] 异常：组合编号为空，导致查询失败。");
            return null;
        }
        if (StringUtils.isBlank(userName)) {
            userName = null;
        }
        return uOrgRefDao.selectUserIdList(userName, combineCode);
    }
    
    /**
     * 根据用户用户姓名与机构组合编码，查询用户列表
     * 
     * @param uId
     * @param combineCode
     * @return
     */
    public List<Long> selectUserIdList(String userName, List<String> combineCodeList) {
        // 入参校验，不能为空
        if (combineCodeList == null || combineCodeList.size() == 0) {
            logger.error("[ 根据组合编号列表和用户姓名查询用户ID列表 ] 异常：组合编号为空，导致查询失败。");
            return null;
        }
        if (StringUtils.isBlank(userName)) {
            userName = null;
        }
        return uOrgRefDao.selectUserIdListByCombineCodeList(userName, combineCodeList);
    }

    /**
     * 查询指定用户的组织机构关系列表，
     * 
     * @param uId
     * @return
     */
    public List<UserOrgRefDto> selectUserOrgRefByUId(Long uId) {

        if (uId == null) {
            logger.error("查询指定用户[uId=null]的组织机构关系列表，检查时发现没有登录，本次操作无效 ！");
            return null;
        }
        List<UserOrgRefEntity> list = userOrgCache.selectByUId(uId);
        return transformUserOrgDTO(list);
    }

    /**
     * 查询当前用户的组织机构关系列表
     * 
     * @return
     */
    public List<UserOrgRefDto> selectCurUserOrgRefByUId() {

        // 查询上下文
        Long uId = SystemContextUtils.getUserID();
        if (uId == null) {
            logger.error("查询当前用户[uId=null]的组织机构关系列表，检查时发现没有登录，本次操作无效 ！");
            return null;
        }
        List<UserOrgRefEntity> list = userOrgCache.selectByUId(uId);
        return transformUserOrgDTO(list);
    }

    public List<String> selectUserCombinationCodeList(Long uId) {
        if (uId == null) {
            uId = SystemContextUtils.getUserID();
        }
        if (uId == null) {
            logger.error("查询当前用户[uId=null]的组织机构关系列表，检查时发现没有登录，本次操作无效 ！");
            return null;
        }
        List<UserOrgRefEntity> list = userOrgCache.selectByUId(uId);
        List<String> result = null;
        if (list != null && list.size() > 0) {
            result = new ArrayList<String>();
            for (UserOrgRefEntity ref : list) {
                result.add(ref.getCombinationCode());
            }
        }
        return result;
    }

    private List<UserOrgRefDto> transformUserOrgDTO(List<UserOrgRefEntity> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<UserOrgRefDto> result = new ArrayList<UserOrgRefDto>();
        for (UserOrgRefEntity enti : list) {
            UserOrgRefDto dto = new UserOrgRefDto();
            dto.setOrId(enti.getOrId());
            dto.setOrName(enti.getOrName());
            dto.setCombinationCode(enti.getCombinationCode());
            dto.setuId(enti.getuId());
            result.add(dto);
        }

        return result;
    }

    /***
     * 根据组合编号查询相关用户列表
     * 
     * @param combineCode
     * @return
     */
    public List<Long> selectUserIds(String combineCode) {
        if (StringUtils.isBlank(combineCode)) {
            return null;
        }
        return uOrgRefDao.selectUserIds(combineCode);
    }
    
    /***
     * 根据组合编号查询相关用户列表
     * 
     * @param combineCode
     * @return
     */
    public List<Long> selectUserIds(List<String> combineCodeList) {
        if (combineCodeList == null || combineCodeList.size() == 0) {
            return null;
        }
        return uOrgRefDao.selectUserIdsByCombineCodeList(combineCodeList);
    }

    public Boolean hasUserUnderOrganization(Long orgId) {
        return uOrgRefDao.hasUserUnderOrganization(orgId) != null;
    }
    
    public boolean validateUserOrg(String combineCode) {
        if(StringUtils.isBlank(combineCode)) {
            return false;
        }
        Long userId = SystemContextUtils.getUserID();
        List<String>  list = this.selectUserCombinationCodeList(userId);
        if( list != null && ! list.isEmpty() ){
            for(String linkCode:list){
                if( combineCode.startsWith(linkCode) ){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Integer getTotalUser(String[] orgCodes){
    	return uOrgRefDao.selectTotalUser(orgCodes);
    }
    
    /**
     * TODO 查询指定用户所属范围的用户手机号码列表
     * @param uId
     * @param combineCode
     * @return
     */
    public List<String> selectUserPhoneNumList(Long uId,
            String[] orgCodes) {
        return uOrgRefDao.selectUserPhoneNums(uId, orgCodes);
    }
}






