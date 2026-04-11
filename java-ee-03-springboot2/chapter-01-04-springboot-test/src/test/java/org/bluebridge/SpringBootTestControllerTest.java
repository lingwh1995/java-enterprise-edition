package org.bluebridge;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.service.SpringBootTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 测试SpringBootTestController
 * @date 2025/11/4 17:50
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootTestControllerTest {

    @Resource
    private SpringBootTestService springBootTestService;

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void testHello(){
        String result = springBootTestService.hello();
        log.info("Service层中方法 hello() 返回值: {}", result);
    }

    @Test
    public void testHelloUseTemplate(){
        String result = restTemplate.getForObject("http://localhost:8080/springboot-test", String.class);
        log.info("Controller层中方法 hello() 返回值: {}", result);
    }

}
