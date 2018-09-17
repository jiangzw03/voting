package test;
  
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanrui.app.user.dto.AllSourceDTO;
import com.vanrui.app.user.dto.RoleSourceUrlDto;
import com.vanrui.app.user.dto.SourceBaseDTO;
import com.vanrui.app.user.dto.SourceInfoDto;
import com.vanrui.app.user.service.RoleSourceService;

import base.BaseJUnitTest;
import ooh.bravo.context.util.SystemContextUtils;

public class RoleServiceTest extends BaseJUnitTest {
    
    @Autowired
    RoleSourceService  rsService;

//     @Test
    public  void  selectAppAllRoleURLs() {
         
        List<RoleSourceUrlDto>  list = null;
        list =  rsService.selectAppAllRoleURLs();
        System.out.println("\n\n @__Finish:"+list.toString()+" \n\n");
    }

//     @Test
    public void selectWebAllRoleURLs() {
         List<RoleSourceUrlDto>  list = null;
         list =  rsService.selectWebAllRoleURLs();
         System.out.println("\n\n @__Finish:"+list.toString()+" \n\n"); 
    }

//    @Test
    public List<SourceInfoDto> selectAppSourcesByrId(Long rId) {
        
        return  rsService.selectAppSourcesByrId(rId);
    }

    @Test
    public void selectWebSourcesByrId() {
        SystemContextUtils.setUserAndTenantID(10072l,"T000001");
        logger.info("\n\nresult:{} \n\n",rsService.selectWebSourcesByrId(100L)); 
       
    }
    
//    @Test
    public  void  selectALLSource(){
        
        AllSourceDTO  dto = rsService.selectAllSource();
        logger.info("result:[{}]"+dto); 
    }
    
    @Test
    public  void  selectWebSourcesBycurUId(){
        SystemContextUtils.setUserAndTenantID(10072l,"T000001");
        List<SourceBaseDTO>   list = rsService.selectWebSourcesBycurUId();

        logger.info("\n\nresult:{} \n\n",list); 
        logger.info("\n\n size:{} \n\n",list.size()); 
        
    }
    
    @Test
    public void selectRolesByOneRight(){
        
        System.out.println(rsService.selectRolesByOneRight(6L));
    }
}
