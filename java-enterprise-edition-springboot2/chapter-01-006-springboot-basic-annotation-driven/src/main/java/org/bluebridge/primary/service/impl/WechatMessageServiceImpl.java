package org.bluebridge.primary.service.impl;

import org.bluebridge.primary.service.IMessageService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class WechatMessageServiceImpl implements IMessageService {

    @Override
    public String sendMessage(String message) {
        return "this is send wechat message -" + message;
    }
}
