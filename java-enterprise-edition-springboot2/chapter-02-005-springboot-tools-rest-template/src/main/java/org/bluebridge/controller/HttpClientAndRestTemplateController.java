package org.bluebridge.controller;

import org.bluebridge.domain.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于返回模拟数据
 * @author ronin
 * @version V1.0
 * @since 2019/11/15 9:20
 */
@Controller
public class HttpClientAndRestTemplateController {

//    /**
//     * 测试@ResponseBody注解:加在类位置
//     * 模拟get请求的接口
//     * @return
//     */
//    @ResponseBody
//    @GetMapping(value="/users")
//    public List<User> getUsers(){
//        ArrayList<User> users = new ArrayList<User>();
//        users.add(new User("zs","18"));
//        users.add(new User("ls","28"));
//        users.add(new User("ww","38"));
//        return users;
//    }

//    /**
//     * 测试@ResponseBody注解:加在返回值位置
//     * 模拟get请求的接口
//     * @return
//     */
//    @GetMapping(value="/users")
//    public @ResponseBody List<User> getUsers(){
//        ArrayList<User> users = new ArrayList<User>();
//        users.add(new User("zs","18"));
//        users.add(new User("ls","28"));
//        users.add(new User("ww","38"));
//        return users;
//    }

    /**
     * 测试@RequestMapping注解的produces属性:值为produces = {"application/json;charset=UTF-8"}
     * 返回json格式数据:
          注意:
              1.加了@ResponseBody注解后,produes={"application/json;charset=UTF-8"}可以省略
              2.produces属性取值有两种:
                  produces = {"application/json;charset=UTF-8"}
                  produces = MediaType.APPLICATION_JSON_VALUE
     * 模拟get请求的接口:查询所有
     * @return
     */
    @ResponseBody
    @GetMapping(value="/users",produces = {"application/json;charset=UTF-8"})
    public List<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("zs","18"));
        users.add(new User("ls","28"));
        users.add(new User("ww","38"));
        return users;
    }

    /**
     * 查询单个
     * @return
     */
    @ResponseBody
    @GetMapping(value="/user/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUsers(@PathVariable("id") Integer id){
        if(id == 1){
            return new User("zs","18");
        }
        return null;
    }

//    /**
//     * 测试@RequestMapping注解的produces属性:值为"produces = {"application/xml;charset=UTF-8}
//     * 返回xml格式数据:
//     *      注意:
//     *          1.要返回xml格式的数据,需要在pom中加入以下依赖
//     *              <dependency>
//     *                  <groupId>com.fasterxml.jackson.dataformat</groupId>
//     *                  <artifactId>jackson-dataformat-xml</artifactId>
//     *              </dependency>
//     *          2.produces属性的取值有两种:
//     *              produces = {"application/xml;charset=UTF-8"}
//     *              produces = MediaType.APPLICATION_XML_VALUE
//     *          3.consumes="application/json"属性作用:
//     *              方法仅处理request Content-Type为“application/json”类型的请求
//     * 模拟get请求的接口
//     * @return
//     */
//    @ResponseBody
//    @GetMapping(value="/users",produces = MediaType.APPLICATION_XML_VALUE)
//    public List<User> getUsers(){
//        ArrayList<User> users = new ArrayList<User>();
//        users.add(new User("zs","18"));
//        users.add(new User("ls","28"));
//        users.add(new User("ww","38"));
//        return users;
//    }
}
