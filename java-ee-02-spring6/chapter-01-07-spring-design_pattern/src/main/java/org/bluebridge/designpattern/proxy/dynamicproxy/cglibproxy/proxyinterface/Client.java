package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyinterface;

/**
 * java8以上环境运行时需要添加VM参数，否则会报错   --add-opens java.base/java.lang=ALL-UNNAMED
 */
public class Client {
    /**/
    public static void main(String[] args) {
        // 1.创建被代理对象
        UserServiceImpl userService = new UserServiceImpl();
        // 2.创建代理对象
        ProxyFactory proxyFactory = new ProxyFactory(userService);
        IUserService proxyInstance = (IUserService)proxyFactory.getProxyInstance();
        // 3.调用代理对象方法
        // 无返回值
        proxyInstance.showUsers();
        // 有返回值
        User user = proxyInstance.getById("001");
        System.out.println("返回值user = " + user);
    }

}
