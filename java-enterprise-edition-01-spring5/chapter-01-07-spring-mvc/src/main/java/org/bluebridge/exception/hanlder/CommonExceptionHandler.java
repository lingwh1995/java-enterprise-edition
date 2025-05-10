package org.bluebridge.exception.hanlder;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionHandler {
    /**
     * 处理异常，和业务相分离
     * 注意:
     *     1.要把异常信息带到页面，不能在参数中传入Map，将异常信息放在Map中，然后返回String,要返回ModelAndView
     *     2.ModelAndView要写在方法体中，不能通过方法参数传入
     *     3.选择哪个方法处理异常存在优先级
     * @param e
     * @return
     */
    //使用ModelAndView正确写法
    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView handleArithmeticException(Exception e){
        System.out.println(e);
        System.out.println("专门的异常处理控制器......");
        System.out.println("出异常了......");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("exceptionMessage",e);
        return mv;
    }
}
