package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String dtShort = "yyyyMMdd";
    public static final String dtLong = "yyyyMMddHHmmss";
    public static final String hour = "HHmmss";

    public static void main(String[] args) {
        long senddate=20180328093142L;
        String s=getDay(senddate);
        System.out.println(s);

    }

    public static int getDay() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtShort);
        String strNow = df.format(date);
        return Integer.valueOf(strNow);
    }

    public static String getDayBefore() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String day = new SimpleDateFormat("yyyyMMdd").format(date);
        return day;
    }

    public static long getTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtLong);
        String strNow = df.format(date);
        return Long.valueOf(strNow);
    }

    public static String getHHmmss() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(hour);
        return df.format(date);
    }

    public static long getLongTime(Date date) {
        DateFormat df = new SimpleDateFormat(dtLong);
        String strNow = df.format(date);
        return Long.valueOf(strNow);
    }

    public static long getDayStart() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtShort);
        String strNow = df.format(date) + "000000";
        return Long.valueOf(strNow);
    }

    public static String getDay(long senddate) {
        String time =String.valueOf(senddate);
        String day = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8)
            +" "+time.substring(8, 10)+":"+time.substring(10, 12)+":"+time.substring(12, 14)    ;
        return day;
    }
}
