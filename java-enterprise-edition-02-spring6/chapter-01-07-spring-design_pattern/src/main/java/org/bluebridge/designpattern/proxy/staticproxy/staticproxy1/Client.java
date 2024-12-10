package org.bluebridge.designpattern.proxy.staticproxy.staticproxy1;

public class Client {
    public static void main(String[] args) {
        //1.创建目标对象
        OrderServiceImpl target = new OrderServiceImpl();
        //2.创建代理对象
        OrderServiceImplProxy orderServiceImplProxy = new OrderServiceImplProxy(target);
        //3.调用代理对象的代理方法
        orderServiceImplProxy.addOrder();
        orderServiceImplProxy.deleteOrderById();
        orderServiceImplProxy.updateOrder();
        orderServiceImplProxy.getOrderById();
    }
}
