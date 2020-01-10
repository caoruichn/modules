package com.example.modules.utils;

import java.security.MessageDigest;

/**
 * MD5工具类
 * @author CaoRui
 * @date 2019年8月3日
 */

public class MD5Util {
    public static final String codes = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ*";
    public static final int code_len = 63;

    public static String createPassword(String inputstr) {
        return getMD5(inputstr);
    }

    /**
     * 验证密码是否正确
     * @param pass
     * @param inputstr
     * @return
     */
    public static boolean authenticatePassword(String pass, String inputstr) {
        if (pass.equals((getMD5(inputstr)))) {
            return true;
        } else {
            return false;
        }
    }

    public static final String getMD5(String s) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(str);
        } catch (Exception e) {
        }
        return null;
    }
}
