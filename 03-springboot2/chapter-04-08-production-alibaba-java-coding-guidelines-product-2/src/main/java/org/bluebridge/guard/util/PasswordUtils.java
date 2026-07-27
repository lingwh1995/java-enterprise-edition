package org.bluebridge.guard.util;
import cn.hutool.core.util.HexUtil;
import org.bluebridge.guard.constant.Pbkdf2Constants;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * 用户密码加密工具类
 *
 * @author lingwh
 * @date 2025/11/22 17:16
 */
public class PasswordUtils {

    /**
     * 对用户输入的密码进行验证
     *
     * @param passwordFromUserLoginDTO 用户输入的密码
     * @param salt             盐值
     * @param passwordFromExistingUser   数据库中存储的密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean verify(String passwordFromUserLoginDTO, String salt, String passwordFromExistingUser) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用相同的盐值对用户输入的密码进行加密  
        String passwordFromUserLoginDTOEncry = getPBKDF2(passwordFromUserLoginDTO, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败  
        return passwordFromUserLoginDTOEncry.equals(passwordFromExistingUser);
    }

    /**
     * 使用 salt 加密 password
     *
     * @param password 密码
     * @param salt 盐值
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String getPBKDF2(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 将 16 进制字符串形式的 salt 转换成 byte 数组
        byte[] saltHexBytes = HexUtil.decodeHex(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltHexBytes,
                Pbkdf2Constants.ITERATION_COUNT, Pbkdf2Constants.KEY_SIZE_BITS);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(Pbkdf2Constants.ALGORITHM);
        byte[] hashBytes = secretKeyFactory.generateSecret(spec).getEncoded();
        // 将 byte 数组转换为 16 进制的字符串
        return HexUtil.encodeHexStr(hashBytes).toUpperCase();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = "899DF0EADE99EA70";
        String password = getPBKDF2("bbt@2025", salt);
        System.out.println(password);
    }
}