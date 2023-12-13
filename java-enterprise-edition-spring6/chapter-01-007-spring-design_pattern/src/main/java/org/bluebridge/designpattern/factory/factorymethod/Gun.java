package org.bluebridge.designpattern.factory.factorymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 枪
 */
public class Gun extends Weapon{

    private static final Logger logger = LoggerFactory.getLogger(Gun.class);

    @Override
    public void attack() {
        logger.info("枪射击子弹...");
    }
}
