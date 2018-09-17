/*
 * @(#)WorkRecordMsgTransfer.java 2017年2月24日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.transfer;

import com.vanrui.app.user.dto.WorkRecordDTO;
import com.vanrui.app.user.dto.WorkRecordMsg;

/**
 *
 * @author fanhuajun
 * @date 2017年2月24日 下午5:19:59
 * @version V1.0.0
 * description：
 * 
 */
public class WorkRecordMsgTransfer {
	
	public static WorkRecordDTO TransferToMsg(WorkRecordMsg workRecordMsg){
		if(workRecordMsg == null){
			return new WorkRecordDTO();
		}
		
		WorkRecordDTO workRecordDTO = new WorkRecordDTO();
		workRecordDTO.setContent(workRecordMsg.getContent());
		workRecordDTO.setId(workRecordMsg.getId());
		workRecordDTO.setRefId(workRecordMsg.getRefId());
		workRecordDTO.setuId(workRecordMsg.getuId());
		workRecordDTO.setWorkTime(workRecordMsg.getWorkTime());
		workRecordDTO.setWorkType(workRecordMsg.getWorkType());
		
		return workRecordDTO;
	}

}
