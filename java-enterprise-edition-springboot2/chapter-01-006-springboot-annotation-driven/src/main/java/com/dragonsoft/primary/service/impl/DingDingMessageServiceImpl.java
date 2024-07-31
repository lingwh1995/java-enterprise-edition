package com.dragonsoft.primary.service.impl;

import com.dragonsoft.primary.service.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class DingDingMessageServiceImpl implements IMessageService {

    @Override
    public String sendMessage(String message) {
        return "this is send dingding message -" + message;
    }
}
