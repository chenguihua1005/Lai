package com.softtek.lai.module.bodygame3.activity.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.ExplainActivity;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureStModel;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.present.RetestPre;


import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodyweidu)
public class BodyweiduActivity extends BaseActivity implements View.OnClickListener{
    //toolbar布局控件
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.tv_title)
    TextView tv_title;
//保存围度按钮
    @InjectView(R.id.btn_retest_save)
    Button btn_retest_save;
//填写说明事件
    @InjectView(R.id.ll_retest_explain)
    LinearLayout ll_retest_explain;

    //TextView保存信息
    //胸围
    @InjectView(R.id.tv_retest_circum)
    TextView tv_retest_circum;
    //腰围
    @InjectView(R.id.tv_retest_waistline)
    TextView tv_retest_waistline;
    //臀围
    @InjectView(R.id.tv_retest_hiplie)
    TextView tv_retest_hiplie;
    //上臂围
    @InjectView(R.id.tv_retest_uparmgirth)
    TextView tv_retest_uparmgirth;
    //大腿围
    @InjectView(R.id.tv_retest_upleggirth)
    TextView tv_retest_upleggirth;
    //小腿围
    @InjectView(R.id.tv_retest_doleggirth)
    TextView tv_retest_doleggirth;
//点击响应信息弹框事件
    //胸围
    @InjectView(R.id.ll_retest_circum)
    RelativeLayout ll_retest_circum;
    //腰围
    @InjectView(R.id.ll_retest_waistline)
    RelativeLayout ll_retest_waistline;
    //臀围
    @InjectView(R.id.ll_retest_hiplie)
    RelativeLayout ll_retest_hiplie;
    //上臂围
    @InjectView(R.id.ll_retest_uparmgirth)
    RelativeLayout ll_retest_uparmgirth;
    //大腿围
    @InjectView(R.id.ll_retest_upleggirth)
    RelativeLayout ll_retest_upleggirth;
    //小腿围
    @InjectView(R.id.ll_retest_doleggirth)
    RelativeLayout ll_retest_doleggirth;

    private FcStDataModel fcStDataModel;
    private MeasuredDetailsModel measuredDetailsModel;
    int Audited;//0，初始数据未审核，1初始数据已审核，2，学员复测录入、学员初始数据录入,3复测未审核，4复测已审核

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_retest_circum.setOnClickListener(this);
        ll_retest_waistline.setOnClickListener(this);
        ll_retest_hiplie.setOnClickListener(this);
        ll_retest_uparmgirth.setOnClickListener(this);
        ll_retest_upleggirth.setOnClickListener(this);
        ll_retest_doleggirth.setOnClickListener(this);
        ll_retest_explain.setOnClickListener(this);
        btn_retest_save.setOnClickListener(this);
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("添加记录");
        fcStDataModel = (FcStDataModel) getIntent().getSerializableExtra("retestWrite");
        measuredDetailsModel=(MeasuredDetailsModel)getIntent().getSerializableExtra("initaudit") ;
        Audited=getIntent().getIntExtra("Audited",0);
        switch (Audited)
        {
            //初始数据未审核
            case 0:
                try {
                    if (measuredDetailsModel != null) {
                        tv_retest_circum.setText(TextUtils.isEmpty(measuredDetailsModel.getCircum()) ? "" : measuredDetailsModel.getCircum());
                        tv_retest_waistline.setText(TextUtils.isEmpty(measuredDetailsModel.getWaistline()) ? "" : measuredDetailsModel.getWaistline());
                        tv_retest_hiplie.setText(TextUtils.isEmpty(measuredDetailsModel.getHiplie()) ? "" : measuredDetailsModel.getHiplie());
                        tv_retest_uparmgirth.setText(TextUtils.isEmpty(measuredDetailsModel.getUpArmGirth()) ? "" : measuredDetailsModel.getUpArmGirth());
                        tv_retest_upleggirth.setText(TextUtils.isEmpty(measuredDetailsModel.getUpLegGirth()) ? "" : measuredDetailsModel.getUpLegGirth());
                        tv_retest_doleggirth.setText(TextUtils.isEmpty(measuredDetailsModel.getDoLegGirth()) ? "" : measuredDetailsModel.getDoLegGirth());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //初始数据已审核
            case 1:
                btn_retest_save.setVisibility(View.GONE);
                try {
                    if (measuredDetailsModel != null) {
                        tv_retest_circum.setText(TextUtils.isEmpty(measuredDetailsModel.getCircum()) ? "" : measuredDetailsModel.getCircum());
                        tv_retest_waistline.setText(TextUtils.isEmpty(measuredDetailsModel.getWaistline()) ? "" : measuredDetailsModel.getWaistline());
                        tv_retest_hiplie.setText(TextUtils.isEmpty(measuredDetailsModel.getHiplie()) ? "" : measuredDetailsModel.getHiplie());
                        tv_retest_uparmgirth.setText(TextUtils.isEmpty(measuredDetailsModel.getUpArmGirth()) ? "" : measuredDetailsModel.getUpArmGirth());
                        tv_retest_upleggirth.setText(TextUtils.isEmpty(measuredDetailsModel.getUpLegGirth()) ? "" : measuredDetailsModel.getUpLegGirth());
                        tv_retest_doleggirth.setText(TextUtils.isEmpty(measuredDetailsModel.getDoLegGirth()) ? "" : measuredDetailsModel.getDoLegGirth());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //学员复测数据、初始数据
            case 2:
                try {
                    if (fcStDataModel != null) {
                        tv_retest_circum.setText(TextUtils.isEmpty(fcStDataModel.getCircum()) ? "" : fcStDataModel.getCircum());
                        tv_retest_waistline.setText(TextUtils.isEmpty(fcStDataModel.getWaistline()) ? "" : fcStDataModel.getWaistline());
                        tv_retest_hiplie.setText(TextUtils.isEmpty(fcStDataModel.getHiplie()) ? "" : fcStDataModel.getHiplie());
                        tv_retest_uparmgirth.setText(TextUtils.isEmpty(fcStDataModel.getUpArmGirth()) ? "" : fcStDataModel.getUpArmGirth());
                        tv_retest_upleggirth.setText(TextUtils.isEmpty(fcStDataModel.getUpLegGirth()) ? "" : fcStDataModel.getUpLegGirth());
                        tv_retest_doleggirth.setText(TextUtils.isEmpty(fcStDataModel.getDoLegGirth()) ? "" : fcStDataModel.getDoLegGirth());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                break;
            case 4:
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_retest_circum:
                if (Audited!=1&&Audited!=4) {
                    show_circum_dialog();
                }
                break;
            case R.id.ll_retest_waistline:
                if (Audited!=1&&Audited!=4) {

                    show_waistline_dialog();
                }
                break;
            case R.id.ll_retest_hiplie:
                if (Audited!=1&&Audited!=4) {

                    show_hiplie_dialog();
                }
                break;
            case R.id.ll_retest_uparmgirth:
                if (Audited!=1&&Audited!=4) {

                    show_uparmgirth_dialog();
                }
                break;
            case R.id.ll_retest_upleggirth:
                if (Audited!=1&&Audited!=4) {

                    show_upleggirth_dialog();
                }
                break;
            case R.id.ll_retest_doleggirth:
                if (Audited!=1&&Audited!=4) {

                    show_doleggirth_dialog();
                }
                break;
            //填写说明
            case R.id.ll_retest_explain:
                startActivity(new Intent(this,ExplainActivity.class));
                break;
            //返回按钮
            case R.id.ll_left:
                finish();
                break;
            //保存记录......
            case R.id.btn_retest_save:
                if (Audited==2) {
                    fcStDataModel = new FcStDataModel();
                    fcStDataModel.setCircum(tv_retest_circum.getText().toString());//胸围
                    fcStDataModel.setWaistline(tv_retest_waistline.getText().toString());//腰围
                    fcStDataModel.setHiplie(tv_retest_hiplie.getText().toString());//臀围
                    fcStDataModel.setUpArmGirth(tv_retest_uparmgirth.getText().toString());//上臂围
                    fcStDataModel.setUpLegGirth(tv_retest_upleggirth.getText().toString());//大腿围
                    fcStDataModel.setDoLegGirth(tv_retest_doleggirth.getText().toString());//小腿围
                    Intent intent = new Intent();
                    intent.putExtra("retestWrite", fcStDataModel);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    measuredDetailsModel = new MeasuredDetailsModel();
                    measuredDetailsModel.setCircum(tv_retest_circum.getText().toString());//胸围
                    measuredDetailsModel.setWaistline(tv_retest_waistline.getText().toString());//腰围
                    measuredDetailsModel.setHiplie(tv_retest_hiplie.getText().toString());//臀围
                    measuredDetailsModel.setUpArmGirth(tv_retest_uparmgirth.getText().toString());//上臂围
                    measuredDetailsModel.setUpLegGirth(tv_retest_upleggirth.getText().toString());//大腿围
                    measuredDetailsModel.setDoLegGirth(tv_retest_doleggirth.getText().toString());//小腿围
                    Intent intent = new Intent();
                    intent.putExtra("retestWrite", measuredDetailsModel);
                    setResult(RESULT_OK, intent);
                    finish();
                }

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
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(200);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择胸围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_retest_circum.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(200);
        np1.setValue(80);
        np1.setMinValue(40);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择腰围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_retest_waistline.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(250);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择臀围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_retest_hiplie.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(70);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择上臂围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_retest_uparmgirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(90);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择大腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_retest_upleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(70);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择小腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_retest_doleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
}
