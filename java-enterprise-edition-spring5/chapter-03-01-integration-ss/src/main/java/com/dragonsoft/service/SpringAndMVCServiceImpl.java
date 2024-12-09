package com.dragonsoft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpringAndMVCServiceImpl implements ISpringAndMVCService {

    @Value("${jdbc.user}")
    private String username;

    @Value("${application.basepackage}")
    private String basePackge;

    public SpringAndMVCServiceImpl(){
        System.out.println("------------------Service中构造方法------------------");
        System.out.println("username:"+username);
        System.out.println("basePackge:"+basePackge);
        System.out.println("SpringAndMVCServiceImpl Constructor......");
        System.out.println("------------------Service中构造方法------------------");
    }

    @Override
    public void save() {
        System.out.println("------------------Service中save方法------------------");
        System.out.println("username:"+username);
        System.out.println("basePackge:"+basePackge);
        System.out.println("save.....");
        System.out.println("------------------Service中save方法------------------");
    }


}
