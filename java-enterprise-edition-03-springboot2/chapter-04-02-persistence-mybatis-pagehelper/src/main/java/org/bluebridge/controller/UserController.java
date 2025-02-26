package org.bluebridge.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bluebridge.domain.User;
import org.bluebridge.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 访问   http://localhost:8080/user/1
     * @return
     */
    @ResponseBody
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    /**
     * 访问   http://localhost:8080/users
     * @return
     */
    @ResponseBody
    @GetMapping("/users")
    public PageInfo<User> getAllUsers() {
        int currentPage = 1;
        int pageSize = 10;
        PageHelper.startPage(currentPage, pageSize);
        List<User> userList = userService.getAllUsers();
        return PageInfo.of(userList);
    }


}
