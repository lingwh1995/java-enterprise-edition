package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类测试
 */
@Slf4j
public class CollectionUtilsTest {

    /**
     * 测试集合是否为空
     */
    @Test
    public void tetsIsEmpty() {
        List<Integer> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            log.info("list is empty");
        }
    }

}
