package com.dragonsoft.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ronin
 */
public class MyFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter执行了......");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(request.getRequestURL().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter初始化......");
    }

    @Override
    public void destroy() {
        System.out.println("filter Destory......");
    }
}
