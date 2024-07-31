package com.dragonsoft.primary.service.impl;

import com.dragonsoft.primary.service.IMessageService;
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
