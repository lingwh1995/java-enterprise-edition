package org.bluebridge.controller;

import org.bluebridge.domain.PersonProto;
import org.springframework.web.bind.annotation.*;

/**
 * @author lingwh
 * @desc  protobuf 测试控制器
 * @date  2025/11/4 15:52
 */
@RestController
public class ProtobufController {

    // 1. 响应 Protobuf 格式数据（返回 PersonProto.Person）
    @GetMapping(value = "/protobuf/{id}", produces = "application/x-protobuf")
    public PersonProto.Person getPersonById(@PathVariable Integer id) {
        // 模拟数据库查询
        return PersonProto.Person.newBuilder()
                .setId(id)
                .setName("张三")
                .setEmail("zhangsan@example.com")
                .setAddress("xian")
                .build();
    }

    // 2. 接收 Protobuf 格式请求（参数为 PersonProto.Person），响应 Protobuf 格式
    @PostMapping(value = "/protobuf/query", consumes = "application/x-protobuf", produces = "application/x-protobuf")
    public PersonProto.Person queryPerson(@RequestBody PersonProto.Person person) {
        Integer userId = person.getId();
        // 模拟查询结果
        return PersonProto.Person.newBuilder()
                .setId(userId)
                .setName("李四")
                .setEmail("lisi@example.com")
                .setAddress("beijing")
                .build();
    }

}
