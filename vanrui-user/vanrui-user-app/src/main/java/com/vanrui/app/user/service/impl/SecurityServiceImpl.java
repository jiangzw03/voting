/*
 * @(#)SecurityServiceImpl.java 2016年10月13日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ooh.bravo.security.web.SecurityService;
import ooh.bravo.security.web.dto.AuthorizationDTO;
import ooh.bravo.security.web.dto.ResourceAuthorizationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vanrui.app.user.dao.AppSourceDao;
import com.vanrui.app.user.dao.UserDao;
import com.vanrui.app.user.dao.UserRoleDao;
import com.vanrui.app.user.dao.WebSourceDao;
import com.vanrui.app.user.model.RoleEntity;
import com.vanrui.app.user.model.SourceAuthEntity;

/**
 *
 * @author maji01
 * @date 2016年10月13日 下午7:58:20
 * @version V1.0.0 description：
 * 
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserRoleDao urDao;
    @Autowired
    UserDao uDao;
    @Autowired
    WebSourceDao webDao;
    @Autowired
    AppSourceDao appDao;

    @Override
    public AuthorizationDTO getAuthorizationsByUserId(Long userID) {
        List<RoleEntity> roles = urDao.selectRolesByUId(userID);
        AuthorizationDTO result = null;
        if (roles != null && roles.size() > 0) {
            Set<String> set = new HashSet<String>();
            int len = roles.size();
            for (int i = 0; i < len; i++) {
                set.add(String.valueOf(roles.get(i).getrId()));
            }
            result = new AuthorizationDTO();
            result.setRoles(set);
            result.setUserID(userID);
        }
        return result;
    }

    private ResourceAuthorizationDTO getResourceAuthorizationDTO(SourceAuthEntity entity) {
        ResourceAuthorizationDTO dto = new ResourceAuthorizationDTO();
        dto.setId(entity.getsId());
        dto.setUrl(entity.getsUrl());
        String temp = entity.getrIds();
        Set<String> rIds = new HashSet<String>();
        if (StringUtils.hasLength(temp)) {
            if (temp.contains(",")) {
                String[] tps = temp.split(",");
                for (int i = 0; i < tps.length; i++) {
                    rIds.add(tps[i]);
                }
            } else {
                rIds.add(temp);
            }
            dto.setRoles(rIds);
        }
        return dto;
    }

    @Override
    public Map<String, List<ResourceAuthorizationDTO>> getResourceAuthorizationList() {
        // 查询权限
        Map<String, List<ResourceAuthorizationDTO>> hashMap = new HashMap<String, List<ResourceAuthorizationDTO>>();
        List<ResourceAuthorizationDTO> list = new ArrayList<ResourceAuthorizationDTO>();
        List<SourceAuthEntity> webAuths = webDao.selectAllWebUrlAndAuths();
        int len = 0;
        if (webAuths != null) {
            len = webAuths.size();
            for (int i = 0; i < len; i++) {
                list.add(getResourceAuthorizationDTO(webAuths.get(i)));
            }
        }
        List<SourceAuthEntity> appAuths = appDao.selectAllWebUrlAndAuths();
        if (appAuths != null) {
            len = appAuths.size();
            for (int i = 0; i < len; i++) {
                list.add(getResourceAuthorizationDTO(appAuths.get(i)));
            }
        }
        // 初始化，所有租户
        hashMap.put("T000001", list);
        return hashMap;
    } 

    @Override
    public List<ResourceAuthorizationDTO> getResourceAuthorizationList(String tenantID) {

        List<ResourceAuthorizationDTO> list = new ArrayList<ResourceAuthorizationDTO>();
        List<SourceAuthEntity> webAuths = webDao.selectAllWebUrlAndAuths();
        int len = 0;
        if (webAuths != null) {
            len = webAuths.size();
            for (int i = 0; i < len; i++) {
                list.add(getResourceAuthorizationDTO(webAuths.get(i)));
            }
        }
        List<SourceAuthEntity> appAuths = appDao.selectAllWebUrlAndAuths();
        if (appAuths != null) {
            len = appAuths.size();
            for (int i = 0; i < len; i++) {
                list.add(getResourceAuthorizationDTO(appAuths.get(i)));
            }
        }
        return list;
    }

}
