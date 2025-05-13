package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass.proxyclass_2;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

public class ProxyFactory{

    private static final Logger logger = LogManager.getLogger(ProxyFactory.class);

    /**
     * 维护一个目标对象
     */
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //返回代理对象
    public Object getProxyInstance(){
        // 1.创建一个工具类
        Enhancer enhancer = new Enhancer();
        // 2.设置父类
        enhancer.setSuperclass(target.getClass());
        // 3.设置回调函数
        enhancer.setCallback(
            new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    Object invoke= null;
                    beforeJdkInvoke();
                    invoke = method.invoke(target,args);
                    afterJdkInvoke();
                    return invoke;
                }
            }
        );
        //4创建子类对象，即代理对象
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
