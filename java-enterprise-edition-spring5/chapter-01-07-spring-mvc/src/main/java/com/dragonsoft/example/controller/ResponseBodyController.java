package com.dragonsoft.example.controller;

import com.dragonsoft.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResponseBodyController {
    /**
     * @ResponseBody注解需要引用下面的三个包
     * SpringMVC使用:HttpMessageConverter将数据转换为Json格式
     */
//     <dependency>
//            <groupId>com.fasterxml.jackson.core</groupId>
//            <artifactId>jackson-databind</artifactId>
//            <version>2.9.9</version>
//        </dependency>
//        <dependency>
//            <groupId>com.fasterxml.jackson.core</groupId>
//            <artifactId>jackson-core</artifactId>
//            <version>2.9.9</version>
//        </dependency>
//        <dependency>
//            <groupId>com.fasterxml.jackson.core</groupId>
//            <artifactId>jackson-annotations</artifactId>
//            <version>2.9.9</version>
//        </dependency>

    /**
     * 返回Json格式数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value="testResponsebody",method= RequestMethod.GET)
    public Person testResponsebody(){
        Person person = new Person("1","zhangsan","123456","145@qq.com",18);
        return person;
    }
}
