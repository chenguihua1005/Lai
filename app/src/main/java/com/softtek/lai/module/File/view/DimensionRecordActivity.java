/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_dimension_record)
public class DimensionRecordActivity extends BaseActivity implements OnClickListener {

    //toolbar布局控件
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.btn_save)
    Button btn_save;

    @InjectView(R.id.ll_explain)
    LinearLayout ll_explain;

    //TextView
    @InjectView(R.id.tv_circum)
    TextView tv_circum;

    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;

    @InjectView(R.id.tv_hiplie)
    TextView tv_hiplie;

    @InjectView(R.id.tv_uparmgirth)
    TextView tv_uparmgirth;

    @InjectView(R.id.tv_upleggirth)
    TextView tv_upleggirth;

    @InjectView(R.id.tv_doleggirth)
    TextView tv_doleggirth;

    @InjectView(R.id.ll_circum)
    RelativeLayout ll_circum;

    @InjectView(R.id.ll_waistline)
    RelativeLayout ll_waistline;

    @InjectView(R.id.ll_hiplie)
    RelativeLayout ll_hiplie;

    @InjectView(R.id.ll_uparmgirth)
    RelativeLayout ll_uparmgirth;

    @InjectView(R.id.ll_upleggirth)
    RelativeLayout ll_upleggirth;

    @InjectView(R.id.ll_doleggirth)
    RelativeLayout ll_doleggirth;

    private FileModel file;//存储用户表对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_circum.setOnClickListener(this);
        ll_waistline.setOnClickListener(this);
        ll_hiplie.setOnClickListener(this);
        ll_uparmgirth.setOnClickListener(this);
        ll_upleggirth.setOnClickListener(this);
        ll_doleggirth.setOnClickListener(this);
        ll_explain.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void initDatas() {
        file = (FileModel) getIntent().getSerializableExtra("file");
        tv_title.setText("添加记录");
        tv_circum.setText("0.0".equals(file.getCircum() + "") ? "" : file.getCircum() + "");
        tv_waistline.setText("0.0".equals(file.getWaistline() + "") ? "" : file.getWaistline() + "");
        tv_hiplie.setText("0.0".equals(file.getHiplie() + "") ? "" : file.getHiplie() + "");
        tv_uparmgirth.setText("0.0".equals(file.getUparmgirth() + "") ? "" : file.getUparmgirth() + "");
        tv_upleggirth.setText("0.0".equals(file.getUpleggirth() + "") ? "" : file.getUpleggirth() + "");
        tv_doleggirth.setText("0.0".equals(file.getDoleggirth() + "") ? "" : file.getDoleggirth() + "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_circum:
                show_circum_dialog();
                break;
            case R.id.ll_waistline:
                show_waistline_dialog();
                break;
            case R.id.ll_hiplie:
                show_hiplie_dialog();
                break;
            case R.id.ll_uparmgirth:
                show_uparmgirth_dialog();
                break;
            case R.id.ll_upleggirth:
                show_upleggirth_dialog();
                break;
            case R.id.ll_doleggirth:
                show_doleggirth_dialog();
                break;
            //填写说明
            case R.id.ll_explain:
                startActivity(new Intent(DimensionRecordActivity.this, ExplainActivity.class));

                break;
            //返回按钮
            case R.id.ll_left:
                finish();
                break;
            //保存记录......
            case R.id.btn_save:
                double circum = Double.parseDouble(tv_circum.getText().toString().equals("") ? "0" : (tv_circum.getText().toString()));
                double waistline = Double.parseDouble(tv_waistline.getText().toString().equals("") ? "0" : tv_waistline.getText().toString());
                double hiplie = Double.parseDouble(tv_hiplie.getText().toString().equals("") ? "0" : tv_hiplie.getText().toString());
                double uparmgirth = Double.parseDouble(tv_uparmgirth.getText().toString().equals("") ? "0" : tv_uparmgirth.getText().toString());
                double tupleggirth = Double.parseDouble(tv_upleggirth.getText().toString().equals("") ? "0" : tv_upleggirth.getText().toString());
                double doleggirth = Double.parseDouble(tv_doleggirth.getText().toString().equals("") ? "0" : tv_doleggirth.getText().toString());

                //创建档案的添加围度
                file = new FileModel();
                file.setCircum(circum);
                file.setWaistline(waistline);
                file.setHiplie(hiplie);
                file.setUparmgirth(uparmgirth);
                file.setUpleggirth(tupleggirth);
                file.setDoleggirth(doleggirth);
                Intent intent = new Intent();
                intent.putExtra("file", file);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //围度dialog
    public void show_circum_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(200);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择胸围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_circum.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_waistline_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(200);
        np1.setValue(80);
        np1.setMinValue(40);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择腰围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_waistline.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_hiplie_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(250);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择臀围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_hiplie.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_uparmgirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(70);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择上臂围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_uparmgirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    public void show_upleggirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(90);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择大腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_upleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_doleggirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(70);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择小腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_doleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

}
