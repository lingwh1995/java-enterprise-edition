package org.bluebridge.controller.requestscope;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = {"/domainObject"})
@Controller
public class ServletRequestScopeController {

    @RequestMapping(value = "/servlet/requestScope/httpServletRequest")
    public String requestScopeByServletByHttpServletRequest(HttpServletRequest request) {
        request.setAttribute("requestScope","存放在request域对象中的值[基于Servlet提供的HttpServletRequest]");
        return "success";
    }

}
