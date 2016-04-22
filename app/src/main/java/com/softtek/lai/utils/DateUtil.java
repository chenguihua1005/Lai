package com.softtek.lai.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jerry.guan on 4/21/2016.
 * 日期工具类
 */
public class DateUtil {

    public static final String yyyy_MM_dd_HH_mm_ss="yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd="yyyy-MM-dd";

    private static String PATTERN="yyyy-MM-dd HH:mm:ss";

    private static DateUtil util;
    private Calendar calendar;

    private DateUtil() {
        calendar=Calendar.getInstance();
    }

    /**
     * 传入的解析格式 解析日期
     * @param pattern 指定的日期格式
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
        if(dateValue==null||"".equals(dateValue)){
            return year;
        }
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
        if(dateValue==null||"".equals(dateValue)){
            return month;
        }
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
        if(dateValue==null||"".equals(dateValue)){
            return day;
        }
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
        if(dateValue==null||"".equals(dateValue)){
            return hour;
        }
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
        if(dateValue==null||"".equals(dateValue)){
            return minute;
        }
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
        if(dateValue==null||"".equals(dateValue)){
            return second;
        }
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

    /**
     * 根据日期格式字符串获取该日期对应的long型数值
     */
    public long getValueOfDate(String dateValue){
        long value=0;
        if(dateValue==null||"".equals(dateValue)){
            return value;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            value=date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }
    /**
     * 转换日期格式字符串为日期类型
     */
    public Date convert2Date(String date){
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 比较两个日期的大小
     * @param date1
     * @param date2
     * @return -1表示date1<date2<br/> 0表示date1=date2<br/> 1表示date1>date2<br/>
     */
    public int compare(String date1,String date2){
        if(date1==null||date2==null||"".equals(date1)||"".equals(date2)){
            throw new RuntimeException("日期字符串不合法");
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=sdf.parse(date1);
            Date d2=convert2Date(date2);
            return d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -2;
    }

    /**
     * date1>date2
     * @param date1
     * @param date2
     * @return
     */
    public boolean isGt(String date1,String date2){
        if(date1==null||date2==null||"".equals(date1)||"".equals(date2)){
            throw new RuntimeException("日期字符串不合法");
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=sdf.parse(date1);
            Date d2=convert2Date(date2);
            return d1.compareTo(d2)==1?true:false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * date1<date2
     * @param date1
     * @param date2
     * @return
     */
    public boolean isLt(String date1,String date2){
        if(date1==null||date2==null||"".equals(date1)||"".equals(date2)){
            throw new RuntimeException("日期字符串不合法");
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=sdf.parse(date1);
            Date d2=convert2Date(date2);
            return d1.compareTo(d2)==-1?true:false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * date1=date2
     * @param date1
     * @param date2
     * @return
     */
    public boolean isEq(String date1,String date2){
        if(date1==null||date2==null||"".equals(date1)||"".equals(date2)){
            throw new RuntimeException("日期字符串不合法");
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=sdf.parse(date1);
            Date d2=convert2Date(date2);
            return d1.compareTo(d2)==0?true:false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
