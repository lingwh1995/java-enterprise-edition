package org.bluebridge.designpattern.factory.factorymethod;

/**
 * 客户端
 *     单纯的工厂方法模式的缺点，下面测试代码中任然出现了new这个关键字，可以使用简单工厂模式对下面代码再次进行优化
 */
public class Client {

    public static void main(String[] args) {
        //创建一个GunFactory
        WeaponFactory gunFactory = new GunFactory();
        Weapon gun = gunFactory.get();
        gun.attack();

        //创建一个TankFactory
        WeaponFactory tankFactory = new TankFactory();
        Weapon tank = tankFactory.get();
        tank.attack();
    }

}
