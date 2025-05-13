package org.bluebridge.profile.aspectorder.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CatServiceImpl implements ICatService {

    private static final Logger logger = LogManager.getLogger(CatServiceImpl.class);

    @Override
    public void deleteCatById(String id) {
        logger.info("正在执行根据id删除操作...");
    }
}
