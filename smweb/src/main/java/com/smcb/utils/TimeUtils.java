package com.smcb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/5.
 */

public class TimeUtils {
    public static String getDValue(String stime, long stmVal) {
        StringBuffer buffer = new StringBuffer();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            Date date = df.parse(stime);
            long l = now.getTime() - date.getTime();
            if (0 != stmVal) {
                l -= stmVal;
            }
            if (l < 0) {
                return "00:00:00";
            }
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day > 0) {
                if (String.valueOf(day).length() == 1) {
                    buffer.append("0");
                }
                buffer.append(day);
                buffer.append(":");
            }
            if (hour > 0) {
                if (String.valueOf(hour).length() == 1) {
                    buffer.append("0");
                }
                buffer.append(hour);
            } else {
                buffer.append("00");
            }
            buffer.append(":");
            if (min > 0) {
                if (String.valueOf(min).length() == 1) {
                    buffer.append("0");
                }
                buffer.append(min);
            } else {
                buffer.append("00");
            }
            buffer.append(":");
            if (s > 0) {
                if (String.valueOf(s).length() == 1) {
                    buffer.append("0");
                }
                buffer.append(s);
            } else {
                buffer.append("00");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
