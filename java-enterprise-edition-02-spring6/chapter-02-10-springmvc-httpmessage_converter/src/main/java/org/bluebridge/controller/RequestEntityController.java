package org.bluebridge.controller;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/httpmessageconverter")
@Controller
public class RequestEntityController {

    /**
     * 请求头信息中的 referer 可以用在带有有分页的列表页面删除功能中，可以实现从删除成功后再跳回发送请求的页面
     * @param requestEntity
     * @return
     */
    @RequestMapping(value ="/requestEntity",method = RequestMethod.POST)
    public String requestBody(RequestEntity<String> requestEntity) {
        System.out.println("本次请求全部的请求头信息: " + requestEntity.getHeaders());
        System.out.println("本次请求全部的请求体信息: " + requestEntity.getBody());
        return "success";
    }
}
