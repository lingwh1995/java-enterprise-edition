package org.bluebridge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private WebSocketClientService clientService;

    public void sendSampleMessage() {
        clientService.sendMessage("/app/sendMessage", "Hello WebSocket!");
    }

}
