package com.dragonsoft.designpattern.adapter_b.target;

import java.lang.reflect.Method;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/23 13:45
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
