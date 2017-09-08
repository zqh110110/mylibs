package com.smcb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/22.
 */

public class DateFormatUtils {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * HH:mm:ss
     */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss：SSS
     */
    public static final String FORMAT_DEFAULT_Time = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String date2Str(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String date2Str(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DEFAULT);
        return df.format(date);
    }
}
