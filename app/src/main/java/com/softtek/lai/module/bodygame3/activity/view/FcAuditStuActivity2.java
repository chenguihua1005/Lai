package com.softtek.lai.module.bodygame3.activity.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.FuceCheckExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.adapter.InitDataExpandableListAdapter;
import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.activity.presenter.FuceCheckPresenter;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.bodygame3.photowall.PublishDyActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.DragFloatActionButton;
import com.softtek.lai.widgets.DragFloatActionButtonCheng;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;


@InjectLayout(R.layout.activity_fcst)
public class FcAuditStuActivity2 extends BaseActivity<FuceCheckPresenter> implements Validator.ValidationListener, FuceCheckPresenter.FuceCheckView, View.OnClickListener {
    //标题栏
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.cheng_float)
    DragFloatActionButtonCheng cheng_float;

    @LifeCircleInject
    ValidateLife validateLife;

    ProgressDialog progressDialog;

    @InjectView(R.id.exlisview_body)
    ExpandableListView exlisview_body;

    private Long accountId;
    private String acmId, classId;

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


    int IsEdit = 1;//是否可编辑，1可编辑，2不可编辑

    private int IsAudit = 0;
    private int resetdatestatus = 1;//复测状态

    private MeasuredDetailsModel fcStDataModel;

    private String typeDate;


    @Override
    protected void initViews() {
        tv_right.setText("审核通过");
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);


        setPresenter(new FuceCheckPresenter(this));

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATE_UI_INPUTED_FUCECHECK));
    }

    @OnClick(R.id.cheng_float)
    public void enterIntoLaicheng(View view) {
        Intent intent = new Intent(FcAuditStuActivity2.this, FuceForStuActivity.class);//跳转到发布动态界面
        intent.putExtra("fucedata", fcStDataModel);
        intent.putExtra("type", 2);
        intent.putExtra("classId", classId);
        intent.putExtra("AccountId", accountId);
        intent.putExtra("from",UPDATE_UI_INPUTED_FUCECHECK);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void initDatas() {
        progressDialog = new ProgressDialog(this);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);

        IsAudit = getIntent().getIntExtra("IsAudit", 0);
        typeDate = getIntent().getStringExtra("typeDate");
        classId = getIntent().getStringExtra("classId");//classId

        acmId = getIntent().getStringExtra("ACMId");
        accountId = getIntent().getLongExtra("accountId", 0);
//        classId = getIntent().getStringExtra("classId");

        if (IsAudit != 0) {
            tv_right.setVisibility(View.INVISIBLE);
            cheng_float.setVisibility(View.INVISIBLE);
//            im_audit_states.setImageResource(R.drawable.passed);
        } else {
            cheng_float.setVisibility(View.VISIBLE);
            resetdatestatus = getIntent().getIntExtra("resetdatestatus", resetdatestatus);
            switch (resetdatestatus) {
                //过去复测日，只能查看
                case 1:
                    tv_right.setVisibility(View.INVISIBLE);
                    cheng_float.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    break;

            }

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
        exlisview_body.setAdapter(adapter);

        exlisview_body.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        if (isExistPhoto) {

                            Intent intent1 = new Intent(FcAuditStuActivity2.this, PreViewPicActivity.class);
//                ArrayList<String> images=new ArrayList<>();
                            intent1.putExtra("photoname", phtoPath);
                            intent1.putExtra("position", 1);
                            startActivity(intent1);
//                            Intent intent1 = new Intent(FcAuditStuActivity2.this, PreViewPicActivity.class);
//                            intent1.putExtra("images", phtoPath);
//                            intent1.putExtra("photoname", "");
//                            intent1.putExtra("IsEdit", 2);
//                            startActivity(intent1);
//                            startActivityForResult(intent1, GET_PRE);
                        }

                        break;
                    case 2:
//                        startActivity(new Intent(FcAuditStuActivity2.this, GuideActivity.class));
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


        if (IsAudit == 0) {
            exlisview_body.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    switch (i) {
                        case 0:
                            switch (i1) {
                                case 1:
                                    if ("1".equals(gender)) { //女的
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

                            }
                            break;
                    }

                    return false;
                }
            });
        }


        //获取后台数据

        getPresenter().getFuceCheckData(acmId);

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
//                        fcStDataModel.setWeight(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
//                        exlisview_body.setAdapter(adapter);
//                        int groupCount = exlisview_body.getCount();
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


    @Override
    public void onValidationSucceeded() {
        progressDialog.setMessage("正在提交数据，请等待");
        progressDialog.show();
        doSetPostData();
    }

    FcAuditPostModel fcAuditPostModel;

    private void doSetPostData() {
        fcAuditPostModel = new FcAuditPostModel();
        fcAuditPostModel.setACMId(acmId);
        fcAuditPostModel.setAccountId(accountId + "");
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
        doPostInitData();
    }


    FuceSevice fuceSevice;

    void doPostInitData() {
        fuceSevice.doReviewMeasuredRecord(UserInfoModel.getInstance().getToken(), fcAuditPostModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                try {
                    int status = responseData.getStatus();
                    switch (status) {
                        case 200:
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            intent.putExtra("ACMID", acmId);
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

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }


//    public FuceCheckExpandableListAdapter(Context context, List<List<String>> childArray, MeasuredDetailsModel fcStDataModel, int IsEdit) {

    @Override
    public void getFuceCheckData(MeasuredDetailsModel model) {
        fcStDataModel = model;
        Log.i(TAG, "获取数据=  " + new Gson().toJson(model));
        if (model != null) {
            FormData formData = new FormData();
            if (!TextUtils.isEmpty(model.getWeekNum())) {
                if (TextUtils.isEmpty(formData.formdata(Integer.parseInt(model.getWeekNum())))) {
                    tv_title.setText("复测审核");
                } else {
                    tv_title.setText("复测审核" + "(第" + formData.formdata(Integer.parseInt(model.getWeekNum())) + "周)");
                }
            }

            if (!TextUtils.isEmpty(model.getImg())) {
                isExistPhoto = true;
                phtoPath = model.getImg();
            } else {
                isExistPhoto = false;
                phtoPath = "";
            }

            adapter = new FuceCheckExpandableListAdapter(this, childArray, fcStDataModel, 1);//默认可编辑
            exlisview_body.setAdapter(adapter);


            int groupCount = exlisview_body.getCount();
            for (int i = 0; i < groupCount; i++) {
                if (i == 0) {
                    exlisview_body.expandGroup(i);
                }
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
                if (TextUtils.isEmpty(fcStDataModel.getWeight())) {
                    String message = "当前体重为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else {
                    validateLife.validate();
                }
                break;

        }

    }

    public static final String UPDATE_UI_INPUTED_FUCECHECK = "UPDATE_UI_INPUTED_FUCECHECK";

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && UPDATE_UI_INPUTED_FUCECHECK.equalsIgnoreCase(intent.getAction())) {
//                ACMID = intent.getStringExtra("ACMID");
//                getPresenter().getFuceCheckData(ACMID);
            }
        }
    };
}
