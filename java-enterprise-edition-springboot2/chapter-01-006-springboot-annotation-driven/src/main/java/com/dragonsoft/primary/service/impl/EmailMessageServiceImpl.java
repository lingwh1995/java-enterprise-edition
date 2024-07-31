package com.dragonsoft.primary.service.impl;

import com.dragonsoft.primary.service.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class EmailMessageServiceImpl implements IMessageService {

    @Override
    public String sendMessage(String message) {
        return "this is send email message -" + message;
    }
}
