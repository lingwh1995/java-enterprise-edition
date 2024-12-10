package com.dragonsoft.designpattern.adapter_b.adapter;

import com.dragonsoft.designpattern.adapter_b.incepter.MethodInterceptor;
import com.dragonsoft.designpattern.adapter_b.target.Advice;
import com.dragonsoft.designpattern.adapter_b.target.Advisor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/23 14:09
 */
public class DefaultAdvisorAdapterRegistry {
    private final List<AdvisorAdapter> adapters = new ArrayList<AdvisorAdapter>(3);


    /**
     * Create a new DefaultAdvisorAdapterRegistry, registering well-known adapters.
     */
    public DefaultAdvisorAdapterRegistry() {
        registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
        registerAdvisorAdapter(new AfterReturningAdviceAdapter());
        registerAdvisorAdapter(new ThrowsAdviceAdapter());
    }


    public MethodInterceptor[] getInterceptors(Advisor advisor) throws Exception {
        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>(3);
        Advice advice = advisor.getAdvice();
        if (advice instanceof MethodInterceptor) {
            interceptors.add((MethodInterceptor) advice);
        }
        for (AdvisorAdapter adapter : this.adapters) {
            if (adapter.supportsAdvice(advice)) {
                interceptors.add(adapter.getInterceptor(advisor));
            }
        }

        return interceptors.toArray(new MethodInterceptor[interceptors.size()]);
    }

    public void registerAdvisorAdapter(AdvisorAdapter adapter) {
        this.adapters.add(adapter);
    }

}
