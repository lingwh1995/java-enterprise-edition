package org.bluebridge.controller.application;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = {"/domainObject"})
@Controller
public class ServletApplicationScopeController {

    @RequestMapping(value = "/servlet/applicationScope/httpServletRequest")
    public String applicationScopeByServletByHttpServletRequest(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("servletContextScope","存放在servletContext域对象中的值[基于Servlet提供的HttpServletRequest]");
        return "success";
    }

    @RequestMapping(value = "/servlet/applicationScope/httpSession")
    public String applicationScopeByServletByHttpSession(HttpSession session) {
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("servletContextScope","存放在servletContext域对象中的值[基于Servlet提供的HttpSession]");
        return "success";
    }
}
