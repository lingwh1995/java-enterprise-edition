package org.bluebridge.annotation.aspectorder.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service("catService")
public class CatServiceImpl implements ICatService{

    private static final Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    @Override
    public void deleteCatById(String id) {
        logger.info("正在执行根据id删除操作...");
    }
}
