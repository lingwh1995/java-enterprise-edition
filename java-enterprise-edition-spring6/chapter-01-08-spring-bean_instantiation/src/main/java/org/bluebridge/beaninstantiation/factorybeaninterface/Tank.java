package org.bluebridge.beaninstantiation.factorybeaninterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 枪
 */
public class Tank extends Weapon {

    private static final Logger logger = LoggerFactory.getLogger(Tank.class);

    @Override
    public void attack() {
        logger.info("坦克发动攻击...");
    }
}
