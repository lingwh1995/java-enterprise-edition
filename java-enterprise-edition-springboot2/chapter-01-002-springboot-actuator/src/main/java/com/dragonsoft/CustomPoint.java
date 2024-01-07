package com.dragonsoft;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

/**
 * 扩展新端点
 * @author ronin
 */
@Component
@WebEndpoint(id = "customPoint")
public class CustomPoint {

    /**
     * 访问url: 注意传参方式
     *      http://localhost:8080/actuator/customPoint/path?name=xx
     * @param name
     * @return
     */
    @ReadOperation
    public String getCustom(@Selector String name) {
        return "MyName is ." + name;
    }
}

