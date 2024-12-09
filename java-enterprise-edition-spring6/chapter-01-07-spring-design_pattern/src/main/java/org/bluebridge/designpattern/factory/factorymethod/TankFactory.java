package org.bluebridge.designpattern.factory.factorymethod;

/**
 * 坦克工厂
 */
public class TankFactory extends WeaponFactory{

    @Override
    public Weapon get() {
        return new Tank();
    }
}
