package org.bluebridge.designpattern.factory.factorymethod;

/**
 * 枪工厂
 */
public class GunFactory extends WeaponFactory{

    @Override
    public Weapon get() {
        return new Gun();
    }
}
