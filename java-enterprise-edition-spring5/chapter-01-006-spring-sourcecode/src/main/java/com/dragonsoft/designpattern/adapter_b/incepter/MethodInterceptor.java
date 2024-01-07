package com.dragonsoft.designpattern.adapter_b.incepter;

public interface MethodInterceptor extends Interceptor {
	
    Object invoke(MethodInvocation invocation) throws Throwable;
}
