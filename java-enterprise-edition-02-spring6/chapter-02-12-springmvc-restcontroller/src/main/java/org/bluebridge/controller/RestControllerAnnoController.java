package org.bluebridge.controller;

import org.bluebridge.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 1.@RestController的作用相当于给Controller中每一个方法都添加了@ResponseBody注解
 * 2.在使用了@RestController后，如果要进行页面跳转，使用ModelAndView封装视图
 * 3.@RestController(value="xxx")中xxx相当于@Controller(value="xxx"),是按名称查找时bean的id，不是路径
 *      如果要设置最顶层访问路径，则还是需要使用@RequestMapping配置
 *      @RestController 效果等同于 @Controller + @ResponseBody，不等同于 @Controller + @ResponseBody + @RequestMapping
 */
@RestController
@RequestMapping(value = "/restcontroller")
public class RestControllerAnnoController {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerAnnoController.class);

    /**
     * 在使用了@RestController后，如果要进行页面跳转，使用ModelAndView封装视图
     * @return
     */
    @RequestMapping("/success")
    public ModelAndView success() {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("跳转到success页面...");
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * 这里在使用了@RestController后，没有使用ModelAndView封装视图，只会返回一个字符串，不会进行页面跳转
     * @return
     */
    @RequestMapping(value = "/testRestControllerAnno")
    public String testRestControllerAnno() {
        return "测试@RestController注解作用(不会进行页面跳转，会直接返回一个字符串)";
    }

    @RequestMapping(value = "/employee/{id}")
    public Employee getEmployeeById(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee(1, "张三", "zhangsan@gmail.com", 1);
        return employee;
    }
}
