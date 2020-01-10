package com.example.modules.utils;

import java.util.UUID;

/**
 * UUID工具类
 * @author CaoRui
 * @date 2019年7月31日
 */
public class UUIDUtil {

    /**
     * 获取UUID，含“-”
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
    /**
     * 获取UUID，不含“-”
     * @return
     */
    public static String getUUIDWithoutRod() {
        return getUUID().replaceAll("-", "");
    }
}
