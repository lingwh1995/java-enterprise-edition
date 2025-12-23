package org.bluebridge.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lingwh
 * @desc 配置放行文件（包括war包形式部署在tomcat下的放行文件）
 * @date 2025/11/22 17:45
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    /** 开发环境标识 */
    private static final String DEV_PROFILE = "dev";

    /** 获取当前运行环境 */
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 如果是开发环境，则不注册拦截器
        if (DEV_PROFILE.equals(activeProfile)) {
            return;
        }

        //存放拦截器放行的的路径
        List<String> excludePathPatterns = new ArrayList<>();
        excludePathPatterns.add("/");
        excludePathPatterns.add("/index.html");
        excludePathPatterns.add("/assets/*.css");
        excludePathPatterns.add("/assets/*.js");
        excludePathPatterns.add("/assets/*.jpg");
        excludePathPatterns.add("/users/login");
        excludePathPatterns.add("/users/logout");
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }

}
