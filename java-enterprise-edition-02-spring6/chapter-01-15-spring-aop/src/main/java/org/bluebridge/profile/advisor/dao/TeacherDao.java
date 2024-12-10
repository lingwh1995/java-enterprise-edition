package org.bluebridge.profile.advisor.dao;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class TeacherDao {

    private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除...");
    }
}
