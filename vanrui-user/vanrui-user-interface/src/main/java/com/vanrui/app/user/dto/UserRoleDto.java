/*
 * @(#)AuthDto.java 2016年10月8日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.dto;

import ooh.bravo.core.model.BaseObject;

/**
 *
 * @author maji01
 * @date 2016年10月8日 上午11:09:58
 * @version V1.0.0
 * description：
 * 
 */
public class UserRoleDto  extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 用户ID **/
    private   Long   uId;
    /** 角色ID **/
    private   Long   rId;
    
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    }
    public Long getrId() {
        return rId;
    }
    public void setrId(Long rId) {
        this.rId = rId;
    }
    
}
