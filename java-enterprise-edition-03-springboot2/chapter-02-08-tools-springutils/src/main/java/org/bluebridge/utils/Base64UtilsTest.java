package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * Base64工具类
 */
@Slf4j
public class Base64UtilsTest {

    /**
     * 测试Base64编码解码
     */
    @Test
    public void testBase64EncodeAndDecode() throws UnsupportedEncodingException {
        String str = "abc";
        // 编码
        String encode = new String(Base64Utils.encode(str.getBytes()));
        log.info("编码：" + encode);

        // 解码
        String decode = new String(Base64Utils.decode(encode.getBytes()), "utf8");
        log.info("解码：" + decode);
    }
}
