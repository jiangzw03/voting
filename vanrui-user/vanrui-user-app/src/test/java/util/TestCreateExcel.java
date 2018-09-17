/*
 * @(#)TestCreateExcel.java 2016年11月24日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ooh.bravo.context.util.SystemContextUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseJUnitTest;

import com.vanrui.app.org.dto.OrgTreeNodeDTO;
import com.vanrui.app.org.facade.OrganizationTreeFacade;

/**
 *
 * @author maji01
 * @date 2016年11月24日 下午5:28:57
 * @version V1.0.0
 * description：
 * 
 */
public class TestCreateExcel  extends BaseJUnitTest {

    @Autowired
    OrganizationTreeFacade    orgTreeFacade;
    
    /**
     * 1、查询组织机构树
     * 2、查询技能树
     * 3、查询数据列表
     */
    @Test
    public  void   testList(){
        //
        SystemContextUtils.setUserAndTenantID(1l,"T000001");
        OrgTreeNodeDTO  dto =  orgTreeFacade.getTreeNode(1l);
        dealDto( dto,null );
        show();
        System.out.println(" ### finish #");
    }
    
    public  List<Map<String,List<String>>>  listmaps = new ArrayList<Map<String,List<String>>>();
    
    
    public   void   dealDto(OrgTreeNodeDTO  dto,OrgTreeNodeDTO  pDto){
        Map<String,List<String>> maps = new  HashMap<String,List<String>>();
        List<String>  list = new ArrayList<String>();
        if(dto.getChildrenList() != null && dto.getChildrenList().size() > 0 ){
            for(OrgTreeNodeDTO org:dto.getChildrenList() ){
                list.add(org.getName()+"\\"+org.getId());
                dealDto( org ,dto);
            }
        }
        String  key = ""; 
        key += dto.getLevelType()+":"+dto.getName()+"\\"+dto.getId();
        if(list != null && list.size() > 0 ){
            maps.put(key, list);
            listmaps.add(maps);
        } 
    }
    
    public  void  show(){
        if(listmaps.size()>0){
            for(Map<String,List<String>> mapss : listmaps){
                for(Map.Entry<String,List<String>> map: mapss.entrySet() ){
                    System.out.print( map.getKey()  );
                    for(String  st:map.getValue()){
                        System.out.print("\nT----"+ st );
                    }
                    System.out.println("\n----------------------------------------------------");
                }
                
            }
        }
    }
    
}
