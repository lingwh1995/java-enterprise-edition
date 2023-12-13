package org.bluebridge.designpattern.factory.simplefactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 匕首类
 */
public class Dagger extends Weapon{

    private static final Logger logger = LoggerFactory.getLogger(Dagger.class);

    @Override
    public void attack() {
        logger.info("投掷匕首...");
    }
}
