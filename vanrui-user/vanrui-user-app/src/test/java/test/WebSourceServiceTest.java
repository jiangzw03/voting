package test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.user.dto.SourceInfoDto;
import com.vanrui.app.user.service.RoleSourceService;

import base.BaseJUnitTest;
import ooh.bravo.context.util.SystemContextUtils;

public class WebSourceServiceTest   extends BaseJUnitTest {

	 @Autowired
	 RoleSourceService  rsService;
	 
	 @Test
	 public void test(){
		 SystemContextUtils.setUserAndTenantID(1L, "T000001");
		 List<SourceInfoDto> list = rsService.selectWebSourcesByUid(null);
		 System.out.println("reuslt"+list);
	 }
}
