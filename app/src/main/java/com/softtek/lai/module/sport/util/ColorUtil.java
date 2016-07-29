package com.softtek.lai.module.sport.util;

/**
 * Created by jerry.guan on 7/26/2016.
 */
public class ColorUtil {

    private static int[] colorList = {
            /*绿*/
            0xFF7DFF00,
            /*淡黄*/
            0xFFE1E618,
            /*黄*/
            0xFFFBE01C,
            /*红*/
            0xFFDE2C00,
            /*灰*/
            0xFF74736C};

    public static int getSpeedColor(double speed,boolean flag){
        int color=0;
        if(flag){
            if(speed<5){
                color=colorList[0];
            }else if(speed<6){
                color=colorList[1];
            }else if(speed<7){
                color=colorList[2];
            }else{
                color=colorList[3];
            }
        }else{
            color=colorList[4];
        }
        return color;
    }
}
