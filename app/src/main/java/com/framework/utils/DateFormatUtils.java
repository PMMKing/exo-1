package com.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shucheng.qu on 2017/8/29.
 */

public class DateFormatUtils {

    public static String format(String oldDate, String format) {
        return format(oldDate, "yyyy-MM-dd", format);
    }

    public static String format(String oldDate, String oldFormat, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat);
        try {
            Date date = simpleDateFormat.parse(oldDate);
            return new SimpleDateFormat(format).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String format(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static Long formatLong(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        try {
            Date parse = simpleDateFormat.parse(time);
            return parse.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        }
    }

}
