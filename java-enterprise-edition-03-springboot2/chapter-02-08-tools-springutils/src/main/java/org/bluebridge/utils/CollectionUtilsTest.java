package org.bluebridge.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类测试
 */
public class CollectionUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(CollectionUtilsTest.class);

    /**
     * 测试集合是否为空
     */
    @Test
    public void tetsIsEmpty() {
        List<Integer> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            log.debug("list is empty");
        }
    }

}
