package com.softtek.lai.module.message2.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.github.snowdream.android.util.Log;

/**
 * Created by Terry on 2016/12/10.
 */

public class ShuoMClickableSpan extends ClickableSpan{

        String string;
        Context context;
        public ShuoMClickableSpan(String str,Context context){
            super();
            this.string = str;
            this.context = context;
        }


        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }


        @Override
        public void onClick(View widget) {
            Log.i("你想去哪？");
        }
}
