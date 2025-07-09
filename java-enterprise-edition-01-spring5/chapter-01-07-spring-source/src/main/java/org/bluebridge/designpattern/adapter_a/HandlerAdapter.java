package org.bluebridge.designpattern.adapter_a;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/15 8:43
 */
public interface HandlerAdapter {

    boolean supports(Object handler);
    void handle(Object handler);

}
