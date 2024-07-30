package org.bluebridge;

import com.sun.xml.internal.messaging.saaj.util.Base64;

import java.nio.charset.Charset;
import java.sql.SQLOutput;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String hex = "680001004C0285300262EBC84223DCC7A2196E81788C7A0CC0A49FD5E34F7AADBB045015765F5A1E997C9CA329FF222466742E9B0125AA55F19A2D3B49A84031CDCAC9D9CB4F65E7C0355416";
        //String bin = hex2bin(hex);
        //System.out.println(bin);

//        String binaryString = Integer.toBinaryString(Integer.parseInt(hex, 16));
//        System.out.println(binaryString);
//        String format = "%" + hex.length() * 2 + "s";
//        System.out.println(String.format(format, binaryString).replace(' ','0'));
        //hexToBin(hex);
        System.out.println("68".getBytes().length);
    }

    public static String hexToBin(String hex) {

        for(int i=0; i<hex.length(); i++) {
            char c = hex.charAt(i);
            System.out.println(c);
            Integer.parseInt(c+"",16);
            //Integer.toBinaryString()
        }
        return null;
    }
    public static String hex2bin(String input) {
        StringBuilder sb = new StringBuilder();
        int len = input.length();
        System.out.println("原数据长度：" + (len / 2) + "字节");

        for (int i = 0; i < len; i++){
            //每1个十六进制位转换为4个二进制位
            String temp = input.substring(i, i + 1);
            int tempInt = Integer.parseInt(temp, 16);
            String tempBin = Integer.toBinaryString(tempInt);
            //如果二进制数不足4位，补0
            if (tempBin.length() < 4){
                int num = 4 - tempBin.length();
                for (int j = 0; j < num; j++){
                    sb.append("0");
                }
            }
            sb.append(tempBin);
        }

        return sb.toString();
    }
}