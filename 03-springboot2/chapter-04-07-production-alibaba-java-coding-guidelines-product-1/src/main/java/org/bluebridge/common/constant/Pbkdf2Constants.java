package org.bluebridge.common.constant;

public final class Pbkdf2Constants {

    /**
     * 是否开启 PBKDF2 加密
     */
    public static boolean ENABLED;

    /**
     * PBKDF2 加密算法
     */
    public static String ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * PBKDF2 密钥长度（位）
     */
    public static int KEY_SIZE_BITS = 256;

    /**
     * PBKDF2 迭代次数
     *  最低推荐值：10,000 次迭代（适用于一般应用）
     *  增强安全性：100,000 次迭代（适用于对安全性要求较高的场景）
     *  极高安全性：500,000 次及以上（适用于金融、军事等高敏感领域）
     */
    public static int ITERATION_COUNT = 10000;

}