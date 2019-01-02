package com.jinxun.hunting_goods.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2018/12/20.
 */

public class CalendarUtil {

    /**
     * 获取一小时后的时间戳
     */
    public static Long getAfterOneHour(Calendar calendar, int hours) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTimeInMillis() / 1000;
    }

    public static Long getAfterTime(Calendar calendar, Date date, int day, int hour) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

}
