/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.view;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.confirmInfo.EventModel.ConinfoEvent;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.confirmInfo.presenter.IUpConfirmInfopresenter;
import com.softtek.lai.module.confirmInfo.presenter.UpConfirmInfoImpl;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.module.message.model.CheckMobileEvent;
import com.softtek.lai.module.message.model.PhotosModel;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.newmemberentry.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.model.NewstudentsModel;
import com.softtek.lai.module.newmemberentry.model.PargradeModel;
import com.softtek.lai.module.newmemberentry.presenter.GuwenClassImp;
import com.softtek.lai.module.newmemberentry.presenter.GuwenClassPre;
import com.softtek.lai.module.newmemberentry.presenter.INewStudentpresenter;
import com.softtek.lai.module.newmemberentry.presenter.NewStudentInputImpl;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.WheelView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 新學員錄入
 */
@InjectLayout(R.layout.activity_joingame_detail)
public class JoinGameDetailActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @Required(order = 1, message = "请输入姓名")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    @Required(order = 2, message = "请输入手机号码")
    @Regex(order = 3, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Required(order = 4, message = "请选择参赛班级")
    @InjectView(R.id.tv_class)
    EditText tv_class;

    @InjectView(R.id.ll_class)
    FrameLayout ll_class;
    @Required(order = 5, message = "请选择初始体重")
    @InjectView(R.id.tv_weight)
    EditText tv_weight;

    @InjectView(R.id.ll_weight)
    FrameLayout ll_weight;

    @InjectView(R.id.tv_tizhi)
    EditText tv_tizhi;

    @InjectView(R.id.ll_tizhi)
    FrameLayout ll_tizhi;

    @InjectView(R.id.tv_neizhi)
    EditText tv_neizhi;

    @InjectView(R.id.ll_neizhi)
    FrameLayout ll_neizhi;
    @Required(order = 6, message = "请选择生日")
    @InjectView(R.id.tv_birthday)
    EditText tv_birthday;

    @InjectView(R.id.ll_birthday)
    FrameLayout ll_birthday;

    @Required(order = 7, message = "请选择性别")
    @InjectView(R.id.tv_sex)
    EditText tv_sex;

    @InjectView(R.id.ll_sex)
    FrameLayout ll_sex;

    @InjectView(R.id.img_photoupload)
    ImageView img_photoupload;

    @InjectView(R.id.img1)
    ImageView img1;

    @InjectView(R.id.img_delete)
    ImageView img_delete;

    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;


    private String type;
    private List<String> gradeList = new ArrayList<>();
    private INewStudentpresenter iNewStudentpresenter;

    boolean isR = false;//是否注册
    boolean isOperation = false;
    int current_operation;

    private ILoginPresenter loginPresenter;

    //获取当前日期
    int myear;//获取年份
    int mmonth;//获取月份
    int mday;//获取日

    private IUpConfirmInfopresenter iUpConfirmInfopresenter;
    String classid;
    long accoutid;
    private GetConfirmInfoModel getConfirmInfoModel;
    private ConinfoModel coninfoModel;
    private CharSequence[] items = {"拍照", "照片"};

    private String change_photo = "";
    private String upload_photo = "";
    private String photo_backup = "";
    private GuwenClassPre guwenClassPre;
    private IMessagePresenter messagePresenter;
    private List<String> pargradeIdlList = new ArrayList<>();
    private List<String> pargradeNamelList = new ArrayList<>();

    private int select_posion = 0;

    private UserModel model;

    private ImageFileCropSelector imageFileCropSelector;
    private ProgressDialog progressDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(PhotosModel photModel) {
        change_photo = photModel.getImg();
        upload();

    }

    @Subscribe
    public void onEvent(ConinfoEvent coninfoEvent) {
        getConfirmInfoModel = coninfoEvent.getConfirmInfoModel();

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        photo_backup=upload_photo=change_photo = getConfirmInfoModel.getPhoto();
        if (!TextUtils.isEmpty(getConfirmInfoModel.getPhoto())) {
            Picasso.with(this).load(path + getConfirmInfoModel.getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img1);
        } else {
            img1.setImageResource(android.R.color.transparent);
        }
        if (TextUtils.isEmpty(change_photo)) {
            img1.setVisibility(View.GONE);
            img_delete.setVisibility(View.GONE);
        } else {
            img1.setVisibility(View.VISIBLE);
            img_delete.setVisibility(View.VISIBLE);
        }
        Log.i("获取照片地址：》》》》》》" + path + getConfirmInfoModel.getPhoto());
        et_nickname.setText(getConfirmInfoModel.getUserName());
        tv_class.setText(getConfirmInfoModel.getClassName());
        et_phone.setText(getConfirmInfoModel.getMobile());
        String weights = StringUtil.getValue(getConfirmInfoModel.getWeight());
        if ("".equals(weights)) {
            tv_weight.setText("");
        } else {
            tv_weight.setText(weights + "斤");
        }
        String pysicals = StringUtil.getValue(getConfirmInfoModel.getPysical());
        if ("".equals(pysicals)) {
            tv_tizhi.setText("");
        } else {
            tv_tizhi.setText(pysicals + "%");
        }

        tv_neizhi.setText(StringUtil.getValue(getConfirmInfoModel.getFat()));
        tv_birthday.setText(getConfirmInfoModel.getBirthday());
        tv_sex.setText(getConfirmInfoModel.getGender().equals("0") ? "男" : "女");
    }


    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        fl_right.setOnClickListener(this);
        ll_class.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
        ll_tizhi.setOnClickListener(this);
        ll_neizhi.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
        img_photoupload.setOnClickListener(this);
        img_delete.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
        //获取当前日期
        Calendar ca = Calendar.getInstance();
        myear = ca.get(Calendar.YEAR);//获取年份
        mmonth = ca.get(Calendar.MONTH);//获取月份
        mday = ca.get(Calendar.DATE);//获取日

        int px = DisplayUtil.dip2px(this, 300);
        imageFileCropSelector = new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutImageSize(px, px);
        imageFileCropSelector.setQuality(50);
        imageFileCropSelector.setOutPutAspect(1, 1);
        imageFileCropSelector.setOutPut(px, px);
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                upload_photo = file;
                img1.setVisibility(View.VISIBLE);
                img_delete.setVisibility(View.VISIBLE);
                File files = new File(upload_photo);
                Picasso.with(JoinGameDetailActivity.this).load(files).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img1);
            }

            @Override
            public void onError() {

            }
        });

    }

    //性别对话框
    private void addGrade() {
        gradeList.add("女");
        gradeList.add("男");
    }

    @Override
    protected void initDatas() {
        iNewStudentpresenter = new NewStudentInputImpl(JoinGameDetailActivity.this);
        iUpConfirmInfopresenter = new UpConfirmInfoImpl(JoinGameDetailActivity.this);
        loginPresenter = new LoginPresenterImpl(this);
        messagePresenter = new MessageImpl(JoinGameDetailActivity.this);
        guwenClassPre = new GuwenClassImp();
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accoutid = Long.parseLong(userInfoModel.getUser().getUserid());
        model=userInfoModel.getUser();
        addGrade();
        type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            tv_right.setText("完成");
            classid = getIntent().getStringExtra("classId");
            dialogShow("加载中");
            iUpConfirmInfopresenter.getConfirmInfo(accoutid, Long.parseLong(classid));
            tv_title.setText("报名参赛");
            et_phone.setEnabled(false);
            ll_class.setEnabled(false);
            ll_left.setVisibility(View.INVISIBLE);
        } else {
            tv_right.setText("保存");
            tv_title.setText("新学员录入");
            getConfirmInfoModel = new GetConfirmInfoModel();
            guwenClassPre.doGetGuwenClass(accoutid);//36
            ll_left.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);
            img_delete.setVisibility(View.GONE);
            ll_left.setOnClickListener(this);
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("正在检测手机号是否已注册");
            et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String phone = et_phone.getText().toString();
                        if (phone.length() == 11) {
                            progressDialog.show();
                            isOperation = true;
                            messagePresenter.phoneIsExist(phone, progressDialog, 0);
                        }
                    }
                }
            });
        }
    }
    private void rigstHX(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    // 调用sdk注册方法
                    String phone=model.getMobile();
                    final String account=MD5.md5WithEncoder(phone).toLowerCase();
                    EMChatManager.getInstance().createAccountOnServer(account, "HBL_SOFTTEK#321");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            loginPresenter.updateHXState(model.getMobile(),account,"1",null,null,"isInBack");
                        }
                    });
                } catch (final EaseMobException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode=e.getErrorCode();
                            if (errorCode == EMError.USER_ALREADY_EXISTS) {
                                String phone=model.getMobile();
                                final String account=MD5.md5WithEncoder(phone).toLowerCase();
                                loginPresenter.updateHXState(model.getMobile(),account,"1",null,null,"isInBack");
                            }else {
                                loginPresenter.updateHXState(model.getMobile(),"","0",null,null,"isInBack");
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Subscribe
    public void onEvent(Integer a) {
        rigstHX();
        Intent intent = new Intent(JoinGameDetailActivity.this, HomeActviity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
    @Subscribe
    public void onEvent(ClassEvent classEvent) {
        List<PargradeModel> pargradeModels = classEvent.getPargradeModels();
        for (PargradeModel cl : pargradeModels) {
            pargradeIdlList.add(cl.getClassId());
            pargradeNamelList.add(cl.getClassName());
        }
    }

    @Subscribe
    public void onEvent(CheckMobileEvent event) {
        isOperation = false;
        boolean b = event.isB();
        isR = b;
        if (b) {
            return;
        } else {
            switch (current_operation) {
                case R.id.fl_right:
                    validateLife.validate();
                    break;
                case R.id.btn_Add_bodydimension:
                    Intent intent = new Intent(this, JoinGameDimensionRecordActivity.class);
                    intent.putExtra("getConfirmInfoModel", getConfirmInfoModel);
                    if ("1".equals(type)) {
                        intent.putExtra("type", "1");
                    } else {
                        intent.putExtra("type", "0");
                    }
                    startActivityForResult(intent, 108);
                    break;
                case R.id.img_delete:
                    img1.setImageResource(android.R.color.transparent);
                    change_photo = "";
                    upload_photo = "";
                    img1.setVisibility(View.GONE);
                    img_delete.setVisibility(View.GONE);
                    break;
                case R.id.img_photoupload:
                    //弹出dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                //拍照
                                imageFileCropSelector.takePhoto(JoinGameDetailActivity.this);
                            } else if (which == 1) {
                                //照片
                                imageFileCropSelector.selectImage(JoinGameDetailActivity.this);
                            }
                        }
                    }).create().show();
                    break;
                case R.id.ll_class:
                    if (pargradeNamelList.size() != 0) {
                        show_class_dialog();
                    } else {
                        Util.toastMsg("当前没有班级");
                    }
                    break;
                case R.id.ll_weight:
                    show_weight_dialog();
                    break;
                case R.id.ll_tizhi:
                    show_pysical_dialog();
                    break;
                case R.id.ll_neizhi:
                    show_fat_dialog();
                    break;
                case R.id.ll_birthday:
                    show_birth_dialog();
                    break;
                case R.id.ll_sex:
                    showGradeDialog();
                    break;
            }
        }
    }

    private void checkPhone(int id) {
        if (isR) {
            Util.toastMsg("手机号码已注册");
        } else {
            switch (id) {
                case R.id.fl_right:
                    validateLife.validate();
                    break;
                case R.id.btn_Add_bodydimension:
                    Intent intent = new Intent(this, JoinGameDimensionRecordActivity.class);
                    intent.putExtra("getConfirmInfoModel", getConfirmInfoModel);
                    if ("1".equals(type)) {
                        intent.putExtra("type", "1");
                    } else {
                        intent.putExtra("type", "0");
                    }
                    startActivityForResult(intent, 108);
                    break;
                case R.id.img_delete:
                    img1.setImageResource(android.R.color.transparent);
                    change_photo = "";
                    upload_photo = "";
                    img1.setVisibility(View.GONE);
                    img_delete.setVisibility(View.GONE);
                    break;
                case R.id.img_photoupload:
                    //弹出dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                //拍照
                                if (ActivityCompat.checkSelfPermission(JoinGameDetailActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                        || ActivityCompat.checkSelfPermission(JoinGameDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                        ActivityCompat.checkSelfPermission(JoinGameDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(JoinGameDetailActivity.this, Manifest.permission.CAMERA) ||
                                            ActivityCompat.shouldShowRequestPermissionRationale(JoinGameDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                            ActivityCompat.shouldShowRequestPermissionRationale(JoinGameDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        ActivityCompat.requestPermissions(JoinGameDetailActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                    } else {
                                        ActivityCompat.requestPermissions(JoinGameDetailActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                    }
                                } else {
                                    imageFileCropSelector.takePhoto(JoinGameDetailActivity.this);
                                }

                            } else if (which == 1) {
                                //照片
                                if (ActivityCompat.checkSelfPermission(JoinGameDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                        ActivityCompat.checkSelfPermission(JoinGameDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(JoinGameDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                            ActivityCompat.shouldShowRequestPermissionRationale(JoinGameDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        ActivityCompat.requestPermissions(JoinGameDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                                    } else {
                                        ActivityCompat.requestPermissions(JoinGameDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                                    }
                                } else {
                                    imageFileCropSelector.selectImage(JoinGameDetailActivity.this); // 图库选择图片
                                }

                            }
                        }
                    }).create().show();
                    break;
                case R.id.ll_class:
                    if (pargradeNamelList.size() != 0) {
                        show_class_dialog();
                    } else {
                        Util.toastMsg("当前没有班级");
                    }
                    break;
                case R.id.ll_weight:
                    show_weight_dialog();
                    break;
                case R.id.ll_tizhi:
                    show_pysical_dialog();
                    break;
                case R.id.ll_neizhi:
                    show_fat_dialog();
                    break;
                case R.id.ll_birthday:
                    show_birth_dialog();
                    break;
                case R.id.ll_sex:
                    showGradeDialog();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_left) {
            finish();
        } else if (v.getId() == R.id.fl_right) {
            if ("0".equals(type)) {
                ll_tizhi.setFocusable(true);
                ll_tizhi.setFocusableInTouchMode(true);
                ll_tizhi.requestFocus();
                ll_tizhi.findFocus();

                et_phone.clearFocus();
                current_operation = v.getId();
                if (isOperation) {

                } else {
                    checkPhone(v.getId());
                }
            } else {
                validateLife.validate();
            }
        } else {
            ll_tizhi.setFocusable(true);
            ll_tizhi.setFocusableInTouchMode(true);
            ll_tizhi.requestFocus();
            ll_tizhi.findFocus();

            et_phone.clearFocus();
            current_operation = v.getId();
            if (isOperation) {

            } else {
                checkPhone(v.getId());
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(JoinGameDetailActivity.this);

            }
        }else if(requestCode == 200){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageFileCropSelector.selectImage(JoinGameDetailActivity.this);// 图库选择图片
            }
        }
    }
    String select_grade;
    public void showGradeDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select_grade, null);
        final WheelView wheel_grade = (WheelView) view.findViewById(R.id.wheel_grade);
        wheel_grade.setOffset(1);
        wheel_grade.setItems(gradeList);
        wheel_grade.setSeletion(0);

        wheel_grade.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_grade = item;
            }
        });
        birdialog.setTitle("选择性别").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(select_grade)) {
                            select_grade = gradeList.get(0);
                        }
                        tv_sex.setText(select_grade);
                        select_grade = "";
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create()
                .show();
    }

    //体脂dialog
    public void show_pysical_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(50);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setValue(25);
        np1.setMinValue(1);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体脂").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()) + "%");
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    //内脂dialog
    public void show_fat_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(30);
        np1.setValue(2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np1.setMinValue(1);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体脂").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    // 生日对话框
    public void show_birth_dialog() {
        final android.support.v7.app.AlertDialog.Builder birdialog = new android.support.v7.app.AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.birth_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np1.setMaxValue(myear);
        np1.setValue(1990);
        np1.setMinValue(1970);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setWrapSelectorWheel(false);

        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np2.setMaxValue(12);
        np2.setValue(1);
        np2.setMinValue(1);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setWrapSelectorWheel(false);

        final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
        np3.setMaxValue(31);
        np3.setValue(1);
        np3.setMinValue(1);
        np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np3.setWrapSelectorWheel(false);

        birdialog.setTitle("选择生日(年-月-日)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (np1.getValue() == myear && np2.getValue() > (mmonth + 1)) {
                    show_warn_dialog();
                }
                if (np1.getValue() == myear && np2.getValue() == (mmonth + 1) && np3.getValue() > mday) {
                    show_warn_dialog();
                } else {
                    tv_birthday.setText(String.valueOf(np1.getValue()) + "-" + String.valueOf(np2.getValue()) + "-" + String.valueOf(np3.getValue()));
                    tv_birthday.setError(null);
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void upload() {
        if ("1".equals(type)) {
            String name = et_nickname.getText().toString();
            if (StringUtil.length(name) > 12) {
                Util.toastMsg("姓名不能超过6个汉字");
            } else {
                coninfoModel = new ConinfoModel();
                //设置classid
                coninfoModel.setClassid(classid);

                coninfoModel.setAccountid(accoutid);
                coninfoModel.setNickname(name);
                coninfoModel.setBirthday(tv_birthday.getText().toString());
                coninfoModel.setGender(tv_sex.getText().toString().equals("男") ? 0 : 1);
                coninfoModel.setPhoto(change_photo);
                if (tv_weight.getText().toString().equals("")) {
                    coninfoModel.setWeight(0.0);
                } else {
                    String weights = tv_weight.getText().toString().split("斤")[0];
                    coninfoModel.setWeight(Double.parseDouble(weights));
                }
                if (tv_tizhi.getText().toString().equals("")) {
                    coninfoModel.setPysical(0.0);
                } else {
                    String tizhi = tv_tizhi.getText().toString().split("%")[0];
                    coninfoModel.setPysical(Double.parseDouble(tizhi));
                }

                if (tv_neizhi.getText().toString().equals("")) {
                    coninfoModel.setFat(0.0);
                } else {
                    coninfoModel.setFat(Double.parseDouble(tv_neizhi.getText().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getCircum())) {
                    coninfoModel.setCircum(0.0);
                } else {
                    coninfoModel.setCircum(Double.parseDouble(getConfirmInfoModel.getCircum().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getWaistline())) {
                    coninfoModel.setWaistline(0.0);
                } else {
                    coninfoModel.setWaistline(Double.parseDouble(getConfirmInfoModel.getWaistline().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getHiplie())) {
                    coninfoModel.setHiplie(0.0);
                } else {
                    coninfoModel.setHiplie(Double.parseDouble(getConfirmInfoModel.getHiplie().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getUpArmGirth())) {
                    coninfoModel.setUparmgirth(0.0);
                } else {
                    coninfoModel.setUparmgirth(Double.parseDouble(getConfirmInfoModel.getUpArmGirth().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getUpLegGirth())) {
                    coninfoModel.setUpleggirth(0.0);
                } else {
                    coninfoModel.setUpleggirth(Double.parseDouble(getConfirmInfoModel.getUpLegGirth().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getDoLegGirth())) {
                    coninfoModel.setDoleggirth(0.0);
                } else {
                    coninfoModel.setDoleggirth(Double.parseDouble(getConfirmInfoModel.getDoLegGirth().toString()));
                }
                String token = UserInfoModel.getInstance().getToken();
                UserModel user = UserInfoModel.getInstance().getUser();
                user.setNickname(et_nickname.getText().toString());
                UserInfoModel.getInstance().saveUserCache(user);
                dialogShow("加载中");
                iUpConfirmInfopresenter.changeUpConfirmInfo(token, coninfoModel);
            }
        } else {
            String name = et_nickname.getText().toString();
            if (StringUtil.length(name) > 12) {
                Util.toastMsg("姓名不能超过6个汉字");
            } else {
                NewstudentsModel newstudentsModel = new NewstudentsModel();
                newstudentsModel.setSentaccid(accoutid);
                newstudentsModel.setNickname(name);
                String mobile = et_phone.getText().toString();
                String b = "000000";
                newstudentsModel.setPassword(MD5.md5WithEncoder(b));
                newstudentsModel.setMobile(mobile);
                String classId = pargradeIdlList.get(select_posion);
                newstudentsModel.setClassid(classId);
                if (tv_weight.getText().toString().equals("")) {
                    newstudentsModel.setWeight(0.0);
                } else {
                    String weights = tv_weight.getText().toString().split("斤")[0];
                    newstudentsModel.setWeight(Double.parseDouble(weights));
                }

                newstudentsModel.setBirthday(tv_birthday.getText().toString());
                newstudentsModel.setGender(tv_sex.getText().toString().equals("男") ? 0 : 1);
                newstudentsModel.setPhoto(change_photo);
                if (tv_tizhi.getText().toString().equals("")) {
                    newstudentsModel.setPysical(0.0);
                } else {
                    String tizhi = tv_tizhi.getText().toString().split("%")[0];
                    newstudentsModel.setPysical(Double.parseDouble(tizhi));
                }
                if (tv_neizhi.getText().toString().equals("")) {
                    newstudentsModel.setFat(0.0);
                } else {
                    newstudentsModel.setFat(Double.parseDouble(tv_neizhi.getText().toString()));
                }
                if (TextUtils.isEmpty(getConfirmInfoModel.getCircum())) {
                    newstudentsModel.setCircum(0.0);
                } else {
                    newstudentsModel.setCircum(Double.parseDouble(getConfirmInfoModel.getCircum().toString()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getWaistline())) {
                    newstudentsModel.setWaistline(0.0);
                } else {
                    newstudentsModel.setWaistline(Double.parseDouble(getConfirmInfoModel.getWaistline()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getHiplie())) {
                    newstudentsModel.setHiplie(0.0);
                } else {
                    newstudentsModel.setHiplie(Double.parseDouble(getConfirmInfoModel.getHiplie()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getUpArmGirth())) {
                    newstudentsModel.setUparmgirth(0.0);
                } else {
                    newstudentsModel.setUparmgirth(Double.parseDouble(getConfirmInfoModel.getUpArmGirth()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getUpLegGirth())) {
                    newstudentsModel.setUpleggirth(0.0);
                } else {
                    newstudentsModel.setUpleggirth(Double.parseDouble(getConfirmInfoModel.getUpLegGirth()));
                }

                if (TextUtils.isEmpty(getConfirmInfoModel.getDoLegGirth())) {
                    newstudentsModel.setDoleggirth(0.0);
                } else {
                    newstudentsModel.setDoleggirth(Double.parseDouble(getConfirmInfoModel.getDoLegGirth()));
                }
                dialogShow("加载中");
                iNewStudentpresenter.input(newstudentsModel);
            }
        }
    }


    @Override
    public void onValidationSucceeded() {
        if (upload_photo.equals("")) {
            if ("1".equals(type)) {
                Util.toastMsg("您还没有上传照片哦");
            } else {
                upload();
            }
        } else {
            if (photo_backup.equals(upload_photo)) {
                upload();
            } else {
                dialogShow("加载中");
                iUpConfirmInfopresenter.upload(upload_photo);
            }
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    //体重对话框
    public void show_weight_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(600);
        if (tv_sex.getText().toString().equals("男")) {
            np1.setValue(150);
        } else if (tv_sex.getText().toString().equals("女")) {
            np1.setValue(100);
        } else {
            np1.setValue(100);
        }
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (np1.getValue() < 80) {
                    Dialog dialog1 = new AlertDialog.Builder(JoinGameDetailActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()) + "斤");
                                            tv_weight.setError(null);
                                        }
                                    })

                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            show_weight_dialog();
                                        }
                                    }).create();
                    dialog1.show();
                    dialog1.setCanceledOnTouchOutside(false);
                } else {
                    tv_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()) + "斤");
                    tv_weight.setError(null);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }


    public void show_class_dialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.class_view, null);
        WheelView wheel_class = (WheelView) outerView.findViewById(R.id.wheel_class);
        wheel_class.setOffset(1);
        wheel_class.setItems(pargradeNamelList);
        wheel_class.setSeletion(0);
        wheel_class.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_posion = selectedIndex - 1;
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("请选择班级")
                .setView(outerView)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_class.setText(pargradeNamelList.get(select_posion));
                    }

                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                dialogDissmiss();
                            }
                        }).create()
                .show();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
        if (requestCode == 108 && resultCode == RESULT_OK) {
            getConfirmInfoModel = (GetConfirmInfoModel) data.getSerializableExtra("getConfirmInfoModel");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!"1".equals(type)) {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
