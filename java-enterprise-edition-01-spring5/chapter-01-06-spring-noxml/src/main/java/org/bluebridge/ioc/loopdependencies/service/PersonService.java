package org.bluebridge.ioc.loopdependencies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lingwh
 * @desc   零配置搭建Spring开发环境测试
 * @date   2019/4/4 10:38
 */
@Service
public class PersonService {
    @Autowired
    private UserService userService;

    public PersonService(){
        System.out.println("PersonService Constructor......");
    }

}
