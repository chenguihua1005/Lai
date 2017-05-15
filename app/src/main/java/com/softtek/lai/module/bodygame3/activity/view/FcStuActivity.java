package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.adapter.InitDataExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.adapter.MyExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.DragFloatActionButtonCheng;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;


@InjectLayout(R.layout.activity_fcst)
public class FcStuActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.exlisview_body)
    ExpandableListView exlisview_body;
    //标题栏
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.cheng_float)
    DragFloatActionButtonCheng cheng_float;

    FuceSevice fuceSevice;
    //    FcStDataModel fcStDataModel;
    MeasuredDetailsModel fcStDataModel;
    String gender = "0";//性别
    String classId, typeDate;//接口参数，从上一个页面获取
    private Long userId;
    private static final int GET_PREVIEW = 1;//查看大图
    private CharSequence[] items = {"拍照", "从相册选择照片"};
    private static final int CAMERA_PREMISSION = 100;
    MultipartTypedOutput multipartTypedOutput;
    int resetstatus, resetdatestatus;
    private ProgressDialog progressDialog;
    int IsEdit = 1;//是否可编辑，1可编辑，2不可编辑
    String filest, photoname;
    File file;
    private ImageFileSelector imageFileSelector;
    int isExistP = 0;
    boolean IsZhankai = false;
    private List<List<String>> childArray = new ArrayList<>();
    private List<String> child = new ArrayList<>();
    private List<String> child2 = new ArrayList<>();
    private List<String> child3 = new ArrayList<>();

    MyExpandableListAdapter adapter;

    private String initWeight = "";

    private boolean isPasses_weight = true;

    @Override
    protected void initViews() {
        tv_title.setText("复测录入");
        tv_right.setText("提交");
        progressDialog = new ProgressDialog(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        cheng_float.setOnClickListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(Constants.UPDATE_UI_STU_FUCEDATA_INPUT));

        int px = DisplayUtil.dip2px(this, 300);
        //*************************
        imageFileSelector = new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px, px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {

                filest = file;
                isExistP = 2;
                isExistP = 2;
                adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray, fcStDataModel, filest, photoname,
                        isExistP, resetstatus, IsEdit);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i = 0; i < groupCount; i++) {
                    if (i == 0) {
                        exlisview_body.expandGroup(i);
                    }
                    if (i == 3) {
                        if (IsZhankai) {
                            exlisview_body.expandGroup(i);
                        }
                    }
                }
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                file = new File(files.get(0));
                filest = file.toString();
                isExistP = 2;
                adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray,
                        fcStDataModel, filest, photoname, isExistP, resetstatus, IsEdit);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i = 0; i < groupCount; i++) {
                    if (i == 0) {
                        exlisview_body.expandGroup(i);
                    }
                    if (i == 3) {
                        if (IsZhankai) {
                            exlisview_body.expandGroup(i);
                        }
                    }
                }
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    protected void initDatas() {
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        classId = getIntent().getStringExtra("classId");
        typeDate = getIntent().getStringExtra("typeDate");
        userId = UserInfoModel.getInstance().getUserId();
        resetstatus = getIntent().getIntExtra("resetstatus", 0);//接收数据审核状态,//复测状态：1：未复测 2：未审核 3：已复测
        resetdatestatus = getIntent().getIntExtra("resetdatestatus", 0);//接收复测日状态//复测日状态  1:已过去 2：进行中 3：未开始
        doData();
        multipartTypedOutput = new MultipartTypedOutput();


    }

    //resetdatestatus=2,当天复测日，可进行录入，type=1（复测录入）
    //resetdatestatus=1,过去复测日，可进行查看，type=2（复测查看）
    //复测状态：1：未复测 2：未审核 3：已复测resetstatus
    private void doData() {
        switch (resetdatestatus) {
            case 1://过去复测日，只能查看
                switch (resetstatus) {
                    //未复测、未审核
                    case 1:
                    case 2:
                        Util.toastMsg("非当天复测日未复测数据或数据未审核不可查看");
                        break;
                    case 3:
                        fl_right.setVisibility(View.INVISIBLE);
                        IsEdit = 2;
                        doGetDataService("2");
                        break;
                    default:
                        fl_right.setVisibility(View.INVISIBLE);
                        IsEdit = 2;
                        doGetDataService("2");
                        break;
                }
                break;
            case 2://当天复测日
                switch (resetstatus) {
                    case 1:
                    case 2:
                        doGetDataService("1");
                        break;
                    case 3:
                        tv_right.setVisibility(View.INVISIBLE);
                        IsEdit = 2;
                        fl_right.setEnabled(false);
                        doGetDataService("2");
                        break;
                }
                break;
            default:
                Util.toastMsg("当前复测日未开始不可查看录入数据");
                tv_right.setVisibility(View.INVISIBLE);
                fl_right.setEnabled(false);
                break;
        }
        child.add(0, "初始体重");
        child.add(1, "当前体重");
        child.add(2, "体脂率");
        child.add(3, "内脂");
        childArray.add(0, child);
        child3.add(0, "胸围");
        child3.add(1, "腰围");
        child3.add(2, "臀围");
        child3.add(3, "上臂围");
        child3.add(4, "大腿围");
        child3.add(5, "小腿围");

        //jessica
        child3.add(6, "BMI");
        child3.add(7, "去脂体重");
//        child3.add(8, "内脏脂肪指数");
        child3.add(8, "身体水分率");
        child3.add(9, "身体水分");

        child3.add(10, "肌肉量");
        child3.add(11, "骨量");
        child3.add(12, "基础代谢");
        child3.add(13, "身体年龄");
        //jessica
        childArray.add(1, child2);
        childArray.add(2, child2);
        childArray.add(3, child3);
        exlisview_body.setGroupIndicator(null);
        exlisview_body.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        if (isExistP == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FcStuActivity.this);
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        //拍照
                                        if (ActivityCompat.checkSelfPermission(FcStuActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                                //允许弹出提示
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                            } else {
                                                //不允许弹出提示
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                            }
                                        } else {
                                            imageFileSelector.takePhoto(FcStuActivity.this);
                                        }
                                    } else if (which == 1) {
                                        //照片
                                        imageFileSelector.selectMutilImage(FcStuActivity.this, 1);
                                    }
                                }
                            }).create().show();
                        } else {
                            Intent intent1 = new Intent(FcStuActivity.this, PreViewPicActivity.class);
                            intent1.putExtra("images", filest);
                            intent1.putExtra("photoname", photoname);
                            intent1.putExtra("IsEdit", IsEdit);
                            startActivityForResult(intent1, GET_PREVIEW);
                        }

                        break;
                    case 2:
                        startActivity(new Intent(FcStuActivity.this, GuideActivity.class));
                        break;
                    case 3:
                        if (IsZhankai) {
                            IsZhankai = false;
                        } else {
                            IsZhankai = true;
                        }
                        break;
                }
                return i == 0 || i == 1 || i == 2 ? true : false;
            }
        });
        if (IsEdit == 1) {
            exlisview_body.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    switch (i) {
                        case 0:
                            switch (i1) {
                                case 0:
                                    break;
                                case 1:
                                    if ("1".equals(gender)) {
                                        show_information("当前体重", 600, 100, 50, 9, 0, 0, 1);
                                    } else {
                                        show_information("当前体重", 600, 150, 50, 9, 0, 0, 1);
                                    }
                                    break;
                                case 2:
                                    show_information("体脂率", 50, 25, 1, 9, 0, 0, 2);
                                    break;
                                case 3:
                                    show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                                    break;

                            }
                            break;
                        case 3:
                            switch (i1) {
                                case 0:
                                    show_information("胸围", 200, 90, 50, 9, 0, 0, 4);
                                    break;
                                case 1:
                                    show_information("腰围", 200, 80, 40, 9, 0, 0, 5);
                                    break;
                                case 2:
                                    show_information("臀围", 250, 90, 50, 9, 0, 0, 6);
                                    break;
                                case 3:
                                    show_information("上臂围", 70, 50, 10, 9, 0, 0, 7);
                                    break;
                                case 4:
                                    show_information("大腿围", 90, 50, 10, 9, 0, 0, 8);
                                    break;
                                case 5:
                                    show_information("小腿围", 70, 50, 10, 9, 0, 0, 9);
                                    break;
                                // jessica
                                case 6:
                                    if ("1".equals(gender)) { //女的
                                        show_information("BMI", 50, 25, 0, 9, 0, 0, 10);
                                    } else {
                                        show_information("BMI", 50, 27, 0, 9, 0, 0, 10);
                                    }
                                    break;
                                case 7:
                                    if ("1".equals(gender)) { //女的
                                        show_information("去脂体重", 180, 40, 0, 9, 0, 0, 11);
                                    } else {
                                        show_information("去脂体重", 180, 60, 0, 9, 0, 0, 11);
                                    }
                                    break;
//                                case 8:
//                                    if ("1".equals(gender)) { //女的
//                                        show_information("内脏脂肪指数", 30, 10, 0, 9, 0, 0, 12);
//                                    } else {
//                                        show_information("内脏脂肪指数", 30, 10, 0, 9, 0, 0, 12);
//                                    }
//                                    break;
                                case 8:
                                    if ("1".equals(gender)) { //女的
                                        show_information("身体水分率", 80, 50, 0, 9, 0, 0, 13);
                                    } else {
                                        show_information("身体水分率", 80, 55, 0, 9, 0, 0, 13);
                                    }
                                    break;
                                case 9:
                                    if ("1".equals(gender)) { //女的
                                        show_information("身体水分", 160, 30, 0, 9, 0, 0, 14);
                                    } else {
                                        show_information("身体水分", 160, 40, 0, 9, 0, 0, 14);
                                    }
                                    break;
                                case 10:
                                    if ("1".equals(gender)) { //女的
                                        show_information("肌肉量", 180, 40, 0, 9, 0, 0, 15);
                                    } else {
                                        show_information("肌肉量", 180, 60, 0, 9, 0, 0, 15);
                                    }
                                    break;
                                case 11:
                                    if ("1".equals(gender)) { //女的
                                        show_information("骨量", 6, 2, 0, 9, 5, 0, 16);
                                    } else {
                                        show_information("骨量", 6, 3, 0, 9, 0, 0, 16);
                                    }

                                    break;
                                case 12:
                                    if ("1".equals(gender)) { //女的
                                        show_information("基础代谢", 2500, 1280, 0, 9, 0, 0, 17);
                                    } else {
                                        show_information("基础代谢", 2500, 1700, 0, 9, 0, 0, 17);
                                    }
                                    break;
                                case 13:
                                    show_information("身体年龄", 150, 30, 0, 9, 0, 0, 18);
                                    break;
                                //jessica

                            }
                            break;
                    }

                    return false;
                }
            });
        }

    }

    private void doGetDataService(String type) {
        fuceSevice.doGetPreMeasureData(classId, UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<MeasuredDetailsModel>>() {
            @Override
            public void success(ResponseData<MeasuredDetailsModel> fcStDataModelResponseData, Response response) {
                int status = fcStDataModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        fcStDataModel = fcStDataModelResponseData.getData();
                        doSetData();
                        break;
                    default:
                        Util.toastMsg(fcStDataModelResponseData.getMsg());
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        BleManager.getInstance().disconnectBluetooth();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                BleManager.getInstance().disconnectBluetooth();
                break;
            case R.id.cheng_float:
                Intent intent = new Intent(FcStuActivity.this, FuceForStuActivity.class);//跳转到发布动态界面
                intent.putExtra("fucedata", fcStDataModel);//FcStDataModel
                intent.putExtra("type", 2);
                intent.putExtra("classId", classId);
                intent.putExtra("AccountId", UserInfoModel.getInstance().getUserId());
                intent.putExtra("from", Constants.UPDATE_UI_STU_FUCEDATA_INPUT);
                startActivity(intent);
                break;
            case R.id.fl_right:
                if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight())) {
                    String message = "当前体重为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getPysical()) ? "" : fcStDataModel.getPysical())) {
                    String message = "体脂率为必填项，请选择";
                    new AlertDialog.Builder(FcStuActivity.this)
                            .setMessage(message)
                            .create().show();
                } else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getFat()) ? "" : fcStDataModel.getFat())) {
                    String message = "内脂为必填项，请选择";
                    new AlertDialog.Builder(FcStuActivity.this)
                            .setMessage(message)
                            .create().show();
                } else if (isExistP == 0) {
                    String message = "请上传照片";
                    new AlertDialog.Builder(FcStuActivity.this)
                            .setMessage(message)
                            .create().show();
                } else if (!TextUtils.isEmpty(initWeight) && !TextUtils.isEmpty(fcStDataModel.getWeight()) && !TextUtils.isEmpty(fcStDataModel.getThreshold()) && Math.abs(Float.parseFloat(fcStDataModel.getWeight()) - Float.parseFloat(initWeight)) > Float.parseFloat(fcStDataModel.getThreshold())) {
                    String message = "检测到体重变化过大, 请检查体重与单位(斤)的正确性, 是否确认? ";
                    new AlertDialog.Builder(this)
                            .setMessage(message).setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            doSetPostData();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).create().show();
                } else {
                    doSetPostData();
                }


                break;

        }
    }

    void doPostInitData() {
        fuceSevice.doPostMeasuredData(classId, UserInfoModel.getInstance().getToken(), multipartTypedOutput, new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        int status = responseData.getStatus();
                        try {
                            switch (status) {
                                case 200:
                                    progressDialog.dismiss();
                                    Intent intent = new Intent();
                                    int IsFcSt = 1;//代表学员复测完成
                                    intent.putExtra("IsFcSt", IsFcSt);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                    break;
                                default:
                                    progressDialog.dismiss();
                                    Util.toastMsg(responseData.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        progressDialog.dismiss();
                    }
                }
        );
    }

    void doSetData() {
        try {
            if (fcStDataModel != null) {
                initWeight = fcStDataModel.getWeight();
                gender = fcStDataModel.getGender();

                if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail())) {
                    isExistP = 1;
                    photoname = fcStDataModel.getImgThumbnail();

                } else if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    isExistP = 1;
                    photoname = fcStDataModel.getImg();
                }

                adapter = new MyExpandableListAdapter(this, this, childArray, fcStDataModel, filest, photoname, isExistP,
                        resetstatus, IsEdit);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i = 0; i < groupCount; i++) {
                    if (i == 0) {
                        exlisview_body.expandGroup(i);
                    }
                }

                cheng_float.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void doSetPostData() {

        {
            progressDialog.setMessage("正在提交数据，请等待");
            progressDialog.show();
            multipartTypedOutput.addPart("accountId", new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
            multipartTypedOutput.addPart("classId", new TypedString(classId));
            if (isExistP == 2) {
                multipartTypedOutput.addPart("image", new TypedFile("image/png", new File(filest)));
            }
            multipartTypedOutput.addPart("pysical", new TypedString(TextUtils.isEmpty(fcStDataModel.getPysical()) ? "0" : fcStDataModel.getPysical()));//体脂
            multipartTypedOutput.addPart("fat", new TypedString(TextUtils.isEmpty(fcStDataModel.getFat()) ? "0" : fcStDataModel.getFat()));//内脂
            multipartTypedOutput.addPart("weight", new TypedString(TextUtils.isEmpty(fcStDataModel.getWeight()) ? "0" : fcStDataModel.getWeight()));//现在体重


            multipartTypedOutput.addPart("circum", new TypedString(TextUtils.isEmpty(fcStDataModel.getCircum()) ? "0" : fcStDataModel.getCircum().toString()));//胸围
            multipartTypedOutput.addPart("waistline", new TypedString(TextUtils.isEmpty(fcStDataModel.getWaistline()) ? "0" : fcStDataModel.getWaistline().toString()));//腰围
            multipartTypedOutput.addPart("hiplie", new TypedString(TextUtils.isEmpty(fcStDataModel.getHiplie()) ? "0" : fcStDataModel.getHiplie().toString()));//臀围
            multipartTypedOutput.addPart("upArmGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpArmGirth()) ? "0" : fcStDataModel.getUpArmGirth().toString()));//上臂围
            multipartTypedOutput.addPart("upLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpLegGirth()) ? "0" : fcStDataModel.getUpLegGirth().toString()));//大腿围
            multipartTypedOutput.addPart("doLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getDoLegGirth()) ? "0" : fcStDataModel.getDoLegGirth().toString()));//小腿围

            multipartTypedOutput.addPart("Bmi", new TypedString(TextUtils.isEmpty(fcStDataModel.getBmi()) ? "0" : fcStDataModel.getBmi().toString()));
            multipartTypedOutput.addPart("FatFreeMass", new TypedString(TextUtils.isEmpty(fcStDataModel.getFatFreeMass()) ? "0" : fcStDataModel.getFatFreeMass().toString()));
            multipartTypedOutput.addPart("BodyWaterRate", new TypedString(TextUtils.isEmpty(fcStDataModel.getBodyWaterRate()) ? "0" : fcStDataModel.getBodyWaterRate().toString()));
            multipartTypedOutput.addPart("BodyWater", new TypedString(TextUtils.isEmpty(fcStDataModel.getBodyWater()) ? "0" : fcStDataModel.getBodyWater().toString()));
            multipartTypedOutput.addPart("MuscleMass", new TypedString(TextUtils.isEmpty(fcStDataModel.getMuscleMass()) ? "0" : fcStDataModel.getMuscleMass().toString()));
            multipartTypedOutput.addPart("BoneMass", new TypedString(TextUtils.isEmpty(fcStDataModel.getBoneMass()) ? "0" : fcStDataModel.getBoneMass().toString()));
            multipartTypedOutput.addPart("BasalMetabolism", new TypedString(TextUtils.isEmpty(fcStDataModel.getBasalMetabolism()) ? "0" : fcStDataModel.getBasalMetabolism().toString()));
            multipartTypedOutput.addPart("PhysicalAge", new TypedString(TextUtils.isEmpty(fcStDataModel.getPhysicalAge()) ? "0" : fcStDataModel.getPhysicalAge().toString()));

            doPostInitData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
        //查看大图返回图片
        if (requestCode == GET_PREVIEW && resultCode == RESULT_OK) {
            filest = data.getStringExtra("images");
            if (TextUtils.isEmpty(filest)) {
                isExistP = 1;
            } else {
                isExistP = 2;
            }
            adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray, fcStDataModel, filest, photoname,
                    isExistP, resetstatus, IsEdit);
            exlisview_body.setAdapter(adapter);
            int groupCount = exlisview_body.getCount();
            for (int i = 0; i < groupCount; i++) {
                if (i == 0) {
                    exlisview_body.expandGroup(i);
                }
                if (i == 3) {
                    if (IsZhankai) {
                        exlisview_body.expandGroup(i);
                    }
                }
            }
            ;
        }

    }

    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
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
                switch (num) {
                    case 0:

                        break;
                    case 1:
                        fcStDataModel.setWeight(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                        exlisview_body.setAdapter(adapter);
                        int groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 2:
                        fcStDataModel.setPysical(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 3:
                        fcStDataModel.setFat(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 4:
                        fcStDataModel.setCircum(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 5:
                        fcStDataModel.setWaistline(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 6:
                        fcStDataModel.setHiplie(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 7:
                        fcStDataModel.setUpArmGirth(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 8:
                        fcStDataModel.setUpLegGirth(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;
                    case 9:
                        fcStDataModel.setDoLegGirth(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                        ;
                        break;

                    case 10: { //BMI
                        fcStDataModel.setBmi(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                    case 11: {// fatFreeMass;//去脂体重
                        fcStDataModel.setFatFreeMass(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
//                    case 12: {//viscusFatIndex;     //内脏脂肪指数
//                        fcStDataModel.setViscusFatIndex(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
//                        exlisview_body.setAdapter(adapter);
//                        groupCount = exlisview_body.getCount();
//                        for (int i = 0; i < groupCount; i++) {
//                            if (i == 0) {
//                                exlisview_body.expandGroup(i);
//                            }
//                            if (i == 3) {
//                                if (IsZhankai) {
//                                    exlisview_body.expandGroup(i);
//                                }
//                            }
//                        }
//                    }
//                    break;
                    case 13: {//bodyWaterRate;//身体水分率
                        fcStDataModel.setBodyWaterRate(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                    case 14: {//bodyWater;//身体水分
                        fcStDataModel.setBodyWater(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                    case 15: {//muscleMass;//肌肉量
                        fcStDataModel.setMuscleMass(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                    case 16: {//boneMass;//骨量
                        fcStDataModel.setBoneMass(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                    case 17: {//basalMetabolism;//基础代谢
                        fcStDataModel.setBasalMetabolism(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                    case 18: {//physicalAge;//身体年龄
                        fcStDataModel.setPhysicalAge(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            if (i == 0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i == 3) {
                                if (IsZhankai) {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        }
                    }
                    break;
                }

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    //权限
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PREMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageFileSelector.takePhoto(this);
                }
                break;

        }
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && Constants.UPDATE_UI_STU_FUCEDATA_INPUT.equalsIgnoreCase(intent.getAction())) {
                BleMainData result_model = (BleMainData) intent.getSerializableExtra("result_model");
                if (result_model != null) {

                    if (result_model.getWeight() != 0) {
                        fcStDataModel.setWeight(result_model.getWeight() + "");
                    }
                    if (!TextUtils.isEmpty(result_model.getBodyFatRate())) {//体脂
                        fcStDataModel.setPysical(result_model.getBodyFatRate());
                    }

                    if (!TextUtils.isEmpty(result_model.getViscusFatIndex())) {
                        fcStDataModel.setFat(result_model.getViscusFatIndex()); //内脂
                    }

                    if (!TextUtils.isEmpty(result_model.getBMI())) {
                        fcStDataModel.setBmi(result_model.getBMI());
                    }
                    if (!TextUtils.isEmpty(result_model.getFatFreemass())) {
                        fcStDataModel.setFatFreeMass(result_model.getFatFreemass());
                    }
                    if (!TextUtils.isEmpty(result_model.getWaterContentRate())) {
                        fcStDataModel.setBodyWaterRate(result_model.getWaterContentRate());
                    }
                    if (!TextUtils.isEmpty(result_model.getWaterContent())) {
                        fcStDataModel.setBodyWater(result_model.getWaterContent());
                    }

                    if (!TextUtils.isEmpty(result_model.getMusclemass())) {
                        fcStDataModel.setMuscleMass(result_model.getMusclemass());
                    }

                    if (!TextUtils.isEmpty(result_model.getBonemass())) {
                        fcStDataModel.setBoneMass(result_model.getBonemass());
                    }

                    if (!TextUtils.isEmpty(result_model.getBasalmetabolicrate())) {
                        fcStDataModel.setBasalMetabolism(result_model.getBasalmetabolicrate());
                    }
                    if (!TextUtils.isEmpty(result_model.getPhysicalAge())) {
                        fcStDataModel.setPhysicalAge(result_model.getPhysicalAge());
                    }

                    adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray, fcStDataModel, filest, photoname, isExistP,
                            resetstatus, IsEdit);
                    exlisview_body.setAdapter(adapter);

                    int groupCount = exlisview_body.getCount();
                    for (int i = 0; i < groupCount; i++) {
                        if (i == 0) {
                            exlisview_body.expandGroup(i);
                        }
                        if (i == 3) {
                            if (IsZhankai) {
                                exlisview_body.expandGroup(i);
                            }
                        }
                    }

                }
            }
        }
    };

}
