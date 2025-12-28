package org.bluebridge.common.util;

import cn.hutool.core.util.HexUtil;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author lingwh
 * @desc 用户密码加密工具类
 * @date 2025/11/22 17:16
 */
public class PasswordUtils {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    //生成密文的长度
    public static final int HASH_SIZE = 32;

    // 迭代次数
    public static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 对用户输入的密码进行验证
     *
     * @param passwordFromUser 用户输入的密码
     * @param salt             盐值
     * @param passwordFromDb   数据库中存储的密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean verify(String passwordFromUser, String salt, String passwordFromDb) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用相同的盐值对用户输入的密码进行加密  
        String passwordFromUserEncry = getPBKDF2(passwordFromUser, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败  
        return passwordFromUserEncry.equals(passwordFromDb);
    }

    /**
     * 使用salt加密password
     *
     * @param password 密码
     * @param salt     盐值
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String getPBKDF2(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //将16进制字符串形式的salt转换成byte数组
        byte[] saltHexBytes = HexUtil.decodeHex(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltHexBytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        byte[] hashBytes = secretKeyFactory.generateSecret(spec).getEncoded();
        //将byte数组转换为16进制的字符串
        return HexUtil.encodeHexStr(hashBytes).toUpperCase();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = "899DF0EADE99EA70";
        String password = getPBKDF2("bbt@2025", salt);
        System.out.println(password);
    }

}