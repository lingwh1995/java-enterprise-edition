package org.bluebridge.designpattern.adapter_a;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/15 8:49
 */
public class AnnotationHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof AnnotationController);
    }

    @Override
    public void handle(Object handler) {
        ((AnnotationController)handler).doAnnotationHandler();
    }

}
