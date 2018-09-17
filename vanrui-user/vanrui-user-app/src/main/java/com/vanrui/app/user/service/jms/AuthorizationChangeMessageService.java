package com.vanrui.app.user.service.jms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.vanrui.app.user.dto.AuthDetailsDto;

import ooh.bravo.context.util.SystemContextUtils;
import ooh.bravo.jms.sender.JmsProduceService;
import ooh.bravo.security.web.constant.AuthorizationChangeType;
import ooh.bravo.security.web.constant.DestinationKey;
import ooh.bravo.security.web.dto.RoleAuthorizationChangeDTO;
import ooh.bravo.service.BaseService;

@Service("authzChangeMessageService")
public class AuthorizationChangeMessageService extends BaseService {

    @Autowired
    private JmsProduceService jmsProduceService;
    
    @Async
    public void sendMessage(AuthDetailsDto authDetailsDto) {
        logger.info("发送角色授权变更消息开始, roleId={}", authDetailsDto == null ? null : authDetailsDto.getrId());
        this.send(null);
    }

    @Async
    public void sendMessage(Long roleId) {
        logger.info("发送角色授权变更消息开始, roleId={}", roleId);
        this.send(null);
    }
    
    /**
     * 为指定ID发送用户授权变更消息
     * @param userIdList 用户ID集合
     */
    @Async
    public void sendForUserIdList(List<Long> userIdList) {
    	if (userIdList != null && userIdList.size() != 0) {
            for (Long userId : userIdList) {
                logger.info("修改角色-发送角色授权变更消息开始, userId={}", userId);
                this.send(userId);
            }
        }
    }

    public  void send(Long userId) {
        logger.info("发送用户授权变更消息开始, userId={}", userId);
        RoleAuthorizationChangeDTO roleAuthzChangeDTO = new RoleAuthorizationChangeDTO();
        if(userId!=null){
            roleAuthzChangeDTO.setType(AuthorizationChangeType.USER);
            roleAuthzChangeDTO.setUserID( userId );
        }
        roleAuthzChangeDTO.setTenantID(SystemContextUtils.getTenantID());
        jmsProduceService.sendTextMessageByTopic(DestinationKey.PRODUCER_TOPIC_ROLE_AUTHZ_CHANGE, roleAuthzChangeDTO);
    }
}
