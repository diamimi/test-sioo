package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: HeQi
 * @Date:Create in 11:27 2018/9/10
 */
public class DayUtil {

    public static void main(String[] args) {
        List<String> dayList2 = getDayList(20161205, 20180428);
        dayList2.stream().forEach(d -> System.out.println(d));
    }

    public static List<String> getDayListOfMonth(int year, int startMonth, int endMonth) {
        List<String> list = new ArrayList();
        String monthStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        int now_month = now.get(Calendar.MONTH) + 1;
        int now_day = now.get(Calendar.DAY_OF_MONTH);
        for (int m = startMonth; m <= endMonth; m++) {
            if (m < 10) {
                monthStr = "0" + m;
            } else {
                monthStr = String.valueOf(m);
            }
            int day = 0;
            try {
                if (m < now_month) {
                    day = getDaysOfMonth(sdf.parse(year + "-" + monthStr + "-01"));
                } else if (m == now_month) {
                    day = now_day;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= day; i++) {
                String dayStr = "";
                if (i < 10) {
                    dayStr = "0" + i;
                } else {
                    dayStr = String.valueOf(i);
                }
                String aDate = String.valueOf(year) + monthStr + dayStr;
                list.add(aDate);
            }
        }
        return list;
    }

    public static List<String> getDayList(int startdate, int enddate) {
        if (startdate > enddate) {
            return null;
        }
        String start = String.valueOf(startdate);
        String end = String.valueOf(enddate);
        int startYear = Integer.valueOf(start.substring(0, 4));
        String startDay = start.substring(4, 8);
        int endYear = Integer.valueOf(end.substring(0, 4));
        String endDay = end.substring(4, 8);
        List<String> list = new ArrayList();
        if (startYear < endYear) {
            for (int i = startYear; i <= endYear; i++) {
                if (i == startYear) {
                    List<String> dayList = getDayList2(i + "", startDay, "1231");
                    list.addAll(dayList);
                } else if (i == endYear) {
                    List<String> dayList = getDayList2(i + "", "0101", endDay);
                    list.addAll(dayList);
                } else {
                    List<String> dayList = getDayList2(i + "", "0101", "1231");
                    list.addAll(dayList);
                }
            }
        }else if(startYear==endYear){
            list=getDayList2(startYear+"", startDay, endDay);
        }
        return list;
    }

    public static List<String> getDayList(int year, int startMonth, int endMonth) {
        List<String> list = new ArrayList();
        String monthStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        for (int m = startMonth; m <= endMonth; m++) {
            if (m < 10) {
                monthStr = "0" + m;
            } else {
                monthStr = String.valueOf(m);
            }
            int day = 0;
            try {
                day = getDaysOfMonth(sdf.parse(year + "-" + monthStr + "-01"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= day; i++) {
                String dayStr = "";
                if (i < 10) {
                    dayStr = "0" + i;
                } else {
                    dayStr = String.valueOf(i);
                }
                String aDate = String.valueOf(year) + monthStr + dayStr;
                list.add(aDate);
            }
        }
        return list;
    }


    public static List<String> getDayList2(String year, String startDate, String endDate) {
        List<String> list = new ArrayList();
        String monthStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int startMonth = Integer.valueOf(startDate.substring(0, 2));
        int startDay = Integer.valueOf(startDate.substring(2, 4));
        int endMonth = Integer.valueOf(endDate.substring(0, 2));
        int endDay = Integer.valueOf(endDate.substring(2, 4));
        for (int m = startMonth; m <= endMonth; m++) {
            if (m < 10) {
                monthStr = "0" + m;
            } else {
                monthStr = String.valueOf(m);
            }
            int day = 0;
            try {
                day = getDaysOfMonth(sdf.parse(year + "-" + monthStr + "-01"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int beginday = 1;
            if (m == startMonth) {
                beginday = startDay;
            }
            int finishday = day;
            if (m == endMonth) {
                finishday = endDay;
            }
            for (int i = beginday; i <= finishday; i++) {
                String dayStr = "";
                if (i < 10) {
                    dayStr = "0" + i;
                } else {
                    dayStr = String.valueOf(i);
                }
                String aDate = String.valueOf(year) + monthStr + dayStr;
                list.add(aDate);
            }
        }
        return list;
    }


    private static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 20180101
     *
     * @param day
     * @return
     */
    public static String getDayAfter(String day) {
        String specifiedDay = day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6, 8);
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int d = c.get(Calendar.DATE);
        c.set(Calendar.DATE, d + 1);

        String dayAfter = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayAfter;
    }


    public static String getDaySec(String time,int sec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = null;
        try {
            parse = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = new GregorianCalendar();
        c.setTime(parse);
        c.add(Calendar.SECOND,sec);
        Date time1 = c.getTime();
        String str = sdf1.format(time1);
        return str;

    }
}
