package org.bluebridge.common.util;

import cn.hutool.crypto.SecureUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author lingwh
 * @desc 加密工具类枚举
 * @date 2026/1/10 16:58
 */
public class CryptoUtils {

    /**
     * MD5 相关操作
     */
    public static class Md5 {
        public static String encrypt(String data) {
            return SecureUtil.md5(data);
        }
    }

    /**
     * AES 相关操作
     */
    public static class Aes {
        public static String encrypt(String data, String key) {
            return SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8)).encryptHex(data);
        }

        public static String decrypt(String hex, String key) {
            return SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8)).decryptStr(hex);
        }
    }

}
