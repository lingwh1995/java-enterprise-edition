package org.bluebridge.designpattern.adapter_a;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/15 8:45
 */
public class SimpleHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleController);
    }

    @Override
    public void handle(Object handler) {
        ((SimpleController)handler).doSimplerHandler();
    }

}
