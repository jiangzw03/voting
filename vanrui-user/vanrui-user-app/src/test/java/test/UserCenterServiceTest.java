/*
 * @(#)Snippet.java 2016年10月13日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.vanrui.app.order.dto.SearchUserCondition;
import com.vanrui.app.user.dto.FreezeDto;
import com.vanrui.app.user.dto.RoleDto;
import com.vanrui.app.user.dto.UserBaseDto;
import com.vanrui.app.user.dto.UserDetailDto;
import com.vanrui.app.user.dto.UserInertDto;
import com.vanrui.app.user.dto.UserInfoDto;
import com.vanrui.app.user.dto.UserListCondition;
import com.vanrui.app.user.dto.UserOrgRefDto;
import com.vanrui.app.user.dto.UserSkillDto;
import com.vanrui.app.user.dto.UserUpdateDto;
import com.vanrui.app.user.facade.UserCenterFacade;
import com.vanrui.app.user.service.UserCenterService;
import com.vanrui.app.user.util.Constants;

import base.BaseJUnitTest;
import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.dto.PageRequestDTO;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.crypto.util.EncryptionUtils;
import ooh.bravo.util.StringUtils;

/**
 *
 * @author maji01
 * @date 2016年10月13日 上午9:17:02
 * @version V1.0.0 description：
 * 
 */
public class UserCenterServiceTest extends BaseJUnitTest {

    @Autowired
    UserCenterService ucService;
    @Autowired
    UserCenterFacade userCenterFacade;

    // @Test
    public void insert() {
        SystemContextUtils.setUserAndTenantID(8l, "T00001");
        UserInertDto user = new UserInertDto();

        user.setAccount("123456d");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        user.setCreator(1l);
        user.setUserName("eba-mj");
        user.setMobilePhone("13243725009");
        user.setStatus(1);
        // 角色关系
        user.setrId(2l);
        // 组织机构关系
        List<UserOrgRefDto> refjg = new ArrayList<UserOrgRefDto>();
        UserOrgRefDto uorg = new UserOrgRefDto();
        uorg.setOrId(5l);
        uorg.setuId(1l);
        uorg.setCombinationCode("000001-000002-000005");
        uorg.setOrName("福田");
        refjg.add(uorg);
        user.setRefjg(refjg);
        // 修改技能关系
        List<Long> skills = new ArrayList<Long>();
        skills.add(1001l);
        skills.add(1002l);
        user.setSkills(skills);
        try {
            ucService.insert(user);
        } catch (DuplicateKeyException ex) {
            if (ex.getRootCause() != null && StringUtils.isNotBlank(ex.getRootCause().getMessage())) {
                String error = ex.getRootCause().getMessage();
                if (error.contains("ACCOUNT") || error.contains("account")) {

                } else if (error.contains("EMPLOYEE_CODE") || error.contains("employee_code")) {

                }
            }
            // 主键或唯一性约束冲突
        } catch (Exception e) {
            System.err.println("error:" + e.getStackTrace()[0]);

            e.printStackTrace();
        }
        System.out.println("\n\n @__finish \n\n");
    }

    public static void main(String[] args) {
        String p1 = EncryptionUtils.encodePassword("123");
        boolean res = EncryptionUtils.verifyPassword("123", p1);
        System.out.println(p1.length() + ":" + p1);
        System.out.println("result:" + res);
    }

    // @Test
    public void update() {
        // UserUpdateDto user
        // ucService.update(user);
        UserUpdateDto user = new UserUpdateDto();
        // 修改角色关系
        RoleDto role = new RoleDto();
        role.setrId(3l);
        user.setRole(role);
        // 修改技能关系
        List<UserSkillDto> skills = new ArrayList<UserSkillDto>();
        UserSkillDto usd = new UserSkillDto();
        usd.setSkillId(1l);
        skills.add(usd);
        usd = new UserSkillDto();
        usd.setSkillId(3l);
        skills.add(usd);
        user.setSkills(skills);
        // 修改机构关系
        List<UserOrgRefDto> refjg = new ArrayList<UserOrgRefDto>();
        UserOrgRefDto uorg = new UserOrgRefDto();
        uorg.setOrId(1l);
        uorg.setuId(1l);
        uorg.setCombinationCode("0005");
        uorg.setOrName("天津");
        refjg.add(uorg);
        uorg = new UserOrgRefDto();
        uorg.setOrId(2l);
        uorg.setuId(1l);
        uorg.setCombinationCode("0006");
        uorg.setOrName("上海");
        refjg.add(uorg);
        user.setRefjg(refjg);
        user.setuId(1l);
        user.setUserName("湄公河");
        user.setEmail("123456@qq.com");
        user.setAddress("全世界");
        user.setEmployeeCode("123456654321");
        user.setAccount("88888");
        user.setfSolution("东东东东");
        user.setStatus(0);
        user.setMobilePhone("123135456");
        user.setIdCard("sdsds12145455623");
        user.setPassword("sdfsfsdf");
        user.setUpdator(1l);
        user.setUpdateTime(new Date());
        ucService.update(user);
    }

    // @Test
    public void updatePassword() {
        // Long uId, String newPassword, String oldPassword
        // ucService.updatePassword(uId, newPassword, oldPassword);
        Long uId = 1l;
        String newPassword = "sad12333";
        String oldPassword = "sad123";
        ucService.updatePassword(uId, newPassword, oldPassword);
        System.out.println("\n\n @___finish  \n\n");
    }

    // @Test
    public void freeze() {
        // FreezeDto freeze
        // ucService.freeze(freeze);
        FreezeDto freeze = new FreezeDto();
        freeze.setfType(Constants.USER_FREEZE_YES);
        freeze.setfUId(8l);
        freeze.setOperateTime(new Date());
        freeze.setOperator(1l);
        freeze.setSolution("冻结：工作调度");

        ucService.freeze(freeze);

        System.out.println("\n\n @___finish  \n\n");
    }

    @Test
    public void selectListByCondition() {
        SystemContextUtils.setUserAndTenantID(1l, "T000001");
        // PageResponseDTO<UserInfoDto>
        // UserListCondition condition, int pageNum, int pageSize
        // ucService.selectListByCondition(condition, pageNum, pageSize);
        UserListCondition condition = new UserListCondition();
        // condition.setUserName("湄");
        condition.setSort(1);
        condition.setCombineCode("000001");
        // condition.setRoleId(3l);
        condition.setSkillId(1L);
        // condition.setStatus(0);
        int pageNum = 1;
        int pageSize = 20;
        PageResponseDTO<UserInfoDto> respons = userCenterFacade.selectListByCondition(false, condition, pageNum, pageSize);
        System.out.println("\n\n @ selectListByCondition ___finish:" + respons.toString() + " \n\n");

    }

    // @Test
    public void selectInDetailByUId() {
        // UserDetailDto Long uId
        // return ucService.selectInDetailByUId(uId);
        int i = 1;
        UserDetailDto dto = ucService.selectInDetailByUId(1006L);
        System.out.println("\n\n @___finish:" + dto.toString() + "  \n\n");
    }

    // @Test
    public void selectInUpdateByUId() {
        UserUpdateDto dto = ucService.selectInUpdateByUId(8l);
        System.out.println("\n\n @___finish:" + dto.toString() + "  \n\n");
    }

    // @Test
    public void selectBaseinfoByUId() {
        SystemContextUtils.setUserAndTenantID(8l, "T000001");
        UserInfoDto dto = ucService.selectUserBaseInfo(8l);
        System.out.println("\n\n @___finish:" + dto.toString() + "  \n\n");
    }

    // @Test
    public void selectUserBaseInfoList() {
        SystemContextUtils.setUserAndTenantID(1l, "T000001");
        Set<Long> uIds = new HashSet<Long>();
        uIds.add(10006l);
        // uIds.add(10l);

        Map<Long, UserInfoDto> res = userCenterFacade.selectUserBaseInfoList(uIds);
        System.out.println("\n\n @___finish:" + res + "  \n\n");
    }

    // @Test
    public void selectAssignOrderUsers() {
        PageRequestDTO<SearchUserCondition> param = new PageRequestDTO<SearchUserCondition>();

        List<Long> skillIds = new ArrayList<Long>();
        skillIds.add(1000L);
        List<Long> roleIds = new ArrayList<Long>();
        roleIds.add(1L);
        SearchUserCondition condtion = new SearchUserCondition();
        condtion.setRoleIdList(roleIds);
        condtion.setRoleIdList(roleIds);
        param.setArgument(condtion);

        System.out.println("\n\n 查询服务" + ucService.selectAssignOrderUsers(param) + "\n\n ");
    }

    @Test
    public void selectWorkOrderManagers() {
        // System.out.println("\n\n 查询服务" + ucService.selectWorkOrderManagers(21L));
        Long orgId = 6l;
        List<Long> roleIds = new ArrayList<Long>();
        roleIds.add(1l);
        roleIds.add(1000l);
        List<Long> skillIds = new ArrayList<Long>();
        skillIds.add(1000L);
        List<UserBaseDto> response = userCenterFacade.selectAssignOrderUsers(orgId, skillIds, roleIds, null);
        System.out.println("\n\n 查询服务" + response + "\n\n ");
    }

    @Test
    public void selectRIdList() {
        List<Long> list = userCenterFacade.selectRIds(10074l);
        System.out.println(list);
    }

    @Test
    public void hasWorkOrderRepairPermission() {
        SystemContextUtils.setUserAndTenantID(1l, "T000001");
        Boolean flag = userCenterFacade.hasWorkOrderRepairPermission(null);
        System.out.println(flag);
    }
    
    @Test
    public void UserIdByPhoneNum() {
        SystemContextUtils.setUserAndTenantID(1l, "T000001");
        System.out.println("\n\n\n"+userCenterFacade.selectUserIdByPhoneNum("18698720767")+"\n\n");
    }
    
    @Test
    public void selectUsermanager() {
        SystemContextUtils.setUserAndTenantID(1l, "T000001");
        System.out.println("\n\n\n"+userCenterFacade.selectWorkOrderManagers(25L)+"\n\n");
    }
}
