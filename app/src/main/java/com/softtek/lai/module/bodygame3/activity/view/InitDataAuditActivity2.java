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
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.FuceCheckExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.activity.presenter.FuceCheckPresenter;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.community.model.ImageResponse2;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.DragFloatActionButtonCheng;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;


@InjectLayout(R.layout.activity_fcst)
public class InitDataAuditActivity2 extends BaseActivity<FuceCheckPresenter> implements Validator.ValidationListener, FuceCheckPresenter.FuceCheckView, View.OnClickListener {
    //标题栏
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.cheng_float)
    DragFloatActionButtonCheng cheng_float;

    @LifeCircleInject
    ValidateLife validateLife;

    ProgressDialog progressDialog;

    @InjectView(R.id.exlisview_body)
    ExpandableListView exlisview_body;

    private FuceCheckExpandableListAdapter adapter;
    private String gender = "1";//性别


    private boolean isExistPhoto = false;//0没有图片1 有
    private String phtoPath = "";//图片路径


    private boolean IsZhankai = false;
    private static final int GET_PRE = 1;//查看大图

    private List<List<String>> childArray = new ArrayList<>();
    private List<String> child = new ArrayList<>();
    private List<String> child2 = new ArrayList<>();
    private List<String> child3 = new ArrayList<>();

    private MeasuredDetailsModel fcStDataModel;

    Long AccountId;//用户id
    String classId = " ";//班级id
    Context context;
    String ACMID;
    String images_url, files;//网络图片   拍照图片
    int isExistP = 0;//0没有图片1网络图片（后台有）2文件图片(新拍的照片)atePic;

    int IsAudit;//是否审核通过的

    private boolean canCommited = false;//已审核状态，中是否可以提交  FALSE:可编辑状态   true :可提交数据
    private String initWeight = ""; //记录初始体重

    private String typeDate;

    private int fromPage;
    int IsEdit = 1;//是否可编辑，1可编辑，2不可编辑
    int firstStatus;

    private static final int CAMERA_PREMISSION = 100;
    private ImageFileSelector imageFileSelector;
    private CharSequence[] items = {"拍照", "从相册选择照片"};

    private String GUANGBO_FROM = "";


    @Override
    protected void initViews() {
        tv_title.setText("初始数据审核");

        classId = getIntent().getStringExtra("classId");//没用
        AccountId = getIntent().getLongExtra("AccountId", 0);
        ACMID = getIntent().getStringExtra("ACMID");
        IsAudit = getIntent().getIntExtra("Audited", 1);
        typeDate = getIntent().getStringExtra("typeDate");
        fromPage = getIntent().getIntExtra("fromPage", -1);//11:未录入
        GUANGBO_FROM = getIntent().getStringExtra("guangbo");

        if (IsAudit == 1) {//已审核
            tv_right.setText("编辑");
            cheng_float.setVisibility(View.INVISIBLE);
            IsEdit = 2;
            firstStatus = 3; //审核通过
        } else {//未审核 ：0
            tv_right.setText("审核通过");//保存数据
            cheng_float.setVisibility(View.INVISIBLE);
            IsEdit = 1;
            firstStatus = 2; //待审核
        }

        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        setPresenter(new FuceCheckPresenter(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATE_UI_INPUTED));
    }

    @OnClick(R.id.cheng_float)
    public void enterIntoLaicheng(View view) {
        if (fcStDataModel != null) {
            Intent intent = new Intent(InitDataAuditActivity2.this, FuceForStuActivity.class);//跳转到发布动态界面
            intent.putExtra("fucedata", fcStDataModel);
            intent.putExtra("ACMID", ACMID);
            intent.putExtra("type", 3);
            intent.putExtra("classId", classId);
            intent.putExtra("AccountId", AccountId);
            intent.putExtra("from", UPDATE_UI_INPUTED);
            intent.putExtra("isAudit", IsAudit);//莱判断从哪一个列表进入（未审核、 已审核）
            startActivity(intent);
        }
    }


    @Override
    protected void initDatas() {
        progressDialog = new ProgressDialog(this);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);

        child.add(0, "初始体重");
        child.add(1, "体脂率");
        child.add(2, "内脂");
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
        exlisview_body.setAdapter(adapter);


        int px = DisplayUtil.dip2px(this, 300);
        //*************************
        imageFileSelector = new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px, px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {// String images_url, files;//网络图片   拍照图片
                files = file;
                isExistP = 2;
                images_url = "";

                adapter = new FuceCheckExpandableListAdapter(InitDataAuditActivity2.this, childArray, fcStDataModel, firstStatus, files, images_url, isExistP, IsEdit);//默认可编辑
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
            public void onMutilSuccess(List<String> filesList) {
                File file = new File(filesList.get(0));
                files = file.toString();
                isExistP = 2;
                images_url = "";
                adapter = new FuceCheckExpandableListAdapter(InitDataAuditActivity2.this, childArray, fcStDataModel, firstStatus, files, images_url, isExistP, IsEdit);//默认可编辑
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

        exlisview_body.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        if (isExistPhoto) {//    String images_url, files;//网络图片   拍照图片
                            if (canCommited) {
                                IsEdit = 1;
                            }
                            Intent intent1 = new Intent(InitDataAuditActivity2.this, PreViewPicActivity.class);
                            intent1.putExtra("images", files);//本地图片
                            intent1.putExtra("photoname", images_url);//网络图片
                            intent1.putExtra("IsEdit", IsEdit);
                            startActivityForResult(intent1, GET_PRE);
                        } else {//不存在照片  IsAudit = 1 {//已审核
                            if (IsAudit != 1 || canCommited) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InitDataAuditActivity2.this);
                                builder.setItems(items, new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            //拍照
                                            if (ActivityCompat.checkSelfPermission(InitDataAuditActivity2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                                //允许弹出提示
                                                ActivityCompat.requestPermissions(InitDataAuditActivity2.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                            } else {
                                                imageFileSelector.takePhoto(InitDataAuditActivity2.this);

                                            }
                                        } else if (which == 1) {
                                            //照片
                                            imageFileSelector.selectMutilImage(InitDataAuditActivity2.this, 1);
                                        }
                                    }
                                }).create().show();
                            }
                        }

                        break;
                    case 2:
                        startActivity(new Intent(InitDataAuditActivity2.this, GuideActivity.class));
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
                if (IsAudit == 0 || canCommited) {
                    switch (i) {
                        case 0:
                            switch (i1) {
                                case 0:
                                    if ("1".equals(gender)) { //女的
                                        show_information("初始体重", 600, 100, 50, 9, 0, 0, 1);
                                    } else {
                                        show_information("初始体重", 600, 150, 50, 9, 0, 0, 1);
                                    }
                                    break;
                                case 1:
                                    show_information("体脂率", 50, 25, 1, 9, 0, 0, 2);
                                    break;
                                case 2:
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

                            }
                            break;
                    }

                }

                return false;
            }
        });
        //获取后台数据
        getPresenter().getFuceCheckData(ACMID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageFileSelector.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==100){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                imageFileSelector.takePhoto(this);
            }
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
                    case 1:
                        fcStDataModel.setInitWeight(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                        fcStDataModel.setWeight(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
                    case 9: //小腿围
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


    @Override
    public void onValidationSucceeded() {
        if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight())) {
            String message = "初始体重为必填项，请选择";
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .create().show();
        } else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getPysical()) ? "" : fcStDataModel.getPysical())) {
            String message = "体脂率为必填项，请选择";
            new AlertDialog.Builder(InitDataAuditActivity2.this)
                    .setMessage(message)
                    .create().show();
        } else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getFat()) ? "" : fcStDataModel.getFat())) {
            String message = "内脂为必填项，请选择";
            new AlertDialog.Builder(InitDataAuditActivity2.this)
                    .setMessage(message)
                    .create().show();
        } else if (isExistP == 0) {
            String message = "请上传图片";
            new AlertDialog.Builder(InitDataAuditActivity2.this)
                    .setMessage(message)
                    .create().show();
        } else if (!TextUtils.isEmpty(initWeight) && !TextUtils.isEmpty(fcStDataModel.getWeight()) && !TextUtils.isEmpty(fcStDataModel.getThreshold()) && Math.abs(Float.parseFloat(fcStDataModel.getWeight()) - Float.parseFloat(initWeight)) > Float.parseFloat(fcStDataModel.getThreshold())) {
            String message = "检测到体重变化过大, 请检查体重与单位(斤)的正确性, 是否确认? ";
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage(message).setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    postImageFirstAndData();

                }
            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            }).create().show();
        } else {
            postImageFirstAndData();
        }

    }

    FcAuditPostModel fcAuditPostModel;


    private void postImageFirstAndData() {
        progressDialog.setMessage("正在提交数据，请等待");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        fcAuditPostModel = new FcAuditPostModel();
        if (!TextUtils.isEmpty(files)) {
            File image = new File(files);
            //先上传图片
            CommunityService service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
            service.uploadSingleImage(UserInfoModel.getInstance().getToken(), new TypedFile("image/*", image),
                    new RequestCallback<ResponseData<ImageResponse2>>() {
                        @Override
                        public void success(ResponseData<ImageResponse2> data, Response response) {
                            int status = data.getStatus();

                            if (status == 200) {
                                fcAuditPostModel.setFileName(data.getData().imgName);
                                fcAuditPostModel.setThumbnail(data.getData().thubName);
                                doSetPostData();
                            } else {
                                dialogShow("上传图片失败！");
                                dialogDissmiss();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            super.failure(error);
                            dialogShow("上传图片失败！");
                            dialogDissmiss();
                        }
                    });

        } else { //直接提交审核
            fcAuditPostModel.setFileName(fcStDataModel.getImg());
            fcAuditPostModel.setThumbnail(fcStDataModel.getImgThumbnail());
            doSetPostData();
        }

    }

    private void doSetPostData() {//    String images_url, files;//网络图片   拍照图片 AddressManager.get("photoHost") + fcStDataModel.getPhoto()

        fcAuditPostModel.setClassId(classId);
        fcAuditPostModel.setACMId(ACMID);
        fcAuditPostModel.setAccountId(AccountId + "");
        fcAuditPostModel.setReviewerId(UserInfoModel.getInstance().getUserId() + "");
        fcAuditPostModel.setWeight(fcStDataModel.getWeight());
        fcAuditPostModel.setPysical(fcStDataModel.getPysical());
        fcAuditPostModel.setFat(fcStDataModel.getFat());
        fcAuditPostModel.setCircum(fcStDataModel.getCircum());//胸围
        fcAuditPostModel.setHiplie(fcStDataModel.getHiplie());//臀围
        fcAuditPostModel.setWaistline(fcStDataModel.getWaistline());//腰围
        fcAuditPostModel.setUpArmGirth(fcStDataModel.getUpArmGirth());
        fcAuditPostModel.setUpLegGirth(fcStDataModel.getUpLegGirth());
        fcAuditPostModel.setDoLegGirth(fcStDataModel.getDoLegGirth());


        fcAuditPostModel.setBmi(fcStDataModel.getBmi());
        fcAuditPostModel.setFatFreeMass(fcStDataModel.getFatFreeMass());
        fcAuditPostModel.setBodyWaterRate(fcStDataModel.getBodyWaterRate());
        fcAuditPostModel.setBodyWater(fcStDataModel.getBodyWater());

        fcAuditPostModel.setMuscleMass(fcStDataModel.getMuscleMass());
        fcAuditPostModel.setBoneMass(fcStDataModel.getBoneMass());
        fcAuditPostModel.setBasalMetabolism(fcStDataModel.getBasalMetabolism());
        fcAuditPostModel.setPhysicalAge(fcStDataModel.getPhysicalAge());


        doPostInitData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
        //查看大图
        if (requestCode == GET_PRE && resultCode == RESULT_OK) {
            files = data.getStringExtra("images");
            if (TextUtils.isEmpty(files)) {
                isExistP = 1;
            } else {
                isExistP = 2;
            }
            adapter = new FuceCheckExpandableListAdapter(this, childArray, fcStDataModel, firstStatus, files, images_url, isExistP, IsEdit);//默认可编辑

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


    FuceSevice fuceSevice;

    void doPostInitData() {
        fuceSevice.doReviewInitData(UserInfoModel.getInstance().getToken(), fcAuditPostModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                try {
                    int status = responseData.getStatus();
                    switch (status) {
                        case 200:
                            progressDialog.dismiss();
                            if (!TextUtils.isEmpty(GUANGBO_FROM)) {//UPDATE_UI_CHUSHI_YISHENHE_TABLIST
                                if ("UPDATE_UI_CHUSHI_DAISHENHE_TABLIST".equals(GUANGBO_FROM)) {
                                    LocalBroadcastManager.getInstance(InitDataAuditActivity2.this).sendBroadcast(new Intent("UPDATE_UI_CHUSHI_DAISHENHE_TABLIST"));
                                    LocalBroadcastManager.getInstance(InitDataAuditActivity2.this).sendBroadcast(new Intent("UPDATE_UI_CHUSHI_YISHENHE_TABLIST"));
                                } else {
                                    LocalBroadcastManager.getInstance(InitDataAuditActivity2.this).sendBroadcast(new Intent("UPDATE_UI_CHUSHI_YISHENHE_TABLIST"));
                                }
                            }
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
        });
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }


    @Override
    public void getFuceCheckData(MeasuredDetailsModel model) {
        fcStDataModel = model;
        Log.i(TAG, "获取数据=  " + new Gson().toJson(model));
        if (model != null) {
            initWeight = fcStDataModel.getWeight();
            if (!TextUtils.isEmpty(model.getImg())) { //    int isExistP = 0;//0没有图片1网络图片（后台有）2文件图片(新拍的照片)atePic;
                // String images_url, files;//网络图片   拍照图片
                isExistPhoto = true;
                images_url = model.getImg();
                isExistP = 1;
            } else {
                isExistPhoto = false;
                isExistP = 0;
                images_url = "";
            }

            adapter = new FuceCheckExpandableListAdapter(this, childArray, fcStDataModel, firstStatus, files, images_url, isExistP, IsEdit);//默认可编辑
            exlisview_body.setAdapter(adapter);

            int groupCount = exlisview_body.getCount();
            for (int i = 0; i < groupCount; i++) {
                if (i == 0) {
                    exlisview_body.expandGroup(i);
                }
            }

            if (IsAudit != 1) {
                cheng_float.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //保存
            case R.id.tv_right:
                if (IsAudit != 1) {//待审核
                    if (TextUtils.isEmpty(fcStDataModel.getWeight())) {
                        String message = "当前体重为必填项，请选择";
                        new AlertDialog.Builder(this)
                                .setMessage(message)
                                .create().show();
                    } else {
                        validateLife.validate();
                    }
                } else if (!canCommited) {//已审核  处于编辑状态
                    tv_right.setText("保存");
                    canCommited = true;
                    cheng_float.setVisibility(View.VISIBLE);
                } else if (canCommited) {
                    validateLife.validate();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    public static final String UPDATE_UI_INPUTED = "UPDATE_UI_INPUTED";

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && UPDATE_UI_INPUTED.equalsIgnoreCase(intent.getAction())) {
                BleMainData result_model = (BleMainData) intent.getSerializableExtra("result_model");
                ACMID = intent.getStringExtra("acmid");
                if (result_model != null) {
//                    ACMID = result_model.getRecordId();

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

                    adapter = new FuceCheckExpandableListAdapter(InitDataAuditActivity2.this, childArray, fcStDataModel, firstStatus, files, images_url, isExistP, IsEdit);//默认可编辑
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

                    //                    同时刷新上一个页面
                    if (!TextUtils.isEmpty(GUANGBO_FROM)) {
                        LocalBroadcastManager.getInstance(InitDataAuditActivity2.this).sendBroadcast(new Intent(GUANGBO_FROM));
                    }
                }
            }
        }
    };

}
