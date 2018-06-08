package com.softtek.lai.module.sport.util;

import com.github.snowdream.android.util.Log;

/**
 * Created by jerry.guan on 7/26/2016.
 */
public class ColorUtil {

    private static final int ABNORMAL=130;//2分10秒小于这个值异常
    private static final int HIGH_SPEED=360;//6分钟小于这个值高速
    private static final int MEDIUM_SPEED=540;//9分钟小于这个值中速


    private static int[] colorList = {
            /*绿*/
            0xFF99FC35,
            /*黄*/
            0xFFF0F936,
            /*红*/
            0xFFF7594A,
            /*灰*/
            0xFFCCCCCC};

    public static int getSpeedColor(long time,boolean flag){
        int color;
        /*if(!flag){*/
            //Log.i("时耗>>"+time);
            if(time<=ABNORMAL){
                color=colorList[3];
                //Log.i("灰");
            }else if(time<=HIGH_SPEED){
                color=colorList[2];
                //Log.i("红");
            }else if(time<=MEDIUM_SPEED){
                color=colorList[1];
                //Log.i("黄");
            }else {
                color=colorList[0];
                //Log.i("绿");
            }
        /*}else{
            color=colorList[3];
            Log.i("异常");
        }*/
        return color;
    }
}
