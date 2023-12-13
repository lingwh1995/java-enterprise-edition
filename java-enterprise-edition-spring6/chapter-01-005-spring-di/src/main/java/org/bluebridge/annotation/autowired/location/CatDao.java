package org.bluebridge.annotation.autowired.location;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CatDao {

    private static final Logger logger = LoggerFactory.getLogger(CatDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除操作...[使用注解完成DI-测试autowired可以出现的位置]");
    }
}
