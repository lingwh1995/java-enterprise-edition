package org.bluebridge.designpattern.factory.simplefactory;

/**
 * 客户端
 */
public class Client {

    public static void main(String[] args) {
        Weapon tank = WeaponFactory.getWeapon("Tank");
        tank.attack();
        Weapon figher = WeaponFactory.getWeapon("Gun");
        figher.attack();
        Weapon dagger = WeaponFactory.getWeapon("Dagger");
        dagger.attack();
    }
}
