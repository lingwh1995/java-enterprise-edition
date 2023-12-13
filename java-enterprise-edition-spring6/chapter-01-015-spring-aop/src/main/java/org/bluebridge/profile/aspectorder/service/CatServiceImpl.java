package org.bluebridge.profile.aspectorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatServiceImpl implements ICatService {

    private static final Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    @Override
    public void deleteCatById(String id) {
        logger.info("正在执行根据id删除操作...");
    }
}
