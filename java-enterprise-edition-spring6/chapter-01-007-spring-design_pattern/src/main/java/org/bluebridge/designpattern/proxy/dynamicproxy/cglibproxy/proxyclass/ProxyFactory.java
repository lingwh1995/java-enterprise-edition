package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory {

	private static final Logger logger = LoggerFactory.getLogger(ProxyFactory.class);

	private Object target;

	public ProxyFactory(Object target) {
		this.target = target;
	}
	
    //返回代理对象
    public Object getProxyInstance(){
        //1.创建一个工具类
        Enhancer enhancer = new Enhancer();
        //2.设置父类
        enhancer.setSuperclass(target.getClass());
        //3.设置回调函数
        enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				beforeJdkInvoke();
				Object methodInvokeResult = proxy.invokeSuper(target,args);
				afterJdkInvoke();
		        return methodInvokeResult;
			}
		});
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
