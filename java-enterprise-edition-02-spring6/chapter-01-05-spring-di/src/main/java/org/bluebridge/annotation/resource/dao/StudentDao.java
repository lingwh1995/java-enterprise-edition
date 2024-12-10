package org.bluebridge.annotation.resource.dao;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除操作...[使用@Resource完成注入]");
    }
}
