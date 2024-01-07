package org.bluebridge.base.controller;

import org.bluebridge.base.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/restful")
@Controller
public class RestfulController {

    /**
     * 使用Restful模拟用户资源的增删改查
     * /user        GET       查询所有用户信息
     * /user/1      GET       根据id查询用户信息
     * /user        POST      添加用户信息
     * /user/1      DELETE    删除用户信息
     * /user        PUT       修改用户信息
     */

    /**
     * 添加用户信息
     * @return
     */
    @PostMapping(value = "/user")
    public String addUser(User user) {
        System.out.println("添加用户信息" + user.toString());
        return "success";
    }

    /**
     * 删除用户信息
     * @return
     */
    @DeleteMapping(value = "/user/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        System.out.println("根据id=" + id + "删除用户信息...");
        return "success";
    }

    /**
     * 修改用户信息
     * @return
     */
    @PutMapping(value = "/user")
    public String updateUser(User user) {
        System.out.println("修改用户信息" + user.toString());
        return "success";
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @GetMapping(value = "/user")
    public String getAllUsers() {
        System.out.println("查询所有用户信息...");
        return "success";
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    public String getUserById(@PathVariable("id") String id) {
        System.out.println("根据id=" + id + "查询用户信息...");
        return "success";
    }
}
