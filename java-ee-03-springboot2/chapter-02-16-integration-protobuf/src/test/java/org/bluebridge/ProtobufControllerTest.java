package org.bluebridge;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.PersonProto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * @author lingwh
 * @desc protobuf 测试类
 * @date 2025/11/4 16:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtobufControllerTest {

    @Resource
    private RestTemplate restTemplate;

    // 测试 GET 接口（响应 Protobuf）
    @Test
    public void testGetPersonById() {
        String url = "http://localhost:8080/protobuf/1";
        HttpHeaders headers = new HttpHeaders();
        // 声明接收 Protobuf 格式响应
        headers.setAccept(MediaType.parseMediaTypes("application/x-protobuf"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        // 发送请求，指定响应类型为 PersonProto.Person
        ResponseEntity<PersonProto.Person> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, PersonProto.Person.class
        );

        // 验证结果
        assertEquals(200, response.getStatusCodeValue());
        PersonProto.Person person = response.getBody();
        log.info("person: {}", person);
    }

    // 测试 POST 接口（接收 Protobuf 请求，响应 Protobuf）
    @Test
    public void testQueryPerson() {
        String url = "http://localhost:8080/protobuf/query";
        HttpHeaders headers = new HttpHeaders();
        // 声明请求格式为 Protobuf
        headers.setContentType(MediaType.valueOf("application/x-protobuf"));
        // 声明响应格式为 Protobuf
        headers.setAccept(MediaType.parseMediaTypes("application/x-protobuf"));

        // 构建 Protobuf 请求参数
        PersonProto.Person request = PersonProto.Person.newBuilder().setId(2).build();
        HttpEntity<PersonProto.Person> requestEntity = new HttpEntity<>(request, headers);

        // 发送请求，指定响应类型为 PersonProto.Person
        ResponseEntity<PersonProto.Person> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, PersonProto.Person.class
        );

        // 验证结果
        assertEquals(200, response.getStatusCodeValue());
        PersonProto.Person person = response.getBody();
        log.info("person: {}", person);
    }

}
