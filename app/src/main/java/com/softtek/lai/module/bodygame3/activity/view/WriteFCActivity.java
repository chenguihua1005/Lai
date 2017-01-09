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
    boolean IsEdit = true;
    private ImageFileSelector imageFileSelector;
    String uri, photoname;
    int isExistP=0;//0没有图片1网络图片2文件图片
    boolean IsZhankai=false;
    private List<String> groupArray=new ArrayList<>();
    private List<List<String>> childArray=new ArrayList<>();
    private List<String> child=new ArrayList<>();
    private List<String> child2=new ArrayList<>();
    private List<String> child3=new ArrayList<>();
    MyExpandableListAdapter adapter;


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
                adapter=new MyExpandableListAdapter(WriteFCActivity.this,WriteFCActivity.this,childArray,fcStDataModel,filest,photoname,isExistP,firststatus);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i=0; i<groupCount; i++)
                {
                    if (i==0) {
                        exlisview_body.expandGroup(i);
                    }
                    if (i==3) {
                        if (IsZhankai) {
                            exlisview_body.expandGroup(i);
                        }
                    }
                };

            }

            @Override
            public void onMutilSuccess(List<String> files) {
                file = new File(files.get(0));
                filest = file.toString();
                isExistP = 2;
                adapter=new MyExpandableListAdapter(WriteFCActivity.this,WriteFCActivity.this,childArray,fcStDataModel,filest,photoname,isExistP,firststatus);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i=0; i<groupCount; i++)
                {
                    if (i==0) {
                        exlisview_body.expandGroup(i);
                    }
                    if (i==3)
                    {
                        if (IsZhankai)
                        {
                            exlisview_body.expandGroup(i);
                        }
                    }
                };
            }

            @Override
            public void onError() {

            }
        });

        iv_email.setVisibility(View.INVISIBLE);
        child.add(0,"初始体重");
        child.add(1,"体脂");
        child.add(2,"内脂");
        childArray.add(0,child);
        child3.add(0,"胸围");
        child3.add(1,"腰围");
        child3.add(2,"臀围");
        child3.add(3,"上臂围");
        child3.add(4,"大腿围");
        child3.add(5,"小腿围");
        childArray.add(1,child2);
        childArray.add(2,child2);
        childArray.add(3,child3);
        exlisview_body = (ExpandableListView) findViewById(R.id.exlisview_body);
        exlisview_body.setGroupIndicator(null);
        exlisview_body.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                switch (i)
                {
                    case 1:
                        if (isExistP==0)
                        {
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
                        }
                        else {
                            Intent intent1 = new Intent(WriteFCActivity.this, PreViewPicActivity.class);
                            intent1.putExtra("images", filest);
                            intent1.putExtra("photoname", photoname);
                            startActivityForResult(intent1,GET_PRE);
                        }

                        break;
                    case 2:
                        startActivity(new Intent(WriteFCActivity.this,GuideActivity.class));
                        break;
                    case 3:
                        if (IsZhankai)
                        {
                            IsZhankai=false;
                        }
                        else {
                            IsZhankai=true;
                        }
                        break;
                }
                return i==0||i==1||i==2?true:false;
            }
        });
        exlisview_body.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                switch (i)
                {
                    case 0:
                        switch (i1)
                        {
                            case 0:
                                if ("1".equals(gender)) {
                                    show_information("初始体重", 600, 100, 50, 9, 0, 0, 0);
                                }
                                else {
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
                        switch (i1)
                        {
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


    private static final int CAMERA_PREMISSION = 100;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //删除照片
            case R.id.im_delete:
//                im_retestwrite_showphoto.setVisibility(View.GONE);
                filest = "";
                break;
            //标题栏左返回
            case R.id.ll_left:
                finish();
                break;
            //标题栏右提交保存事件
            case R.id.tv_right:

                    validateLife.validate();

                break;
            //拍照事件
            case R.id.im_retestwrite_takephoto:
                SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
                boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isFirstRun) {
                    Intent intent1 = new Intent(this, GuideActivity.class);
                    startActivityForResult(intent1, BODY);
                    Log.d("debug", "第一次运行");
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

                    Log.d("debug", "不是第一次运行");
                }


                break;

            case R.id.ll_retestWrite_chu_weight:
                if (gender.equals("1")) {
                    show_information("初始体重（斤）", 600, 100, 50, 9, 0, 0, 0);
                } else {
                    show_information("初始体重（斤）", 600, 150, 50, 9, 0, 0, 0);
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                break;
            case R.id.im_retestwrite_showphoto:
                Intent intent1 = new Intent(this, PreViewPicActivity.class);
                intent1.putExtra("images", filest);
                intent1.putExtra("photoname", photoname);
                startActivity(intent1);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
        //查看大图
        if (requestCode == GET_PRE && resultCode == RESULT_OK) {
            filest=data.getStringExtra("images");
            if (TextUtils.isEmpty(filest))
            {
                isExistP=1;
            }
            else {
                isExistP=2;
            }
            adapter=new MyExpandableListAdapter(WriteFCActivity.this,WriteFCActivity.this,childArray,fcStDataModel,filest,photoname,isExistP,firststatus);
            exlisview_body.setAdapter(adapter);
            int groupCount = exlisview_body.getCount();
            for (int i=0; i<groupCount; i++)
            {
                if (i==0) {
                    exlisview_body.expandGroup(i);
                }
                if (i==3)
                {
                    if (IsZhankai)
                    {
                        exlisview_body.expandGroup(i);
                    }
                }
            };
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
                switch (num)
                {
                    case 0:
                        fcStDataModel.setWeight(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                        exlisview_body.setAdapter(adapter);
                        int groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 1:
                        break;
                    case 2:
                        fcStDataModel.setPysical(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 3:
                        fcStDataModel.setFat(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 4:
                        fcStDataModel.setCircum(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 5:
                        fcStDataModel.setWaistline(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 6:
                        fcStDataModel.setHiplie(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 7:
                        fcStDataModel.setUpArmGirth(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 8:
                        fcStDataModel.setUpLegGirth(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
                        break;
                    case 9:
                        fcStDataModel.setDoLegGirth(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                        exlisview_body.setAdapter(adapter);
                        groupCount = exlisview_body.getCount();
                        for (int i=0; i<groupCount; i++)
                        {
                            if (i==0) {
                                exlisview_body.expandGroup(i);
                            }
                            if (i==3)
                            {
                                if (IsZhankai)
                                {
                                    exlisview_body.expandGroup(i);
                                }
                            }
                        };
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
        if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getWeight())?"":fcStDataModel.getWeight()))
        {
            String message = "体重为必填";
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .create().show();
        }
        else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getPysical())?"":fcStDataModel.getPysical()))
        {
            String message = "体脂为必填";
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .create().show();
        }
        else if (TextUtils.isEmpty("0.0".equals(fcStDataModel.getFat())?"":fcStDataModel.getFat()))
        {
            String message = "内脂为必填";
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .create().show();
        }else if (isExistP==0) {
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
                IsEdit = false;
                doGetDataService("3");
                break;
        }


    }

    private void doGetDataService(String type) {
        service.doGetPreMeasureData(UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<FcStDataModel>>() {
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

    private static int READ_WRITER = 0X10;

    /*
    * 获取数据值
    * */
    void doSetData() {
        if (fcStDataModel != null) {
            try {

                final String url = AddressManager.get("photoHost");

                if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail())) {
                    photourl = fcStDataModel.getImgThumbnail();
                    Log.i("看看图片地址是什么" + photourl);
                    uri = fcStDataModel.getImgThumbnail();
                    isExistP = 1;
                } else if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    photourl = fcStDataModel.getImgThumbnail();
                    Log.i("看看图片地址是什么" + photourl);
                    uri = fcStDataModel.getImgThumbnail();
                    isExistP = 1;
                }
                if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    photoname = fcStDataModel.getImg();
                }
                gender = fcStDataModel.getGender();
                adapter= new MyExpandableListAdapter(this,this,childArray,fcStDataModel,filest,photoname,isExistP,firststatus);
                exlisview_body.setAdapter(adapter);
                int groupCount = exlisview_body.getCount();
                for (int i=0; i<groupCount; i++)
                {
                    if (i==0) {
                        exlisview_body.expandGroup(i);
                    }
                };
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    /*l录入*/
    void doSetPostData() {
        Log.i("图片文件" + "身体维度上传" +"体重"+fcStDataModel.getWeight()+ "胸围" + fcStDataModel.getCircum() + "腰围 " + fcStDataModel.getWaistline() + "臀围" + fcStDataModel.getHiplie() + "上臂围" + fcStDataModel.getUpArmGirth() + "大腿围" + fcStDataModel.getUpLegGirth() + "小腿围" + fcStDataModel.getDoLegGirth());
        multipartTypedOutput.addPart("accountId", new TypedString(userId + ""));
        multipartTypedOutput.addPart("classId", new TypedString(classId));
        if (isExistP==2) {
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
        Log.i("上传数据" + multipartTypedOutput.getPartCount());
        doPostInitData();
    }

    //录入请求
    private void doPostInitData() {
        service.doPostInitData(UserInfoModel.getInstance().getToken(), multipartTypedOutput, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                try {
                    switch (status) {
                        case 200:
                            progressDialog.dismiss();
                            Intent intent=new Intent();
                            int IsInitW=1;
                            intent.putExtra("IsInitW",IsInitW);
                            setResult(RESULT_OK,intent);
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


}
