package com.softtek.lai.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by jerry.guan on 3/8/2016.
 */
public class SoftInputUtil {

    /**
     * 软键盘显示
     * @param context
     */
    public static void show(Context context){
        InputMethodManager im= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0,InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 软键盘隐藏
     * @param context
     */
    public static void hidden(Context context){
        InputMethodManager im= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
