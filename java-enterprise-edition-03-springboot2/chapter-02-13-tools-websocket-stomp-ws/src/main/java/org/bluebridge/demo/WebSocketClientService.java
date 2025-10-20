package org.bluebridge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Service
public class WebSocketClientService {

    @Autowired
    private WebSocketStompClient stompClient;

    private StompSession stompSession;

    @PostConstruct
    public void init() throws ExecutionException, InterruptedException {
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new CustomStompSessionHandler();
        stompSession = stompClient.connect("ws://localhost:8080/ws", sessionHandler).get();
    }

    public void sendMessage(String destination, Object message) {
        stompSession.send(destination, message);
    }

}
