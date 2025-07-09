package org.bluebridge;

import org.bluebridge.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 测试RestTemplate的exchange方法
 * @author ronin
 * @version V1.0
 * @since 2019/11/15 15:32
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateExchangeTest {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * exchange()方式发送PUT请求
     */
    @Test
    public void fun1(){
        String url = "http://localhost:8080/rest/put";
        HttpHeaders headers = new HttpHeaders();
        //header可以不设置值
        //headers.set("phone", "1234567");
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("name", "zhaoliu");
        params.add("age", "55");
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url,
                HttpMethod.PUT, httpEntity, User.class);
        System.out.println(responseEntity);
    }

    /**
     * exchange()方式发送POST请求
     */
    @Test
    public void fun2(){
        String url = "http://localhost:8080/rest/post";
        HttpHeaders headers = new HttpHeaders();
        //header可以不设置值
        //headers.set("phone", "1234567");
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("name", "zhaoliu");
        params.add("age", "55");
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, User.class);
        System.out.println(responseEntity);
    }

    /**
     * exchange()方式发送GET请求:查询单个并且不传递参数
     *  http://localhost:8080/rest/get/1
     */
    @Test
    public void fun3(){
        String url = "http://localhost:8080/rest/get/1";
        ResponseEntity<User> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, null, User.class);
        System.out.println(responseEntity);
    }

    /**
     * exchange()方式发送GET请求:查询单个并且传递参数
     *      注意:url形式可以为以下形式
     *          http://localhost:8080/rest/get?name=zs
     */
    @Test
    public void fun4(){
        String url = "http://localhost:8080/rest/get?name=zs";
        ResponseEntity<User> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, null, User.class);
        System.out.println(responseEntity);
    }

    /**
     * exchange()方式发送DELETE请求:
     *      注意:url形式可以为以下形式
     *          http://localhost:8080/rest/delete/1
     */
    @Test
    public void fun5(){
        String url = "http://localhost:8080/rest/delete/1";
        ResponseEntity<User> responseEntity = restTemplate.exchange(url,
                HttpMethod.DELETE, null, User.class);
        System.out.println(responseEntity);
    }

    /**
     * exchange()方式发送请求时用替换参数中的占位符,
     * GET、POST、DELETE、PUT这几种REST风格的请求都可以通过下面的方式来传递参数
     * 注意:
     *      url的形式可以是下面的形式:
     *          http://localhost:8080/rest/get/{1}
     *          http://localhost:8080/rest/get/{placeholder}
     */
    @Test
    public void fun6(){
        String url = "http://localhost:8080/rest/get/{1}";
        ResponseEntity<User> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, null, User.class,1);
        System.out.println(responseEntity);
    }
}
