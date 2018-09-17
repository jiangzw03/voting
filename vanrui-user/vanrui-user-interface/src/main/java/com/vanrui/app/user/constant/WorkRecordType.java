/*
 * @(#)WorkRecordType.java 2017年2月27日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.constant;

/**
 *
 * @author fanhuajun
 * @date 2017年2月27日 上午10:08:37
 * @version V1.0.0
 * description：
 * 
 */
public enum WorkRecordType {
	
	/**
	 * 操作日志
	 */
	
	/*考勤 操作日志*/
	RECORD_TYPE_SIGN_IN(30, 3010, "签到", "签到："),
	
	RECORD_TYPE_SIGN_OUT(30, 3020, "签出", "签出："),
	
	
	/*工单 操作日志*/
	RECORD_TYPE_WORKORDER_START_RECEIVE(20, 2010, "开始接单", "开始接单"),//可以接单的状态
	
	RECORD_TYPE_WORKORDER_STOP_RECEIVE(20, 2011, "停止接单", "停止接单："),//不可以接单的状态
	
	RECORD_TYPE_WORKORDER_RECEIVE(20, 2020, "接单", "接单："),
	
	RECORD_TYPE_WORKORDER_STARTDEAL(20, 2021, "开始处理工单", "开始处理工单："),
	
	RECORD_TYPE_WORKORDER_HANG(20, 2022, "挂起工单", "挂起工单："),
	
	RECORD_TYPE_WORKORDER_LAUNCH_ASSIST(20, 2023, "发起协助工单", "发起协助工单："),
	
	RECORD_TYPE_WORKORDER_FINSH(20, 2024, "完成工单（包含提交维保）", "完成工单："),
	
	RECORD_TYPE_WORKORDER_TRANFORM(20, 2030, "转单", "转单给"),
	
	RECORD_TYPE_WORKORDER_TRANBACK(20, 2031, "退单", "退单："),
	
	
	
	/*知会 操作日志*/
	RECORD_TYPE_NOTISE_SIGNIN_INFORM(10, 105, "发布了签到知会", "发布了签到知会:"),
	
	RECORD_TYPE_NOTISE_SIGNOUT_INFORM(10, 104, "发布了签出知会", "发布了签出知会:"),
	
	RECORD_TYPE_NOTISE_LEAVE_NOTIFY(10, 103, "发布了离岗知会", "停止接单:"),
	
	RECORD_TYPE_NOTISE_LEAVE_INFORM(10, 106, "发布了请假知会", "发布了请假知会:"),
	
	RECORD_TYPE_NOTISE_MEET_INFORM(10, 101, "发布了开会知会", "发布了开会知会:"),
	
	RECORD_TYPE_NOTISE_TRAIN_INFORM(10, 102, "发布了培训知会", "发布了培训知会:"),
	
	RECORD_TYPE_NOTISE_OTHER(10, 107, "发布了其它知会", "发布了其它知会:");
	
	
	private int type;
	private int subType;
	private String name;
	private String preDsc;
	
	WorkRecordType(int type, int subType, String name, String preDsc){
		this.type = type;
		this.subType = subType;
		this.name = name;
		this.preDsc = preDsc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreDsc() {
		return preDsc;
	}

	public void setPreDsc(String preDsc) {
		this.preDsc = preDsc;
	}

	

}



