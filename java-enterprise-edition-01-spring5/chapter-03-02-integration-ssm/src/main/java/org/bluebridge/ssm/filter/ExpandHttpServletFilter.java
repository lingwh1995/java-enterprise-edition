package org.bluebridge.ssm.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 用来增强Servlet的Filter
 */
public class ExpandHttpServletFilter implements Filter {
    private static  Logger logger = Logger.getLogger("ExpandHttpServletFilter.class");
    /**
     * 缓存项目访问路径
     */
    private static String _contextPath = null;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 获取项目完整上下文根路径，并放于request域中
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            if (_contextPath == null) {
                _contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() ;
            }
            request.setAttribute("_contextPath", _contextPath);
            logger.info("项目完整上下文根路径:" + _contextPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("获取项目完整上下文根路径时发生了异常！");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
