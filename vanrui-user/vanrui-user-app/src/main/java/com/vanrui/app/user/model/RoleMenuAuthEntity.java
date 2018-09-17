/*
 * @(#)AuthDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.model;

import ooh.bravo.core.model.BaseObject;

/**
 * 角色与资源关系表实体类
 * @author maji01
 * @date 2016年10月8日 上午11:09:58
 * @version V1.0.0
 * description：
 * 
 */
public class RoleMenuAuthEntity  extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 菜单资源ID **/
    private   Long   sId;
    /** 角色ID **/
    private   Long   rId;
    
    public Long getsId() {
        return sId;
    }
    
    public void setsId(Long sId) {
        this.sId = sId;
    }
    
    public Long getrId() {
        return rId;
    }
    
    public void setrId(Long rId) {
        this.rId = rId;
    }
}
