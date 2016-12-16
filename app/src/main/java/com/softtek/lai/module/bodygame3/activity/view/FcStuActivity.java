package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import java.io.File;

import butterknife.InjectView;
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

/**
 * Created by Terry on 2016/12/3.
 */

@InjectLayout(R.layout.activity_initwrite)
public class FcStuActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{
    @InjectView(R.id.iv_write_head)
    CircleImageView iv_write_head;//头像
    @InjectView(R.id.tv_write_nick)
    TextView tv_write_nick;//昵称
    @InjectView(R.id.tv_write_phone)
    TextView tv_write_phone;//手机号
    @InjectView(R.id.tv_write_class)
    TextView tv_write_class;//班级名称
    @InjectView(R.id.tv_retest_write_weekth)
    TextView tv_retest_write_weekth;//第几周
    @InjectView(R.id.tv_write_starm)
    TextView tv_write_starm;//开始月
    @InjectView(R.id.tv_write_stard)
    TextView tv_write_stard;//开始日
    @InjectView(R.id.tv_write_endm)
    TextView tv_write_endm;//结束月
    @InjectView(R.id.tv_write_endd)
    TextView tv_write_endd;//结束日
    @InjectView(R.id.tv_write_chu_weight)
    EditText tv_write_chu_weight;//初始体重
    @InjectView(R.id.tv_retestWrite_nowweight)
    EditText tv_retestWrite_nowweight;//现在体重
    @Required(order = 1,message = "体脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;//体脂
    @Required(order = 2,message = "内脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;//内脂
    @InjectView(R.id.tv_write)
    TextView tv_write;

    @InjectView(R.id.ll_retestWrite_chu_weight)
    RelativeLayout ll_retestWrite_chu_weight;
    @InjectView(R.id.ll_retestWrite_nowweight)
    RelativeLayout ll_retestWrite_nowweight;
    @InjectView(R.id.ll_retestWrite_tizhi)
    RelativeLayout ll_retestWrite_tizhi;
    @InjectView(R.id.ll_retestWrite_neizhi)
    RelativeLayout ll_retestWrite_neizhi;
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;
    @InjectView(R.id.im_retestwrite_takephoto)
    ImageView im_retestwrite_takephoto;
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    @InjectView(R.id.im_delete)
    ImageView im_delete;

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
    String gender="0";
    String classId,typeDate;
    private Long userId;
    private static final int GET_BODY=2;//身体维度
    private static final int BODY=3;
    private CharSequence[] items={"拍照","从相册选择照片"};
    private ImageFileCropSelector imageFileCropSelector;
    private static final int CAMERA_PREMISSION=100;
    MultipartTypedOutput multipartTypedOutput;
    @LifeCircleInject
    ValidateLife validateLife;
    String files;
    int resetstatus,resetdatestatus;
    boolean IsEdit=true;
    @Override
    protected void initViews() {
        tv_title.setText("复测录入");
        tv_right.setText("保存");
        tv_write.setText("初始体重");
        fl_right.setOnClickListener(this);
        im_delete.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        ll_retestWrite_nowweight.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);
        imageFileCropSelector=new ImageFileCropSelector(this);
        imageFileCropSelector.setQuality(50);
        imageFileCropSelector.setOutPutAspect(1,1);
        int px=Math.min(DisplayUtil.getMobileHeight(this),DisplayUtil.getMobileWidth(this));
        imageFileCropSelector.setOutPut(px,px);
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                im_delete.setVisibility(View.VISIBLE);
                Picasso.with(FcStuActivity.this).load(new File(file)).fit().into(im_retestwrite_showphoto);
                files=file;

                Log.i(files);
//                retestPre.goGetPicture(file);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    protected void initDatas() {
        fuceSevice= ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        classId=getIntent().getStringExtra("classId");
        typeDate=getIntent().getStringExtra("typeDate");
        userId=UserInfoModel.getInstance().getUserId();
        resetstatus=getIntent().getIntExtra("resetstatus",0);//接收数据审核状态,//复测状态：1：未复测 2：未审核 3：已复测
        resetdatestatus=getIntent().getIntExtra("resetdatestatus",0);//接收复测日状态//复测日状态  1:已过去 2：进行中 3：未开始
        doData();
        multipartTypedOutput=new MultipartTypedOutput();
        if (fcStDataModel!=null)
        {
            tv_write_chu_weight.setText(fcStDataModel.getInitWeight());//初始体重
            tv_retestWrite_nowweight.setText(fcStDataModel.getWeight());//现在体重
            tv_retestWrite_tizhi.setText(fcStDataModel.getPysical());//体脂
            tv_retestWrite_neizhi.setText(fcStDataModel.getFat());//内脂
            gender=fcStDataModel.getGender();
        }

    }

    private void doData() {
        //resetdatestatus=2,未当天复测日，可进行录入，type=1（复测录入）
        //resetdatestatus=1,过去复测日，可进行查看，type=2（复测查看）
        //复测状态：1：未复测 2：未审核 3：已复测resetstatus
        switch (resetdatestatus)
        {
            case 1:
                switch (resetstatus)
                {
                    case 1:
                    case 2:
                        Util.toastMsg("非当天复测日未复测数据或数据未审核不可查看");
                        btn_retest_write_addbody.setVisibility(View.GONE);
                        break;
                    case 3:
                        tv_right.setVisibility(View.INVISIBLE);
                        fl_right.setEnabled(false);
                        IsEdit=false;
                        doGetDataService("0");
                        break;
                }

                break;
            case 2:
                switch (resetstatus)
                {
                    case 1:
                    case 2:
                        doGetDataService("1");
                        break;
                    case 3:
                        tv_right.setVisibility(View.INVISIBLE);
                        IsEdit=false;
                        fl_right.setEnabled(false);
                        doGetDataService("0");
                        break;
                }
                break;
            default:
                Util.toastMsg("当前复测日未开始不可查看录入数据");
                btn_retest_write_addbody.setVisibility(View.GONE);
                tv_right.setVisibility(View.INVISIBLE);
                fl_right.setEnabled(false);
                break;
        }



    }

    private void doGetDataService(String type)
    {
        fuceSevice.doGetPreMeasureData(UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<FcStDataModel>>() {
            @Override
            public void success(ResponseData<FcStDataModel> fcStDataModelResponseData, Response response) {
                int status=fcStDataModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        fcStDataModel=fcStDataModelResponseData.getData();
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
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            //删除照片
            case R.id.im_delete:
                im_retestwrite_showphoto.setVisibility(View.GONE);
                im_delete.setVisibility(View.GONE);
                files="";
                break;
            //初始体重
            case R.id.ll_retestWrite_chu_weight:

                break;
            case R.id.ll_retestWrite_nowweight:
                if (gender.equals("1")) {
                    show_information("初始体重（斤）", 600, 100, 50, 9, 0, 0, 1);
                }
                else
                {
                    show_information("初始体重（斤）", 600, 150, 50, 9, 0, 0, 1);
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                show_information("体脂（%）",50,25,1,9,0,0,2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂",30,2,1,9,0,0,3);
                break;
            case R.id.btn_retest_write_addbody:
                Intent intent=new Intent(this, BodyweiduActivity.class);
                intent.putExtra("retestWrite",fcStDataModel);
                intent.putExtra("Audited",2);
                intent.putExtra("IsEdit",IsEdit);
                startActivityForResult(intent,GET_BODY);
                break;
            //拍照事件
            case R.id.im_retestwrite_takephoto:
                SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
                boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isFirstRun)
                {
                    Intent intent1=new Intent(this,GuideActivity.class);
                    startActivityForResult(intent1,BODY);
                    Log.d("debug", "第一次运行");
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();
                } else
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                if(ActivityCompat.checkSelfPermission(FcStuActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                    if(ActivityCompat.shouldShowRequestPermissionRationale(FcStuActivity.this,Manifest.permission.CAMERA)){
                                        //允许弹出提示
                                        ActivityCompat.requestPermissions(FcStuActivity.this,
                                                new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                    }else{
                                        //不允许弹出提示
                                        ActivityCompat.requestPermissions(FcStuActivity.this,
                                                new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                    }
                                }else {
                                    imageFileCropSelector.takePhoto(FcStuActivity.this);
                                }
                            } else if (which == 1) {
                                imageFileCropSelector.selectImage(FcStuActivity.this);
                            }
                        }
                    }).create().show();
                    Log.d("debug", "不是第一次运行");
                }


                break;
            case R.id.fl_right:
               if (TextUtils.isEmpty(tv_retestWrite_nowweight.getText()))
            {
                String message = "现在体重为必填项，请选择";
                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .create().show();
            }
                else {
                    validateLife.validate();
                }

                break;

        }
    }
    void doPostInitData()
    {
        fuceSevice.doPostMeasuredData(UserInfoModel.getInstance().getToken(), multipartTypedOutput, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        finish();
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

        });
    }
    void doSetData()
    {
        try {
            if (fcStDataModel!=null) {
                if (!TextUtils.isEmpty(fcStDataModel.getPhoto())) {
                    Picasso.with(this).load(AddressManager.get("photoHost")+fcStDataModel.getPhoto()).fit().into(iv_write_head);
                }
                gender=fcStDataModel.getGender();
                tv_write_nick.setText(fcStDataModel.getUserName());
                tv_write_phone.setText(fcStDataModel.getMobile());
                tv_write_class.setText(fcStDataModel.getClassName());
                tv_retest_write_weekth.setText(fcStDataModel.getWeekNum());
                if (!TextUtils.isEmpty(fcStDataModel.getStartDate())) {
                    String[] stardata = fcStDataModel.getStartDate().split("-");
                    String[] stardata1 = stardata[2].split(" ");
                    tv_write_starm.setText(stardata[1]);
                    tv_write_stard.setText(stardata1[0]);
                }
                if (!TextUtils.isEmpty(fcStDataModel.getEndDate())) {
                    String[] enddata = fcStDataModel.getEndDate().split("-");
                    String[] enddata1 = enddata[2].split(" ");
                    tv_write_endm.setText(enddata[1]);
                    tv_write_endd.setText(enddata1[0]);
                }
                if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail()))
                {
                    im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(AddressManager.get("photoHost")+fcStDataModel.getImgThumbnail()).fit().into(im_retestwrite_showphoto);//图片

                }
                tv_write_chu_weight.setText("0.0".equals(fcStDataModel.getInitWeight())?"":fcStDataModel.getInitWeight());
                tv_retestWrite_nowweight.setText("0.0".equals(fcStDataModel.getWeight())?"":fcStDataModel.getWeight());
                tv_retestWrite_tizhi.setText("0.0".equals(fcStDataModel.getPysical())?"":fcStDataModel.getPysical());
                tv_retestWrite_neizhi.setText("0.0".equals(fcStDataModel.getFat())?"":fcStDataModel.getFat());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void doSetPostData()
    {
        multipartTypedOutput.addPart("accountId",new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
        multipartTypedOutput.addPart("classId",new TypedString(classId));
        multipartTypedOutput.addPart("image", new TypedFile("image/png", new File(files)));
        multipartTypedOutput.addPart("pysical", new TypedString(tv_retestWrite_tizhi.getText().toString()));//体脂
        multipartTypedOutput.addPart("fat", new TypedString(tv_retestWrite_neizhi.getText().toString()));//内脂
        multipartTypedOutput.addPart("weight", new TypedString(tv_retestWrite_nowweight.getText().toString()));//现在体重
        multipartTypedOutput.addPart("circum", new TypedString(TextUtils.isEmpty(fcStDataModel.getCircum())?"":fcStDataModel.getCircum().toString()));//胸围
        multipartTypedOutput.addPart("waistline", new TypedString(TextUtils.isEmpty(fcStDataModel.getWaistline())?"":fcStDataModel.getWaistline().toString()));//腰围
        multipartTypedOutput.addPart("hiplie",new TypedString(TextUtils.isEmpty(fcStDataModel.getHiplie())?"":fcStDataModel.getHiplie().toString()));//臀围
        multipartTypedOutput.addPart("upArmGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpArmGirth())?"":fcStDataModel.getUpArmGirth().toString()));//上臂围
        multipartTypedOutput.addPart("upLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getUpLegGirth())?"":fcStDataModel.getUpLegGirth().toString()));//大腿围
        multipartTypedOutput.addPart("doLegGirth", new TypedString(TextUtils.isEmpty(fcStDataModel.getDoLegGirth())?"":fcStDataModel.getDoLegGirth().toString()));//小腿围
        Log.i("上传数据" + multipartTypedOutput.getPartCount());
        doPostInitData();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(FcStuActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode,resultCode,data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode,resultCode,data);
        //身体围度值传递
        if (requestCode==GET_BODY&&resultCode==RESULT_OK){
            Log.i("》》》》》requestCode："+requestCode+"resultCode："+resultCode);
            fcStDataModel=(FcStDataModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite"+fcStDataModel);
        }
        if (requestCode==BODY&&resultCode==RESULT_OK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        imageFileCropSelector.takePhoto(FcStuActivity.this);
                    } else if (which == 1) {
                        //照片
                        imageFileCropSelector.selectImage(FcStuActivity.this);
                    }
                }
            }).create().show();
            Log.d("debug", "不是第一次运行");
        }
    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker)view.findViewById(R.id.numberPicker1);
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
                if (num==0) {
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                    tv_write_chu_weight.setError(null);
                }
                else if (num==1)
                {
                    tv_retestWrite_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    tv_retestWrite_nowweight.setError(null);
                }
                else if (num==2)
                {
                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==3)
                {
                    tv_retestWrite_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

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
        if (!TextUtils.isEmpty(files)) {
            doSetPostData();
        }
        else {
            String message ="请上传图片";
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .create().show();
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
}
