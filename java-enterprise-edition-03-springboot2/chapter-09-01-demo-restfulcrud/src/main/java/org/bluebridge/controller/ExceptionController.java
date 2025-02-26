package org.bluebridge.controller;

import org.bluebridge.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author ronin
 */
@Controller
public class ExceptionController {
    /**
     * testexception简称te
     * 测试链接:
     *      http://localhost:8080/crud/te?user=aaa
     * @param user
     * @return
     */
    @RequestMapping("/te")
    public String testexcExption(@RequestParam("user") String user){
        if("aaa".equalsIgnoreCase(user)){
            throw new UserNotExistException();
        }
        return "success";
    }
}
