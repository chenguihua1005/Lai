package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.MyExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
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

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    FuceSevice fuceSevice;
    FcStDataModel fcStDataModel;
    String gender = "0";
    String classId, typeDate;
    private Long userId;
    private static final int GET_PREVIEW = 1;//查看大图
    private CharSequence[] items = {"拍照", "从相册选择照片"};
    private static final int CAMERA_PREMISSION = 100;
    MultipartTypedOutput multipartTypedOutput;
    int resetstatus, resetdatestatus;
    private ProgressDialog progressDialog;
    boolean IsEdit = true;
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

    @Override
    protected void initViews() {
        tv_title.setText("复测录入");
        tv_right.setText("提交");
        progressDialog = new ProgressDialog(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
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
                adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray, fcStDataModel, filest, photoname, isExistP, resetstatus);
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

            @Override
            public void onMutilSuccess(List<String> files) {
                file = new File(files.get(0));
                filest = file.toString();
                isExistP = 2;
                adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray, fcStDataModel, filest, photoname, isExistP, resetstatus);
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
            case 1:
                switch (resetstatus) {
                    //未复测、未审核
                    case 1:
                    case 2:
                        Util.toastMsg("非当天复测日未复测数据或数据未审核不可查看");
                        break;
                    case 3:
                        tv_right.setVisibility(View.INVISIBLE);
                        fl_right.setEnabled(false);
                        IsEdit = false;
                        doGetDataService("2");
                        break;
                    default:
                        tv_right.setVisibility(View.INVISIBLE);
                        IsEdit = false;
                        doGetDataService("2");
                        break;
                }
                break;
            case 2:
                switch (resetstatus) {
                    case 1:
                    case 2:
                        doGetDataService("1");
                        break;
                    case 3:
                        tv_right.setVisibility(View.INVISIBLE);
                        IsEdit = false;
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
        child.add(2, "体脂");
        child.add(3, "内脂");
        childArray.add(0, child);
        child3.add(0, "胸围");
        child3.add(1, "腰围");
        child3.add(2, "臀围");
        child3.add(3, "上臂围");
        child3.add(4, "大腿围");
        child3.add(5, "小腿围");
        childArray.add(1, child2);
        childArray.add(2, child2);
        childArray.add(3, child3);
        exlisview_body = (ExpandableListView) findViewById(R.id.exlisview_body);
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
                                show_information("体脂", 50, 25, 1, 9, 0, 0, 2);
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

                        }
                        break;
                }

                return false;
            }
        });


    }

    private void doGetDataService(String type) {
        fuceSevice.doGetPreMeasureData(UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<FcStDataModel>>() {
            @Override
            public void success(ResponseData<FcStDataModel> fcStDataModelResponseData, Response response) {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //初始体重
            case R.id.ll_retestWrite_chu_weight:

                break;
            case R.id.ll_retestWrite_nowweight:
                if (gender.equals("1")) {
                    show_information("当前体重（斤）", 600, 100, 50, 9, 0, 0, 1);
                } else {
                    show_information("当前体重（斤）", 600, 150, 50, 9, 0, 0, 1);
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                break;

            case R.id.tv_takepho_guide:
                startActivity(new Intent(this, GuideActivity.class));
                break;
            case R.id.fl_right:
                if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight())) {
                    String message = "当前体重为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getPysical()) ? "" : fcStDataModel.getPysical())) {
                    String message = "体脂为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getFat()) ? "" : fcStDataModel.getFat())) {
                    String message = "内脂为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else if (isExistP == 0) {
                    String message = "请上传照片";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else {
                    doSetPostData();
                }
                break;

        }
    }

    void doPostInitData() {
        fuceSevice.doPostMeasuredData(UserInfoModel.getInstance().getToken(), multipartTypedOutput, new RequestCallback<ResponseData>() {
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

                gender = fcStDataModel.getGender();

                if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail())) {
                    isExistP = 1;
                    photoname = fcStDataModel.getImgThumbnail();

                } else if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    isExistP = 1;
                    photoname = fcStDataModel.getImg();
                }

                adapter = new MyExpandableListAdapter(this, this, childArray, fcStDataModel, filest, photoname, isExistP, resetstatus);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i = 0; i < groupCount; i++) {
                    if (i == 0) {
                        exlisview_body.expandGroup(i);
                    }
                }
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void doSetPostData() {
        multipartTypedOutput.addPart("accountId", new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
        multipartTypedOutput.addPart("classId", new TypedString(classId));
        if (isExistP == 2) {
            multipartTypedOutput.addPart("image", new TypedFile("image/png", new File(filest)));
        }
        multipartTypedOutput.addPart("pysical", new TypedString(fcStDataModel.getPysical()));//体脂
        multipartTypedOutput.addPart("fat", new TypedString(fcStDataModel.getFat()));//内脂
        multipartTypedOutput.addPart("weight", new TypedString(fcStDataModel.getWeight()));//现在体重
        multipartTypedOutput.addPart("circum", new TypedString(TextUtils.isEmpty(fcStDataModel.getCircum()) ? "" : fcStDataModel.getCircum().toString()));//胸围
        multipartTypedOutput.addPart("waistline", new TypedString(TextUtils.isEmpty(fcStDataModel.getWaistline()) ? "" : fcStDataModel.getWaistline().toString()));//腰围
        multipartTypedOutput.addPart("hiplie", new TypedString(TextUtils.isEmpty(fcStDataModel.getHiplie()) ? "" : fcStDataModel.getHiplie().toString()));//臀围
        multipartTypedOutput.addPart("upArmGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpArmGirth()) ? "" : fcStDataModel.getUpArmGirth().toString()));//上臂围
        multipartTypedOutput.addPart("upLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpLegGirth()) ? "" : fcStDataModel.getUpLegGirth().toString()));//大腿围
        multipartTypedOutput.addPart("doLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getDoLegGirth()) ? "" : fcStDataModel.getDoLegGirth().toString()));//小腿围
        Log.i("上传数据" + multipartTypedOutput.getPartCount());
        doPostInitData();
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
            adapter = new MyExpandableListAdapter(FcStuActivity.this, FcStuActivity.this, childArray, fcStDataModel, filest, photoname, isExistP, resetstatus);
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
                }

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
}