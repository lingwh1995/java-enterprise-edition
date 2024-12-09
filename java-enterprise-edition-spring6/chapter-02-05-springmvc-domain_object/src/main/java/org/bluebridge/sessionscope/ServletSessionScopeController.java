package org.bluebridge.sessionscope;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = {"/domainObject"})
@Controller
public class ServletSessionScopeController {

    /**
     * 使用HttpServletRequest对象获取HttpSession对象
     * @param request
     * @return
     */
    @RequestMapping(value = "/servlet/sessionScope/httpServletRequest")
    public String sessionScopeByServletByHttpServletRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("sessionScope","存放在session域对象中的值[基于Servlet提供的HttpServletRequest]");
        return "success";
    }

    /**
     * 直接在参数处写上HttpSession对象
     * @param session
     * @return
     */
    @RequestMapping(value = "/servlet/sessionScope/httpSession")
    public String sessionScopeByServletByHttpSession(HttpSession session) {
        session.setAttribute("sessionScope","存放在session域对象中的值[基于Servlet提供的HttpSession]");
        return "success";
    }

}
