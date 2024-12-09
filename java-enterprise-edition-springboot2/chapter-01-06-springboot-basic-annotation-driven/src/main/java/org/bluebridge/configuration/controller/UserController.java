package org.bluebridge.configuration.controller;

import org.bluebridge.configuration.domain.User;
import org.bluebridge.configuration.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * SpringBoot通过 @Configuration + @Bean 配置Controller
 * 这里这个接口直接实现了标记接口,标记接口是被@Controller注解修饰的
 *      public class UserController implements MarkController{}
 */
public class UserController implements MarkController {

    private IUserService userService;

    /**
     * 使用set方式注入
     * @param userService
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 访问   http://localhost:8080/add-user
     * 新增user
     * @return
     */
    @RequestMapping(value = "/add-user",method = RequestMethod.GET)
    public ResponseEntity addUser() {
        //这里只是用来测试SpringBoot使用注解驱动配置代替传统xml配置，测试时前台不会给这个user传递具体的值，这里手动设置一下
        User user = new User("zhangsan","123456");
        int row = userService.addUser(user);
        return new ResponseEntity(row, HttpStatus.OK);
    }
}
