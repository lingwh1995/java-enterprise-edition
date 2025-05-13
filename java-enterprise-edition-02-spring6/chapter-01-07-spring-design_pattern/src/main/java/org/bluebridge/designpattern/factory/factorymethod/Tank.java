package org.bluebridge.designpattern.factory.factorymethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 坦克类
 */
public class Tank extends Weapon{

    private static final Logger logger = LogManager.getLogger(Tank.class);

    @Override
    public void attack() {
        logger.info("坦克发动了攻击...");
    }

}
