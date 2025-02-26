package org.bluebridge;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bluebridge.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * 测试HttpClient原生方式远程调用和使用RestTemplate进行远程调用的区别
 * @author ronin
 * @version V1.0
 * @since 2019/11/15 9:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HttpClientAndRestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 使用httpClient测试get请求:获取所有
     * @throws IOException
     */
    @Test
    public void fun1() throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:8080/users");
        CloseableHttpResponse response = closeableHttpClient.execute(get);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
    }

    /**
     * 使用httpClient测试get请求:获取单个
     * @throws IOException
     */
    @Test
    public void fun2() throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:8080/user/1");
        CloseableHttpResponse response = closeableHttpClient.execute(get);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
    }

    /**
     * 使用RestTemplate进行远程调用:查询单个
     */
    @Test
    public void fun3(){
        User user = restTemplate.getForObject("http://localhost:8080/user/1",User.class);
        System.out.println(user);
    }
    /**
     * 使用RestTemplate进行远程调用:查询所有
     */
    @Test
    public void fun4(){
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:8080/users",List.class);
        System.out.println(responseEntity);
    }
}
