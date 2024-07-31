package com.dragonsoft.primary.controller;

import com.dragonsoft.primary.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MessageController {

    @Resource
    private IMessageService messageService;

    /**
     * 访问  http://localhost:8080/send-message    查看效果
     * @return
     */
    @ResponseBody
    @RequestMapping("/send-message")
    public String sendMessage(){
        String message = "hello springboot~";
        return messageService.sendMessage(message);
    }
}
