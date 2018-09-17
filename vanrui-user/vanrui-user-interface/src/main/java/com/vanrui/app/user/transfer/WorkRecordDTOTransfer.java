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
public class WorkRecordDTOTransfer {
	
	public static WorkRecordMsg TransferToMsg(WorkRecordDTO workRecordDTO){
		if(workRecordDTO == null){
			return new WorkRecordMsg();
		}
		
		WorkRecordMsg workRecordMsg = new WorkRecordMsg();
		workRecordMsg.setContent(workRecordDTO.getContent());
		workRecordMsg.setId(workRecordDTO.getId());
		workRecordMsg.setRefId(workRecordDTO.getRefId());
		workRecordMsg.setuId(workRecordDTO.getuId());
		workRecordMsg.setWorkTime(workRecordDTO.getWorkTime());
		workRecordMsg.setWorkType(workRecordDTO.getWorkType());
		
		return workRecordMsg;
	}

}
