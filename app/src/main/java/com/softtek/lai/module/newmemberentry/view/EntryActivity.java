/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.widget.*;
import butterknife.InjectView;
import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.newmemberentry.view.Adapter.MemberAdapter;
import com.softtek.lai.module.newmemberentry.view.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.view.model.NewstudentsModel;
import com.softtek.lai.module.newmemberentry.view.model.PargradeModel;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.newmemberentry.view.presenter.GuwenClassImp;
import com.softtek.lai.module.newmemberentry.view.presenter.GuwenClassPre;
import com.softtek.lai.module.newmemberentry.view.presenter.INewStudentpresenter;
import com.softtek.lai.module.newmemberentry.view.presenter.NewStudentInputImpl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@InjectLayout(R.layout.activity_member_entry)
public class EntryActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private String SexData[] = {"男", "女"};//性别数据

    private INewStudentpresenter iNewStudentpresenter;
    private GuwenClassPre guwenClassPre;

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

    //资格证号
    @InjectView(R.id.et_certification)
    EditText et_certification;

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
        guwenClassPre.doGetGuwenClass(36);
        iNewStudentpresenter = new NewStudentInputImpl(this);

        newstudentsModel = new NewstudentsModel();
        tv_title.setText("新学员录入");
        //tv_left.setBackground(null);
        tv_right.setText("确定");
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
            case R.id.img_photoupload:
//                final Dialog dialog=new AlertDialog.Builder(EntryActivity.this)
//                        .setTitle("照片上传")
//                        .setPositiveButton("从相机选择图片",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int which) {
//                                        takecamera();
//                                        dialogInterface.dismiss();
//
//                                    }
//                                })
//                        .setNegativeButton("从相册选择图片",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int arg1) {
//                                        takepic();
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                        .create();
//                dialog.show();

                final GetPhotoDialog dialog = new GetPhotoDialog(this,
                        new GetPhotoDialog.GetPhotoDialogListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
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
                //dialog.setCanceledOnTouchOutside(false);
                dialog.show();
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
                show_sex_dialog();
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
        Log.i("path:" + path);
    }

    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }

    @Subscribe
    public void onEvent(ClassEvent classEvent) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + classEvent.getPargradeModels());
//        String SexData[] = {"男","女"};//性别数据
        List<PargradeModel> pargradeModels = classEvent.getPargradeModels();
        for (PargradeModel cl : pargradeModels) {
            System.out.println("dsfsdfsdfsdfsdfsdf?????/?????>>》》》》》》》》》》》》》》" + "ClassIdModel:" + cl.getClassId() + "ClassName:" + cl.getClassName());
            PargradeModel p1 = new PargradeModel(cl.getClassId(), cl.getClassName());
            pargradeModelList.add(p1);
        }
    }

    @Subscribe
    public void onEvent1(PhotModel photModel) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + photModel.getImg());
        newstudentsModel.setPhoto(photModel.getImg());
    }

    @Override
    public void onValidationSucceeded() {
        String nickname = et_nickname.getText().toString();
        String certification = et_certification.getText().toString();
        String mobile = et_mobile.getText().toString();
        String classid = et_classid.getText().toString();
        String weight = et_weight.getText().toString();
        String pysical = et_pysical.getText().toString();
        String fat = et_fat.getText().toString();
        String birthday = et_birthday.getText().toString();
        String gender = et_gender.getText().toString();
        Log.i("新学员录入：" + "nickname:" + nickname + ";certification:" + certification + ";mobile:" + mobile + ";classid:" + classid + ";weight:" + weight + "pysical:" + pysical + "fat:" + fat + "birthday:" + birthday + "gender:" + gender);

        String b = mobile.substring(mobile.length() - 6, mobile.length());
        Log.i("获取新学员录入手机号码后6位：" + b);

        newstudentsModel.setPassword(b);
        newstudentsModel.setSentaccid(1);
        newstudentsModel.setNickname(nickname);
        newstudentsModel.setCertification(certification);
        newstudentsModel.setMobile(mobile);
        newstudentsModel.setClassid(Integer.parseInt(classid));
        newstudentsModel.setWeight(Double.parseDouble(weight.equals("") ? "0" : weight));
        newstudentsModel.setPysical(Double.parseDouble(pysical.equals("") ? "0" : pysical));
        newstudentsModel.setFat(Double.parseDouble(fat.equals("") ? "0" : fat));
        newstudentsModel.setBirthday(birthday);
        newstudentsModel.setGender(gender.equals("女") ? 0 : 1);
//        newstudentsModel.setPhoto(img.getPhoto()+"");
        iNewStudentpresenter.input(newstudentsModel);
        //newstudentsModel.setPhoto("/storage/emulated/0/123.jpg");
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PHOTO) {
            Bitmap bm = BitmapFactory.decodeFile(path.toString());
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
            Log.i("picturePath------------------------------------------------:" + picturePath);
            c.close();
        }

        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            newstudentsModel = (NewstudentsModel) data.getSerializableExtra("newstudentsModel");
            Log.i("新学员录入围度:newstudentsModel" + newstudentsModel);
        }
    }

    //生日对话框
    public void show_birth_dialog() {
        DatePickerDialog dialog = new DatePickerDialog(
                EntryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, 2016, 8, 17);
        dialog.setTitle("");
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.show();
    }

    //性别对话框
    public void show_sex_dialog() {
        Dialog genderdialog = new AlertDialog.Builder(EntryActivity.this)
                .setTitle("请选择您的性别")
                .setSingleChoiceItems(SexData, 2,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et_gender.setText(SexData[which]);
                            }
                        })
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", null)
                .create();
        genderdialog.setCanceledOnTouchOutside(false);  // 设置点击屏幕Dialog不消失
        genderdialog.show();
    }
}
