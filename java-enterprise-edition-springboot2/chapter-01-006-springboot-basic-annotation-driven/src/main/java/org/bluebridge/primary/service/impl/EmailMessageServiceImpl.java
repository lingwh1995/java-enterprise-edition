package org.bluebridge.primary.service.impl;

import org.bluebridge.primary.service.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class EmailMessageServiceImpl implements IMessageService {

    @Override
    public String sendMessage(String message) {
        return "this is send email message -" + message;
    }
}
