package com.dragonsoft.example.controller;

import com.dragonsoft.domain.Address;
import com.dragonsoft.domain.User;
//import com.dragonsoft.example.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在SpingMVC中使用Servlet原生API,HttpServlertRequest HttpServletResponse
 */
@Controller
public class ServletApiController {

    private static final String SUCCESS = "success";

    /**
     * 在SpingMVC中使用Servlet原生API
     * @return
     */
    @RequestMapping(value="testServletApi",method = RequestMethod.POST)
    public String testServletApi(HttpServletRequest request, HttpServletResponse response){
//        User user = CommonUtils.toBean(request.getParameterMap(), User.class);
//        Address address = new Address(request.getParameter("address.province"), request.getParameter("address.city"));
//        user.setAddress(address);
//        System.out.println(user);
        return SUCCESS;
    }
}
