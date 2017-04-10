package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.InitDataExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.adapter.MyExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
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
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_fcst)
public class WriteFCActivity extends BaseActivity implements View.OnClickListener,
        Validator.ValidationListener {
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;


    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.exlisview_body)
    ExpandableListView exlisview_body;

    String gender = "1";//性别
    private static final int GET_PRE = 1;//查看大图
    private static final int BODY = 3;
    private CharSequence[] items = {"拍照", "从相册选择照片"};
    FuceSevice service;
    private ProgressDialog progressDialog;
    MultipartTypedOutput multipartTypedOutput;
    Long userId;//用户id
    String classId = " ";//班级id
    Context context;
    String filest;
    File file;
    FcStDataModel fcStDataModel;
    String photourl, typeDate;
    int firststatus;
    int IsEdit = 1;//是否可编辑，1可编辑，2不可编辑
    private ImageFileSelector imageFileSelector;
    String uri, photoname;
    int isExistP = 0;//0没有图片1网络图片2文件图片
    boolean IsZhankai = false;
    private List<List<String>> childArray = new ArrayList<>();
    private List<String> child = new ArrayList<>();
    private List<String> child2 = new ArrayList<>();
    private List<String> child3 = new ArrayList<>();
    InitDataExpandableListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }

    @Override
    protected void initViews() {
        context = this;
        tv_right.setText("提交");
        progressDialog = new ProgressDialog(this);
    }


    @Override
    protected void initDatas() {
        title.setText("初始数据录入");//设置标题栏标题
        classId = getIntent().getStringExtra("classId");
        typeDate = getIntent().getStringExtra("typeDate");
        firststatus = getIntent().getIntExtra("firststatus", 0);//接收数据审核状态,//初始数据状态：1：未录入 2：未审核 3：已复测

        userId = UserInfoModel.getInstance().getUserId();
        Log.i("classid" + classId + "typedata" + typeDate);
        service = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        //获取数据接口
        doGetInfo();
        multipartTypedOutput = new MultipartTypedOutput();

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
                adapter = new InitDataExpandableListAdapter(WriteFCActivity.this, WriteFCActivity.this, childArray, fcStDataModel
                        , filest, photoname, isExistP, firststatus, IsEdit);
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
                adapter = new InitDataExpandableListAdapter(WriteFCActivity.this, WriteFCActivity.this, childArray, fcStDataModel
                        , filest, photoname, isExistP, firststatus, IsEdit);
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

        iv_email.setVisibility(View.INVISIBLE);
        child.add(0, "初始体重");
        child.add(1, "体脂");
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
        child3.add(8, "内脏脂肪指数");
        child3.add(9, "身体水分率");
        child3.add(10, "身体水分");

        child3.add(11, "肌肉量");
        child3.add(12, "骨量");
        child3.add(13, "基础代谢");
        child3.add(14, "身体年龄");

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
                            AlertDialog.Builder builder = new AlertDialog.Builder(WriteFCActivity.this);
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        //拍照
                                        if (ActivityCompat.checkSelfPermission(WriteFCActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                                //允许弹出提示
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                            } else {
                                                //不允许弹出提示
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                            }
                                        } else {
                                            imageFileSelector.takePhoto(WriteFCActivity.this);
                                        }
                                    } else if (which == 1) {
                                        //照片
                                        imageFileSelector.selectMutilImage(WriteFCActivity.this, 1);
                                    }
                                }
                            }).create().show();
                        } else {
                            Intent intent1 = new Intent(WriteFCActivity.this, PreViewPicActivity.class);
                            intent1.putExtra("images", filest);
                            intent1.putExtra("photoname", photoname);
                            intent1.putExtra("IsEdit", IsEdit);
                            startActivityForResult(intent1, GET_PRE);
                        }

                        break;
                    case 2:
                        startActivity(new Intent(WriteFCActivity.this, GuideActivity.class));
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
                                    if ("1".equals(gender)) {
                                        show_information("初始体重", 600, 100, 50, 9, 0, 0, 0);
                                    } else {
                                        show_information("初始体重", 600, 150, 50, 9, 0, 0, 0);
                                    }
                                    break;
                                case 1:
                                    show_information("体脂", 50, 25, 1, 9, 0, 0, 2);
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
                                    show_information("BMI", 200, 90, 50, 9, 0, 0, 10);
                                    break;
                                case 7:
                                    show_information("去脂体重", 200, 80, 40, 9, 0, 0, 11);
                                    break;
                                case 8:
                                    show_information("内脏脂肪指数", 250, 90, 50, 9, 0, 0, 12);
                                    break;
                                case 9:
                                    show_information("身体水分率", 70, 50, 10, 9, 0, 0, 13);
                                    break;
                                case 10:
                                    show_information("身体水分", 90, 50, 10, 9, 0, 0, 14);
                                    break;
                                case 11:
                                    show_information("肌肉量", 70, 50, 10, 9, 0, 0, 15);
                                    break;
                                case 12:
                                    show_information("骨量", 70, 50, 10, 9, 0, 0, 16);
                                    break;
                                case 13:
                                    show_information("基础代谢", 90, 50, 10, 9, 0, 0, 17);
                                    break;
                                case 14:
                                    show_information("身体年龄", 70, 50, 10, 9, 0, 0, 18);
                                    break;

                            }
                            break;
                    }

                    return false;
                }
            });
        }

    }


    private static final int CAMERA_PREMISSION = 100;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //标题栏左返回
            case R.id.ll_left:
                finish();
                break;
            //标题栏右提交保存事件
            case R.id.tv_right:
                validateLife.validate();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
        //查看大图
        if (requestCode == GET_PRE && resultCode == RESULT_OK) {
            filest = data.getStringExtra("images");
            if (TextUtils.isEmpty(filest)) {
                isExistP = 1;
            } else {
                isExistP = 2;
            }
            adapter = new InitDataExpandableListAdapter(WriteFCActivity.this, WriteFCActivity.this, childArray, fcStDataModel, filest, photoname, isExistP, firststatus, IsEdit);
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
                    case 1:
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
                        fcStDataModel.setBMI(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
                    case 12: {//viscusFatIndex;     //内脏脂肪指数
                        fcStDataModel.setViscusFatIndex(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
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
        //验证成功
        if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight())) {
            String message = "初始体重为必填项，请选择";
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
            //判断是否已经存在已上传图片
            String message = "请上传图片";
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .create().show();
        } else {
            progressDialog.setMessage("正在提交数据，请等待");
            progressDialog.show();
            doSetPostData();
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
        String message = failedRule.getFailureMessage();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create().show();
    }

    /*
        * 获取初始基本数据
        * */
    private void doGetInfo() {
        //初始数据状态：1：未录入 2：未审核 3：firststatus
        switch (firststatus) {
            case 1:
                doGetDataService("0");
                break;
            case 2:
                doGetDataService("0");
                break;
            default:
                tv_right.setVisibility(View.INVISIBLE);
                IsEdit = 2;
                doGetDataService("3");
                break;
        }
    }

    private void doGetDataService(String type) {
        service.doGetPreMeasureData(classId, UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<FcStDataModel>>() {
            @Override
            public void success(ResponseData<FcStDataModel> fcStDataModelResponseData, Response response) {
                int status = fcStDataModelResponseData.getStatus();
                try {
                    switch (status) {
                        case 200:
                            fcStDataModel = fcStDataModelResponseData.getData();
                            doSetData();
                            break;
                        default:
                            Util.toastMsg(fcStDataModelResponseData.getMsg());
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    * 获取数据值
    * */
    void doSetData() {
        if (fcStDataModel != null) {
            try {
                final String url = AddressManager.get("photoHost");
                if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail())) {
                    photourl = fcStDataModel.getImgThumbnail();
                    uri = fcStDataModel.getImgThumbnail();
                    isExistP = 1;
                } else if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    photourl = fcStDataModel.getImgThumbnail();
                    uri = fcStDataModel.getImgThumbnail();
                    isExistP = 1;
                }
                if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    photoname = fcStDataModel.getImg();
                }
                gender = fcStDataModel.getGender();
                adapter = new InitDataExpandableListAdapter(this, this, childArray, fcStDataModel, filest, photoname,
                        isExistP, firststatus, IsEdit);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i = 0; i < groupCount; i++) {
                    if (i == 0) {
                        exlisview_body.expandGroup(i);
                    }
                }
                ;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    /*l录入*/
    void doSetPostData() {
        Log.i("图片文件" + "身体维度上传" + "体重" + fcStDataModel.getWeight() + "胸围" + fcStDataModel.getCircum() + "腰围 " + fcStDataModel.getWaistline() + "臀围" + fcStDataModel.getHiplie() + "上臂围" + fcStDataModel.getUpArmGirth() + "大腿围" + fcStDataModel.getUpLegGirth() + "小腿围" + fcStDataModel.getDoLegGirth());
        multipartTypedOutput.addPart("accountId", new TypedString(userId + ""));
        multipartTypedOutput.addPart("classId", new TypedString(classId));
        if (isExistP == 2) {
            multipartTypedOutput.addPart("image", new TypedFile("image/png", new File(filest)));
        }
        multipartTypedOutput.addPart("pysical", new TypedString(fcStDataModel.getPysical()));//体脂
        multipartTypedOutput.addPart("fat", new TypedString(fcStDataModel.getFat()));//内脂
        multipartTypedOutput.addPart("ChuWeight", new TypedString(fcStDataModel.getWeight()));//初始体重
        multipartTypedOutput.addPart("circum", new TypedString(TextUtils.isEmpty(fcStDataModel.getCircum()) ? "" : fcStDataModel.getCircum().toString()));//胸围
        multipartTypedOutput.addPart("waistline", new TypedString(TextUtils.isEmpty(fcStDataModel.getWaistline()) ? "" : fcStDataModel.getWaistline().toString()));//腰围
        multipartTypedOutput.addPart("hiplie", new TypedString(TextUtils.isEmpty(fcStDataModel.getHiplie()) ? "" : fcStDataModel.getHiplie().toString()));//臀围
        multipartTypedOutput.addPart("upArmGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpArmGirth()) ? "" : fcStDataModel.getUpArmGirth().toString()));//上臂围
        multipartTypedOutput.addPart("upLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpLegGirth()) ? "" : fcStDataModel.getUpLegGirth().toString()));//大腿围
        multipartTypedOutput.addPart("doLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getDoLegGirth()) ? "" : fcStDataModel.getDoLegGirth().toString()));//小腿围
        doPostInitData();
    }

    //录入请求
    private void doPostInitData() {
        service.doPostInitData(classId, UserInfoModel.getInstance().getToken(), multipartTypedOutput, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                try {
                    switch (status) {
                        case 200:
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            int IsInitW = 1;
                            intent.putExtra("IsInitW", IsInitW);
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
        });
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
}
