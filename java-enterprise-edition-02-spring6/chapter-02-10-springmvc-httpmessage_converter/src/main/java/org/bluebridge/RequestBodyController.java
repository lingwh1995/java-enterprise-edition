package org.bluebridge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/httpmessageconverter")
@Controller
public class RequestBodyController {

    /**
     * 以字符串形式获取请求报文
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/requestBody",method = RequestMethod.POST)
    public String requestBody(@RequestBody String requestBody) {
        System.out.println("本次请求全部的请求体信息: " + requestBody);
        return "success";
    }
}
