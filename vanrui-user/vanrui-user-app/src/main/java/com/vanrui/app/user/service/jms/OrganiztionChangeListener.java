/*
 * @(#)MessageListener.java 2016年10月18日
 * 
 * Copyright (c), 2016 深圳市万睿智能科技有限公司（Shenzhen Wan Rui Intelligent Technology Co., Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.vanrui.app.user.service.jms;

import javax.jms.JMSException;

import ooh.bravo.service.BaseService;
import ooh.bravo.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.vanrui.app.org.msg.OrgChangeMsg;
import com.vanrui.app.user.service.UserOrgRefService;

/**
 *
 * @author maji01
 * @date 2016年10月18日 上午9:37:05
 * @version V1.0.0 description：
 * 
 */
@Service("organiztionChangeListener")
public class OrganiztionChangeListener extends BaseService {

	@Autowired
	UserOrgRefService userOrgService;

	@JmsListener(destination = "${consumer.topic.destination.organiztionChangeQueue}", containerFactory = "topicJmsListenerContainer")
	public void receiveMessage(final String message) throws JMSException {
		logger.info("[ 接收消息 并批量修改组织机构编码 ] 入参： OrgChangeMsg=[{}]", message);
		try {
			OrgChangeMsg changeMsg = JsonUtils.parseJSON(message, OrgChangeMsg.class);
			userOrgService.batchUdpateOrg(changeMsg);
			logger.info("[ 接收消息 并批量修改组织机构编码 ] 成功 ");
		} catch (Exception ex) {
			logger.error("[ 接收消息 并批量修改组织机构编码 ] 异常: {}", ex);
		}
	}
}





