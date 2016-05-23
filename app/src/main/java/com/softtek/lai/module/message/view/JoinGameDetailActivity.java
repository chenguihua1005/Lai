/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.view;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.confirmInfo.EventModel.ConinfoEvent;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.confirmInfo.presenter.IUpConfirmInfopresenter;
import com.softtek.lai.module.confirmInfo.presenter.UpConfirmInfoImpl;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.message.model.PhotosModel;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.newmemberentry.view.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.view.model.NewstudentsModel;
import com.softtek.lai.module.newmemberentry.view.model.PargradeModel;
import com.softtek.lai.module.newmemberentry.view.presenter.GuwenClassImp;
import com.softtek.lai.module.newmemberentry.view.presenter.GuwenClassPre;
import com.softtek.lai.module.newmemberentry.view.presenter.INewStudentpresenter;
import com.softtek.lai.module.newmemberentry.view.presenter.NewStudentInputImpl;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.WheelView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageCropper;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.sw926.imagefileselector.ImageFileSelector;

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
 * 荣誉榜
 */
@InjectLayout(R.layout.activity_joingame_detail)
public class JoinGameDetailActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

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
    private List<String> gradeList = new ArrayList<String>();
    private List<String> gradeIDList = new ArrayList<String>();
    private ImageFileSelector imageFileSelector;
    private ImageCropper imageCropper;
    private INewStudentpresenter iNewStudentpresenter;

    //获取当前日期
    Calendar ca;
    int myear;//获取年份
    int mmonth;//获取月份
    int mday;//获取日

    private String select_grade = "";
    private String grade_id = "";

    private IUpConfirmInfopresenter iUpConfirmInfopresenter;
    String classid;
    long accoutid;
    private GetConfirmInfoModel getConfirmInfoModel;
    private ConinfoModel coninfoModel;
    private CharSequence[] items = {"拍照", "照片"};
    String path = "";
    private static final int PHOTO = 1;

    private String change_photo = "";
    private String upload_photo = "";
    private GuwenClassPre guwenClassPre;
    private IMessagePresenter messagePresenter;
    private List<String> pargradeIdlList = new ArrayList<String>();
    private List<String> pargradeNamelList = new ArrayList<String>();

    private int select_posion = 0;

    private ImageFileCropSelector imageFileCropSelector;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        //获取当前日期
        ca = Calendar.getInstance();
        myear = ca.get(Calendar.YEAR);//获取年份
        mmonth = ca.get(Calendar.MONTH);//获取月份
        mday = ca.get(Calendar.DATE);//获取日

        int px = DisplayUtil.dip2px(this, 300);
        imageFileCropSelector = new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutImageSize(px, px);
        imageFileCropSelector.setQuality(30);
        imageFileCropSelector.setScale(true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(PhotosModel photModel) {
        System.out.println("photModel:" + photModel);
        change_photo = photModel.getImg();
        upload();

    }

    @Subscribe
    public void onEvent(ConinfoEvent coninfoEvent) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + coninfoEvent.getConfirmInfoModel());
        getConfirmInfoModel = coninfoEvent.getConfirmInfoModel();

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        //String path= AddressManager.get("photoHost","http://172.16.98.167/FileUpload/PostFile/");
        change_photo = getConfirmInfoModel.getPhoto();
        if (!TextUtils.isEmpty(getConfirmInfoModel.getPhoto())) {
            Picasso.with(this).load(path + getConfirmInfoModel.getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img1);
        } else {
            img1.setImageResource(android.R.color.transparent);
        }
        if ("".equals(change_photo)) {
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
            tv_weight.setText(StringUtil.getValue(getConfirmInfoModel.getWeight()) + "斤");
        }
        String pysicals = StringUtil.getValue(getConfirmInfoModel.getPysical());
        if ("".equals(pysicals)) {
            tv_tizhi.setText("");
        } else {
            tv_tizhi.setText(StringUtil.getValue(getConfirmInfoModel.getPysical()) + "%");
        }

        tv_neizhi.setText(StringUtil.getValue(getConfirmInfoModel.getFat()) + "");
        tv_birthday.setText(getConfirmInfoModel.getBirthday());
        tv_sex.setText(getConfirmInfoModel.getGender().equals("0") ? "男" : "女");
    }


    @Override
    protected void initViews() {
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

    }

    //性别对话框
    private void addGrade() {
        gradeList.add("女");
        gradeList.add("男");
        gradeIDList.add("1");
        gradeIDList.add("0");
    }

    @Override
    protected void initDatas() {
        iNewStudentpresenter = new NewStudentInputImpl(JoinGameDetailActivity.this);
        iUpConfirmInfopresenter = new UpConfirmInfoImpl(JoinGameDetailActivity.this);
        messagePresenter = new MessageImpl(JoinGameDetailActivity.this);
        guwenClassPre = new GuwenClassImp();
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accoutid = Long.parseLong(userInfoModel.getUser().getUserid());
        addGrade();
        type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            tv_right.setText("完成");
            MessageDetailInfo messageDetailInfo = (MessageDetailInfo) getIntent().getSerializableExtra("messageDetailInfo");
            classid = messageDetailInfo.getClassId();
            dialogShow("加载中");
            iUpConfirmInfopresenter.getConfirmInfo(accoutid, Long.parseLong(classid));
            tv_title.setText("报名参赛");
            et_phone.setEnabled(false);
            ll_class.setEnabled(false);
            ll_left.setVisibility(View.GONE);
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
                    System.out.println("hasFocus--------");
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                    } else {
                        // 此处为失去焦点时的处理内容
                        String phone = et_phone.getText().toString();
                        System.out.println("phone.length:"+phone.length());
                        if (phone.length() == 11) {
                            progressDialog.show();
                            messagePresenter.phoneIsExist(phone, progressDialog);
                        }
                    }
                }
            });
        }
    }

    @Subscribe
    public void onEvent(ClassEvent classEvent) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + classEvent.getPargradeModels());
        List<PargradeModel> pargradeModels = classEvent.getPargradeModels();
        for (PargradeModel cl : pargradeModels) {
            pargradeIdlList.add(cl.getClassId());
            pargradeNamelList.add(cl.getClassName());
        }
    }

    @Override
    public void onClick(View v) {
        ll_tizhi.setFocusable(true);
        ll_tizhi.setFocusableInTouchMode(true);
        ll_tizhi.requestFocus();
        ll_tizhi.findFocus();

        et_phone.clearFocus();
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
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

    public void showGradeDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select_grade, null);
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
        np1.setValue(myear);
        np1.setMinValue(1900);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setWrapSelectorWheel(false);

        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np2.setMaxValue(12);
        np2.setValue(mmonth + 1);
        np2.setMinValue(1);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setWrapSelectorWheel(false);

        final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
        np3.setMaxValue(31);
        np3.setValue(mday);
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
            if (length(name) > 12) {
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

                if (getConfirmInfoModel.getCircum().toString().equals("")) {
                    coninfoModel.setCircum(0.0);
                } else {
                    coninfoModel.setCircum(Double.parseDouble(getConfirmInfoModel.getCircum().toString()));
                }

                if (getConfirmInfoModel.getWaistline().toString().equals("")) {
                    coninfoModel.setWaistline(0.0);
                } else {
                    coninfoModel.setWaistline(Double.parseDouble(getConfirmInfoModel.getWaistline().toString()));
                }

                if (getConfirmInfoModel.getHiplie().toString().equals("")) {
                    coninfoModel.setHiplie(0.0);
                } else {
                    coninfoModel.setHiplie(Double.parseDouble(getConfirmInfoModel.getHiplie().toString()));
                }

                if (getConfirmInfoModel.getUpArmGirth().equals("")) {
                    coninfoModel.setUparmgirth(0.0);
                } else {
                    coninfoModel.setUparmgirth(Double.parseDouble(getConfirmInfoModel.getUpArmGirth().toString()));
                }

                if (getConfirmInfoModel.getUpLegGirth().toString().equals("")) {
                    coninfoModel.setUpleggirth(0.0);
                } else {
                    coninfoModel.setUpleggirth(Double.parseDouble(getConfirmInfoModel.getUpLegGirth().toString()));
                }

                if (getConfirmInfoModel.getDoLegGirth().toString().equals("")) {
                    coninfoModel.setDoleggirth(0.0);
                } else {
                    coninfoModel.setDoleggirth(Double.parseDouble(getConfirmInfoModel.getDoLegGirth().toString()));
                }
                System.out.println("coninfoModel:" + coninfoModel);
                String token = UserInfoModel.getInstance().getToken();
                UserModel user = UserInfoModel.getInstance().getUser();
                user.setNickname(et_nickname.getText().toString());
                UserInfoModel.getInstance().saveUserCache(user);
                dialogShow("加载中");
                iUpConfirmInfopresenter.changeUpConfirmInfo(token, coninfoModel);
            }
        } else {
            String name = et_nickname.getText().toString();
            if (length(name) > 12) {
                Util.toastMsg("姓名不能超过6个汉字");
            } else {
                NewstudentsModel newstudentsModel = new NewstudentsModel();
                newstudentsModel.setSentaccid(accoutid);
                newstudentsModel.setNickname(name);
                String mobile = et_phone.getText().toString();
                String b = "hbl" + mobile.substring(mobile.length() - 6, mobile.length());
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
                if (getConfirmInfoModel.getCircum().toString().equals("")) {
                    newstudentsModel.setCircum(0.0);
                } else {
                    newstudentsModel.setCircum(Double.parseDouble(getConfirmInfoModel.getCircum().toString()));
                }

                if (getConfirmInfoModel.getWaistline().toString().equals("")) {
                    newstudentsModel.setWaistline(0.0);
                } else {
                    newstudentsModel.setWaistline(Double.parseDouble(getConfirmInfoModel.getWaistline().toString()));
                }

                if (getConfirmInfoModel.getHiplie().toString().equals("")) {
                    newstudentsModel.setHiplie(0.0);
                } else {
                    newstudentsModel.setHiplie(Double.parseDouble(getConfirmInfoModel.getHiplie().toString()));
                }

                if (getConfirmInfoModel.getUpArmGirth().equals("")) {
                    newstudentsModel.setUparmgirth(0.0);
                } else {
                    newstudentsModel.setUparmgirth(Double.parseDouble(getConfirmInfoModel.getUpArmGirth().toString()));
                }

                if (getConfirmInfoModel.getUpLegGirth().toString().equals("")) {
                    newstudentsModel.setUpleggirth(0.0);
                } else {
                    newstudentsModel.setUpleggirth(Double.parseDouble(getConfirmInfoModel.getUpLegGirth().toString()));
                }

                if (getConfirmInfoModel.getDoLegGirth().toString().equals("")) {
                    newstudentsModel.setDoleggirth(0.0);
                } else {
                    newstudentsModel.setDoleggirth(Double.parseDouble(getConfirmInfoModel.getDoLegGirth().toString()));
                }
                dialogShow("加载中");
                iNewStudentpresenter.input(newstudentsModel);
            }
        }
    }

    private boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    private int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    @Override
    public void onValidationSucceeded() {
        if (upload_photo.equals("")) {
            upload();
        } else {
            dialogShow("加载中");
            iUpConfirmInfopresenter.upload(upload_photo);
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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

        final WheelView wheel_class = (WheelView) outerView.findViewById(R.id.wheel_class);

        wheel_class.setOffset(1);
        wheel_class.setItems(pargradeNamelList);
        wheel_class.setSeletion(0);
        wheel_class.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_posion = selectedIndex - 1;
            }
        });

        new android.app.AlertDialog.Builder(this)
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
            if ("1".equals(type)) {
                System.out.println("");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
