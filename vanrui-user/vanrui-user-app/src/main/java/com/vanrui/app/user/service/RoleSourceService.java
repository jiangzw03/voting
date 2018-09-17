/*
 * @(#)RoleSourceService.java 2016年10月9日
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vanrui.app.order.util.ResponseUtils;
import com.vanrui.app.user.dao.AppSourceDao;
import com.vanrui.app.user.dao.RoleAppmenuDao;
import com.vanrui.app.user.dao.WebSourceDao;
import com.vanrui.app.user.dto.AllSourceDTO;
import com.vanrui.app.user.dto.RoleDto;
import com.vanrui.app.user.dto.RoleSourceUrlDto;
import com.vanrui.app.user.dto.SourceBaseDTO;
import com.vanrui.app.user.dto.SourceDto;
import com.vanrui.app.user.dto.SourceInfoDto;
import com.vanrui.app.user.dto.WebAllSourceDTO;
import com.vanrui.app.user.model.RoleEntity;
import com.vanrui.app.user.model.SourceEntity;
import com.vanrui.app.user.model.UserEntity;
import com.vanrui.app.user.service.cache.SourceCacheService;
import com.vanrui.app.user.service.cache.UserCacheService;
import com.vanrui.app.user.service.cache.UserRoleCacheService;
import com.vanrui.app.user.util.FMLoginValidateUtil;
import com.vanrui.app.user.util.ObjectConverter;
import com.vanrui.app.user.util.UCException;
import com.vanrui.app.user.util.rsa.RsaEncryptDecryptUtils;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.core.util.PropertiesLoader;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;
import ooh.bravo.util.StringUtils;

/**
 *
 * @author maji01
 * @date 2016年10月9日 下午8:39:41
 * @version V1.0.0 description：
 * 
 */
@Service("roleSourceService")
@TransactionalMark
public class RoleSourceService extends BaseService {

    @Autowired
    AppSourceDao asDao;

    @Autowired
    WebSourceDao wsDao;

    @Autowired
    UserRoleCacheService urCache;

    @Autowired
    SourceCacheService sourceCache;

    @Autowired
    RoleAppmenuDao roleAppmenuDao;

    @Autowired
    UserRelationService userRelationService;

    @Autowired
    UserCacheService uCache;

    private static final String FM_URL_PRE = PropertiesLoader.getProperty("FM.URL.PRE");
    private static final String FM_URL_COMMON = PropertiesLoader.getProperty("FM.URL.COMMON");
    private static String [] urlsCommon = null;
    static {
        if(StringUtils.isNotBlank(FM_URL_COMMON)){
            urlsCommon = FM_URL_COMMON.split(",");
        }
    }
    /**
     * 异常待补充 ，待测试 查询app资源中所有角色ID与url的关系，用于鉴权
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
    public List<RoleSourceUrlDto> selectAppAllRoleURLs() {
        // 查询app资源url与rId关系
        return asDao.selectAllrIdAndUrl();
    }

    /**
     * 异常待补充 ，待测试 查询web资源中所有角色ID与url的关系，用于鉴权
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
    public List<RoleSourceUrlDto> selectWebAllRoleURLs() {
        // 查询web资源url与rId关系
        return wsDao.selectAllrIdAndUrl();

    }

    /**
     * 异常待补充 ，待测试 根据角色ID查询app权限详情
     * 
     * @param rId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
    public List<SourceInfoDto> selectAppSourcesByrId(Long rId) {
        if (rId == null) {
            logger.error("角色ID 不能为空  ");
            return null;
        }
        List<SourceEntity> list = asDao.selecDetailListByrId(rId);
        return ObjectConverter.transferSouceInfos(list);
    }

    /**
     * 异常待补充 ，待测试 根据角色ID查询web权限详情
     * 
     * @param rId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
    public List<SourceInfoDto> selectWebSourcesByrId(Long rId) {
        if (rId == null) {
            logger.error("角色ID 不能为空  ");
            return null;
        }
        List<SourceEntity> list = wsDao.selectDetailListByrId(rId);
        return ObjectConverter.transferSouceInfos(list);
    }

    /**
     * 根据userId查询该用户的web权限
     * 
     * @param userId
     * @return
     */
    public List<SourceInfoDto> selectWebSourcesByUid(Long userId) {
        if (userId == null) {
            userId = SystemContextUtils.getUserID();
        }
        List<RoleEntity> rList = urCache.selectByUId(userId);
        if (rList == null || rList.size() == 0) {
            logger.error("获取用户角色失败，导致获取菜单资源失败！");
            return null;
        }
        Long rId = rList.get(0).getrId();
        return this.selectWebSourcesByrId(rId);
    }

    /***
     * 校验用户是否有操作权限
     * 
     * @param keyWord 密码
     * @param url 地址
     * @return 是否有操作权限
     */
    public ResponseDTO<Long> validateAppSourceAuthByuserId(String keyWord, String url) {
        ResponseDTO<Long> responseDTO = new ResponseDTO<Long>();  
        Long fmUserId = FMLoginValidateUtil.getUIdFromKeyword(keyWord,true);
        //fmUserId是否有效
        if (fmUserId == null || fmUserId.equals(0L)) {
            // 校验不通过
            responseDTO.setStatus(ResponseStatus.FAIL.getCode());
            responseDTO.setMessage("校验失败，登录的key无效");
            return responseDTO;
        }
        
        Long uId = userRelationService.selectEbaByFMUId(fmUserId);
        //校验uid是否存在
        if (uId == null) {
            responseDTO.setErrorCode(UCException.ERROR_ID_IS_NOT_EXISTS.getCode());
            responseDTO.setStatus(ResponseStatus.FAIL.getCode());
            responseDTO.setMessage("FM账户未绑定，登录失败");
            return responseDTO;
        }
        
        //校验该账户是否异常或被冻结状态
        UserEntity entity = uCache.selectDetailByUId(uId);
        if (entity != null) {
            // 用户状态
            if (entity.getStatus() == null) {
                responseDTO.setErrorCode(UCException.ERROR_ACCOUNT_EXCEPTION.getCode());
                responseDTO.setStatus(ResponseStatus.FAIL.getCode());
                responseDTO.setMessage("账号异常，请联系你的上级管理人员");
                return responseDTO;
            } else if (entity.getStatus().equals(0)) {
                responseDTO.setErrorCode(UCException.ERROR_ACCOUNT_FREEZE.getCode());
                responseDTO.setStatus(ResponseStatus.FAIL.getCode());
                responseDTO.setMessage("账号已被冻结，如需开通请联系你的上级管理人员");
                return responseDTO;
            }
            // 配置上下文
            SystemContextUtils.setUserAndTenantID(entity.getuId(), "T000001");
        } else {
            responseDTO.setErrorCode(UCException.ERROR_ID_IS_NOT_EXISTS.getCode());
            responseDTO.setStatus(ResponseStatus.FAIL.getCode());
            responseDTO.setMessage("FM账户未绑定，登录失败");
            return responseDTO;
        }

        // 根据用户ID获取角色
        List<RoleEntity> rList = urCache.selectByUId(uId);
        if (rList == null || rList.size() == 0 || StringUtils.isBlank(url)) {
            logger.error("获取用户角色失败，导致获取菜单资源失败！");
            ResponseUtils.failedWithMsg(responseDTO, "获取用户角色失败，导致获取菜单资源失败！");
            return responseDTO;
        }
        
        Long rId = rList.get(0).getrId();
        // 根据角色ID获取可访问的资源
        List<SourceEntity> list = sourceCache.selectRoleSourceRefByRId(rId, false);
        if (list == null || list.isEmpty()) {
            ResponseUtils.failedWithMsg(responseDTO, "获取菜单资源失败！");
            return responseDTO;
        }
        // 校验是否是共用接口
        if(urlsCommon!=null&&urlsCommon.length>0){
            for(int i=0;i<urlsCommon.length;i++){
                if(StringUtils.isBlank(urlsCommon[i])){ 
                    continue;
                }
                if (url.startsWith(urlsCommon[i])) {
                    responseDTO.setData(entity.getuId());
                    responseDTO.setStatus(ResponseStatus.SUCCESS.getCode());
                    return responseDTO;
                }
            }
        }
        //判断是否有维修工单权限  或者 维保工单权限
        for (SourceEntity source : list) {
            String urlPre = source.getsUrl();
            if (StringUtils.isBlank(urlPre)) {
                continue;
            }
            urlPre = FM_URL_PRE + urlPre;
            if (source.getsId().equals(2L) || source.getsId().equals(3L)) {
                if (url.startsWith(urlPre)) {
                    responseDTO.setData(entity.getuId());
                    responseDTO.setStatus(ResponseStatus.SUCCESS.getCode());
                    return responseDTO;
                }
            }
        }
        logger.warn("当前用户无访问url={}的权限", url);
        responseDTO.setStatus(ResponseStatus.FAIL.getCode());
        responseDTO.setMessage("没有处理工单的权限，如果需要请联系您的主管");
        return responseDTO;
    }

    /**
     * 查询所有角色
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
    public List<RoleDto> selectAllROle() {

        List<RoleDto> list = null;
        List<RoleEntity> roles = urCache.selectAllRoles();
        if (roles != null) {
            list = new ArrayList<RoleDto>();
            int len = roles.size();
            for (int i = 0; i < len; i++) {
                RoleDto dto = new RoleDto();
                dto.setrId(roles.get(i).getrId());
                dto.setrName(roles.get(i).getrName());
                list.add(dto);
            }
        }

        return list;
    }

    /**
     * 查询所有资源，用于界面展示
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3)
    public AllSourceDTO selectAllSource() {
        //
        AllSourceDTO dto = new AllSourceDTO();
        List<WebAllSourceDTO> source = null;

        List<SourceDto> allWebs = transFormToSourceDto(sourceCache.selectAllSource(true));
        if (allWebs != null) {
            source = new ArrayList<WebAllSourceDTO>();
            List<SourceDto> ps = new ArrayList<SourceDto>();
            int len = allWebs.size();
            int status = 0;
            // 选择模块
            for (int i = 0; i < len; i++) {
                if (allWebs.get(i).getPsId() == 1) {
                    status = 1;
                    ps.add(allWebs.get(i));
                    continue;
                }
                if (status == 1) {
                    break;
                }
            }
            int psLen = ps.size();
            // 根据模块分类
            for (int i = 0; i < psLen; i++) {
                SourceDto tmp = ps.get(i);
                WebAllSourceDTO tempWeb = new WebAllSourceDTO();
                // Map<String,List<SourceDto>> tmpMap =new
                // HashMap<String,List<SourceDto>>();
                List<SourceDto> list = new ArrayList<SourceDto>();
                for (int j = 0; j < len; j++) {
                    if (allWebs.get(j).getPsId().equals(tmp.getsId())) {
                        list.add(allWebs.get(j));
                        continue;
                    }
                }
                if (list.size() > 0) {
                    tempWeb.setSonSource(list);
                    tempWeb.setSourceName(tmp.getsName());
                    source.add(tempWeb);
                }
            }
        }
        // 查所有app资源
        dto.setAppSource(transFormToSourceDto(sourceCache.selectAllSource(false)));
        // 查所有web资源
        dto.setWebSource(source);
        return dto;
    }

    public List<SourceBaseDTO> selectWebSourcesBycurUId() {
        Long uId = SystemContextUtils.getUserID();
        if (uId == null) {
            logger.error("从上下文获取用户id失败[uId=null]，导致获取菜单资源失败！");
            return null;
        }
        List<RoleEntity> rList = urCache.selectByUId(uId);
        if (rList == null || rList.size() == 0) {
            logger.error("获取用户角色失败，导致获取菜单资源失败！");
            return null;
        }
        Long rId = rList.get(0).getrId();
        // 查询web资源
        List<SourceEntity> list = wsDao.selectBaseListByrId(rId);
        if (list != null && list.size() > 0) {
            // 归类
            SourceEntity enti = null;
            List<SourceEntity> modulList = new ArrayList<SourceEntity>();
            List<SourceEntity> pageList = new ArrayList<SourceEntity>();
            // List<SourceEntity> elementList = new ArrayList<SourceEntity>();
            int len = list.size();
            for (int i = 0; i < len; i++) {
                enti = list.get(i);
                switch (enti.getsType()) {
                case 1:// 模块
                    modulList.add(enti);
                    break;
                case 2:// 页面资源
                    pageList.add(enti);
                    break;
                // 按钮资源 不考虑页面元素，后续补充
                // default: elementList.add(enti);
                }
            }
            return dealParentSonRef(modulList, pageList);
        }

        logger.error("通过角色获取web端菜单资源失败！");
        return null;
    }

    private List<SourceBaseDTO> dealParentSonRef(List<SourceEntity> pList, List<SourceEntity> sList) {
        if (sList == null || sList.size() == 0) {
            return null;
        }
        List<SourceBaseDTO> list = new ArrayList<SourceBaseDTO>();
        SourceBaseDTO tmpDTO = null;
        int len = sList.size();
        for (SourceEntity pl : pList) {
            // 添加模块到list中
            tmpDTO = transFormToSourceBaseDTO(pl);
            list.add(tmpDTO);
            for (int i = 0; i < len; i++) {
                if (sList.get(i).getPsId().equals(pl.getsId())) {
                    // 添加页面资源到模块中
                    refToSourceBaseDTO(tmpDTO, sList.get(i));
                }
            }
        }
        return list;
    }

    public List<Long> selectRolesByOneRight(Long sId) {
        return this.roleAppmenuDao.selectRolesByOneRight(sId);
    }

    /**
     * 建立父子关系
     * 
     * @param pEnti
     * @param sEnti
     * @return
     */
    private void refToSourceBaseDTO(SourceBaseDTO pDTO, SourceEntity sEnti) {
        SourceBaseDTO sDTO = transFormToSourceBaseDTO(sEnti);
        List<SourceBaseDTO> tmpList = pDTO.getSonDTOList();
        if (tmpList == null) {
            tmpList = new ArrayList<SourceBaseDTO>();
            pDTO.setSonDTOList(tmpList);
        }
        tmpList.add(sDTO);

    }

    private SourceBaseDTO transFormToSourceBaseDTO(SourceEntity enti) {
        SourceBaseDTO dto = new SourceBaseDTO();

        dto.setsId(enti.getsId());
        dto.setPsId(enti.getPsId());
        dto.setsName(enti.getsName());
        dto.setsType(enti.getsType());
        dto.setsCode(enti.getsCode());
        dto.setStatus(enti.getStatus());
        dto.setsUrl(enti.getsUrl());

        return dto;
    }

    private List<SourceDto> transFormToSourceDto(List<SourceEntity> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<SourceDto> dtos = new ArrayList<SourceDto>();
        SourceDto dto = null;
        for (SourceEntity enti : list) {
            if (enti.getsType() > 2) {
                // 过滤掉 页面中的url级别的资源
                continue;
            }
            dto = new SourceDto();
            dto.setsId(enti.getsId());
            dto.setPsId(enti.getPsId());
            dto.setsName(enti.getsName());
            dto.setsType(enti.getsType());
            dtos.add(dto);
        }
        return dtos;
    }
}
