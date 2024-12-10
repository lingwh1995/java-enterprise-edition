package org.bluebridge.profile.autowire.byname.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据属性名称autowire   需要为属性创建setter方法
 */
public class OrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除操作...[使用XML配置完成DI-Autowire By Name]");
    }
}
