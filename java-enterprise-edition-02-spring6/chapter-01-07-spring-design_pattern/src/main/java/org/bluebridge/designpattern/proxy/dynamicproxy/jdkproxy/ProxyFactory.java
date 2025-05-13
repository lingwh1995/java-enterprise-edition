package org.bluebridge.designpattern.proxy.dynamicproxy.jdkproxy;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private static final Logger logger = LogManager.getLogger(ProxyFactory.class);

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * 使用JDK动态代理获取代理对象
     * newProxyInstance     新建代理对象
     *  这个方法执行了两个操作：
     *      1.在内存中创建了一个接口的实现类的class文件
     *      2.为该class文件在内存中创建了一个对象
     * 该方法的三个参数
     *      ClassLoader loader          目标类的类加载器/公共接口的实现类的类加载器
     *      Class<?>[] interfaces       目标类的接口的class/公共接口的class
     *      InvocationHandler h         调用处理器
     *          InvocationHandler.invoke()  这个方法不是程序员调用的，是JDK自身负责调用的
     *          当代理对象调用代理方法时，注册在InvocationHandler中的invoke()中的方法会被JDK调用
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        beforeJdkInvoke();
                        Object methodInvokeResult = method.invoke(target, args);
                        afterJdkInvoke();
                        return methodInvokeResult;
                    }
                });
    }

    /**
     * JDK动态代理调用目标方法之前执行的方法
     */
    public void beforeJdkInvoke() {
        logger.info("JDK动态代理调用目标方法之前执行的增强操作...");
    }

    /**
     * JDK动态代理调用目标方法之后执行的方法
     */
    public void afterJdkInvoke() {
        logger.info("JDK动态代理调用目标方法之后执行的增强操作...");
    }
}
