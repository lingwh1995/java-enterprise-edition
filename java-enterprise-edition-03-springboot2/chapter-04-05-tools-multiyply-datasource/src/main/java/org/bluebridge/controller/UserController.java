package org.bluebridge.controller;

import org.bluebridge.domain.User;
import org.bluebridge.service.master.IUserServiceMaster;
import org.bluebridge.service.slave.IUserServiceSlave;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource
    private IUserServiceMaster userServiceMaster;

    @Resource
    private IUserServiceSlave userServiceSlave;

    /**
     * 访问   http://localhost:8080/user/master/1
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/user/master/{id}")
    public User getUserByIdMaster(@PathVariable("id") int id) {
        return userServiceMaster.getUserById(id);
    }

    /**
     * 访问   http://localhost:8080/user/slave/1
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/user/slave/{id}")
    public User getUserByIdSlave(@PathVariable("id") int id) {
        return userServiceSlave.getUserById(id);
    }
}
