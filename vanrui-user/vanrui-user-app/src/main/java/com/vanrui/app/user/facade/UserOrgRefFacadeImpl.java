/*
 * @(#)AuthManaFacade.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.UserOrgRefDto;
import com.vanrui.app.user.service.UserOrgRefService;

import ooh.bravo.service.BaseService;

/**
 * 用户机构关系表
 * 
 * @author maji01
 * @date 2016年10月8日 上午8:58:23
 * @version V1.0.0 description：
 * 
 */
@Service("userOrgRefFacade")
public class UserOrgRefFacadeImpl extends BaseService implements UserOrgRefFacade {

    @Autowired
    UserOrgRefService uorgRefService;

    @Override
    public List<UserOrgRefDto> selectUserOrgRefListByUIds(List<Long> uIds) {
        logger.info("[根据用户ID列表 查询用户组织机构关系列表] 入参：uIds=[{}] ", uIds);
        List<UserOrgRefDto> response = null;
        try {
            response = uorgRefService.selectUserOrgRefListByUIds(uIds);
            logger.info("[根据用户ID列表 查询用户组织机构关系列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据用户ID列表 查询用户组织机构关系列表]异常: {}", ex);
        }
        return response;
    }
    
    @Override
	public boolean validateUserOrgRef(Long userId, Long orgId) {
    	logger.info("[校验指定用户是否拥有该地区有权限] 入参：userId=[{}],orgId=[{}] ", userId, orgId);
        Boolean response = null;
        try {
            response = uorgRefService.validateUserOrgRef(userId, orgId);
            logger.info("[校验指定用户是否拥有该地区有权限]成功.");
        } catch (Exception ex) {
            logger.error("[校验指定用户是否拥有该地区有权限]异常: {}", ex);
        }
        return response;
	}

	@Override
    public List<Long> selectUserOrgRefList(Long uId, String combineCode) {
        logger.info("[根据这组编号和用户ID查询用户ID列表] 入参：uId={},combineCode= ", uId, combineCode);
        List<Long> response = null;
        try {
            response = uorgRefService.selectUserOrgRefList(uId, combineCode);
            logger.info("[根据这组编号和用户ID查询用户ID列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据这组编号和用户ID查询用户ID列表]异常: {}", ex);
        }
        return response;
    }

    /**
     * 根据用户用户姓名与机构组合编码，查询用户列表
     * 
     * @param uId
     * @param combineCode
     * @return 返回null:发生异常，返回 [ ]没有符合条件的数据
     */
    @Override
    public List<Long> selectUserIdList(String userName, String combineCode) {
        logger.info("[根据这组编号和用户姓名查询用户ID列表] 入参：userName={},combineCode= ", userName, combineCode);
        List<Long> response = null;
        try {
            response = uorgRefService.selectUserIdList(userName, combineCode);
            logger.info("[根据这组编号和用户姓名查询用户ID列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据这组编号和用户姓名查询用户ID列表]异常: {}", ex);
        }
        return response;
    }
    
    /**
     * 根据用户用户姓名与机构组合编码，查询用户列表
     * 
     * @param uId
     * @param combineCode
     * @return 返回null:发生异常，返回 [ ]没有符合条件的数据
     */
    @Override
    public List<Long> selectUserIdList(String userName, List<String> combineCodeList) {
        logger.info("[根据编号列表和用户姓名查询用户ID列表] 入参：userName={}, combineCodeList= ", userName, combineCodeList);
        List<Long> response = null;
        try {
            response = uorgRefService.selectUserIdList(userName, combineCodeList);
            logger.info("[根据编号列表和用户姓名查询用户ID列表]成功.");
        } catch (Exception ex) {
            logger.error("[根据编号列表和用户姓名查询用户ID列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public List<UserOrgRefDto> selectUserOrgRefByUId(Long uId) {
        logger.info("[查询指定用户组织结构关系列表] 入参：uId={} ", uId);
        List<UserOrgRefDto> response = null;
        try {
            response = uorgRefService.selectUserOrgRefByUId(uId);
            logger.info("[查询指定用户组织结构关系列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询指定用户组织结构关系列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public List<UserOrgRefDto> selectCurUserOrgRefByUId() {
        logger.info("[查询当前用户组织结构关系列表] 开始 ");
        List<UserOrgRefDto> response = null;
        try {
            response = uorgRefService.selectCurUserOrgRefByUId();
            logger.info("[查询当前用户组织结构关系列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询当前用户组织结构关系列表]异常: {}", ex);
        }
        return response;
    }

    @Override
    public List<String> selectUserCombinationCodeList(Long uId) {
        logger.info("[查询指定用户组织结构组合编码列表] 入参：uId={} ", uId);
        List<String> response = null;
        try {
            response = uorgRefService.selectUserCombinationCodeList(uId);
            logger.info("[查询指定用户组织结构组合编码列表]成功.");
        } catch (Exception ex) {
            logger.error("[查询指定用户组织结构组合编码列表]异常", ex);
        }
        return response;
    }

    @Override
    public Boolean hasUserUnderOrganization(Long orgId) {
        logger.info("[判断机构下是否存在用户] 参数orgId={} ", orgId);
        Boolean result = null;
        try {
            result = uorgRefService.hasUserUnderOrganization(orgId);
            logger.info("[判断机构下是否存在用户]结果：{}", result);
        } catch (Exception ex) {
            logger.error("[判断机构下是否存在用户]异常: {}", ex);
        }
        return result;
    }

    @Override
    public List<Long> selectUserIds(String combineCode) {
        logger.info("[查询指定机构范围的用户列表] 参数: combineCode={} ", combineCode);
        List<Long> result = null;
        try {
            result = uorgRefService.selectUserIds(combineCode);
            logger.info("[查询指定机构范围的用户列表]结果：{}", result);
        } catch (Exception ex) {
            logger.error("[查询指定机构范围的用户列表]异常: {}", ex);
        }
        return result;
    }
    
    @Override
    public List<Long> selectUserIds(List<String> combineCodeList) {
        logger.info("[查询组织机构列表范围的用户列表] 参数: combineCodeList={} ", combineCodeList);
        List<Long> result = null;
        try {
            result = uorgRefService.selectUserIds(combineCodeList);
            logger.info("[查询组织机构列表范围的用户列表]结果：{}", result);
        } catch (Exception ex) {
            logger.error("[查询组织机构列表范围的用户列表]异常: {}", ex);
        }
        return result;
    }
    

    @Override
    public boolean validateUserOrg(String combineCode) {
        logger.info("[校验用户编码是否在当前用户的权限范围内 ] 参数: combineCode={} ", combineCode);
        boolean result = false ;
        try {
            result = uorgRefService.validateUserOrg(combineCode);
            logger.info("[校验用户编码是否在当前用户的权限范围内 ]结果：{}", result);
        } catch (Exception ex) {
            logger.error("[校验用户编码是否在当前用户的权限范围内 ]异常: {}", ex);
        }
        return result;
    }

	@Override
	public Integer getTotalUser(String[] orgCodes) {
		// TODO Auto-generated method stub
		return uorgRefService.getTotalUser(orgCodes);
	}

    @Override
    public List<String> selectUserPhoneNumList(Long uId,
            String[] orgCodes) {
        logger.info("[查询单前用户所处范围的手机号码列表 ] 参数: uId={},orgCodes={}",uId,orgCodes);
        List<String> result = null ;
        try {
            result = uorgRefService.selectUserPhoneNumList(uId, orgCodes);
            logger.info("[查询单前用户所处范围的手机号码列表 ]结果：{}", result);
        } catch (Exception ex) {
            logger.error("[查询单前用户所处范围的手机号码列表 ]异常: {}", ex);
        }
        return result;
    }

}
