package org.bluebridge.designpattern.factory.simplefactory;

/**
 * 抽象武器工厂
 */
public class WeaponFactory {

    /**
     * 获取武器的静态方法
     * @param weaponType
     * @return
     */
    public static Weapon getWeapon(String weaponType) {
        Weapon weapon = null;
        if("Tank".equals(weaponType)) {
            weapon = new Tank();
        }else if("Gun".equals(weaponType)) {
            weapon = new Gun();
        }else if("Dagger".equals(weaponType)){
            weapon = new Dagger();
        }else {
            throw  new RuntimeException();
        }
        return weapon;
    }
}
