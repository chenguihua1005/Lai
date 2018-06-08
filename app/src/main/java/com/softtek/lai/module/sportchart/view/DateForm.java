package com.softtek.lai.module.sportchart.view;

import java.util.Calendar;

/**
 * Created by lareina.qiao on 6/17/2016.
 */
public class DateForm {
    public String formdate(String nowdate)
    {
        String date;
        String sr=nowdate.substring(4,5);
        if (nowdate.substring(4,5).equals("0"))
        {
            date=nowdate.substring(5,6)+"/"+nowdate.substring(6,8);
        }
        else {
            date=nowdate.substring(4,6)+"/"+nowdate.substring(6,8);

        }
        return date;

    }
    public String getDateform(String nowdate)
    {
        String date;
        String sr=nowdate.substring(4,5);
        date=nowdate.substring(0,4)+"-"+nowdate.substring(4,6)+"-"+nowdate.substring(6,8);
        return date;

    }
    /**
     * 获取阶段日期
     * @param  dateType
     * 推算日期
     */
    //使用方法 char datetype = '7';
    public static StringBuilder getPeriodDate(char dateType,int n) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int hour; // 需要更改的小时
        int day; // 需要更改的天数
        switch (dateType) {
            case '0': // 1小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 1;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '1': // 2小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 2;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '2': // 3小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 3;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '3': // 6小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 6;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '4': // 12小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 12;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '5': // 一天前
                day = c.get(Calendar.DAY_OF_MONTH) - 1;
                c.set(Calendar.DAY_OF_MONTH, day);
                // System.out.println(df.format(c.getTime()));
                break;
            case '6': // 一星期前
                day = c.get(Calendar.DAY_OF_MONTH) - n;
                c.set(Calendar.DAY_OF_MONTH, day);
                // System.out.println(df.format(c.getTime()));
                break;
            case '7': // 一个月前
                day = c.get(Calendar.DAY_OF_MONTH) - 30*n;
                c.set(Calendar.DAY_OF_MONTH, day);
                // System.out.println(df.format(c.getTime()));
                break;
        }
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder strForwardDate = new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay);
        return strForwardDate;
        //return c.getTimeInMillis();
    }
}
