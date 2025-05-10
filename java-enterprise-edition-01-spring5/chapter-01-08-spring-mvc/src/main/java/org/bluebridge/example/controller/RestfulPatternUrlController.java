package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * SpringMVC的Rest风格的URL:
 *      向后台发送PUT/DELETE请求要求如下:
 *          1.需要在web.xml中配置HiddenHttpMethodFilter
 *          2.需要发送post请求
 *          3.在发送post请求的时候在表单中携带一个name="_method",value="PUT"/value="DELETE"的隐藏域
 *      REST风格的C0RUD(以订单为例 ):
 *          REST风格URL写法      请求方式    传统的URL写法
 *          新增:/order       POST
 *          修改:/order/1     PUT         /update?id=1
 *          删除:/order/1     DELETE      /delete?id=1
 *          查询:/order/1     GET         /get?id=1
 *      PathVariable("id"):使用这个注解获取URL中的参数值
 */
@Controller
public class RestfulPatternUrlController {
    private static final String SUCCESS = "success";

    /**
     * 增加
     * @return
     */
    @RequestMapping(value="testPost",method=RequestMethod.POST)
    public String testPost(){
        System.out.println("testPost");
        return SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="testDelete/{id}",method=RequestMethod.DELETE)
    public String testDelete(@PathVariable("id") String id){
        System.out.println("testDelete"+id);
        return SUCCESS;
    }

    /**
     * 修改
     * @param id
     * @return
     */
    @RequestMapping(value="testPut/{id}",method=RequestMethod.PUT)
    public String testPut(@PathVariable("id") String id){
        System.out.println("testPut"+id);
        return SUCCESS;
    }

    /**
     * 查询
     * @param id
     * @return
     */
    @RequestMapping(value="testGet/{id}",method=RequestMethod.GET)
    public String testGet(@PathVariable("id") String id){
        System.out.println("testGet"+id);
        return SUCCESS;
    }
}
