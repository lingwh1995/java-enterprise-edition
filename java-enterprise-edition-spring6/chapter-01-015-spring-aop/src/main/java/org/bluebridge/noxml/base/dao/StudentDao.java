package org.bluebridge.noxml.base.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    public void deleteStudentById(String id) {
        logger.info("正在执行根据id删除操作...");
    }
}
