package org.bluebridge.designpattern.factory.simplefactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 匕首类
 */
public class Dagger extends Weapon{

    private static final Logger logger = LogManager.getLogger(Dagger.class);

    @Override
    public void attack() {
        logger.info("投掷匕首...");
    }
}
