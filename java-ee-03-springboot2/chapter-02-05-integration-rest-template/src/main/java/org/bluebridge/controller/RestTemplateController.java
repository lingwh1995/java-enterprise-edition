package org.bluebridge.controller;

import org.bluebridge.domain.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 为RestTemplate调用提供数据
 * @author ronin
 * @version V1.0
 * @since 2019/11/15 13:53
 */
@Controller
public class RestTemplateController {

    /**
     * GET方式的请求:需要传递参数
     * @param name
     * @return
     */
    @ResponseBody
    @GetMapping("get")
    public User testget(@RequestParam("name") String name){
        User user = null;
        if("zs".equals(name)){
            user = new User("zhangsan","18");
        }else if("ls".equals(name)){
            user = new User("李四","18");
        }
        return user;
    }

    /**
     * GET方式的请求:不需要传递参数
     * @return
     */
    @ResponseBody
    @GetMapping(value="/get/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUsers(@PathVariable("id") Integer id){
        // 模拟发生了异常
        //int i = 1/0 ;
        if(id == 1){
            return new User("zs","18");
        }else if(id == 2){
            return new User("ls","28");
        }else{
            return new User("ww","38");
        }
    }

    /**
     * POST方式的请求
     * @param user 前台传递的是普通的表单数据
     * @return
     */
    @ResponseBody
    @PostMapping("post")
    public User testPost(User user){
        System.out.println("要新增的user的是:"+user);
        return user;
    }

    /**
     * POST方式的请求
     * @param user 前台传递的是json格式的参数
     * @return
     */
    @ResponseBody
    @PostMapping("postjson")
    public User testPostJson(@RequestBody User user){
        System.out.println("要新增的user的是:"+user);
        return user;
    }


    /**
     * PUT方式的请求
     * @param user 前台传递的是普通的表单数据格式
     * @return
     */
    @ResponseBody
    @PutMapping("put")
    public User testPut(User user){
        System.out.println("要更新的user的是:"+user);
        return user;
    }

    /**
     * PUT方式的请求
     * @param user 前台传递的是json格式的参数
     * @return
     */
    @ResponseBody
    @PutMapping("putjson")
    public User testPutJson(@RequestBody User user){
        System.out.println("要更新的user的是:"+user);
        return user;
    }

    /**
     * DELETE方式的请求
     * @param id 路径传参
     * @return
     */
    @ResponseBody
    @DeleteMapping("delete/{id}")
    public User testDelete(@PathVariable("id") String id){
        System.out.println("要删除的user的ID是:"+id);
        return null;
    }

    /**
     * DELETE方式的请求
     * @param id 问号传参
     * @return
     */
    @ResponseBody
    @DeleteMapping("delete")
    public User testDelete1(@RequestParam(name="id") String id){
        System.out.println("要删除的user的ID是:"+id);
        return null;
    }
}
