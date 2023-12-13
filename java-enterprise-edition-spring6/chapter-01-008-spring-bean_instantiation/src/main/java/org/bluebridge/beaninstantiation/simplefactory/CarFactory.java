package org.bluebridge.beaninstantiation.simplefactory;

/**
 * Spring示例化bean的第二种方式： 使用简单(静态)工厂实例化bean
 */
public class CarFactory {

    /**
     * 简单工厂模式中的获取对象的方法是静态方法
     * @return
     */
    public static Car getCar() {
        //注意这里这个对象是我们通过new手动创建的，如果使用简单工厂模式实例化bean，这个对象不能通过Spring的IOC创建
        return new Car();
    }
}
