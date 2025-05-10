package org.bluebridge.example.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/26 15:26
 */
public class WarpperHttpServletFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WarpperHttpServletRquest wapperHttpServletRequest = new WarpperHttpServletRquest((HttpServletRequest)servletRequest);
        WarpperHttpServletResponse warpperHttpServletResponse = new WarpperHttpServletResponse((HttpServletResponse)servletResponse);
        filterChain.doFilter(wapperHttpServletRequest, warpperHttpServletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * 包装的HttpServletRequest
     */
    private class WarpperHttpServletRquest extends HttpServletRequestWrapper {
        public WarpperHttpServletRquest(HttpServletRequest request) {
            super(request);
        }

        /**
         * 对getParameter()这个方法进行增强
         * @param name
         * @return
         */
        @Override
        public String getParameter(String name) {
            return "modify:"+ super.getParameter(name);
        }
    }

    /**
     * 包装的HttpServletResponse
     */
    private class WarpperHttpServletResponse extends HttpServletResponseWrapper {
        public WarpperHttpServletResponse(HttpServletResponse response) {
            super(response);
        }
    }
}
