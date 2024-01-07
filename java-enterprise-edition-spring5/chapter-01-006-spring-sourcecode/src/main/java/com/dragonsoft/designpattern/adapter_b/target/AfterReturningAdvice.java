package com.dragonsoft.designpattern.adapter_b.target;

import java.lang.reflect.Method;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/23 13:47
 */
public interface AfterReturningAdvice extends AfterAdvice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
