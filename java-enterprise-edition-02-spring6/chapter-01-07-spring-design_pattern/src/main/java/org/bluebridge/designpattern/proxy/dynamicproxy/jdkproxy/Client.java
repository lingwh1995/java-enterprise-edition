package org.bluebridge.designpattern.proxy.dynamicproxy.jdkproxy;


import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        //1.创建目标对象
        ICatService target = new CatServiceImpl();
        //2.创建代理对象
        ProxyFactory proxyFactory = new ProxyFactory(target);
        ICatService CatServiceImplProxy = (ICatService)proxyFactory.getProxyInstance();
        //3.调用代理对象的方法
            //调用不带有返回值的方法
        CatServiceImplProxy.addCat(new Cat("001","煤球",1));
            //调用带有返回值的方法
        Cat cat = CatServiceImplProxy.getCatById("001");
        logger.info(cat.toString());
    }
}
