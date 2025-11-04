package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * Object工具类测试
 */
@Slf4j
public class ObjectUtilsTest {

    /**
     * 测试isEmpty
     */
    @Test
    public void testEmpty() {
        String a = "123";
        Integer b = new Integer(1);
        List<String> c = new ArrayList<>();
        Integer[] d = new Integer[]{b};
        c.add(a);
        Map<String, String> e = new HashMap<>();
        e.put(a, a);
        Optional<String> f = Optional.of(a);
        if (!ObjectUtils.isEmpty(a)) {
            log.info("a不为空");
        }
        if (!ObjectUtils.isEmpty(b)) {
            log.info("b不为空");
        }
        if (!ObjectUtils.isEmpty(c)) {
            log.info("c不为空");
        }
        if (!ObjectUtils.isEmpty(d)) {
            log.info("d不为空");
        }
        if (!ObjectUtils.isEmpty(e)) {
            log.info("e不为空");
        }
        if (!ObjectUtils.isEmpty(f)) {
            log.info("f不为空");
        }
    }

    /**
     * 测试equals
     */
    @Test
    public void testEquals() {
        String a = "123";
        String b = null;
        System.out.println(ObjectUtils.nullSafeEquals(a, b));
    }

    /**
     * 测试数组equals   不会出现空指针异常
     */
    @Test
    public void testArrayEquals() {
        String[] a = new String[]{"123"};
        String[] b = new String[]{"123"};
        System.out.println(ObjectUtils.nullSafeEquals(a, b));
    }

    /**
     * 测试获取对象十六进制hashcode
     */
    @Test
    public void testIdentityHex() {
        String a = "123";
        System.out.println(ObjectUtils.getIdentityHexString(a));
    }

}
