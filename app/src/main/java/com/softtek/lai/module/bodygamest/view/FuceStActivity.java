/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import butterknife.InjectView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.newmemberentry.view.GetPhotoDialog;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.module.retest.view.BodyweiduActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;

import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_fuce_st)
public class FuceStActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    //toolbar
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

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
    @InjectView(R.id.btn_retest_write_addbodyst)
    Button btn_retest_write_addbodyst;
    //提交
    @InjectView(R.id.bt_pingshen)
    Button bt_pingshen;


    @InjectView(R.id.tv_writes_chu_weight)
    TextView tv_writes_chu_weight;
    @InjectView(R.id.tv_retestWrites_nowweight)
    TextView tv_retestWrites_nowweight;
    @InjectView(R.id.tv_retestWritest_tizhi)
    TextView tv_retestWritest_tizhi;
    @InjectView(R.id.tv_retestWritest_neizhi)
    TextView tv_retestWritest_neizhi;
    //takeohoto
    @InjectView(R.id.im_retestwritest_takephoto)
    ImageView im_retestwritest_takephoto;
    //picture
    @InjectView(R.id.im_retestwritest_showphoto)
    ImageView im_retestwritest_showphoto;
    //delete
    @InjectView(R.id.im_deletest)
            ImageView im_deletest;
    //laichen
    @InjectView(R.id.selectlaichenst)
    CheckBox selectlaichenst;
    String path="";
    private static final int PHOTO=1;
    private static final int GET_BODY=2;
    private static final String LAI_CHEN_SWITCH_KEY1="laichenSwitch";
    private RetestPre retestPre;
    RetestWriteModel retestWrite;
    MeasureModel measureModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        btn_retest_write_addbodyst.setOnClickListener(this);
        ll_fucest_chu_weight.setOnClickListener(this);
        ll_fucest_nowweight.setOnClickListener(this);
        ll_fucest_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_retestwritest_takephoto.setOnClickListener(this);
        im_deletest.setOnClickListener(this);
        selectlaichenst.setOnCheckedChangeListener(this);
        bt_pingshen.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("复测录入");
        retestPre=new RetestclassImp();
        retestPre.doGetAudit(36,3,"2016-03-28");
        retestWrite=new RetestWriteModel();
        measureModel=new MeasureModel();
        boolean laichenSwitch= SharedPreferenceService.getInstance().get(LAI_CHEN_SWITCH_KEY1,false);
        selectlaichenst.setChecked(laichenSwitch);
        if(selectlaichenst.isChecked()){
            Log.i("上一次莱秤被打开");
            retestPre.doGetMeasure("0Pmg0UmrnZBYbcPABC5YB0pSqNXOFnB885ZYInLptG8YvAZsT87oGUPZtU5wbAad-26xsvP8Ov_eoq6Mj9rISg-XZiz2xesbiiqYPWK0AeYquQ8fXwXNpmvL0XwbUkse","18206182086");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //删除照片
            case R.id.im_deletest:
                im_retestwritest_showphoto.setVisibility(View.GONE);
                im_deletest.setVisibility(View.GONE);
                break;
            case R.id.btn_retest_write_addbodyst:
                Intent intent=new Intent(this, BodyweidustActivity.class);
//                intent.putExtra("retestWrite",retestWrite);
                Log.i("retestWrite="+retestWrite.toString());
                intent.putExtra("retestWrite",retestWrite);
                startActivityForResult(intent,GET_BODY);
                break;
            case R.id.ll_fucest_chu_weight:
                show_information("初始体重（kg）",200,70,20,9,5,0,0);
                break;
            case R.id.ll_fucest_nowweight:
                show_information("现在体重（kg）",200,70,20,9,5,0,1);
                break;
            case R.id.ll_fucest_tizhi:
                show_information("体脂（%）",100,50,0,9,5,0,2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂（%）",100,50,0,9,5,0,3);
                break;
            //拍照事件
            case R.id.im_retestwritest_takephoto:
                final GetPhotoDialog dialog = new GetPhotoDialog(this,
                        new GetPhotoDialog.GetPhotoDialogListener() {
                            @Override
                            public void onClick(View view) {
                                switch(view.getId()){
                                    case R.id.imgbtn_camera:
                                        takecamera();
                                        break;
                                    case R.id.imgbtn_pic:
                                        takepic();
                                        break;
                                }
                            }
                        });
                dialog.setTitle("照片上传");
                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                dialog.show();
                break;
            case R.id.bt_pingshen:
                retestWrite.setWeight(tv_retestWrites_nowweight.getText()+"");
                retestWrite.setPysical(tv_retestWritest_tizhi.getText()+"");
                retestWrite.setFat(tv_retestWritest_neizhi.getText()+"");
                retestWrite.setClassId("4");
                retestWrite.setImage("");
                retestWrite.setAccountId("3");
                retestPre.doPostWrite(3,36,retestWrite);
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }
    @Subscribe
    public void eventst(MeasureModel measureModel1){
        measureModel=measureModel1;
        Log.i("username"+measureModel.getUsername());
        tv_retestWrites_nowweight.setText(measureModel.getMeasureddata().getItems().get(0).getWeight());
        tv_retestWritest_tizhi.setText(measureModel.getMeasureddata().getItems().get(0).getBodyfat());
        tv_retestWritest_neizhi.setText(measureModel.getMeasureddata().getItems().get(0).getVisceralfatindex());
        retestWrite.setCircum(measureModel.getChestgirth());
        retestWrite.setWaistline(measureModel.getWaistgirth());
        retestWrite.setHiplie(measureModel.getHipgirth());
        retestWrite.setUpArmGirth(measureModel.getUpperarmgirth());
        retestWrite.setUpLegGirth(measureModel.getThighgirth());
        retestWrite.setDoLegGirth(measureModel.getCalfgirth());
    }
    @Subscribe
    public void doGetPhotost(PhotModel photModel) {
        System.out.println("照片名称" + photModel.getImg());
        retestWrite.setImage(photModel.getImg());
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //莱秤
        Log.i("莱秤被点击了。。。。。。。。。。。。。。。。。。。。。。");
        SharedPreferenceService.getInstance().put(LAI_CHEN_SWITCH_KEY1, isChecked);
        if (isChecked) {
            retestPre.doGetMeasure("0Pmg0UmrnZBYbcPABC5YB0pSqNXOFnB885ZYInLptG8YvAZsT87oGUPZtU5wbAad-26xsvP8Ov_eoq6Mj9rISg-XZiz2xesbiiqYPWK0AeYquQ8fXwXNpmvL0XwbUkse", "18206182086");
        }
    }

    public void takecamera() {

        path=(Environment.getExternalStorageDirectory().getPath())+"/123.jpg";
        File file=new File(path.toString());
        Uri uri= Uri.fromFile(file);
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,PHOTO);
        Bitmap bitmap= null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        im_deletest.setVisibility(View.VISIBLE);
        im_retestwritest_showphoto.setVisibility(View.VISIBLE);
        im_retestwritest_showphoto.setImageBitmap(bitmap);
        Log.i("path:"+path);
    }
    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PHOTO) {
            Bitmap bm = BitmapFactory.decodeFile(path.toString());
            im_retestwritest_showphoto.setImageBitmap(bm);
            retestPre.goGetPicture(path.toString());
        }
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            im_deletest.setVisibility(View.VISIBLE);
            im_retestwritest_showphoto.setVisibility(View.VISIBLE);
            im_retestwritest_showphoto.setImageBitmap(bitmap);
            retestPre.goGetPicture(picturePath.toString());
            Log.i("picturePath------------------------------------------------:" + picturePath);
            c.close();
        }
        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            Log.i("》》》》》requestCode：" + requestCode + "resultCode：" + resultCode);
            retestWrite = (RetestWriteModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite" + retestWrite);
        }
    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker)view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(np1maxvalur);
        np1.setValue(np1value);
        np1.setMinValue(np1minvalue);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(np2maxvalue);
        np2.setValue(np2value);
        np2.setMinValue(np2minvalue);
        np2.setWrapSelectorWheel(false);
        information_dialog.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (num==0) {
                    tv_writes_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview

                }
                else if (num==1)
                {
                    tv_retestWrites_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==2)
                {
                    tv_retestWritest_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==3)
                {
                    tv_retestWritest_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

        
    }
}
