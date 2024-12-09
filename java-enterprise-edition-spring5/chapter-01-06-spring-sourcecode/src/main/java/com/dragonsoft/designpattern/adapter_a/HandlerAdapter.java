package com.dragonsoft.designpattern.adapter_a;

public interface HandlerAdapter {
    boolean supports(Object handler);
    void handle(Object handler);

}
