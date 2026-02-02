package org.bluebridge.common.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author lingwh
 * @desc
 * @date 2026/2/1 15:55
 */
public class CacheKeyUtils {

    /**
     * 支持可变参数的 Key 生成方法
     * @param params 待转换的对象数组
     * @return 最终的缓存 Key 字符串
     */
    public static String generateKey(Object... params) {
        if (params == null || params.length == 0) {
            return ":" + CryptoUtils.Md5.encrypt("empty");
        }

        String combinedJson = Arrays.stream(params)
                .map(param -> param != null ? JsonUtils.toJsonString(param) : "null")
                .collect(Collectors.joining("|"));

        return ":" + CryptoUtils.Md5.encrypt(combinedJson);
    }

}
