package com.softtek.lai.module.retest.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.ExplainActivity;
import com.softtek.lai.module.bodygame3.activity.model.InitComitModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;

import org.greenrobot.eventbus.Subscribe;

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

    private InitComitModel initComitModel;
    private RetestPre retestPre;
    MeasureModel measureModel;
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
        tv_title.setText("添加记录");
        initComitModel= (InitComitModel) getIntent().getSerializableExtra("retestWrite");

        tv_retest_circum.setText(initComitModel.getCircum()+"");
        tv_retest_waistline.setText(initComitModel.getWaistline()+"");
        tv_retest_hiplie.setText(initComitModel.getPysical()+"");
        tv_retest_uparmgirth.setText(initComitModel.getUpArmGirth()+"");
        tv_retest_upleggirth.setText(initComitModel.getUpLegGirth()+"");
        tv_retest_doleggirth.setText(initComitModel.getDoLegGirth()+"");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_retest_circum:
                show_circum_dialog();
                break;
            case R.id.ll_retest_waistline:
                show_waistline_dialog();
                break;
            case R.id.ll_retest_hiplie:
                show_hiplie_dialog();
                break;
            case R.id.ll_retest_uparmgirth:
                show_uparmgirth_dialog();
                break;
            case R.id.ll_retest_upleggirth:
                show_upleggirth_dialog();
                break;
            case R.id.ll_retest_doleggirth:
                show_doleggirth_dialog();
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

                initComitModel=new InitComitModel();
                initComitModel.setCircum(tv_retest_circum.getText()+"");
                initComitModel.setWaistline(tv_retest_waistline.getText().toString());
                initComitModel.setPysical(tv_retest_hiplie.getText().toString());
                initComitModel.setUpArmGirth(tv_retest_uparmgirth.getText().toString());
                initComitModel.setUpLegGirth(tv_retest_upleggirth.getText().toString());
                initComitModel.setDoLegGirth(tv_retest_doleggirth.getText().toString());
                Intent intent=new Intent();
                intent.putExtra("retestWrite",initComitModel);
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
