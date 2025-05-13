package org.bluebridge.profile.advisor.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeacherDao {

    private static final Logger logger = LogManager.getLogger(TeacherDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除...");
    }
}
