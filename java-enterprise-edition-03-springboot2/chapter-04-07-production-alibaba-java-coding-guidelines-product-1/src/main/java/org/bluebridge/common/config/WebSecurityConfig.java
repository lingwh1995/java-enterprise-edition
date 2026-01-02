package org.bluebridge.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.bluebridge.common.constant.EnvironmentConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingwh
 * @desc 配置放行文件（包括war包形式部署在tomcat下的放行文件）
 * @date 2025/11/22 17:45
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    // 1. 使用 final 修饰符，确保变量不可变且必须在构造时初始化
    private final Environment environment;

    // 2. 构造函数注入：这是 Spring 官方最推荐、最健壮的方式
    // Spring Boot 启动时，如果发现只有这一个构造函数，会自动把 Environment 传进来
    public WebSecurityConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 3. 在这里使用 environment 绝对不会是 null
        String activeProfile = environment.getActiveProfiles()[0];
        // 如果是开发环境，则不注册拦截器
        if (EnvironmentConstants.DEV.equals(activeProfile)) {
            return;
        }

        //存放拦截器放行的的路径
        List<String> excludePathPatterns = new ArrayList<>();
        excludePathPatterns.add("/");
        excludePathPatterns.add("/logo.ico");
        excludePathPatterns.add("/index.html");
        excludePathPatterns.add("/assets/*.css");
        excludePathPatterns.add("/assets/*.js");
        excludePathPatterns.add("/assets/*.jpg");
        excludePathPatterns.add("/assets/*.png");

        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }

}
