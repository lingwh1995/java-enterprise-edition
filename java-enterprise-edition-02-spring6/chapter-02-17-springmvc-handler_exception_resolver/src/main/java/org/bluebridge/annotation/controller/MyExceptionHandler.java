package org.bluebridge.annotation.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 这个类中写的代码替代了xml中异常处理器的配置
 * @ControllerAdvice    将当前类标注为处理异常的组件
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 用注解代替xml文件中的配置
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(value = {ArithmeticException.class, NullPointerException.class})
    public String toError(Exception e, Model model){
        System.out.println(this.getClass().getName());
        model.addAttribute("exception",e);
        return "error";
    }
}
