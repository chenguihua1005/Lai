package com.softtek.lai.module.retest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.newmemberentry.view.GetPhotoDialog;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.module.retest.view.BodyweiduActivity;
import com.squareup.picasso.Picasso;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_write)
public class WriteActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.im_retestwrite_takephoto)
    ImageView im_retestwrite_takephoto;
    //显示照片
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    //删除照片
    @InjectView(R.id.im_delete)
    ImageView im_delete;
    //信息点击弹框
    //初始体重
    @InjectView(R.id.ll_retestWrite_chu_weight)
    LinearLayout ll_retestWrite_chu_weight;
    //现在体重
    @InjectView(R.id.ll_retestWrite_nowweight)
    LinearLayout ll_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.ll_retestWrite_tizhi)
    LinearLayout ll_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.ll_retestWrite_neizhi)
    LinearLayout ll_retestWrite_neizhi;

    //信息保存
    @InjectView(R.id.tv_write_chu_weight)
    TextView tv_write_chu_weight;
    //现在体重
    @InjectView(R.id.tv_retestWrite_nowweight)
    TextView tv_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;

    //昵称
    @InjectView(R.id.tv_write_nick)
    TextView tv_write_nick;
    //手机号
    @InjectView(R.id.tv_write_phone)
    TextView tv_write_phone;
    //头像
    @InjectView(R.id.iv_write_head)
    ImageView iv_write_head;
    //班级名称
    @InjectView(R.id.tv_write_class)
    TextView tv_write_class;
    //第几周期
    @InjectView(R.id.tv_retest_write_weekth)
    TextView tv_retest_write_weekth;
    //开始月份
    @InjectView(R.id.tv_write_starm)
    TextView tv_write_starm;
    //开始日期
    @InjectView(R.id.tv_write_stard)
    TextView tv_write_stard;
    //结束月份
    @InjectView(R.id.tv_write_endm)
    TextView tv_write_endm;
    //结束日期
    @InjectView(R.id.tv_write_endd)
    TextView tv_write_endd;
    //莱秤开关
    @InjectView(R.id.selectlaichen)
    CheckBox selectlaichen;

    private RetestPre retestPre;
    RetestWriteModel retestWrite;
    MeasureModel measureModel;
    String path="";
    private static final int PHOTO=1;
    private static final int GET_BODY=2;
    private static final String LAI_CHEN_SWITCH_KEY="laichenSwitch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        ll_retestWrite_nowweight.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_delete.setOnClickListener(this);
        //selectlaichen.setOnClickListener(this);
        selectlaichen.setOnCheckedChangeListener(this);
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
        title.setText("复测录入");
        tv_right.setText("保存");
        iv_email.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        String accountId=intent.getStringExtra("accountId");
        String loginId=intent.getStringExtra("loginId");
        String classId=intent.getStringExtra("classId");
        String typedate=intent.getStringExtra("typeDate");
        //开班时间，判断班级名称（几月班）
        String StartDate=intent.getStringExtra("StartDate");
        //开始周期
        String CurrStart=intent.getStringExtra("CurrStart");
        //结束周期
        String CurrEnd=intent.getStringExtra("CurrEnd");
        //昵称
        String UserName=intent.getStringExtra("UserName");
        //手机号
        String Mobile=intent.getStringExtra("Mobile");
        //头像
        String Photo=intent.getStringExtra("Photo");
        //第几周期
        String Weekth=intent.getStringExtra("Weekth");
        Log.i("dijizhouqi"+Weekth);
        //头部基本信息
        tv_write_nick.setText(UserName);
        tv_write_phone.setText(Mobile);
        tv_retest_write_weekth.setText(Weekth);
        String[] mon=StartDate.split("-");
        String[] currStart=CurrStart.split("-");
        String[] currEnd=CurrEnd.split("-");
        tv_write_class.setText(tomonth(mon[1]));
        tv_write_starm.setText(currStart[1]);
        tv_write_stard.setText(currStart[2]);
        tv_write_endm.setText(currEnd[1]);
        tv_write_endd.setText(currEnd[2]);
       // Picasso.with(this).load(Photo).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_write_head);
        Log.i("chuanzhizhizhizhizhi",accountId+loginId+classId);
        retestPre=new RetestclassImp();
        retestWrite=new RetestWriteModel();
//        retestWrite.setAccountId(accountId);
        retestPre.doGetAudit(Integer.parseInt(accountId),Integer.parseInt(classId),typedate);
        boolean laichenSwitch= SharedPreferenceService.getInstance().get(LAI_CHEN_SWITCH_KEY,false);
        selectlaichen.setChecked(laichenSwitch);
        if(selectlaichen.isChecked()){
            Log.i("上一次莱秤被打开");
            retestPre.doGetMeasure("0Pmg0UmrnZBYbcPABC5YB0pSqNXOFnB885ZYInLptG8YvAZsT87oGUPZtU5wbAad-26xsvP8Ov_eoq6Mj9rISg-XZiz2xesbiiqYPWK0AeYquQ8fXwXNpmvL0XwbUkse","18206182086");
        }



    }


    @Subscribe
    public void event(MeasureModel measureModel1){
        measureModel=measureModel1;
        Log.i("username"+measureModel.getUsername());
        tv_write_nick.setText(measureModel.getUsername());
        tv_write_phone.setText(measureModel.getPhone());
        tv_retestWrite_nowweight.setText(measureModel.getMeasureddata().getItems().get(0).getWeight());
        tv_retestWrite_tizhi.setText(measureModel.getMeasureddata().getItems().get(0).getBodyfat());
        tv_retestWrite_neizhi.setText(measureModel.getMeasureddata().getItems().get(0).getVisceralfatindex());
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(RetestAuditModelEvent event){
//        //......
////        RetestAuditModel model=event.getRetestAuditModels().get(0);
//        tv_write_nick.setText(event.getRetestAuditModels().get(0).getUserName());
//        tv_write_phone.setText(event.getRetestAuditModels().get(0).getMobile());
//
//
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //删除照片
            case R.id.im_delete:
                im_retestwrite_showphoto.setVisibility(View.GONE);
                im_delete.setVisibility(View.GONE);
                break;
            //标题栏左返回
            case R.id.ll_left:
                finish();

            break;
            //标题栏右提交保存事件
            case R.id.tv_right:
                retestWrite.setWeight(tv_retestWrite_nowweight.getText()+"");
                retestWrite.setPysical(tv_retestWrite_tizhi.getText()+"");
                retestWrite.setFat(tv_retestWrite_neizhi.getText()+"");
                retestWrite.setClassId("4");
                retestWrite.setImage("");
                retestWrite.setAccountId("3");
                retestPre.doPostWrite(3,36,retestWrite);
                break;
            //拍照事件
            case R.id.im_retestwrite_takephoto:
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
            //添加身体围度
            case R.id.btn_retest_write_addbody:
                Intent intent=new Intent(WriteActivity.this, BodyweiduActivity.class);
//                intent.putExtra("retestWrite",retestWrite);
                Log.i("measureModel="+measureModel.toString());
                intent.putExtra("measureModel",measureModel);
                startActivityForResult(intent,GET_BODY);
                break;
            //点击弹框事件
            case R.id.ll_retestWrite_chu_weight:
                show_information("初始体重（kg）",200,70,20,9,5,0,0);
                break;
            case R.id.ll_retestWrite_nowweight:
                show_information("现在体重（kg）",200,70,20,9,5,0,1);
                break;
            case R.id.ll_retestWrite_tizhi:
                show_information("体脂（%）",100,50,0,9,5,0,2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂（%）",100,50,0,9,5,0,3);
                break;


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
        im_delete.setVisibility(View.VISIBLE);
        im_retestwrite_showphoto.setVisibility(View.VISIBLE);
        im_retestwrite_showphoto.setImageBitmap(bitmap);
        Log.i("path:"+path);
    }
    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==PHOTO){
            Bitmap bm= BitmapFactory.decodeFile(path.toString());
            im_retestwrite_showphoto.setImageBitmap(bm);
            retestPre.goGetPicture(path.toString());
        }
        if(requestCode == 101 && resultCode == Activity.RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumns={MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath= c.getString(columnIndex);
            Bitmap bitmap= null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            im_delete.setVisibility(View.VISIBLE);
            im_retestwrite_showphoto.setVisibility(View.VISIBLE);
            im_retestwrite_showphoto.setImageBitmap(bitmap);
            retestPre.goGetPicture(picturePath.toString());
            Log.i("picturePath------------------------------------------------:"+picturePath);
            c.close();
        }

        //身体围度值传递
        if (requestCode==GET_BODY&&resultCode==RESULT_OK){
            Log.i("》》》》》requestCode："+requestCode+"resultCode："+resultCode);
            retestWrite=(RetestWriteModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite"+retestWrite);
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
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview

                }
                else if (num==1)
                {
                    tv_retestWrite_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==2)
                {
                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==3)
                {
                    tv_retestWrite_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

//        information_dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //莱秤
        Log.i("莱秤被点击了。。。。。。。。。。。。。。。。。。。。。。");
        SharedPreferenceService.getInstance().put(LAI_CHEN_SWITCH_KEY,isChecked);
        if(isChecked){
            retestPre.doGetMeasure("0Pmg0UmrnZBYbcPABC5YB0pSqNXOFnB885ZYInLptG8YvAZsT87oGUPZtU5wbAad-26xsvP8Ov_eoq6Mj9rISg-XZiz2xesbiiqYPWK0AeYquQ8fXwXNpmvL0XwbUkse","18206182086");
        }


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
}
