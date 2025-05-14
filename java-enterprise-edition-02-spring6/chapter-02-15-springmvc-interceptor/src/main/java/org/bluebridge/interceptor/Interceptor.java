package org.bluebridge.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器  作用于控制器(Controller)执行前后
 */
public class Interceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    /**
     * 该方法在目标方法之前被调用
     *      若返回值为 true, 则继续调用后续的拦截器和目标方法
     *      若返回值为 false, 则不会再调用后续的拦截器和目标方法
     * 可以考虑做权限、 日志、事务等
     *      执行时机:   org.springframework.web.servlet.DispatcherServlet.doDispatch()中
     *      具体位置:   mv = ha.handle()方法执行之前  if (!mappedHandler.applyPreHandle(processedRequest, response)) {} 这行代码中执行
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截器中preHandle()方法执行了...");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 调用目标方法之后, 但渲染视图之前
     *      可以对请求域中的属性或视图做出修改
     *      执行时机:   org.springframework.web.servlet.DispatcherServlet.doDispatch()中
     *      具体位置:   mv = ha.handle()方法执行之后  mappedHandler.applyPostHandle(processedRequest, response, mv); 这一行代码中执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("拦截器中postHandle()方法执行了...");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在视图渲染完成之后执行，通常用来释放资源
     *      法执时机:   org.springframework.web.servlet.DispatcherServlet.processDispatchResult()中
     *      具体位置:   mappedHandler.triggerAfterCompletion(request, response, null);执行时调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("拦截器中afterCompletion()方法执行了...");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
