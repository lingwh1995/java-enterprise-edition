package org.bluebridge.event_driven;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lingwh
 * @desc Spring 工具类测试类
 * @date 2026/1/10 12:01
 */
// 使用 Spring 的运行器
@RunWith(SpringJUnit4ClassRunner.class)
// 指定配置文件
@ContextConfiguration(locations = "classpath:applicationContext-event_driven.xml")
public class SpringEventDrivenTest {

    @Autowired
    private UserService userService;

    /**
     * 测试 Spring 工具类
     */
    @Test
    public void testSpringUtils() {
        // 发布事件
        userService.register("zhangsan");
    }

}
