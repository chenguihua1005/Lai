/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.module.newmemberentry.view.model.NewstudentsModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
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
    private NewstudentsModel newstudentsModel;//存储用户表单数据
    private RetestWriteModel retestWriteModel;

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
//
//        String data=load();
//        if (!TextUtils.isEmpty(data)){
//            tv_circum.setText(data);
//            //tv_circum.setSelection(circum.length());
//        }

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        file = (FileModel) getIntent().getSerializableExtra("file");
        newstudentsModel = (NewstudentsModel) getIntent().getSerializableExtra("newstudentsModel");
        retestWriteModel = (RetestWriteModel) getIntent().getSerializableExtra("retestWriteModel");
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
                //startActivity(new Intent(DimensionRecordActivity.this,CreatFlleActivity.class));
                finish();
                break;
            //保存记录......
            case R.id.btn_save:
                /*getIntent().putExtra("","token");
                String token= SharedPreferenceService.getInstance().get("token","");*/
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
//
//                //新学员录入的添加围度
//                newstudentsModel=new NewstudentsModel();
//                newstudentsModel.setCircum(circum);
//                newstudentsModel.setWaistline(waistline);
//                newstudentsModel.setHiplie(hiplie);
//                newstudentsModel.setUparmgirth(uparmgirth);
//                newstudentsModel.setUpleggirth(tupleggirth);
//                newstudentsModel.setDoleggirth(doleggirth);
//                Intent intent1=new Intent();
//                intent1.putExtra("newstudentsModel",newstudentsModel);
//                setResult(RESULT_OK,intent1);
//                //复测录入
//                retestWriteModel=new RetestWriteModel();
//                retestWriteModel.setCircum(circum+"");
//                retestWriteModel.setWaistline(waistline+"");
//                retestWriteModel.setHiplie(hiplie+"");
//                retestWriteModel.setUpArmGirth(uparmgirth+"");
//                retestWriteModel.setUpLegGirth(tupleggirth+"");
//                retestWriteModel.setDoLegGirth(doleggirth+"");
//                Intent intent2=new Intent();
//                intent2.putExtra("retestWriteModel",retestWriteModel+"");
//                setResult(RESULT_OK,intent2);
//
//                Intent intent3=new Intent();
//                intent.putExtra("data_return","Hello");
//                setResult(RESULT_OK,intent3);


                finish();

                //  Log.i("-------------------newstudentsModel----------------------"+newstudentsModel);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    //    public String load(){
//        FileInputStream in=null;
//        BufferedReader reader=null;
//        StringBuilder content=new StringBuilder();
//        try{
//            in=openFileInput("data");///////
//            reader=new BufferedReader(new InputStreamReader(in));
//            String line="";
//            while ((line=reader.readLine())!=null){
//                content.append(line);
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if (reader!=null){
//                try {
//                    reader.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        return content.toString();
//    }
    //围度dialog
    public void show_circum_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
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
        np1.setMaxValue(220);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
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
