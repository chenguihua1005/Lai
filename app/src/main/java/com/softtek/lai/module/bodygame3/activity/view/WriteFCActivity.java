package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.BuildConfig;
import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.InitComitModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygamest.view.GuideActivity;
import com.softtek.lai.module.newmemberentry.model.PhotModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

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

@InjectLayout(R.layout.activity_initwrite)
public class WriteFCActivity extends BaseActivity implements View.OnClickListener,
        Validator.ValidationListener/*,ImageFileSelector.Callback*/{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.im_retestwrite_takephoto)
    ImageView im_retestwrite_takephoto;
    //显示照片
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    //删除照片
    @InjectView(R.id.im_delete)
    ImageView im_delete;
    //信息点击弹框
    //初始体重
    @InjectView(R.id.ll_retestWrite_chu_weight)
    RelativeLayout ll_retestWrite_chu_weight;
    //现在体重
    @InjectView(R.id.ll_retestWrite_nowweight)
    RelativeLayout ll_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.ll_retestWrite_tizhi)
    RelativeLayout ll_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.ll_retestWrite_neizhi)
    RelativeLayout ll_retestWrite_neizhi;

    @LifeCircleInject
    ValidateLife validateLife;

    //初始体重
    @Required(order = 1,message = "现在体重必填项，请选择")
    @InjectView(R.id.tv_write_chu_weight)
    EditText tv_write_chu_weight;


    //现在体重
    @Required(order = 1,message = "现在体重必填项，请选择")
    @InjectView(R.id.tv_retestWrite_nowweight)
    EditText tv_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;

    //昵称
    @InjectView(R.id.tv_write_nick)
    TextView tv_write_nick;
    //手机号
    @InjectView(R.id.tv_write_phone)
    TextView tv_write_phone;
    //头像
    @InjectView(R.id.iv_write_head)
    CircleImageView iv_write_head;
    //班级名称
    @InjectView(R.id.tv_write_class)
    TextView tv_write_class;
    //第几周期
    @InjectView(R.id.tv_retest_write_weekth)
    TextView tv_retest_write_weekth;
    //开始月份
    @InjectView(R.id.tv_write_starm)
    TextView tv_write_starm;
    //开始日期
    @InjectView(R.id.tv_write_stard)
    TextView tv_write_stard;
    //结束月份
    @InjectView(R.id.tv_write_endm)
    TextView tv_write_endm;
    //结束日期
    @InjectView(R.id.tv_write_endd)
    TextView tv_write_endd;
    @InjectView(R.id.rootlayout)
    RelativeLayout rootlayout;
    @InjectView(R.id.vi_noweight)
    View vi_noweight;

    String gender="1";//性别
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    private static final int GET_BODY=2;//身体维度
    private static final int BODY=3;
    private CharSequence[] items={"拍照","从相册选择照片"};
    String isState="true";
    FuceSevice service;
    private ProgressDialog progressDialog;
    private ImageFileCropSelector imageFileCropSelector;
    InitComitModel initComitModel;
    InitDataModel initDataModel;
    MultipartTypedOutput multipartTypedOutput;
    Long accountId=Long.parseLong(userInfoModel.getUser().getUserid());//用户id
    String Classid="";//班级id
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);
        ll_retestWrite_nowweight.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_delete.setOnClickListener(this);
        tv_retestWrite_nowweight.setEnabled(false);

    }

    @Override
    protected void initViews() {
        context=this;
        progressDialog = new ProgressDialog(this);
        im_retestwrite_showphoto.setOnClickListener(this);
        vi_noweight.setVisibility(View.GONE);
        ll_retestWrite_nowweight.setVisibility(View.GONE);
    }


    @Override
    protected void initDatas() {
        title.setText("初始数据录入");//设置标题栏标题
        tv_right.setText("保存");//保存数据
        service = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        if(BuildConfig.DEBUG) {
            doGetInfo(Long.parseLong("3399"),"C4E8E179-FD99-4955-8BF9-CF470898788B");
            Util.toastMsg("C4E8E179-FD99-4955-8BF9-CF470898788B");
        }
        else {
            //获取数据接口
            doGetInfo(Long.parseLong("3399"),"C4E8E179-FD99-4955-8BF9-CF470898788B");
//            doGetInfo(accountId,classid);
            Util.toastMsg("不是debug");
        }
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
                Picasso.with(WriteFCActivity.this).load(new File(file)).fit().into(im_retestwrite_showphoto);
                multipartTypedOutput=new MultipartTypedOutput();
                multipartTypedOutput.addPart("accountId",new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
                String classID = "C4E8E179-FD99-4955-8BF9-CF470898788B";
                multipartTypedOutput.addPart("classId",new TypedString(classID));

                multipartTypedOutput.addPart("pysical",new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
                multipartTypedOutput.addPart("Fat",new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
                multipartTypedOutput.addPart("ChuWeight",new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
                multipartTypedOutput.addPart("Circum",new TypedString(UserInfoModel.getInstance().getUser().getUserid()));
                multipartTypedOutput.addPart("image",new TypedFile("image/png", new File(file)));

//                retestPre.goGetPicture(file);
            }

            @Override
            public void onError() {

            }
        });

        iv_email.setVisibility(View.INVISIBLE);


    }
    /*
    * 获取初始基本数据
    * */
    private void doGetInfo(Long accountId,String classId) {
        service.dogetInitData(UserInfoModel.getInstance().getToken(),accountId, classId, new RequestCallback<ResponseData<InitDataModel>>() {
            @Override
            public void success(ResponseData<InitDataModel> initDataModelResponseData, Response response) {
                int status=initDataModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        try {
                            initDataModel=initDataModelResponseData.getData();
                            if (initDataModel!=null)
                            {
                                tv_write_nick.setText(initDataModel.getUserName());//设置用户名
                                tv_write_phone.setText(initDataModel.getMobile());//手机号
                                if (!TextUtils.isEmpty(initDataModel.getPhoto()))
                            {
                                String url= AddressManager.get("photoHost");
                                Picasso.with(context).load(url+initDataModel.getPhoto()).fit().into(iv_write_head);//头像
                            }
                                tv_write_class.setText(initDataModel.getClassName());//班级名
                                tv_retest_write_weekth.setText(initDataModel.getWeekNum());//当前周
                                String Stardata[]=initDataModel.getStartDate().split("-");
                                tv_write_starm.setText(Long.parseLong(Stardata[1])+"");//开班月
                                tv_write_stard.setText(Long.parseLong(Stardata[2])+"");//开班日
                                String Enddata[]=initDataModel.getEndDate().split("-");
                                tv_write_endm.setText(Long.parseLong(Enddata[1])+"");//结束月
                                tv_write_endd.setText(Long.parseLong(Enddata[2])+"");//结束日
//                                d
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        Util.toastMsg(initDataModelResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void doPostInitData()
    {
        service.doPostInitData(UserInfoModel.getInstance().getToken(), multipartTypedOutput, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Util.toastMsg(responseData.getMsg());

            }
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Subscribe
    public void doGetPhoto(PhotModel photModel) {
        System.out.println("照片名称" + photModel.getImg());
//        retestWrite.setImage(photModel.getImg());
    }

    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //删除照片
            case R.id.im_delete:
                im_retestwrite_showphoto.setVisibility(View.GONE);
                im_delete.setVisibility(View.GONE);
//                retestWrite.setImage("");
                break;
            //标题栏左返回
            case R.id.ll_left:
                finish();
            break;
            //标题栏右提交保存事件
            case R.id.tv_right:
                tv_retestWrite_nowweight.setEnabled(true);
                validateLife.validate();
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
                                if(ActivityCompat.checkSelfPermission(WriteFCActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                    if(ActivityCompat.shouldShowRequestPermissionRationale(WriteFCActivity.this,Manifest.permission.CAMERA)){
                                        //允许弹出提示
                                        ActivityCompat.requestPermissions(WriteFCActivity.this,
                                                new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                    }else{
                                        //不允许弹出提示
                                        ActivityCompat.requestPermissions(WriteFCActivity.this,
                                                new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                    }
                                }else {
                                    imageFileCropSelector.takePhoto(WriteFCActivity.this);
                                }
                            } else if (which == 1) {
                                imageFileCropSelector.selectImage(WriteFCActivity.this);
                            }
                        }
                    }).create().show();
                    Log.d("debug", "不是第一次运行");
                }


                break;
            //添加身体围度
            case R.id.btn_retest_write_addbody:
//                Intent intent=new Intent(WriteFCActivity.this, BodyweiduActivity.class);
//                intent.putExtra("retestWrite",retestWrite);
//                intent.putExtra("isState",isState);
//                startActivityForResult(intent,GET_BODY);
                break;
            case R.id.ll_retestWrite_chu_weight:
                if (gender.equals("1")) {
                    show_information("初始体重（斤）", 600, 100, 50, 9, 0, 0, 0);
                }
                else
                {
                    show_information("初始体重（斤）", 600, 150, 50, 9, 0, 0, 0);
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                show_information("体脂（%）",50,25,1,9,0,0,2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂",30,2,1,9,0,0,3);
                break;
            case R.id.im_retestwrite_showphoto:
                /*Intent intent1=new Intent(this,PictureActivity.class);
                ArrayList<String> imags=new ArrayList<>();
                intent1.putExtra("images",imags);
                intent1.putExtra("position",0);
                startActivity(intent1);*/
                break;


        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(WriteFCActivity.this);

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
//            retestWrite=(RetestWriteModel) data.getSerializableExtra("retestWrite");
//            Log.i("新学员录入围度:retestWrite"+retestWrite);
        }
        if (requestCode==BODY&&resultCode==RESULT_OK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        imageFileCropSelector.takePhoto(WriteFCActivity.this);
                    } else if (which == 1) {
                        //照片
                        imageFileCropSelector.selectImage(WriteFCActivity.this);
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
        Snackbar.make(rootlayout,"暂不能提交",Snackbar.LENGTH_SHORT)
//                .setAction("Undo", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                })
                .setActionTextColor(getResources().getColor(R.color.red))
                .show();
//        if (TextUtils.isEmpty(retestWrite.getImage()))
//        {
//            Util.toastMsg("请上传照片");
//        }
//        else {
//            retestWrite.setInitWeight(tv_write_chu_weight.getText() + "");
//            retestWrite.setWeight(tv_retestWrite_nowweight.getText() + "");
//            retestWrite.setPysical(tv_retestWrite_tizhi.getText() + "");
//            retestWrite.setFat(tv_retestWrite_neizhi.getText() + "");
//            retestWrite.setClassId(classid);
//            retestWrite.setAccountId(acountid);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setMessage("数据刷新中...");
//            progressDialog.show();
//            retestPre.doPostWrite(Long.parseLong(acountid), loginid, retestWrite, this, progressDialog);
//        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }



    /*@Override
    public void onSuccess(String file) {
        im_retestwrite_showphoto.setVisibility(View.VISIBLE);
        im_delete.setVisibility(View.VISIBLE);
        Picasso.with(this).load(new File(file)).fit().into(im_retestwrite_showphoto);
        retestPre.goGetPicture(file);
    }

    @Override
    public void onError() {

    }*/
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
