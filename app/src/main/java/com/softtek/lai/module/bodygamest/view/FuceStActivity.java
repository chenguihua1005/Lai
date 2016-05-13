/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.view.EditPersonalDynamicActivity;
import com.softtek.lai.module.home.view.ModifyPhotoActivity;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.module.lossweightstory.view.PictureActivity;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.sw926.imagefileselector.ImageFileSelector;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_fuce_st)
public class FuceStActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener
,ImageFileSelector.Callback{
    @LifeCircleInject
    ValidateLife validateLife;

    //toolbar
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_writest_head)
    CircleImageView iv_writest_head;
    @InjectView(R.id.tv_writest_nick)
    TextView tv_writest_nick;
    @InjectView(R.id.tv_writest_phone)
    TextView tv_writest_phone;
    @InjectView(R.id.tv_writest_class)
    TextView tv_writest_class;
    @InjectView(R.id.tv_writest_classweek)
    TextView tv_writest_classweek;
    @InjectView(R.id.tv_writest_monst)
    TextView tv_writest_monst;
    @InjectView(R.id.tv_writest_dayst)
    TextView tv_writest_dayst;
    @InjectView(R.id.tv_writest_monen)
    TextView tv_writest_monen;
    @InjectView(R.id.tv_writest_dayen)
    TextView tv_writest_dayen;
    @InjectView(R.id.tv_right)
            TextView tv_right;
    private ProgressDialog progressDialog;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());
    String moblie=userInfoModel.getUser().getMobile();

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
    //初始体重
    @Required(order = 1,message = "初始体重必填项，请选择")
    @InjectView(R.id.tv_writes_chu_weight)
    EditText tv_writes_chu_weight;
    //现在体重
    @Required(order = 2,message = "现在体重必填项，请选择")
    @InjectView(R.id.tv_retestWrites_nowweight)
    EditText tv_retestWrites_nowweight;
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
    String gender="1";
    private static final int PHOTO=1;
    private static final int GET_BODY=2;
    private static final int BODY=3;

    private RetestPre retestPre;
    RetestWriteModel retestWrite;
    MeasureModel measureModel;
    RetestAuditModel retestAuditModel;
    String Mobile;
    String isState="true";
    private ImageFileCropSelector imageFileCropSelector;
    private ImageFileSelector imageFileSelector;
    private CharSequence[] items={"拍照","从相册选择照片"};

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void event(LaichModel laichModel){

        tv_retestWrites_nowweight.setText(StringUtils.isEmpty(laichModel.getWeight())?"":Float.parseFloat(laichModel.getWeight())+"");
        tv_retestWritest_tizhi.setText(StringUtils.isEmpty(laichModel.getPysical())?"":Float.parseFloat(laichModel.getPysical())+"");
        tv_retestWritest_neizhi.setText(StringUtils.isEmpty(laichModel.getFat())?"":Float.parseFloat(laichModel.getFat())+"");
        retestWrite.setCircum(StringUtils.isEmpty(laichModel.getCircum())?"":Float.parseFloat(laichModel.getCircum())+"");
        retestWrite.setWaistline(StringUtils.isEmpty(laichModel.getWaistline())?"":Float.parseFloat(laichModel.getWaistline())+"");
        retestWrite.setHiplie(StringUtils.isEmpty(laichModel.getHiplie())?"":Float.parseFloat(laichModel.getHiplie())+"");
        retestWrite.setUpArmGirth(StringUtils.isEmpty(laichModel.getUpArmGirth())?"":Float.parseFloat(laichModel.getUpArmGirth())+"");
        retestWrite.setUpLegGirth(StringUtils.isEmpty(laichModel.getUpLegGirth())?"":Float.parseFloat(laichModel.getUpLegGirth())+"");
        retestWrite.setDoLegGirth(StringUtils.isEmpty(laichModel.getDoLegGirth())?"":Float.parseFloat(laichModel.getDoLegGirth())+"");
    }
    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        progressDialog = new ProgressDialog(this);
        btn_retest_write_addbodyst.setOnClickListener(this);
        ll_fucest_nowweight.setOnClickListener(this);
        ll_fucest_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_retestwritest_takephoto.setOnClickListener(this);
        im_deletest.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        im_retestwritest_showphoto.setOnClickListener(this);


    }
    //2016-03-28
    @Override
    protected void initDatas() {
        tv_title.setText("复测");
        retestPre=new RetestclassImp();
        retestPre.doGetAudit(loginid,0,"");
        retestWrite=new RetestWriteModel();
        retestAuditModel=new RetestAuditModel();
        measureModel=new MeasureModel();
        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(DisplayUtil.dip2px(this,600),
                DisplayUtil.dip2px(this,300));
        imageFileSelector.setQuality(80);
        imageFileSelector.setCallback(this);
        tv_writes_chu_weight.setFocusable(false);
        tv_retestWrites_nowweight.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //删除照片
            case R.id.im_deletest:
                retestWrite.setImage("");
                im_deletest.setVisibility(View.GONE);
                im_retestwritest_showphoto.setVisibility(View.GONE);
                im_retestwritest_showphoto.setImageBitmap(null);
                break;
            case R.id.btn_retest_write_addbodyst:
                Intent intent=new Intent(this, BodyweidustActivity.class);
                Log.i("retestWrite="+retestWrite.toString());
                intent.putExtra("retestWrite",retestWrite);
                intent.putExtra("isState",isState);
                startActivityForResult(intent,GET_BODY);
                break;
            case R.id.ll_fucest_nowweight:
                if (isState.equals("true")) {
                    if (gender.equals("1")) {
                        show_information("现在体重（斤）", 600, 100, 50, 9, 0, 0, 1);
                    }
                    else
                    {
                        show_information("现在体重（斤）", 600, 150, 50, 9, 0, 0, 1);
                    }
                }

                break;
            case R.id.ll_fucest_tizhi:
                if (isState.equals("true")) {
                    show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                }
                break;
            case R.id.ll_retestWrite_neizhi:
                if (isState.equals("true")) {
                    show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                }
                break;
            //拍照事件
            case R.id.im_retestwritest_takephoto:

                if (isState.equals("true")) {
                    SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
                    boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (isFirstRun)
                    {
                        Intent intent1=new Intent(this,GuideActivity.class);
                        startActivityForResult(intent1,BODY);

                        Log.d("debug", "第一次运行");
                        editor.putBoolean("isFirstRun", false);
                        editor.commit();
                    } else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
//                                takecamera();
                                    imageFileSelector.takePhoto(FuceStActivity.this);
                                } else if (which == 1) {
                                    //照片
//                                takepic();
                                    imageFileSelector.selectImage(FuceStActivity.this);
                                }
                            }
                        }).create().show();
                        Log.d("debug", "不是第一次运行");
                    }

                }

                break;
            case R.id.tv_right:
                if (isState.equals("true")) {
                    validateLife.validate();
                }
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.im_retestwritest_showphoto:
                Intent intent1=new Intent(this,PictureActivity.class);
                ArrayList<String> imags=new ArrayList<>();
                imags.add(retestWrite.getImage());
                intent1.putExtra("images",imags);
                intent1.putExtra("position",0);
                startActivity(intent1);
                break;
        }
    }
    @Subscribe
    public void doGetDates(RetestAuditModelEvent retestAuditModelEvent) throws Exception {
        Log.i("retestAuditModel"+retestAuditModelEvent.getRetestAuditModels());
        tv_writes_chu_weight.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getInitWeight().equals("")?"":Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getInitWeight())+"");
        tv_writest_nick.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getUserName());
        Mobile=retestAuditModelEvent.getRetestAuditModels().get(0).getMobile();
        tv_writest_phone.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getMobile());
        gender=retestAuditModelEvent.getRetestAuditModels().get(0).getGender();
        String StartDate=retestAuditModelEvent.getRetestAuditModels().get(0).getStartDate();
        String CurrStart=retestAuditModelEvent.getRetestAuditModels().get(0).getCurrStart();
        String CurrEnd=retestAuditModelEvent.getRetestAuditModels().get(0).getCurrEnd();
        String[] mon=StartDate.split("-");
        String[] currStart=CurrStart.split("-");
        String[] currEnd=CurrEnd.split("-");
        retestWrite.setClassId(retestAuditModelEvent.getRetestAuditModels().get(0).getClassId());
        tv_writest_class.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getClassName());
        tv_writest_monst.setText(currStart[1]);
        tv_writest_dayst.setText(currStart[2]);
        tv_writest_monen.setText(currEnd[1]);
        tv_writest_dayen.setText(currEnd[2]);
        tv_writest_classweek.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getWeekth());
        if(!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getPhoto())) {
            Picasso.with(this).load(retestAuditModelEvent.getRetestAuditModels().get(0).getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(iv_writest_head);
        }
        else {
            Picasso.with(this).load("www").placeholder(R.drawable.img_default).error(R.drawable.img_default).fit().into(iv_writest_head);
        }
        if (!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getTypeDate())) {
            if (!(ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getTypeDate()).getTime() >= ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getCurrStart()).getTime() &&
                    ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getTypeDate()).getTime() <= ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getCurrEnd()).getTime())) {
                retestPre.GetUserMeasuredInfo(moblie);
                tv_right.setText("保存");
            } else {
                if (retestAuditModelEvent.getRetestAuditModels().get(0).getAMStatus().equals("1") || retestAuditModelEvent.getRetestAuditModels().get(0).getAMStatus().equals("2")) {
                    tv_retestWrites_nowweight.setText(Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getWeight()) + "");
                    tv_retestWritest_tizhi.setText((StringUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getPysical()))?"":Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getPysical())+"");
                    tv_retestWritest_neizhi.setText((StringUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getFat()))?"":Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getFat())+"");
                    retestWrite.setCircum(retestAuditModelEvent.getRetestAuditModels().get(0).getCircum());
                    retestWrite.setWaistline(retestAuditModelEvent.getRetestAuditModels().get(0).getWaistline());
                    retestWrite.setHiplie(retestAuditModelEvent.getRetestAuditModels().get(0).getHiplie());
                    retestWrite.setUpArmGirth(retestAuditModelEvent.getRetestAuditModels().get(0).getUpArmGirth());
                    retestWrite.setUpLegGirth(retestAuditModelEvent.getRetestAuditModels().get(0).getUpLegGirth());
                    retestWrite.setDoLegGirth(retestAuditModelEvent.getRetestAuditModels().get(0).getDoLegGirth());
                    isState = "false";
                    btn_retest_write_addbodyst.setText("查看身体围度");
                    tv_right.setFocusable(false);

                    if (!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getImage())) {
                        im_retestwritest_showphoto.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(retestAuditModelEvent.getRetestAuditModels().get(0).getImage()).placeholder(R.drawable.default_pic).fit().error(R.drawable.default_pic).into(im_retestwritest_showphoto);
                        String[] images=retestAuditModelEvent.getRetestAuditModels().get(0).getImage().split("/");
                        retestWrite.setImage(images[images.length-1]);
                    } else {
                        im_retestwritest_showphoto.setVisibility(View.GONE);
                        Picasso.with(this).load("www").placeholder(R.drawable.default_pic).fit().error(R.drawable.default_pic).into(im_retestwritest_showphoto);
                    }
                } else {
                    retestPre.GetUserMeasuredInfo(moblie);
                    tv_right.setText("保存");

                }
            }
        }
        else {
            retestPre.GetUserMeasuredInfo(moblie);
            tv_right.setText("保存");
        }
    }
    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }
    @Subscribe
    public void doGetPhotost(PhotModel photModel) {
        System.out.println("照片名称" + photModel.getImg());
        retestWrite.setImage(photModel.getImg()+"");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);
        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            retestWrite = (RetestWriteModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite" + retestWrite);
        }
        if (requestCode == BODY && resultCode == RESULT_OK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
//                                takecamera();
                        imageFileSelector.takePhoto(FuceStActivity.this);
                    } else if (which == 1) {
                        //照片
//                                takepic();
                        imageFileSelector.selectImage(FuceStActivity.this);
                    }
                }
            }).create().show();
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
                    tv_writes_chu_weight.setError(null);
                }
                else if (num==1)
                {
                    tv_retestWrites_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    tv_retestWrites_nowweight.setError(null);
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
    public String tomonth(String month){
        if (month.equals("01")){
            month="一月班";
        }
        else if (month.equals("02")){
            month="二月班";
        }else if (month.equals("03"))
        {
            month="三月班";
        }else if (month.equals("04"))
        {
            month="四月班";

        }else if (month.equals("05"))
        {
            month="五月班";
        }else if (month.equals("06"))
        {
            month="六月班";
        }else if (month.equals("07"))
        {
            month="七月班";
        } else if (month.equals("08"))
        {
            month="八月班";
        }else if (month.equals("09"))
        {
            month="九月班";
        }else if (month.equals("10"))
        {
            month="十月班";
        }else if (month.equals("11"))
        {
            month="十一月班";
        }else
        {
            month="十二月班";
        }
        return month;
    }

    @Override
    public void onValidationSucceeded() {
        tv_writes_chu_weight.setFocusable(false);
        tv_retestWrites_nowweight.setFocusable(false);
        retestWrite.setWeight(tv_retestWrites_nowweight.getText()+"");
        retestWrite.setInitWeight(tv_writes_chu_weight.getText()+"");
        retestWrite.setPysical(tv_retestWritest_tizhi.getText()+"");
        retestWrite.setFat(tv_retestWritest_neizhi.getText()+"");
        retestWrite.setAccountId(loginid+"");
        Log.i(retestWrite.getImage()+"");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在保存...");
        progressDialog.show();
        retestPre.doPostWrite(loginid,loginid,retestWrite,this,progressDialog);



    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    @Override
    public void onSuccess(String file) {
        Log.i("摘牌回来了");
        im_retestwritest_showphoto.setVisibility(View.VISIBLE);
        im_deletest.setVisibility(View.VISIBLE);
        Picasso.with(this).load(new File(file)).fit().into(im_retestwritest_showphoto);
        //im_retestwritest_showphoto.setImageBitmap(BitmapFactory.decodeFile(file));
        retestPre.goGetPicture(file);
    }

    @Override
    public void onError() {

    }
//    public void takecamera() {
//
//        path=(Environment.getExternalStorageDirectory().getPath())+"/123.jpg";
//        File file=new File(path.toString());
//        Uri uri= Uri.fromFile(file);
//        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(intent,PHOTO);
//        Bitmap bitmap= null;
//        try {
//            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        im_delete.setVisibility(View.VISIBLE);
//        im_retestwrite_showphoto.setVisibility(View.VISIBLE);
//        im_retestwrite_showphoto.setImageBitmap(bitmap);
//        Log.i("path:"+path);
//    }
//    public void takepic() {
//        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(picture, 101);
//    }
}
