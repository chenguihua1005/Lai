package com.softtek.lai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jerry.guan on 4/21/2016.
 * 日期工具类
 */
public class DateUtil {

    public static final String yyyy_MM_dd="yyyy-MM-dd";

    private static String PATTERN="yyyy-MM-dd HH:mm:ss";

    private static DateUtil util;
    private Calendar calendar;

    private DateUtil() {
        calendar=Calendar.getInstance();
    }

    /**
     * 更具传入的解析格式 解析日期
     * @param pattern
     * @return
     */
    public static DateUtil getInstance(String pattern){
        PATTERN=pattern;
        if(util==null){
            util=new DateUtil();
        }
        return util;
    }
    public static DateUtil getInstance(){
        if(util==null){
            util=new DateUtil();
        }
        return util;
    }

    /**
     * 根据日期格式字符串获取年
     */
    public int getYear(String dateValue){
        int year=0;
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            year=calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }

    /**
     * 根据日期格式字符串获取月
     */
    public int getMonth(String dateValue){
        int month=0;
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            month=calendar.get(Calendar.MONTH)+1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }

    /**
     * 根据日期格式字符串获取日
     */
    public int getDay(String dateValue){
        int day=0;
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            day=calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 根据日期格式字符串获取小时
     */
    public int getHour(String dateValue){
        int hour=0;
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            hour=calendar.get(Calendar.HOUR_OF_DAY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour;
    }

    /**
     * 根据日期格式字符串获取分钟
     */
    public int getMinute(String dateValue){
        int minute=0;
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            minute=calendar.get(Calendar.MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return minute;
    }

    /**
     * 根据日期格式字符串获取秒数
     */
    public int getSecond(String dateValue){
        int second=0;
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            second=calendar.get(Calendar.SECOND);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return second;
    }
}
