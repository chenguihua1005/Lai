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
}
