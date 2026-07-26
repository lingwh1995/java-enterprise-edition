package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * Base64 工具类
 *
 * @author lingwh
 * @date 2025/7/1 9:00
 */
@Slf4j
public class Base64UtilsTest {

    /**
     * 测试 Base64 编码解码
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
