package org.bluebridge.designpattern.proxy.dynamicproxy.jdkproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatServiceImpl implements ICatService {

    private static final Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    @Override
    public void addCat(Cat cat) {
        logger.info("正在执行新增用户操作...");
    }

    @Override
    public void deleteCatById(String id) {
        logger.info("正在执行删除用户操作...");
    }

    @Override
    public void updateCat(Cat cat) {
        logger.info("正在执行修改用户操作...");
    }

    @Override
    public Cat getCatById(String id) {
        logger.info("正在执行查询用户操作...");
        //模拟从数据中根据id查询到了一个用户
        Cat cat = new Cat("001","煤球",2);
        return cat;
    }
}
