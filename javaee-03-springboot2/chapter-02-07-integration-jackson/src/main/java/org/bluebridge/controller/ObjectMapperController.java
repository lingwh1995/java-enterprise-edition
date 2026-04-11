package org.bluebridge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        // 把java对象转换成json字符串
        String userJson = objectMapper.writeValueAsString(user);
        System.out.println("userJson = " + userJson);

        // 把json字符串转换成JsonNode对象
        JsonNode jsonNode = objectMapper.readTree(userJson);
        System.out.println(jsonNode);
        int id = jsonNode.get("id").asInt();
        String username = jsonNode.get("username").asText();
        String address = jsonNode.get("address").asText();
        System.out.println("id = " + id);
        System.out.println("username = " + username);
        System.out.println("address = " + address);

        // JsonNode对象只能读取值，不能设置值，要设置值，将JsonNode对象转换成ObjectNode对象
        ObjectNode objectNodeFromJsonNode = (ObjectNode) jsonNode;
        objectNodeFromJsonNode.put("id",2);
        objectNodeFromJsonNode.put("username","李四");
        objectNodeFromJsonNode.put("address","西安");
        System.out.println("objectNodeFromJsonNode = " + objectNodeFromJsonNode);

        // 创建ObjectNode对象
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("id", 3);
        objectNode.put("username", "王五");
        objectNode.put("address", "上海");
        // 将objectNode对象转换成java对象
        user = objectMapper.treeToValue(objectNode, User.class);
        System.out.println("user = " + user);
        return user;
    }
}
