package test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.core.dto.PageResponseDTO;
import ooh.bravo.core.dto.ResponseDTO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.user.dto.AuthDetailsDto;
import com.vanrui.app.user.dto.AuthDto;
import com.vanrui.app.user.dto.AuthInfoDto;
import com.vanrui.app.user.service.AuthManaService;
import com.vanrui.app.user.service.jms.AuthorizationChangeMessageService;
import com.vanrui.app.user.util.rsa.RsaEncrypt;

import base.BaseJUnitTest;

public class AuthServiceTest extends BaseJUnitTest {
    @Autowired
    private AuthManaService authService;

    @Autowired
    AuthorizationChangeMessageService authzChangeMessageService;

    @Test
    public void testSendTopicMsgByRid() {
        /*
         * List<Long> userIdList = authService.selectUserIdsByRoleId(105L); if
         * (userIdList != null && userIdList.size() != 0) { for (Long userId :
         * userIdList) { System.out.println("修改角色-发送角色授权变更消息开始, userId="
         * +userId); authzChangeMessageService.send(userId); } }
         */
        authzChangeMessageService.send(10181L);
    }

    @Test
    public void queryUserInfoByLoginName() {

        SystemContextUtils.setUserAndTenantID(8l, "T000001");
        AuthDetailsDto authObj = new AuthDetailsDto();
        authObj.setrName("普通员工");
        authObj.setCreateTime(new Date());
        List<Long> authsApp = new ArrayList<Long>();
        authsApp.add(5l);
        authsApp.add(6l);
        authObj.setAuthsApp(authsApp);
        ResponseDTO<Integer> reponse = authService.insert(authObj);
        System.out.println("\n\n\n# __Finsh:" + reponse + "\n\n\n");
    }

    @Test
    public void updateAuth() {

        SystemContextUtils.setUserAndTenantID(1l, "T000001");
        AuthDetailsDto authObj = new AuthDetailsDto();
        authObj.setrId(105L);
        authObj.setrName("实习生");
        authObj.setCreateTime(new Date());
        authObj.setCreator(1l);
        List<Long> webs = new ArrayList<Long>();
        webs.add(210L);
        webs.add(220L);
        webs.add(230L);
        authObj.setAuthsWeb(webs);
        List<Long> apps = new ArrayList<Long>();
        apps.add(2L);
        apps.add(3L);
        apps.add(4L);
        apps.add(5L);
        apps.add(6L);
        authObj.setAuthsApp(apps);
        authService.update(authObj);
        System.out.println("\n\n\n# __Finsh\n\n\n");
    }

    // @Test
    public void deleteRole() {

        SystemContextUtils.setUserAndTenantID(9l, "T000001");
        authService.deleteByRoleId(22l);
        System.out.println("\n\n\n# __Finsh\n\n\n");
    }

    // @Test
    public void queryAllAuthority() {
        PageResponseDTO<AuthDto> dto = authService.selectAllAuthority(1, 2);
        int total = dto.getTotalRow();
        List<AuthDto> auths = dto.getData();
        System.out.println("total:" + total);
        for (int i = 0; i < auths.size(); i++) {
            System.out.print("\n name:" + auths.get(i).getrName() + ",app:"
                    + auths.get(i).getAuthApp() + ",num:"
                    + auths.get(i).getNum());
        }
    }

    @Test
    public void selectAuthority() {

        AuthInfoDto dto = authService.selectAuthority(1l);

        AuthInfoDto dtoNull = authService.selectAuthority(null);
        dto.getrName();
        dtoNull.getrId();
        System.out.println("\n\n\n# __Finsh" + dtoNull + "\n\n\n");
    }

    /**
     * 解密
     * 
     * @param args
     */
    public static void main(String[] args) {

        String pass = "00000000001010000";
        pass = RsaEncrypt.encryptString(pass);
        System.out.println("加密：" + pass);
        // pass =
        // "Xz4obWUfJj+v1xoeW/dcaBUpaXO+9j3pee9mtfhpBL+xoW0zKWnxfVYJHgeCzl/Cw8PISKANu6SPR8kWF8/XDvut3SeUlI/kw4Wh9jW+g3unU4QGzEHRB94YL3OpT2DJD8GBV9ki6Y7GQ1UbY2cp1D/PSCzYG1c23BnM1k6mY68=";

        String password = RsaEncrypt.decryptStringAndBack(pass);
        try {

            password = new String(password.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("解密:" + password);

    }
    
    @Test
    public  void etstWebVisitValidate(){
        SystemContextUtils.setUserAndTenantID(16l, "T000001");
    	String url ="/man/ordre/myOrder/list/page";
    	ResponseDTO<Long> response = null;
    	 response =authService.webVisitValidate(url);
    	 System.out.println("result:" + response);
    }

}
