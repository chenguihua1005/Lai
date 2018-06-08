package com.softtek.lai.module.bodygame3.activity.view;

/**
 * Created by lareina.qiao on 1/11/2017.
 */

public class FormData {
    public String formdata(int num)
    {
        String weeknum = null;
        switch (num)
        {
            case 1:
                weeknum="一";
                break;
            case 2:
                weeknum="二";
                break;
            case 3:
                weeknum="三";
                break;
            case 4:
                weeknum="四";
                break;
            case 5:
                weeknum="五";
                break;
            case 6:
                weeknum="六";
                break;
            case 7:
                weeknum="七";
                break;
            case 8:
                weeknum="八";
                break;
            case 9:
                weeknum="九";
                break;
            case 10:
                weeknum="十";
                break;
            case 11:
                weeknum="十一";
                break;
            case 12:
                weeknum="十二";
                break;
            
        }
        return weeknum;

    }
}
