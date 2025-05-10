package org.bluebridge.ioc.loopdependencies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 */
@Service
public class UserService {

    @Autowired
    private PersonService personService;

    public UserService(){
        System.out.println("UserService Constructor......");
    }
    public void getPersonService(){
        System.out.println("personService:"+personService);
    }
}
