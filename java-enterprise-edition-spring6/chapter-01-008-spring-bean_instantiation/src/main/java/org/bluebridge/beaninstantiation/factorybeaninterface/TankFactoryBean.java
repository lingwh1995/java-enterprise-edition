package org.bluebridge.beaninstantiation.factorybeaninterface;

import org.springframework.beans.factory.FactoryBean;

/**
 * 坦克工厂
 */
public class TankFactoryBean implements FactoryBean<Weapon> {

    @Override
    public Weapon getObject() throws Exception {
        //注意这里这个对象是我们通过new手动创建的，如果使用简单工厂模式实例化bean，这个对象不能通过Spring的IOC创建
        return new Tank();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
