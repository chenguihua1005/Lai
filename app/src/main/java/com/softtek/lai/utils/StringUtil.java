package com.softtek.lai.utils;

import com.github.snowdream.android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by jerry.guan on 4/26/2016.
 * 项目的一些操作字符串的工具
 */
public class StringUtil {

    /**
     * 在原始字符串后面添加逗号并拼接新的字符串
     * 若需要追加的字符不存在 则用手机号替代,只显示10人
     * @param orginal
     * @param append
     * @return
     */
    public static String appendDot(String orginal,String append,String phone){
        StringBuffer buffer=new StringBuffer();
        if(StringUtils.isNotEmpty(orginal)){
            buffer.append(orginal);
            buffer.append(",");
        }
        if(StringUtils.isEmpty(append)){
            buffer.append(filterPhonNumber(phone));
        }else{
            buffer.append(append);
        }
        //判断点赞人数是否大于10人
        String[] peoples=buffer.toString().split(",");
        if(peoples.length>10){
            StringBuffer res=new StringBuffer();
            for(int i=0;i<10;i++){
                if (i==9){
                    res.append(peoples[i]);
                }else{
                    res.append(peoples[i]);
                    res.append(",");
                }
            }
            res.append("等"+(peoples.length-10)+"人也觉得很赞");
            return res.toString();
        }
        return buffer.toString();
    }

    /**
     * 在原始字符串后面添加逗号并拼接新的字符串
     * 若需要追加的字符不存在 则用手机号替代
     * @param orginal
     * @param append
     * @return
     */
    public static String appendDotAll(String orginal,String append,String phone){
        StringBuffer buffer=new StringBuffer();
        if(StringUtils.isNotEmpty(orginal)){
            buffer.append(orginal);
            buffer.append(",");
        }
        if(StringUtils.isEmpty(append)){
            buffer.append(filterPhonNumber(phone));
        }else{
            buffer.append(append);
        }
        Log.i("拼接后的字符="+buffer.toString());
        return buffer.toString();
    }

    /**
     * 过滤手机号保留前3位和后4位中间用****
     * @param phone
     * @return
     */
    public static String filterPhonNumber(String phone){
        if(StringUtils.isEmpty(phone)){
            return "";
        }
        String before3=phone.substring(0,3);
        String after4=phone.substring(phone.length()-4,phone.length());
        return before3+"****"+after4;
    }

    public static String showName(String userName,String phone){
        return StringUtils.isEmpty(userName)?filterPhonNumber(phone):userName;
    }

    /**
     * 获取数值，转换成小数后一位
     * 若传入的数值为空则返回空
     * @param value
     * @return
     */
    public static String getValue(String value){
        return StringUtils.isEmpty(value)||Float.parseFloat(value)==0?"":Float.parseFloat(value)+"";
    }
    public static String getFloatValue(String value){
        return StringUtils.isEmpty(value)||Float.parseFloat(value)==0?"0.0":Float.parseFloat(value)+"";
    }
    public static String getDoubleValue(String value){
        return StringUtils.isEmpty(value)||Float.parseFloat(value)==0?"0.00":Double.parseDouble(value)+"";
    }

    public static float getFloat(String value){
        return StringUtils.isEmpty(value)?0.0f:Float.parseFloat(value);
    }

    public static long getLong(String value){
        return StringUtils.isEmpty(value)?-1:Long.parseLong(value);
    }


    private static  boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }
    /**
     * 返回中英文混合的长度
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    public static String withValue(String value){
        return StringUtils.isEmpty(value)?"未知":value;
    }

    public static String convertValue1(String value){
        return StringUtils.isEmpty(value)?"--":value;
    }
    public static String convertValue2(String value){
        return StringUtils.isEmpty(value)?"--":Float.parseFloat(value)==0?"--":value;
    }
    public static String convertValue7(String value,String unit){
        return StringUtils.isEmpty(value)?"-":Float.parseFloat(value)==0?"-":value+unit;
    }
    public static String convertValue3(String value){
        return StringUtils.isEmpty(value)?"--:--":value;
    }

    public static String convertValue4(String value){
        if(StringUtils.isEmpty(value)||"--".equals(value)){
            return "--";
        }
        DecimalFormat format=new DecimalFormat("#0.0");
        return format.format(Float.parseFloat(value))+"%";
    }
    public static String convertValue5(String value){
        if(StringUtils.isEmpty(value)||"--".equals(value)){
            return "--";
        }
        DecimalFormat format=new DecimalFormat("#0.0");
        return format.format(Float.parseFloat(value));
    }
    public static String convertValue6(String value){
        if(StringUtils.isEmpty(value)){
            return "0.0";
        }
        DecimalFormat format=new DecimalFormat("#0.0");
        return format.format(Float.parseFloat(value));
    }
}
