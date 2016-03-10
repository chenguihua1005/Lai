package com.softtek.lai.module.home.File.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.softtek.lai.R;


public class Mydata extends Activity {
        //implements NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);
        TextView birth = (TextView) findViewById(R.id.birth);
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showPopwindow();
            }
        });
    }

//    public String format(int value) {
//        String tmpStr = String.valueOf(value);
//        if (value < 10) {
//            tmpStr = "0" + tmpStr;
//        }
//        return tmpStr;
//    }
//
//
//    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//        Toast.makeText(this,"原来的值 " + oldVal + "--新值: "+ newVal, Toast.LENGTH_SHORT).show();
//    }
//
//    public void onScrollStateChange(NumberPicker view, int scrollState) {
//        switch (scrollState) {
//            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
//                Toast.makeText(this, "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG).show();
//                break;
//            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
//                Toast.makeText(this, "不滑动", Toast.LENGTH_LONG).show();
//                break;
//            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                Toast.makeText(this, "滑动中", Toast.LENGTH_LONG)
//                        .show();
//                break;
//        }
//    }


/**
 * 显示popupWindow
 */
    /*private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(Mydata.this.findViewById(R.id.birth),
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
//        Button first = (Button) view.findViewById(R.id.first);
//        first.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                System.out.println("第一个按钮被点击了");
//            }
//        });

//
//        NumberPicker hourPicker=(NumberPicker) findViewById(R.id.hourpicker);
//        NumberPicker minutePicker=(NumberPicker) findViewById(R.id.minuteicker);

//        hourPicker.setFormatter(this);
//        hourPicker.setOnValueChangedListener(this);
//        hourPicker.setOnScrollListener(this);
//        hourPicker.setMaxValue(24);
//        hourPicker.setMinValue(0);
//        hourPicker.setValue(9);

//        minutePicker.setFormatter(this);
//        minutePicker.setOnValueChangedListener(this);
//        minutePicker.setOnScrollListener(this);
//        minutePicker.setMaxValue(60);
//        minutePicker.setMinValue(0);
//        minutePicker.setValue(49);



        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });

    }*/
 }