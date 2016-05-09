package com.softtek.lai.utils;

import com.github.snowdream.android.util.Log;

import org.apache.commons.lang3.StringUtils;

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
        Log.i("拼接后的字符="+buffer.toString());
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
        String before3=phone.substring(0,3);
        String after4=phone.substring(phone.length()-4,phone.length());
        return before3+"****"+after4;
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


}
