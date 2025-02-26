package org.bluebridge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bluebridge.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ObjectMapperController {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 访问下面路径查看返回结果
     *  http://localhost:8080/test_objectmapper
     * @return
     */
    @ResponseBody
    @GetMapping("/test_objectmapper")
    public User getUser() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        user.setAddress("北京");

        String s = objectMapper.writeValueAsString(user);
        System.out.println(s);
        return user;
    }
}
