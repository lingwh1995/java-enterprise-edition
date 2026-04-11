package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化工具类测试
 */
@Slf4j
public class SerializationUtilsTest {

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        byte[] serialize = SerializationUtils.serialize(map);
        Object deserialize = SerializationUtils.deserialize(serialize);
        log.info("{}", deserialize);
    }

}
