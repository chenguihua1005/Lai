package com.softtek.lai.module.sport.util;

import com.github.snowdream.android.util.Log;

/**
 * Created by jerry.guan on 7/26/2016.
 */
public class ColorUtil {

    private static int[] colorList = {
            /*绿*/
            0xFF74BB2A,
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
        if(!flag){
            if(speed<4){
                color=colorList[0];
                Log.i("绿");
            }else if(speed<5){
                color=colorList[1];
                Log.i("淡黄");
            }else if(speed<6){
                color=colorList[2];
                Log.i("黄");
            }else{
                color=colorList[3];
                Log.i("红");
            }
        }else{
            color=colorList[4];
            Log.i("异常");
        }
        return color;
    }
}
