package org.bluebridge.example.controller;


import org.bluebridge.domain.Address;
import org.bluebridge.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;


/**
 * SessionAttribuates注解:把数据放在request域的同时放入session域中:
 *      value:map的key值，用法见①②③④
 *      type:map的value的Class类型，用法见④
 */
@Controller
//@SessionAttributes("address")//①
//@SessionAttributes(value="address")//②
//@SessionAttributes(value={"user","address"})//③
//@SessionAttributes(value={"user","address"},types={String.class})//④加了types={String.class}，session同时会存放map的value类型为String的值
@SessionAttributes(types={User.class,Address.class})//⑤session同时会存放map的value的Class卡类型为User.class和Address.class的值
public class SessionAttribuatesController {

    private static final String VIEWNAME = "modelView";

    @RequestMapping(value="testSessionAttribuates")
    public String testSessionAttribuates(Model model){
        Address address = new Address("陕西", "西安");
        model.addAttribute("address",address);
        model.addAttribute("time",new Date()+",testSessionAttribuates...");
        model.addAttribute("session","session");

        User user = new User("zhangsan", "123456", "@qq.com", 18, address);
        model.addAttribute("user",user);
        return VIEWNAME;
    }
}
