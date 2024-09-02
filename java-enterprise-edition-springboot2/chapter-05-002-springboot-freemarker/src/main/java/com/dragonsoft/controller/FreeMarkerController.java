package com.dragonsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/28 16:11
 */
@Controller
public class FreeMarkerController {

    /**
     * 访问:localhost:8080/index
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(){
        return "views/index";
    }

    /**
     * 访问:localhost:8080/welcome
     * @return
     */
    @RequestMapping(value = "/welcome")
    public String welcome(Model model){
        model.addAttribute("message","I am message from FreeMarker-Controller");
        return "views/welcome";
    }
}
