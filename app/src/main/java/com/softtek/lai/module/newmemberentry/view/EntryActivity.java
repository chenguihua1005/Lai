/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.newmemberentry.Adapter.MemberAdapter;
import com.softtek.lai.module.newmemberentry.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.model.NewstudentsModel;
import com.softtek.lai.module.newmemberentry.model.PargradeModel;
import com.softtek.lai.module.newmemberentry.model.PhotModel;
import com.softtek.lai.module.newmemberentry.presenter.GuwenClassImp;
import com.softtek.lai.module.newmemberentry.presenter.GuwenClassPre;
import com.softtek.lai.module.newmemberentry.presenter.INewStudentpresenter;
import com.softtek.lai.module.newmemberentry.presenter.NewStudentInputImpl;
import com.softtek.lai.widgets.WheelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_member_entry)
public class EntryActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private INewStudentpresenter iNewStudentpresenter;
    private GuwenClassPre guwenClassPre;
    private List<String> gradeList = new ArrayList<String>();
    private List<String> gradeIDList = new ArrayList<String>();
    private String select_grade = "";
    private String grade_id = "";
    //toolbar
    @InjectView(R.id.tv_title)
    TextView tv_title;
    //返回
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //确定
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.ll_birthday)
    LinearLayout ll_birthday;

    @InjectView(R.id.ll_gender)
    LinearLayout ll_gender;

    //参赛班级
    @InjectView(R.id.ll_classid)
    LinearLayout ll_classid;

    //表单验证
    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, message = "昵称必填项")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    @Required(order = 2, message = "手机号码必填项")
    @Regex(order = 3, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_mobile)
    EditText et_mobile;

    @Required(order = 4, message = "参赛班级必填项")
    @InjectView(R.id.et_classid)
    EditText et_classid;

    @Required(order = 5, message = "初始体重必填项")
    @InjectView(R.id.et_weight)
    EditText et_weight;

    //体脂
    @InjectView(R.id.et_pysical)
    EditText et_pysical;
    //内脂
    @InjectView(R.id.et_fat)
    EditText et_fat;

    @Required(order = 6, message = "生日必填项")
    @InjectView(R.id.et_birthday)
    EditText et_birthday;

    @Required(order = 7, message = "性别必填项")
    @InjectView(R.id.et_gender)
    EditText et_gender;

    //照片上传
    @InjectView(R.id.img_photoupload)
    ImageView img_photoupload;
    //照片删除
    @InjectView(R.id.img_delete)
    ImageView img_delete;

    @InjectView(R.id.img1)
    ImageView img1;

    //添加身体围度
    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    //照片上传
    String path = "";
    private static final int PHOTO = 1;

    NewstudentsModel newstudentsModel;//存储用户表单数据
    PhotModel imphot;
    private static final int GET_BODY = 2;

    @InjectView(R.id.list_cansaibanji)
    ListView list_cansaibanji;

    private List<PargradeModel> pargradeModelList = new ArrayList<PargradeModel>();

    //获取当前日期
    Calendar ca = Calendar.getInstance();
    int myear = ca.get(Calendar.YEAR);//获取年份
    int mmonth=ca.get(Calendar.MONTH);//获取月份
    int mday=ca.get(Calendar.DATE);//获取日

    UserInfoModel userInfoModel=UserInfoModel.getInstance();

    long sentaccid=Long.parseLong(userInfoModel.getUser().getUserid());
    long role=Long.parseLong(userInfoModel.getUser().getUserrole());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

        //返回按钮
        ll_left.setOnClickListener(this);
        //确定按钮
        tv_right.setOnClickListener(this);
        //照片上传
        img_photoupload.setOnClickListener(this);
        //照片删除
        img_delete.setOnClickListener(this);
        //添加身体围度
        btn_Add_bodydimension.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_classid.setOnClickListener(this);

        MemberAdapter memberAdapter = new MemberAdapter(EntryActivity.this, R.layout.member_item, pargradeModelList);

        list_cansaibanji.setAdapter(memberAdapter);
        list_cansaibanji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PargradeModel pargradeModel = pargradeModelList.get(position);
                et_classid.setText(pargradeModel.getClassId());
                et_classid.setError(null);
                list_cansaibanji.setVisibility(View.INVISIBLE);

            }
        });
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
        guwenClassPre = new GuwenClassImp();
        guwenClassPre.doGetGuwenClass(sentaccid);//36
        iNewStudentpresenter = new NewStudentInputImpl(this);
        newstudentsModel = new NewstudentsModel();
        tv_title.setText("新学员录入");
        tv_right.setText("确定");

        //添加性别
        addGrade();
    }

    private CharSequence[] items={"拍照","照片"};
    private static final int CAMERA_PREMISSION=100;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takecamera();

            } else {

            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //确定按钮
            case R.id.tv_right:
                validateLife.validate();
                break;
            case R.id.btn_Add_bodydimension:
                Intent intent1 = new Intent(EntryActivity.this, DimensioninputActivity.class);
                intent1.putExtra("newstudentsModel", newstudentsModel);
                startActivityForResult(intent1, GET_BODY);
                break;
            //弹出照片选择对话框
            case R.id.img_photoupload:
                AlertDialog.Builder builder=new AlertDialog.Builder(EntryActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            //拍照
                            //先验证手机是否有sdcard
                            String status = Environment.getExternalStorageState();
                            if (status.equals(Environment.MEDIA_MOUNTED)) {
                                try {
                                    if(ActivityCompat.checkSelfPermission(EntryActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                        //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                        if(ActivityCompat.shouldShowRequestPermissionRationale(EntryActivity.this,Manifest.permission.CAMERA)){
                                            //允许弹出提示
                                            ActivityCompat.requestPermissions(EntryActivity.this,
                                                    new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                        }else{
                                            //不允许弹出提示
                                            ActivityCompat.requestPermissions(EntryActivity.this,
                                                    new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                        }
                                    }else {
                                        takecamera();
                                    }
                                } catch (ActivityNotFoundException e) {
                                    Util.toastMsg("没有找到存储目录");
                                }
                            } else {
                                Util.toastMsg("没有存储卡");
                            }
                        }else if(which==1){
                            //照片
                            takepic();
                        }
                    }
                }).create().show();

                break;
            //删除照片功能
            case R.id.img_delete:
                img1.setVisibility(View.GONE);
                img_delete.setVisibility(View.GONE);
                break;
            case R.id.ll_birthday:
                show_birth_dialog();
                break;
            case R.id.ll_gender:
                showGradeDialog();
                break;
            case R.id.ll_classid:
                if (list_cansaibanji.getVisibility() == View.VISIBLE) {
                    list_cansaibanji.setVisibility(View.INVISIBLE);
                } else if (list_cansaibanji.getVisibility() == View.INVISIBLE) {
                    list_cansaibanji.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void takecamera() {
        path = (Environment.getExternalStorageDirectory().getPath()) + "/123.jpg";
        File file = new File(path.toString());
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PHOTO);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img_delete.setVisibility(View.VISIBLE);
        img1.setVisibility(View.VISIBLE);
        img1.setImageBitmap(bitmap);
    }

    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }

    @Subscribe
    public void onEvent(ClassEvent classEvent) {

        List<PargradeModel> pargradeModels = classEvent.getPargradeModels();
        for (PargradeModel cl : pargradeModels) {
            PargradeModel p1 = new PargradeModel(cl.getClassId(), cl.getClassName());
            pargradeModelList.add(p1);
        }
    }

    @Subscribe
    public void onEvent1(PhotModel photModel) {
        newstudentsModel.setPhoto(photModel.getImg());
    }

    @Override
    public void onValidationSucceeded() {
        String nickname = et_nickname.getText().toString();
        String mobile = et_mobile.getText().toString();
        String classid = et_classid.getText().toString();
        String weight = et_weight.getText().toString();
        String pysical = et_pysical.getText().toString();
        String fat = et_fat.getText().toString();
        String birthday = et_birthday.getText().toString();
        String gender = et_gender.getText().toString();
        String b = mobile.substring(mobile.length() - 6, mobile.length());
        newstudentsModel = new NewstudentsModel();
        newstudentsModel.setPassword(b);

        newstudentsModel.setSentaccid(sentaccid);//36
        newstudentsModel.setNickname(nickname);
        newstudentsModel.setMobile(mobile);
        newstudentsModel.setClassid(classid);
        newstudentsModel.setWeight(Double.parseDouble(weight.equals("") ? "0" : weight));
        newstudentsModel.setPysical(Double.parseDouble(pysical.equals("") ? "0" : pysical));
        newstudentsModel.setFat(Double.parseDouble(fat.equals("") ? "0" : fat));
        newstudentsModel.setBirthday(birthday);
        newstudentsModel.setGender(gender.equals("女") ? 0 : 1);
        newstudentsModel.setPhoto("2024938094839380");
        iNewStudentpresenter.input(newstudentsModel);
        finish();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    public Bitmap compressBitmap(Resources res, int resId, int targetWidth, int targetHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设为true，节约内存
        BitmapFactory.decodeResource(res, resId, options);//返回null
        int height = options.outHeight;//得到源图片height，单位px
        int width = options.outWidth;//得到源图片的width，单位px
        //计算inSampleSize
        options.inSampleSize = calculateInSampleSize(width, height, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;//设为false，可以返回Bitmap
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算压缩比例
     * @param width        源图片的宽
     * @param height       源图片的高
     * @param targetWidth  目标图片的宽
     * @param targetHeight 目标图片的高
     * @return inSampleSize 压缩比例
     */
    public int calculateInSampleSize(int width, int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            //计算图片实际的宽高和目标图片宽高的比率
            final int heightRate = Math.round((float) height / (float) targetHeight);
            final int widthRate = Math.round((float) width / (float) targetWidth);
            //选取最小的比率作为inSampleSize
            inSampleSize = heightRate < widthRate ? heightRate : widthRate;
        }
        return inSampleSize;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PHOTO) {
            Bitmap bm = BitmapFactory.decodeFile(path.toString());
            Util.toastMsg(bm+"");
            img1.setImageBitmap(bm);
            iNewStudentpresenter.upload(path.toString());
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
            img_delete.setVisibility(View.VISIBLE);
            img1.setVisibility(View.VISIBLE);
            img1.setImageBitmap(bitmap);
            iNewStudentpresenter.upload(picturePath.toString());
            c.close();
        }

        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            newstudentsModel = (NewstudentsModel) data.getSerializableExtra("newstudentsModel");
        }
    }

    public void show_birth_dialog() {
        final android.support.v7.app.AlertDialog.Builder birdialog=new android.support.v7.app.AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.birth_dialog,null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np1.setMaxValue(myear);
        np1.setValue(myear);
        np1.setMinValue(1900);
        np1.setWrapSelectorWheel(false);

        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np2.setMaxValue(12);
        np2.setValue(mmonth+1);
        np2.setMinValue(1);
        np2.setWrapSelectorWheel(false);

        final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
        np3.setMaxValue(31);
        np3.setValue(mday);
        np3.setMinValue(1);
        np3.setWrapSelectorWheel(false);

        birdialog.setTitle("选择生日(年-月-日)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (np1.getValue()==myear&&np2.getValue()>(mmonth+1)) {
                    show_warn_dialog();
                }
                if (np1.getValue() == myear && np2.getValue() == (mmonth+1) && np3.getValue() > mday) {
                    show_warn_dialog();
                } else {
                    et_birthday.setText(String.valueOf(np1.getValue()) + "-" + String.valueOf(np2.getValue()) + "-" + String.valueOf(np3.getValue()));
                    et_birthday.setError(null);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    //生日警告对话框
    public void show_warn_dialog() {
        Dialog dialog = new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage("生日不能大于当前日期,请重新选择")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                show_birth_dialog();
                            }
                        }).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void addGrade() {
        gradeList.add("男");
        gradeList.add("女");
        gradeIDList.add("0");
        gradeIDList.add("1");
    }

    public void showGradeDialog() {
        final android.support.v7.app.AlertDialog.Builder birdialog=new android.support.v7.app.AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog_select_grade,null);
        final WheelView wheel_grade = (WheelView) view.findViewById(R.id.wheel_grade);
        wheel_grade.setOffset(1);
        wheel_grade.setItems(gradeList);
        wheel_grade.setSeletion(0);
        select_grade = "";
        wheel_grade.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_grade = item;
                grade_id = gradeIDList.get(selectedIndex - 1);
            }
        });
        birdialog.setTitle("选择性别").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_grade)) {
                            select_grade = gradeList.get(0);
                            grade_id = gradeIDList.get(0);
                        }
                        et_gender.setText(select_grade);
                        et_gender.setError(null);
                        select_grade = "";
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create()
                .show();
    }

}
