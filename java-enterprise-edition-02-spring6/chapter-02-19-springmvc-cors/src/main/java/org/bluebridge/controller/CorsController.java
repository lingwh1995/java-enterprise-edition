package org.bluebridge.controller;

import org.bluebridge.domian.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试跨域功能的后台接口
 */
@RequestMapping("/cors")
@Controller
public class CorsController {


    /**
     * 参考文档 //https://zhuanlan.zhihu.com/p/118381660
     * @return
     */
    @ResponseBody
    @GetMapping("/user")
    public List<User> getUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(new User("001","张三",18));
        userList.add(new User("002","李四",24));
        userList.add(new User("003","王五",36));
        return userList;
    }

}
