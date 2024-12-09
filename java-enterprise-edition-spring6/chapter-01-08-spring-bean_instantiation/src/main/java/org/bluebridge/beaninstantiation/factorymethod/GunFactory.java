package org.bluebridge.beaninstantiation.factorymethod;

/**
 * 枪工厂
 */
public class GunFactory extends WeaponFactory {

    /**
     * 工厂方法模式中的获取对象的方法是实例方法
     * @return
     */
    @Override
    public Weapon get() {
        //注意这里这个对象是我们通过new手动创建的，如果使用简单工厂模式实例化bean，这个对象不能通过Spring的IOC创建
        return new Gun();
    }
}
