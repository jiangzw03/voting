/*
 * @(#)AuthManaService.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.constant.ResponseStatus;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;
import ooh.bravo.mybatis.util.CountHelper;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vanrui.app.user.dao.AppSourceDao;
import com.vanrui.app.user.dao.RoleAppmenuDao;
import com.vanrui.app.user.dao.RoleDao;
import com.vanrui.app.user.dao.RoleWebmenuDao;
import com.vanrui.app.user.dao.UserRoleDao;
import com.vanrui.app.user.dao.WebSourceDao;
import com.vanrui.app.user.dto.AuthDetailsDto;
import com.vanrui.app.user.dto.AuthDto;
import com.vanrui.app.user.dto.AuthInfoDto;
import com.vanrui.app.user.model.RoleEntity;
import com.vanrui.app.user.model.RoleMenuAuthEntity;
import com.vanrui.app.user.model.SourceEntity;
import com.vanrui.app.user.service.cache.SourceCacheService;
import com.vanrui.app.user.service.cache.UserRoleCacheService;
import com.vanrui.app.user.util.ObjectConverter;
import com.vanrui.app.user.util.RowBoundsUtil;
import com.vanrui.app.user.util.UCException;

/**
 *
 * @author maji01
 * @date 2016年10月8日 下午10:25:57
 * @version V1.0.0
 * description：
 * 
 */
@Service("authManaService")
@TransactionalMark
public class AuthManaService   extends BaseService {
   
    @Autowired
    RoleDao  rDao;
    @Autowired
    RoleWebmenuDao    rwDao;
    @Autowired
    RoleAppmenuDao    raDao;
    @Autowired
    UserRoleDao       urDao;
    @Autowired
    AppSourceDao      appSourceDao;
    @Autowired
    WebSourceDao      webSourceDao;
    
    @Autowired
    UserRoleCacheService    userRoleCache;
    @Autowired
    SourceCacheService    sourceCache;
    /** 
     * 新增角色
     * @param authObj
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
    public  ResponseDTO<Integer>  insert(AuthDetailsDto authObj) {
        ResponseDTO<Integer>    response = new ResponseDTO<Integer>(); 
        // 角色对象校验
        RoleEntity  role = getRoleEntity("I",authObj,response);
        logger.info("新增角色：校验过之后的RoleEntity  role =[{}]",authObj);
        if( role == null ){
            return  response;
        }
        // 资源校验
        List<RoleMenuAuthEntity> webs = this.getBytype("W", authObj,response);
        List<RoleMenuAuthEntity> app = this.getBytype("A", authObj,response);
        if(response.getData()!=null&&response.getData().equals(0)){
            return response;
        }
         
        // 新增 角色
        rDao.insert(role);
        printDebug("新增角色");
        setRIdForList( webs,role.getrId()); 
        setRIdForList( app,role.getrId()); 
        // 新增角色与web资源的关系 
        if( webs !=null ){ 
            rwDao.insertAuth(webs);
        }else{ 
            printDebug("为角色分配 web资源时，发现web资源 为空 [ ] ，跳过");
        } 
        // 新增角色与app资源的关系
        if( app !=null ){ 
            raDao.insertAuth(app);
        }else{
            printDebug("为角色分配 app资源时，发现app资源 为空 [ ]，跳过 ！ ");
        }
        // 刷新角色列表的缓存
        logger.info("[新增角色] 成功，刷新角色列表缓存");
        userRoleCache.refreshAllRolesCache();
        response.setData(1);
        response.setStatus(ResponseStatus.SUCCESS.getCode());
        return  response;
    }

    
    /**  
     * 修改角色
     * @param authObj
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
    public ResponseDTO<Integer>  update(AuthDetailsDto authObj) {
        ResponseDTO<Integer>    response = new ResponseDTO<Integer>();
        
        // 角色是否存在，不存在，返回并提示 
        RoleEntity  role = getRoleEntity("U",authObj, response);
        if( role == null ){
            return  response;
        }
        // 校验资源是否存在，不通过会返回null，并且记录异常原因
        List<RoleMenuAuthEntity> webs = this.getBytype("W", authObj,response);
        List<RoleMenuAuthEntity> app = this.getBytype("A", authObj,response);
        if(response.getData()!=null&&response.getData().equals(0)){
            return response;
        }
        
        // 修改 角色
        rDao.update(role);
        authObj.setrId(role.getrId());
        //  重建角色与web菜单资源间的关系
        if( webs !=null && webs.size() > 0 ){ 
            rwDao.deleteAuth(role.getrId());
            rwDao.insertAuth(webs);
        }else{ 
            rwDao.deleteAuth(role.getrId());
        }
        // 重建角色与app菜单资源间的关系
        if( app !=null && app.size() > 0 ){ 
            raDao.deleteAuth(role.getrId());
            raDao.insertAuth(app);
        }else{ 
            raDao.deleteAuth(role.getrId());
        }
        // 刷新角色列表的缓存
        logger.info("[修改角色] 成功，刷新角色列表缓存");
        userRoleCache.refreshAllRolesCache();
        // TODO 刷新角色资源关系列表
        sourceCache.refresh(role.getrId());
        response.setData(1);
        response.setStatus(ResponseStatus.SUCCESS.getCode());
        response.setMessage(ResponseStatus.SUCCESS.getMsg());
        return  response;
    }

    
    /**  
     * 超级管理员--修改自己
     * @param authObj
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
    public ResponseDTO<Integer>  updateMyself(AuthDetailsDto authObj) {
        ResponseDTO<Integer>    response = new ResponseDTO<Integer>();
        
        // 角色是否存在，不存在，返回并提示 
        RoleEntity  role = getRoleEntity("U",authObj, response);
        if( role == null ){
            return  response;
        }
        // 校验资源是否存在，不通过会返回null，并且记录异常原因
        List<RoleMenuAuthEntity> webs = this.getBytype("W", authObj,response);
        List<RoleMenuAuthEntity> app = this.getBytype("A", authObj,response);
        if(response.getData()!=null&&response.getData().equals(0)){
            return response;
        }
        
        // 修改 角色
        rDao.update(role);
        authObj.setrId(role.getrId());
        //  重建角色与web菜单资源间的关系
        if( webs !=null && webs.size() > 0 ){ 
            rwDao.deleteAuth(role.getrId());
            rwDao.insertAuth(webs);
        }else{ 
            rwDao.deleteAuth(role.getrId());
        }
        // 重建角色与app菜单资源间的关系
        if( app !=null && app.size() > 0 ){ 
            raDao.deleteAuth(role.getrId());
            raDao.insertAuth(app);
        }else{ 
            raDao.deleteAuth(role.getrId());
        }
        // 刷新角色列表的缓存
        logger.info("[修改角色] 成功，刷新角色列表缓存");
        userRoleCache.refreshAllRolesCache();
        response.setData(1);
        response.setStatus(ResponseStatus.SUCCESS.getCode());
        response.setMessage(ResponseStatus.SUCCESS.getMsg());
        return  response;
    }
     
     /**
      * 删除角色
      * @param roleId
      * @return
      */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
    public  ResponseDTO<Integer>   deleteByRoleId(Long roleId) {
        ResponseDTO<Integer>    response = new ResponseDTO<Integer>();
        if( roleId == null){
            logger.error("[ 删除角色 ]入参：roleId不能为空，删除失败");
            response.setData(0);
            response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
            response.setMessage(UCException.ERROR_NULLPOINT_FIELD.getMessage()+": 删除角色，角色ID不能为空，删除失败！");
            response.setStatus(ResponseStatus.FAIL.getCode());
            return  response;
        }
        // 当前用户是否登录，未登录，不允许操作删除
        Long uId = SystemContextUtils.getUserID(); 
        if(uId == null){
            logger.error("[ 删除角色 ] 异常：未登录用户无权操作，删除失败");
            response.setData(0);
            response.setErrorCode(UCException.ERROR_SYSTEM_CONTEXT_NULL.getCode());
            response.setMessage(UCException.ERROR_SYSTEM_CONTEXT_NULL.getMessage() );
            response.setStatus(ResponseStatus.FAIL.getCode());
            return  response;
        }
        // 角色是否存在，不存在，就直接返回
        if(isNotExistForRoleId( roleId )){
            response.setData(0);
            response.setErrorCode(UCException.ERROR_ID_IS_NOT_EXISTS.getCode());
            response.setMessage(UCException.ERROR_ID_IS_NOT_EXISTS.getMessage()+": 删除失败，角色ID不存在:rId="+ roleId );
            response.setStatus(ResponseStatus.FAIL.getCode());
            return response;
        }
        // 判断角色是否有绑定用户
        Integer  ex = urDao.isExistBindUsersByRoleId(roleId);
        if(ex != 0){
            logger.error("[ 删除角色 ] 异常：角色有绑定用户，删除失败"); 
            // 角色有绑定用户，不能删除，应该抛出异常
            response.setData(0); 
            response.setStatus(ResponseStatus.FAIL.getCode());
            response.setErrorCode(UCException.ERROR_HAVED_BIND_ROLE.getCode());
            response.setMessage(UCException.ERROR_HAVED_BIND_ROLE.getMessage());
            return  response;
        }
        printDebug("角色没有绑定用户,开始删除角色。。。");
        // 删除角色与web资源的关系
        rwDao.deleteAuth(roleId);
        printDebug("删除角色与web资源的关系");
        // 删除角色与app资源的关系
        raDao.deleteAuth(roleId);
        printDebug("删除角色与app资源的关系"); 
        // 删除角色 
        rDao.deleteByRoleId(roleId); 
        logger.info("用户[uId={}]删除角色[rId={}] 成功 。",uId,roleId);
        // 刷新缓存 
        logger.info("[新增角色] 成功，刷新角色列表缓存");
        userRoleCache.refreshAllRolesCache();
        response.setData(1);
        response.setStatus(ResponseStatus.SUCCESS.getCode());
        return  response;
    }
    
    private   void   setRIdForList(List<RoleMenuAuthEntity> app,Long rId){
        if(app == null||rId==null ){
            return ;
        }
        for(int i=0;i<app.size();i++){
            app.get(i).setrId(rId);
        }
    }
    
    /**   分页还有问题
     * 查询权限信息
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
    public PageResponseDTO<AuthDto> selectAllAuthority(int pageNum, int pageSize) {
         
        RowBounds   bound = RowBoundsUtil.getRowBounds(pageNum, pageSize);
        List<AuthDto> list = rDao.selectAllAuthority(bound);
        PageResponseDTO<AuthDto>   responseDto = new PageResponseDTO<>();
        responseDto.setData(list);
        responseDto.setTotalRow(CountHelper.getTotalRow());
        return responseDto;
    }
    /**
     * 查询角色信息
     * @param rId   rId=null时，不查询角色与资源的关系，rId非空的时，按照rId来查询
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 5)
    public AuthInfoDto selectAuthority(Long rId) {
        AuthInfoDto dto = new AuthInfoDto();
        if(rId == null){ 
            // app 资源查询  
            dto.setAuthsApp(ObjectConverter.transferSouceObjs(appSourceDao.selectAll()));
            // web 资源查询
            dto.setAuthsWeb(ObjectConverter.transferSouceObjs(webSourceDao.selectAll())); 
            return dto;
        }
        RoleEntity entity = rDao.selectRoleInfoByRoleId(rId);
        dto.setrId(entity.getrId());
        dto.setrName(entity.getrName());
        // app 资源查询  
        dto.setAuthsApp(ObjectConverter.transferSouceObjs(appSourceDao.selectAllByRoleId(rId)));
        // web 资源查询
        dto.setAuthsWeb(ObjectConverter.transferSouceObjs(webSourceDao.selectAllByRoleId(rId))); 
        return dto;
    }

    /**
     * 根据类型来封装关系对象
     * @param type
     * @param authObj
     * @return
     */
    private  List<RoleMenuAuthEntity>   getBytype(String  type,AuthDetailsDto authObj,ResponseDTO<Integer>    response){
        List<Long> temp = null;
        List<SourceEntity> allSs = null;
        // 页面内部的按钮事件，请求
        List<RoleMenuAuthEntity>   sonAuth = null;
        if(type.equals("W")){
            // web端资源
            temp = authObj.getAuthsWeb();
            allSs =sourceCache.selectAllSource(true);
        }else if(type.equals("A")){
            // app端资源
            temp = authObj.getAuthsApp();
            allSs =sourceCache.selectAllSource(false);
        } 
        // 
        sonAuth = getSonSource( allSs,temp,authObj.getrId() );
        if( sonAuth !=null ){
            List<RoleMenuAuthEntity>   list = new ArrayList<RoleMenuAuthEntity>();
            list.addAll( sonAuth );
            RoleMenuAuthEntity   entity = null;
            int len = temp.size();
            int size = allSs.size();
            for(int i=0;i<len;i++ ){
                if(isExistsInList( temp.get(i), allSs,size)){
                    // 合法的资源ID
                    entity = new RoleMenuAuthEntity();
                    entity.setrId(authObj.getrId());
                    entity.setsId(temp.get(i));                     
                    list.add(entity);
                }else{
                    logger.error("角色资源关系中，存在不合法的资源ID：sId={}，",temp.get(i));
                    response.setData(0);
                    response.setStatus(ResponseStatus.FAIL.getCode());
                    response.setMessage(ResponseStatus.FAIL.getMsg()+": 角色资源关系中，存在未知资源ID：sId="+temp.get(i));
                    return null;
                } 
            }
            return  list;
        } 
        return null;
    }
    
    /**
     * 查询按钮资源，用于给角色分配资源时
     * @param allSs
     */
    private  List<RoleMenuAuthEntity>    getSonSource(List<SourceEntity> allSs,List<Long> ids,Long roleId){
        if(ids == null || ids.isEmpty() || allSs == null || allSs.isEmpty() ){
            return null;
        }
        int len = allSs.size();
        List<RoleMenuAuthEntity>   list = new  ArrayList<RoleMenuAuthEntity>(); 
        SourceEntity  tmp = null;
        for( int i=0;i<len;i++){
            tmp = allSs.get(i);
            if(tmp.getsType() > 2 && ids.contains( tmp.getPsId() ) ){
                RoleMenuAuthEntity entity = new RoleMenuAuthEntity();
                entity.setrId( roleId );
                entity.setsId( tmp.getsId() );
                list.add(entity);
            }
        }
        return list;
    }
    
    /**
     * 是否存在指定的资源ID，不存在就表示不合法的
     * @param sId
     * @param allSs
     * @param size
     * @return
     */
    private   boolean   isExistsInList(Long sId,List<SourceEntity> allSs,int size){
        // 存在返回true
        for(int j=0;j<size;j++){
            if(allSs.get(j).getsId().equals( sId )){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 整理对象 
     * @param type
     * @param authObj
     * @return
     */
    private  RoleEntity   getRoleEntity(String  type,AuthDetailsDto authObj,ResponseDTO<Integer>  response){
        // 校验填字段 
        if( ! isValidateRoleEntity(type,authObj,response) ){
            return null;
        }
        RoleEntity   role = new RoleEntity(); 
        role.setrName(authObj.getrName()); 
        if(type.equals("U")){
            // web端资源
            role.setrId(authObj.getrId());
            role.setUpdator(authObj.getCreator());
            role.setUpdateTime(authObj.getCreateTime());
        }else if(type.equals("I")&& authObj.getrName() != null && authObj.getCreator() != null ){
            // app端资源
            role.setCreator(authObj.getCreator());
            role.setCreateTime(authObj.getCreateTime());
        } 
        return  role;
    }
    
    /**
     * 打印debug日志
     * @param debug
     */
    private   void  printDebug(String debug){
        if(logger.isDebugEnabled()){
            logger.debug(debug);
        }
    }
    
    private  boolean   isValidateRoleEntity(String  type,AuthDetailsDto authObj,ResponseDTO<Integer>  response){
        // 获取上下文
        Long  roleId = null;
        Long uId  = SystemContextUtils.getUserID();
        if(uId == null){
            logger.error("从上下文获取操作人ID失败，本次操作无效,[{}] ",authObj);
            response.setData(0); 
            response.setErrorCode(UCException.ERROR_SYSTEM_CONTEXT_NULL.getCode());
            response.setMessage(UCException.ERROR_SYSTEM_CONTEXT_NULL.getMessage()+": 从上下文获取操作人ID失败，本次操作无效");
            response.setStatus(ResponseStatus.FAIL.getCode());
            return false;
        }
        if(type.equals("U")){
            if(authObj == null 
                    || authObj.getrName() == null 
                    || authObj.getrId() == null ){
                response.setData(0);
                response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
                response.setMessage("修改角色，[角色ID,角色名]都是必填项，非空检查不通过");
                response.setStatus(ResponseStatus.FAIL.getCode());
                logger.error("新增角色，[角色ID,角色名]都是必填项，非空检查不通过");
                return false;
            } 
            if( authObj.getrId().equals( 1l )){
                response.setData(0);
                response.setMessage( "系统默认“超级管理员”角色禁止修改" );
                response.setStatus(ResponseStatus.FAIL.getCode());
                logger.error("修改角色失败，系统默认超级管理员角色禁止修改:[rId={}]",authObj.getrId());
                return false;
            }else if(isNotExistForRoleId( authObj.getrId() )){
                response.setData(0);
                response.setErrorCode(UCException.ERROR_ID_IS_NOT_EXISTS.getCode());
                response.setMessage(UCException.ERROR_ID_IS_NOT_EXISTS.getMessage()+": 修改角色失败，角色ID不存在:rId="+ authObj.getrId() );
                response.setStatus(ResponseStatus.FAIL.getCode());
                logger.error("修改角色失败，角色ID不存在:[rId={}]",authObj.getrId());
                return false;
            }
            if(authObj.getCreateTime() == null ){
                authObj.setUpdateTime(new Date());
            } 
            if(authObj.getUpdator() == null ){
                authObj.setUpdator(uId);
            } 
            roleId = authObj.getrId();
        }else if(type.equals("I") && authObj != null && authObj.getrName() != null  ){
            if(authObj == null 
                    || authObj.getrName() == null   ){
                response.setData(0); 
                response.setErrorCode(UCException.ERROR_NULLPOINT_FIELD.getCode());
                response.setMessage( "新增角色，[角色名称]是必填项，非空检查不通过");
                response.setStatus(ResponseStatus.FAIL.getCode());
                logger.error("新增角色，[角色名称]是必填项，非空检查不通过");
                return false;
            } 
            
            if(authObj.getCreator() == null ){ 
                authObj.setCreator(uId);
            }
            if(authObj.getCreateTime() == null ){
                authObj.setCreateTime(new Date());
            }   
        } 
     // 校验角色名称是否重复
        if( authObj != null && this.rDao.isExistsForRoleName( authObj.getrName() , roleId ) != null ){
            response.setData(0); 
            response.setErrorCode(UCException.ERROR_REPEAT_EXCEPTION.getCode());
            response.setMessage( "角色名称已被使用，请检查是否重复录入");
            response.setStatus(ResponseStatus.FAIL.getCode());
            logger.error("角色名称已被使用，请检查是否重复录入:[roleName={}]", authObj.getrName());
            return false;
        }
        return  true;
    } 
    
    private   boolean   isNotExistForRoleId(Long rId){
        if(rId==null|| rId.equals(0l)){
            return true;
        }
        List<RoleEntity>  allRoles = userRoleCache.selectAllRoles(); 
        for(RoleEntity r:allRoles){
            if(rId.equals(r.getrId())){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 根据角色ID查询用户ID列表
     * @param roleId
     * @return
     */
    public List<Long> selectUserIdsByRoleId(Long roleId) {
        return urDao.selectUserIdsByRoleId(roleId);
    }
    
    
    public ResponseDTO<Long> webVisitValidate(String url) {
		logger.info("[校验用户访问是否允许]入参: url={}", url);
		ResponseDTO<Long> response = new ResponseDTO<Long>();
		Long userId = SystemContextUtils.getUserID();
		if(userId==null){ 
			response.setStatus(ResponseStatus.FAIL.getCode());
			response.setMessage("用户未登陆，校验失败");
			return response;
		}
		List<SourceEntity> sourceList = null;
		List<RoleEntity> roles = userRoleCache.selectByUId(userId);
		if(roles!=null&&!roles.isEmpty()){
			Long roleId = roles.get(0).getrId();
			sourceList = sourceCache.selectRoleSourceRefByRId(roleId,true);
		}
		if(sourceList!=null&&!sourceList.isEmpty()){
			for(SourceEntity entity:sourceList){
				if(entity.getsUrl()!=null&&url.startsWith(entity.getsUrl())){
					response.setStatus(ResponseStatus.SUCCESS);
					return response; 
				}
			}
		}  
		response.setStatus(ResponseStatus.FAIL.getCode());
		response.setMessage("无访问权限，校验失败");
		return response; 
	}
    
     
}
