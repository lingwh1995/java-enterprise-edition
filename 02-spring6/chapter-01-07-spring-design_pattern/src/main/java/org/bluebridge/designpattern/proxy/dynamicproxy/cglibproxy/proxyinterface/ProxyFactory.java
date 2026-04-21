package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyinterface;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

public class ProxyFactory {
    private static final Logger logger = LogManager.getLogger(ProxyFactory.class);

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //返回代理对象
    public Object getProxyInstance(){
        // 使用 CGLIB 代理 UserServiceImpl
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Object invokeResult;
                beforeJdkInvoke();
                invokeResult = proxy.invokeSuper(obj, args);
                afterJdkInvoke();
                return invokeResult;
            }
        });
        return enhancer.create();
    }


    /**
     * cglib动态代理调用目标方法之前执行的方法
     */
    public void beforeJdkInvoke() {
        logger.info("cglib动态代理调用目标方法之前执行的增强操作...");
    }

    /**
     * cglib动态代理调用目标方法之后执行的方法
     */
    public void afterJdkInvoke() {
        logger.info("cglib动态代理调用目标方法之后执行的增强操作...");
    }
}
