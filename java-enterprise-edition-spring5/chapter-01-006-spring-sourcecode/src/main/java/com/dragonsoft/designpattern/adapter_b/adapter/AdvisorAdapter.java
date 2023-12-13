package com.dragonsoft.designpattern.adapter_b.adapter;

import com.dragonsoft.designpattern.adapter_b.incepter.MethodInterceptor;
import com.dragonsoft.designpattern.adapter_b.target.Advice;
import com.dragonsoft.designpattern.adapter_b.target.Advisor;

public interface AdvisorAdapter {
    /**判断通知类型是否匹配*/
    boolean supportsAdvice(Advice advice);
    /**对应该类型Advice对应的拦截器*/
    MethodInterceptor getInterceptor(Advisor advisor);
}
