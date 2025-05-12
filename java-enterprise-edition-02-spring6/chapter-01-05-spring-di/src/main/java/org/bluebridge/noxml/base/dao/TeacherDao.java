package org.bluebridge.noxml.base.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao {

    private static final Logger logger = LogManager.getLogger(TeacherDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除操作...[Spring全注解开发]");
    }
}
