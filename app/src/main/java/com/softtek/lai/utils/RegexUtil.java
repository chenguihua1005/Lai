package com.softtek.lai.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jerry.guan on 3/8/2016.
 * 手动验证表单
 */
public class RegexUtil {

    public static boolean match(String pattern,String value){
        Pattern regex=Pattern.compile(pattern);
        Matcher matcher=regex.matcher(value);
        return matcher.matches();
    }
}
