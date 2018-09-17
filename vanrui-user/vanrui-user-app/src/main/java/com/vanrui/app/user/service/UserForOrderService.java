/*
 * @(#)UserForOrder.java 2016年12月13日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.org.constant.OrgLevleType;
import com.vanrui.app.org.dto.OrganizationDTO;
import com.vanrui.app.org.facade.OrganizationInfoFacade;
import com.vanrui.app.user.dao.UserDao;
import com.vanrui.app.user.dao.UserOrgRefDao;
import com.vanrui.app.user.dao.UserSkillRefDao;
import com.vanrui.app.user.dto.UserBaseDto;
import com.vanrui.app.user.dto.UserForOrderDTO;
import com.vanrui.app.user.dto.UserInfoDto;
import com.vanrui.app.user.model.UserForOrdersDTO;
import com.vanrui.app.user.service.cache.UserSkillCacheService;
import com.vanrui.app.user.util.RowBoundsUtil;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.dto.PageRequestDTO;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.mybatis.util.CountHelper;
import ooh.bravo.service.BaseService;
import ooh.bravo.tx.annotation.TransactionalMark;
import ooh.bravo.util.StringUtils;

/**
 *
 * @author fanhuajun
 * @date 2016年12月13日 下午3:49:07
 * @version V1.0.0
 * description：
 * 
 */
@Service("userForOrderService")
@TransactionalMark
public class UserForOrderService extends BaseService {
    
    @Autowired
    UserSkillCacheService userSkillCache;
    
    @Autowired
    UserCenterService userCenterService;
    
    @Autowired
    OrganizationInfoFacade organizationInfoFacade;
    
    @Autowired
    UserSkillRefDao userSkillRefDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    UserOrgRefDao userOrgRefDao;
    
    public PageResponseDTO<UserForOrderDTO> getUserForOrder(PageRequestDTO<SearchUserCondition> param) {
        // 安分页查询用户信息
        
        PageResponseDTO<UserForOrderDTO> userForOrderWebPage = new PageResponseDTO<UserForOrderDTO>();
        
        List<UserForOrderDTO> userForOrderWebList;
        List<UserBaseDto> userBaseDtoList;
        userBaseDtoList = userCenterService.selectAssignOrderUsers(param);
        List<Long> uIds = new ArrayList<Long>();
        
        for(UserBaseDto baseDto :userBaseDtoList){
            uIds.add(baseDto.getuId());
        }
        
        PageResponseDTO<UserInfoDto> responseDto = new PageResponseDTO<UserInfoDto>();
        if (uIds != null && uIds.size() > 0) {
            // 存在符合条件的ID列表，则根据ID列表查询用户信息列表并按照时间排序(逆序)
            List<UserInfoDto> list = userDao.selectUsersByUids(uIds, null);
            responseDto.setData(list);
        }
        
        userForOrderWebList = processNames(responseDto);
        
        userForOrderWebPage.setData(userForOrderWebList);
        userForOrderWebPage.setTotalRow(CountHelper.getTotalRow());
//        userForOrderWebPage.setTotalRow(userForOrderWebList.size());
        return userForOrderWebPage;
    }
    
    
    
private List<UserForOrderDTO> processNames(PageResponseDTO<UserInfoDto> pageResponseDTO){
        
        List<UserForOrderDTO> userForOrderWebList = new ArrayList<UserForOrderDTO>();
        
        if(pageResponseDTO.getData() != null && pageResponseDTO.getData().size() > 0){
            
                    Map<Long, List<Long>> userOrgMap = new HashMap<Long, List<Long>>();
                    for(UserInfoDto userInfoDto : pageResponseDTO.getData()){
                        //获取用户orgId
                        if(StringUtils.isNotBlank(userInfoDto.getJgs())){
                            String[] items = userInfoDto.getJgs().split("\\,");
                            List<Long> orgList = new ArrayList<Long>();
                            for(String item : items){
                                orgList.add(Long.valueOf(item.split("\\:")[0]));
                            }
                            logger.info("用户信息：[{}]", userInfoDto);
                            userOrgMap.put(userInfoDto.getuId(), orgList);
                        }
                        
                        //获取用户skillId
                        StringBuilder skillNameSB = new StringBuilder();
                        String skillsStr = "-";
                        if(StringUtils.isNotBlank(userInfoDto.getSkills())){
                            String[] items = userInfoDto.getSkills().split("\\,");
                            for(String item : items){
                                if(skillNameSB.length() > 20){
                                    break;
                                }
                                skillNameSB.append(item.substring(item.indexOf(':') + 1)).append("、");
                            }
                            if(skillNameSB.length() > 20){
                                skillsStr = skillNameSB.substring(0, 20) + "...";
                            } else if(skillNameSB.length() > 0){
                                skillsStr = skillNameSB.substring(0, skillNameSB.lastIndexOf("、"));
                            }
                        }
                        logger.info("技能字符串：[{}]", skillNameSB.toString());
                        UserForOrderDTO userForOrderWeb = new UserForOrderDTO();
                        userForOrderWeb.setuId(userInfoDto.getuId());
                        userForOrderWeb.setUserName(userInfoDto.getUserName());
                        userForOrderWeb.setSkillNames(skillsStr);
                        userForOrderWebList.add(userForOrderWeb);
                    }
                    
                    //组装用户所属区域
                    Map<Long, String> cityMap = organizationInfoFacade.selectParentNamesByIdAndLevleType(OrgLevleType.CITY,
                            mapValueToArray(userOrgMap));
                    logger.info("城市集合：[{}]", cityMap);
                    Map<Long, String> areaMap = organizationInfoFacade.selectParentNamesByIdAndLevleType(OrgLevleType.AREA,
                            mapValueToArray(userOrgMap));
                    logger.info("片区集合：[{}]", areaMap);
                    Map<Long, String> projectMap = organizationInfoFacade.selectParentNamesByIdAndLevleType(OrgLevleType.PROJECT,
                            mapValueToArray(userOrgMap));
                    logger.info("项目集合：[{}]", projectMap);
                    
                    for(UserForOrderDTO userForOrderWeb : userForOrderWebList){
                        List<Long> orgIdList = userOrgMap.get(userForOrderWeb.getuId());
                        userForOrderWeb.setCityNames(concatMapValueString(cityMap, orgIdList, "、", 10, "全国"));
                        userForOrderWeb.setAreaNames(concatMapValueString(areaMap, orgIdList, "、", 20,
                                "-"));
                        userForOrderWeb.setProjectNames(concatMapValueString(projectMap, orgIdList, "、",
                                20,  "-"));
                    }
                    
                }
        
        return userForOrderWebList;
        
    }
    
    Long[] mapValueToArray(Map<Long, List<Long>> map){
        
        if(map == null){
            return null;
        }
        
        Set<Long> set = mapValueToSet(map);
        
        Long[] orgArray = new Long[set.size()];
        set.toArray(orgArray);
        return orgArray;
    }
    
    /**
     * map中的值转set
     * @param map
     * @return
     */
    private Set<Long> mapValueToSet(Map<Long, List<Long>> map){
        
        if(map == null){
            return null;
        }
        
        Set<Long> set = new HashSet<Long>();
        
        for(Collection<Long> value : map.values()){
            for(Long orgId : value){
                set.add(orgId);
            }
        }
        return set;
    }
    
    /**
     * 拼接map中的值，去重
     * @param map 结果集
     * @param keyList orgId list
     * @param sepChar 分隔符
     * @param maxLength 最大长度
     * @param defaultValue 默认值
     * @return
     */
    String concatMapValueString(Map<Long, String> map, List<Long> keyList, String sepChar,
            int maxLength, String defaultValue){
        
        if(map == null){
            return defaultValue;
        }
        
        logger.info("拼接用户城市、片区、项目字符串：orgMap = [{}], orgIdList = [{}]", map, keyList);
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> valueMap = new HashMap<String, Integer>();
        for(Long key : keyList){
            if(map.get(key) == null){
                continue;
            }
            if(sb.length() > maxLength){
                break;
            }
            if(!valueMap.containsKey(map.get(key))){
                valueMap.put(map.get(key), 0);
                sb.append(map.get(key)).append(sepChar);
            }
        }
        String finalStr = "";
        if(sb.length() > maxLength){
            finalStr =  sb.substring(0, maxLength) + "...";
        } else if (sb.length() > 0){
            finalStr = sb.substring(0, sb.lastIndexOf(sepChar));
        } else {
            finalStr = defaultValue;
        }
        logger.info("最终字符串为：finalStr = [{}]", finalStr);
        return finalStr;
    }

    /**
     * 查询待派、转单、协助列表 维修、维保共用
     * @param param
     * @return
     */
	public PageResponseDTO<UserForOrdersDTO> getUserForOrderN(PageRequestDTO<SearchUserCondition> param){
    	
        PageResponseDTO<UserForOrdersDTO> pageResponseDTO = new PageResponseDTO<UserForOrdersDTO>();
        RowBounds bounds = new RowBounds();
        bounds = RowBoundsUtil.getRowBounds(param.getPageNum(), param.getPageSize());
        SearchUserCondition searchUserCondition = param.getArgument();
        Long skillId= searchUserCondition.getSkillId();
        if (skillId == null || skillId == 0) {
        	//查询全部技能
        	skillId = null;
        }
        String userName = searchUserCondition.getUserName();
        Integer projectType = searchUserCondition.getProjectType();
        Integer privilege = searchUserCondition.getPrivilege();
        String combineCode = searchUserCondition.getCombineCode();
        Integer assignType = searchUserCondition.getAssignType();
        List<Long> excludeUserIds = searchUserCondition.getExcludeUserIds();
        
        /*
         * 
         * 2、符合组织机构条件的 用户ID
         * 3、skillId 查询符合技能  
         * 4、userName 用户名 条件的 用户ID 
         * 5、id:2：拥有维修权限   3：拥有维保权限
         * 6、机构类型 projectType
         */
        List<Long> skillUserListNew = null;
        
        if (privilege == 3 && assignType == 1) {
            //维保并且请求协助人员列表
            skillUserListNew = userSkillRefDao.selectUserIdsByCondition(combineCode, skillId, userName, 2, projectType);
        } else {
            skillUserListNew = userSkillRefDao.selectUserIdsByCondition(combineCode, skillId, userName, privilege, projectType);
        }
        
        // 过滤当前处理人
        if (excludeUserIds != null && excludeUserIds.size() > 0) {
            for (Long userId : excludeUserIds) {
                if (skillUserListNew.contains(userId)) {
                    skillUserListNew.remove(userId);
                }
            }
        }
        
        //添加售后管理 用户IDs
        List<Long> afterSaleList = this.getAfterSaleList(searchUserCondition);
        skillUserListNew.addAll(afterSaleList);
        
        List<UserForOrdersDTO> userForOrderDTOList = null;
        
        if (skillUserListNew.size() > 0) {
            userForOrderDTOList = userSkillRefDao.selectUserForOrder(bounds, skillUserListNew);
            //设置售后团队接单状态、用户类型
            userForOrderDTOList = this.setUserStatus(userForOrderDTOList, afterSaleList);
        }
        
        pageResponseDTO.setData(userForOrderDTOList);
        pageResponseDTO.setTotalRow(CountHelper.getTotalRow());
        
        return pageResponseDTO;
        
    }
    
	/**
	 * 查询转验收人员列表
	 * @param param
	 * @return
	 */
	public PageResponseDTO<UserForOrdersDTO> getUserForOrderAccept(PageRequestDTO<SearchUserCondition> param){
	    
        PageResponseDTO<UserForOrdersDTO> pageResponseDTO = new PageResponseDTO<UserForOrdersDTO>();
        RowBounds bounds = new RowBounds();
        bounds = RowBoundsUtil.getRowBounds(param.getPageNum(), param.getPageSize());
        SearchUserCondition searchUserCondition = param.getArgument();
        Long skillId= null; //查询全部技能
        
        String userName = searchUserCondition.getUserName();
        Integer projectType = searchUserCondition.getProjectType();
        String combineCode = searchUserCondition.getCombineCode();
        List<Long> excludeUserIds = searchUserCondition.getExcludeUserIds();
        
        /*
         * 
         * 2、符合组织机构条件的 用户ID
         * 3、skillId 查询符合技能  
         * 4、userName 用户名 条件的 用户ID 
         * 5、id:2:拥有维修权限  4:拥有维修工单池权限
         * 6、机构类型 projectType
         */
        int privilege = 2;
        int privilegeM = 4;
        List<Long> skillUserListAll = new ArrayList<Long>();
        List<Long> skillUserList = userSkillRefDao.selectUserIdsByCondition(combineCode, skillId, userName, privilege, projectType);
        List<Long> skillUserListM = userSkillRefDao.selectUserIdsByCondition(combineCode, skillId, userName, privilegeM, projectType);
       
        for (Long i : skillUserList) {
            skillUserListAll.add(i);
        }
        
        for (Long j : skillUserListM) {
            if (!skillUserListAll.contains(j)) {
                skillUserListAll.add(j);
            }
        }

        // 过滤当前处理人、当前验收人
        if (excludeUserIds != null && excludeUserIds.size() > 0) {
            for (Long userId : excludeUserIds) {
                if (skillUserListAll.contains(userId)) {
                    skillUserListAll.remove(userId);
                }
            }
        }
        
        List<UserForOrdersDTO> userForOrderDTOList = userSkillRefDao.selectUserForOrder(bounds, skillUserListAll);
        
        pageResponseDTO.setData(userForOrderDTOList);
        pageResponseDTO.setTotalRow(CountHelper.getTotalRow());
        
        return pageResponseDTO;
	    
	}
	
    private Map<String, Object> getCombineCode(PageRequestDTO<SearchUserCondition> param, List<Long> upLevelUserIdList){
    	Map<String, Object> map = new HashMap<String, Object>();
    	SearchUserCondition searchUserCondition = param.getArgument();
    	String combineCode = param.getArgument().getCombineCode();
    	OrganizationDTO organizationDTO = organizationInfoFacade.selectById(param.getArgument().getOrgId());
    	
    	if(searchUserCondition.getProjectType() !=null){
            if(searchUserCondition.getProjectType()==1){
                //case 1 app 片区、所查项目查询
                String combineCodeApp = combineCode.substring(0, combineCode.length()-7);//获取上级机构编码
                upLevelUserIdList = userOrgRefDao.selectUserIds(combineCodeApp);
                
            }else if(searchUserCondition.getProjectType()==2){
                //case 2 web 片区、片区下所有的项目
                if(organizationDTO.getLevelType().intValue() == 30){  //30:区域层级
                    combineCode = combineCode+"%";
                }
            }
        }
    	map.put("orgUserApp", upLevelUserIdList);
    	map.put("combineCode", combineCode);
    	return map;
    }
    /**
     * 取lists的交集
     * @param map
     * @return
     */
    private List<Long> getIntersection(Map<Integer, List<Long>> map){
    	logger.info("获取工单人员列表 [取lists的交集]");
    	List<Long> userList = new ArrayList<Long>();
    	List<Long> list1 = map.get(0);
    	userList.addAll(list1); 
		for (int i = 1; i < map.size(); i++) {
			list1.clear();
			list1.addAll(userList);
			list1.removeAll(map.get(i));
			userList.removeAll(list1);
		}
    	return userList;
    }
    
    private List<Long> getAfterSaleList(SearchUserCondition searchUserCondition){
    	logger.info("获取工单人员列表 [根据 转单，协助，派单类型 选择售后团队人员]");
    	Boolean boolean1 = false;
    	Integer assignType = searchUserCondition.getAssignType();
    	
    	List<Long> afterSaleList = new ArrayList<>();
    	List<Long> afterSaleList1 = new ArrayList<>();//售后团队
    	List<Long> afterSaleList2 = new ArrayList<>();//售后管理
    	List<Long> afterSaleList3 = new ArrayList<>();//售后调度
    	List<Long> afterSaleList4 = new ArrayList<>();//技术支持
    	
    	Long[] afterSaleArray2 = new Long[]{12L, 13L, 14L, 15L};
    	Long[] afterSaleArray3 = new Long[]{16L, 17L, 18L, 19L, 20L};
    	Long[] afterSaleArray4 = new Long[]{21L, 22L, 23L, 24L, 25L, 26L, 27L, 
    	       28L, 29L, 30L, 31L, 33L, 34L, 35L, 36L, 37L, 38L, 39L, 40L, 41L,
    	       42L, 43L, 44L, 45L, 46L, 47L, 48L, 49L, 50L};
    	
    	afterSaleList2.addAll(Arrays.asList(afterSaleArray2));
    	afterSaleList3.addAll(Arrays.asList(afterSaleArray3));
    	afterSaleList4.addAll(Arrays.asList(afterSaleArray4));
    	
    	afterSaleList1.addAll(Arrays.asList(afterSaleArray2));
    	afterSaleList1.addAll(Arrays.asList(afterSaleArray3));
    	afterSaleList1.addAll(Arrays.asList(afterSaleArray4));
    	
    	//判断当前用户是否为售后团队成员
    	if(afterSaleList1.contains(SystemContextUtils.getUserID())){
    		boolean1 = true;
    	}
    	
    	if(assignType == null){
    		logger.info("assignType为空  searchUserCondition",searchUserCondition);
    		return afterSaleList;
    	}
    	if(assignType.intValue() == 1){
    		//case 1 协助
    		afterSaleList.addAll(afterSaleList3);
    	} else if (assignType.intValue() == 2 && boolean1.booleanValue() == true){
    		//case 2 转单
    		afterSaleList.addAll(afterSaleList1);
    	} else if (assignType.intValue() == 3) {
    		return afterSaleList;
		}
    	
    	return afterSaleList;
    }
    
    private List<UserForOrdersDTO> setUserStatus(List<UserForOrdersDTO> list, List<Long> afterSaleList){
    	logger.info("获取工单人员列表 [设置售后团队人员的状态为接单中]");
    	for(UserForOrdersDTO userForOrdersDTO : list){
    		if(afterSaleList.contains(userForOrdersDTO.getuId().longValue())){
    			userForOrdersDTO.setAcceptStatus(1);
    			userForOrdersDTO.setUserType(2);
    		}
    	}
    	return list;
    	
    }

}

