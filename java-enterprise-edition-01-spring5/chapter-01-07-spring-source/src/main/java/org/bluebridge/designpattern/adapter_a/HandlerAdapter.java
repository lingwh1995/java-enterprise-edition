package org.bluebridge.designpattern.adapter_a;

public interface HandlerAdapter {
    boolean supports(Object handler);
    void handle(Object handler);

}
