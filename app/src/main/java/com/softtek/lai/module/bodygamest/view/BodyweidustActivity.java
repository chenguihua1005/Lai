package com.softtek.lai.module.bodygamest.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.ExplainActivity;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.present.RetestPre;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodyweidu)
public class BodyweidustActivity extends BaseActivity implements View.OnClickListener{
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

    private RetestWriteModel retestWrite;
    private RetestPre retestPre;
    MeasureModel measureModel;
    String state="true";
    String img="";

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
//        retestWrite= (RetestWriteModel) getIntent().getSerializableExtra("retestWrite");
        tv_title.setText("添加记录");
        retestWrite=new RetestWriteModel();
        Intent intent=getIntent();
        retestWrite= (RetestWriteModel) getIntent().getSerializableExtra("retestWrite");
        state=intent.getStringExtra("isState");
        if (!state.equals("true"))
        {
            btn_retest_save.setVisibility(View.GONE);
        }
        tv_retest_circum.setText(retestWrite.getCircum().equals("")?"":Float.parseFloat(retestWrite.getCircum())+"");
        tv_retest_waistline.setText(retestWrite.getWaistline().equals("")?"":Float.parseFloat(retestWrite.getWaistline())+"");
        tv_retest_hiplie.setText(retestWrite.getHiplie().equals("")?"":Float.parseFloat(retestWrite.getHiplie())+"");
        tv_retest_uparmgirth.setText(retestWrite.getUpArmGirth().equals("")?"":Float.parseFloat(retestWrite.getUpArmGirth())+"");
        tv_retest_upleggirth.setText(retestWrite.getUpLegGirth().equals("")?"":Float.parseFloat(retestWrite.getUpLegGirth())+"");
        tv_retest_doleggirth.setText(retestWrite.getDoLegGirth().equals("")?"":Float.parseFloat(retestWrite.getDoLegGirth())+"");
        img=retestWrite.getImage();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_retest_circum:
                if (state.equals("true")) {
                    show_circum_dialog();
                }
                break;
            case R.id.ll_retest_waistline:
                if (state.equals("true")) {
                    show_waistline_dialog();
                }
                break;
            case R.id.ll_retest_hiplie:
                if (state.equals("true")) {
                    show_hiplie_dialog();
                }
                break;
            case R.id.ll_retest_uparmgirth:
                if (state.equals("true")) {
                    show_uparmgirth_dialog();
                }
                break;
            case R.id.ll_retest_upleggirth:
                if (state.equals("true")) {
                    show_upleggirth_dialog();
                }
                break;
            case R.id.ll_retest_doleggirth:
                if (state.equals("true")) {
                    show_doleggirth_dialog();
                }
                break;
            //填写说明
            case R.id.ll_retest_explain:
                startActivity(new Intent(this,ExplainActivity.class));

                break;
            //返回按钮
            case R.id.ll_left:
                //startActivity(new Intent(DimensionRecordActivity.this,CreatFlleActivity.class));
                finish();
                break;
            //保存记录......
            case R.id.btn_retest_save:


                retestWrite=new RetestWriteModel();
                retestWrite.setCircum(tv_retest_circum.getText().toString()+"");
                retestWrite.setWaistline(tv_retest_waistline.getText().toString()+"");
                retestWrite.setHiplie(tv_retest_hiplie.getText().toString()+"");
                retestWrite.setUpArmGirth(tv_retest_uparmgirth.getText().toString()+"");
                retestWrite.setUpLegGirth(tv_retest_upleggirth.getText().toString()+"");
                retestWrite.setDoLegGirth(tv_retest_doleggirth.getText().toString()+"");
                retestWrite.setImage(img);
                Intent intent=new Intent();
                intent.putExtra("retestWrite",retestWrite);
                setResult(RESULT_OK,intent);
                finish();

//                  Log.i("-------------------retestWrite----------------------"+retestWrite+"");
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
//    @Subscribe
//    public void event(MeasureModel measureModel){
//
//        com.github.snowdream.android.util.Log.i("username"+measureModel.getUsername());
//        tv_retest_circum.setText(measureModel.getChestgirth());
//        tv_retest_waistline.setText(measureModel.getWaistgirth());
//        tv_retest_hiplie.setText(measureModel.getHipgirth());
//        tv_retest_uparmgirth.setText(measureModel.getUpperarmgirth());
//        tv_retest_upleggirth.setText(measureModel.getThighgirth());
//        tv_retest_doleggirth.setText(measureModel.getCalfgirth());
//    }


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
