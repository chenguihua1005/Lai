/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_fuce_st)
public class FuceStActivity extends BaseActivity implements View.OnClickListener{

    //保存数据点击
    //初始体重
    @InjectView(R.id.ll_fucest_chu_weight)
    LinearLayout ll_fucest_chu_weight;
    //现在体重
    @InjectView(R.id.ll_fucest_nowweight)
    LinearLayout ll_fucest_nowweight;
    // 体脂
    @InjectView(R.id.ll_fucest_tizhi)
    LinearLayout ll_fucest_tizhi;
    //内脂
    @InjectView(R.id.ll_retestWrite_neizhi)
    LinearLayout ll_retestWrite_neizhi;
    //添加身体维度
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;

    @InjectView(R.id.tv_write_chu_weight)
    TextView tv_write_chu_weight;
    @InjectView(R.id.tv_retestWrite_nowweight)
    TextView tv_retestWrite_nowweight;
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_retest_write_addbody.setOnClickListener(this);
        ll_fucest_chu_weight.setOnClickListener(this);
        ll_fucest_nowweight.setOnClickListener(this);
        ll_fucest_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_retest_write_addbody:

                break;
            case R.id.ll_fucest_chu_weight:

                break;
            case R.id.ll_fucest_nowweight:

                break;
            case R.id.ll_fucest_tizhi:

                break;
            case R.id.ll_retestWrite_neizhi:

                break;
        }
    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final Dialog information_dialog = new Dialog(this);
        information_dialog.setTitle(title);
        information_dialog.setContentView(R.layout.dimension_dialog);
        Button b1 = (Button) information_dialog.findViewById(R.id.button1);
        Button b2 = (Button) information_dialog.findViewById(R.id.button2);
        final NumberPicker np1 = (NumberPicker) information_dialog.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) information_dialog.findViewById(R.id.numberPicker2);
        np1.setMaxValue(np1maxvalur);
        np1.setValue(np1value);
        np1.setMinValue(np1minvalue);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(np2maxvalue);
        np2.setValue(np2value);
        np2.setMinValue(np2minvalue);
        np2.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num==0) {
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                    information_dialog.dismiss();
                }
                else if (num==1)
                {
                    tv_retestWrite_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    information_dialog.dismiss();
                }
                else if (num==2)
                {
                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    information_dialog.dismiss();
                }
                else if(num==3)
                {
                    tv_retestWrite_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    information_dialog.dismiss();
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information_dialog.dismiss();
            }
        });
        information_dialog.show();
        information_dialog.setCanceledOnTouchOutside(false);
    }
}
