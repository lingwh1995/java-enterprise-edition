package org.bluebridge.reflect.object;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cat {

    private static final Logger logger = LogManager.getLogger(Cat.class);

    private String id;
    private String name;
    private Integer age;

    public void drink() {
        logger.info("猫喝水...");
    }

    public String showCatInfos(String id, String name) {
        return "猫的编号是：" + id + "，猫的姓名是：" + name;
    }

    public String showCatInfos(String id, String name, Integer age) {
        return "猫的编号是" + id + "，猫的姓名是：" + name + "，猫的年龄是：" + age;
    }
}
