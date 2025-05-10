package org.bulebridge.ioc.loopdependencies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 */
@Service
public class PersonService {
    @Autowired
    private UserService userService;

    public PersonService(){
        System.out.println("PersonService Constructor......");
    }

}
