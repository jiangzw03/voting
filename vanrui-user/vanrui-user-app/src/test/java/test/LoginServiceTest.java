/*
 * @(#)LoginServiceTest.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package test;

import ooh.bravo.core.dto.ResponseDTO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseJUnitTest;

import com.vanrui.app.user.dto.AuthenticationDTO;
import com.vanrui.app.user.dto.LoginUserDTO;
import com.vanrui.app.user.service.LoginService;

/**
 *
 * @author maji01
 * @date 2016年10月18日 下午2:03:59
 * @version V1.0.0 description：
 * 
 */
public class LoginServiceTest extends BaseJUnitTest {

	@Autowired
	LoginService service;

	@Test
	public void test() {

		AuthenticationDTO authDTO = new AuthenticationDTO();
		authDTO.setDeviceType(4);
		authDTO.setPassword("UEscURiaf0Yt8J+1SCUrAkT6GW3seGeDKS8+oe4gOz0iV9gJLtbvoNaMvWaenhSxtscPNjyy0dQnff503KkRTuRtQUelR1iVBOoO2QNa8A4vR+7s2heNpIDYGqFK4eIXwdEv7Ib0D1O1OvPD/PBqwJSJZRVKFS3HcKPoaY0s32k=");
		authDTO.setUsername("asfsaf");
		ResponseDTO<LoginUserDTO> result = service.loginAuthenticate(authDTO);
		LoginUserDTO data = result.getData();
		System.out.println("result:"+result);
	}
}
