package com.smcb.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/5.
 */
public class AppUtils {

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
