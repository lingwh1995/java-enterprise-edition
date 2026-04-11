package org.bluebridge.beaninstantiation.factorymethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 枪
 */
public class Gun extends Weapon {

    private static final Logger logger = LogManager.getLogger(Gun.class);

    @Override
    public void attack() {
        logger.info("枪射击子弹...");
    }
}
