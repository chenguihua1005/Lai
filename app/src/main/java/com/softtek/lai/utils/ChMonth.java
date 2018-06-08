package com.softtek.lai.utils;

/**
 * Created by lareina.qiao on 6/29/2016.
 */
public class ChMonth {
    public String tomonth(String month){

        if (month.equals("01")||month.equals("1")){
            month="一月";
        }
        else if (month.equals("02")||month.equals("2")){
            month="二月";
        }else if (month.equals("03")||month.equals("3"))
        {
            month="三月";
        }else if (month.equals("04")||month.equals("4"))
        {
            month="四月";

        }else if (month.equals("05")||month.equals("5"))
        {
            month="五月";
        }else if (month.equals("06")||month.equals("6"))
        {
            month="六月";
        }else if (month.equals("07")||month.equals("7"))
        {
            month="七月";
        } else if (month.equals("08")||month.equals("8"))
        {
            month="八月";
        }else if (month.equals("09")||month.equals("9"))
        {
            month="九月";
        }else if (month.equals("10"))
        {
            month="十月";
        }else if (month.equals("11"))
        {
            month="十一月";
        }else
        {
            month="十二月";
        }
        return month;
    }

    public static String toText(int month){

        switch (month){
            case 1:
                return "一月";
            case 2:
                return "二月";
            case 3:
                return "三月";
            case 4:
                return "四月";
            case 5:
                return "五月";
            case 6:
                return "六月";
            case 7:
                return "七月";
            case 8:
                return "八月";
            case 9:
                return "九月";
            case 10:
                return "十月";
            case 11:
                return "十一月";
            case 12:
                return "十二月";

        }
        return "";
    }
}
