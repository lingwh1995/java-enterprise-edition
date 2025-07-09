package org.bluebridge;
import org.bluebridge.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用RestTemplate完成远程调用
 * @author ronin
 * @version V1.0
 * @since 2019/11/15 13:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * getForEntity()
     * 测试get方式远程调用,url的形式可以为下面的形式:
     *      http://localhost:8080/get?name={?}
     *      http://localhost:8080/get?name={1}
     *      http://localhost:8080/get?name={xxx}
     *      http://localhost:8080/get?name={name}
     * 注意:参数是直接写在方法里面的
     */
    @Test
    public void fun1(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/get?name={name}", String.class, "ls");
        System.out.println(responseEntity);
    }

    /**
     * getForEntity()
     * 测试get方式远程调用,url的形式可以为下面的形式:
     *      http://localhost:8080/get?name={?}
     *      http://localhost:8080/get?name={1}
     *      http://localhost:8080/get?name={xxx}
     *      http://localhost:8080/get?name={name}
     *  注意:
     *      1.参数是通过map传递进的
     *      2.map的key必须和请求url中?的值保持一致,这里是字符串name
     */
    @Test
    public void fun2(){
        Map<String, String> map = new HashMap<String,String>();
        map.put("name", "ls");
        //泛型为User
        ResponseEntity<User> responseEntity1 = restTemplate.getForEntity("http://localhost:8080/get?name={name}", User.class, map);
        System.out.println(responseEntity1);
        //泛型为String
        ResponseEntity<String> responseEntity2 = restTemplate.getForEntity("http://localhost:8080/get?name={name}", String.class, map);
        System.out.println(responseEntity2);
    }

    /**
     * getForObject()
     *      测试get方式远程调用,url的形式可以为下面的形式:
     *          http://localhost:8080/get?name={?}
     *          http://localhost:8080/get?name={1}
     *          http://localhost:8080/get?name={xxx}
     *          http://localhost:8080/get?name={name}
     *      注意:
     *          1.getForObject()直接就可以返回需要的数据类型,而getForEntity()不会直接返回想要的数据类型,
     *            而是把返回结果封装到ResponseEntity中
     *          2.使用map传递参数
     */
    @Test
    public void fun3(){
        Map<String, String> map = new HashMap<String,String>();
        map.put("name", "ls");
        User user = restTemplate.getForObject("http://localhost:8080/get?name={name}", User.class, map);
        System.out.println(user);
    }

    /**
     * 使用RestTemplate进行远程调用:查询单个并且不传递参数
     */
    @Test
    public void fun4(){
        User user = restTemplate.getForObject("http://localhost:8080/get/1",User.class);
        System.out.println(user);
    }

    /**
     * getForObject()
     *      测试get方式远程调用,url的形式可以为下面的形式:
     *          http://localhost:8080/get?name={?}
     *          http://localhost:8080/get?name={1}
     *          http://localhost:8080/get?name={xxx}
     *          http://localhost:8080/get?name={name}
     *      注意:
     *          1.getForObject()直接就可以返回需要的数据类型,而getForEntity()不会直接返回想要的数据类型,
     *            而是把返回结果封装到ResponseEntity中
     *          2.不使用map传递参数,适用可变参数传递参数
     */
    @Test
    public void fun5(){
        User user = restTemplate.getForObject("http://localhost:8080/get?name={name}", User.class, "ls");
        System.out.println(user);
    }

    /**
     * postForEntity()
     *      注意:
     *          1.getForEntity()不会直接返回想要的数据类型,而是把返回结果封装到ResponseEntity中
     *          2.使用map传递参数,适用可变参数传递参数
     */
    @Test
    public void fun6(){
        String url = "http://localhost:8080/rest/post";
        HttpHeaders headers = new HttpHeaders();
        //header可以不设置值
        //headers.set("phone", "1234567");
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("name", "zhaoliu");
        params.add("age", "55");
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println(responseEntity1);
        ResponseEntity<User> responseEntity2 = restTemplate.postForEntity(url, httpEntity, User.class);
        System.out.println(responseEntity2);
    }

    /**
     * postForObject()
     * 向后台提交数据
     *      注意:
     *          1.getForObject()直接就可以返回需要的数据类型,而getForEntity()不会直接返回想要的数据类型,
     *            而是把返回结果封装到ResponseEntity中
     *          2.使用map传递参数,适用可变参数传递参数
     */
    @Test
    public void fun7(){
        String url = "http://localhost:8080/post";
        HttpHeaders headers = new HttpHeaders();
        //header可以不设置值
        //headers.set("phone", "1234567");
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("name", "zhaoliu");
        params.add("age", "55");
        HttpEntity httpEntity = new HttpEntity(params, headers);
        User user = restTemplate.postForObject(url, httpEntity, User.class);
        System.out.println(user);
    }

    @Test
    public void fun71(){
        String url = "http://localhost:8080/rest/postjson";
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Content-Type", "application/json");
        Map<String, String> requestBody = new HashMap();
        requestBody.put("name","zhangsan");
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<Map<String, String>>(requestBody, requestHeaders);
        String postForObjectResult = restTemplate.postForObject(url, requestEntity,String.class);
        System.out.println(postForObjectResult);
        ResponseEntity<String> postForEntityResult = restTemplate.postForEntity(url, requestEntity,String.class);
        System.out.println(postForEntityResult);
    }


    /**
     * put()
     */
    @Test
    public void fun8(){
        String url = "http://localhost:8080/put";
        HttpHeaders headers = new HttpHeaders();
        //header可以不设置值
        //headers.set("phone", "1234567");
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("name", "zhaoliu");
        params.add("age", "55");
        HttpEntity httpEntity = new HttpEntity(params, headers);
        restTemplate.put(url, httpEntity, User.class);
    }

    /**
     * delete()
     */
    @Test
    public void fun9(){
        String url = "http://localhost:8080/delete/1";
        restTemplate.delete(url);
    }


    /**
     * 占位符处理示例程序:
     *      注意:url可以是以下的形式
     *      http://localhost:8080/get/{1}
     *      http://localhost:8080/get/{id}
     *      http://localhost:8080/get/{placeholder}
     */
    @Test
    public void fun10(){
        User user = restTemplate.getForObject("http://localhost:8080/get/{id}",User.class,1);
        System.out.println(user);
    }
}
