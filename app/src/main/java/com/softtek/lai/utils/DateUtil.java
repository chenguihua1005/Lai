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
    public static final String yyyy_MM_dd_HH_mm="yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_dd="yyyy-MM-dd";

    private static String PATTERN="yyyy-MM-dd HH:mm:ss";
    /**
     * 平年
     */
    public static int COMMON_YEAR=0;
    /**
     * 闰年
     */
    public static int LEAP_YEAR=1;

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
        if(util==null){
            util=new DateUtil();
        }
        PATTERN=pattern;
        return util;
    }
    public static DateUtil getInstance(){
        if(util==null){
            util=new DateUtil();
        }
        PATTERN=yyyy_MM_dd_HH_mm_ss;
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

    public int getCurrentYear(){
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }
    public int getCurrentMonth(){
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH)+1;
    }
    public int getCurrentDay(){
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 计算某一年是平年还是闰年
     *  返回0平年1闰年
     */
    public int commonOrLeapYear(int year){
        return (year%4==0&&year%100!=0)||year%400==0?LEAP_YEAR:COMMON_YEAR;
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

    public int getDay(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int[] getDates(String dateValue){
        int[] datas=new int[]{0,0,0,0,0,0,0};
        if(TextUtils.isEmpty(dateValue)){
            return datas;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date date=sdf.parse(dateValue);
            calendar.setTime(date);
            datas[0]=calendar.get(Calendar.YEAR);
            datas[1]=calendar.get(Calendar.MONTH)+1;
            datas[2]=calendar.get(Calendar.DAY_OF_MONTH);
            datas[3]=calendar.get(Calendar.HOUR_OF_DAY);
            datas[4]=calendar.get(Calendar.MINUTE);
            datas[5]=calendar.get(Calendar.SECOND);
            datas[6]=calendar.get(Calendar.AM_PM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datas;
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
     * 获取当前日期时间格式字符串
     */
    public String getCurrentDate(){
        return new SimpleDateFormat(PATTERN).format(new Date());
    }

    /**
     * 转换日期格式字符串到指定的格式
     * @param date 需要转换的日期格式字符串
     * @param pattern 指定转换后的格式
     * @return 返回结果
     */
    public String convertDateStr(String date,String pattern){
        try {
            Date d1=new SimpleDateFormat(PATTERN).parse(date);
            return new SimpleDateFormat(pattern).format(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 转换日期格式字符串为日期类型
     */
    public Date convert2Date(String date){
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
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
        if(TextUtils.isEmpty(date1)||TextUtils.isEmpty(date2)){
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
            return false;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=sdf.parse(date1);
            Date d2=convert2Date(date2);
            return d1.compareTo(d2)==1;
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
            return d1.compareTo(d2)==-1;
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
            return d1.compareTo(d2)==0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更具传入的日期跳转到指定天数的日期
     * @param date 指定日期 如果为null则默认当前日期
     * @param days 跳转几天 整数向后，负数向前
     * @return
     */
    public String jumpDateByDay(String date,int days){
        if(TextUtils.isEmpty(date)){
            return "";
        }
        if(days==0){
            return date;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=null;
            if(date==null){
                d1=new Date();
            }else{
                d1=sdf.parse(date);
            }
            calendar.setTime(d1);
            calendar.add(Calendar.DATE,days);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 更具传入的日期跳转到指定月数的日期
     * @param date 指定日期 如果为null则默认当前日期
     * @param months 跳转几天 整数向后，负数向前
     * @return
     */
    public String jumpDateByMonth(String date,int months){
        if("".equals(date)){
            return "";
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=null;
            if(date==null){
                d1=new Date();
            }else{
                d1=sdf.parse(date);
            }
            calendar.setTime(d1);
            calendar.add(Calendar.MONTH,months);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 更具传入的日期跳转到指定月数的日期
     * @param date 指定日期 如果为null则默认当前日期
     * @param years 跳转几天 整数向后，负数向前
     * @return
     */
    public String jumpDateByYear(String date,int years){
        if("".equals(date)){
            return "";
        }
        SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
        try {
            Date d1=null;
            if(date==null){
                d1=new Date();
            }else{
                d1=sdf.parse(date);
            }
            calendar.setTime(d1);
            calendar.add(Calendar.YEAR,years);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }


    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 获取该日期是周几
     */
    public int getDayOfWeek(Date date){
        calendar.setTime(date);
        int day=calendar.get(Calendar.DAY_OF_WEEK);
        /*
        由于国外是周日为一周第一天，而国内是周一为第一天，
        因此，当计算到当天如果是1则为周日，对应转换为中国的7，如果不是周日则
        按照规则 减去1即可。
         */
        return day==1?7:day-1;
    }
    public String getWeek(Date date){
        if(date==null){
            return "周?";
        }
        calendar.setTime(date);
        int day=calendar.get(Calendar.DAY_OF_WEEK);
        /*
        由于国外是周日为一周第一天，而国内是周一为第一天，
        因此，当计算到当天如果是1则为周日，对应转换为中国的7，如果不是周日则
        按照规则 减去1即可。
         */
        day=day==1?7:day-1;
        String week="";
        switch (day){
            case 1:
                week="周一";
                break;
            case 2:
                week="周二";
                break;
            case 3:
                week="周三";
                break;
            case 4:
                week="周四";
                break;
            case 5:
                week="周五";
                break;
            case 6:
                week="周六";
                break;
            case 7:
                week="周日";
                break;
        }
        return week;
    }

    /**
     * 获取两个日期之间一共有多少天
     */
    public long getWeekNumForDate(Date date1,Date date2){
        calendar.setTime(date1);
        long time1=calendar.getTimeInMillis();
        calendar.setTime(date2);
        long time2=calendar.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return between_days;
    }
    /**
     * 获取某日期和现在一共有多少天
     */
    public long[] getDaysForNow(String date){
        long[] res=new long[]{0,0,0,0};
        calendar.setTime(convert2Date(date));
        long time1=calendar.getTimeInMillis();
        long time2=new Date().getTime();
        /**
         * time2-time1计算出相差多少毫秒
         * 3600×24是一天的秒数在×1000就是一天的毫秒数
         */
        res[0]=(time2-time1)/(1000*3600*24);//计算多少天
        res[1]=(time2-time1)/(1000*3600);//计算多少小时
        res[2]=(time2-time1)/(1000*60);//计算多少分钟
        res[3]=(time2-time1)/(1000);//计算多少秒
        return res;
    }

    /**
     * 获取当日凌晨
     *
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static String weeHours( int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (flag == 0) {
            return sdf.format(cal.getTime());
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }

        return sdf.format(cal.getTime());
    }
}
