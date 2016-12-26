package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.model.InitComitModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.community.view.EditPersonalDynamicActivity;
import com.softtek.lai.module.home.view.HealthyFragment;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.module.picture.view.PictureActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

@InjectLayout(R.layout.activity_initwrite)
public class WriteFCActivity extends BaseActivity implements View.OnClickListener,
        Validator.ValidationListener/*,ImageFileSelector.Callback*/ {
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
    @InjectView(R.id.tv_write_chu_weight)
    EditText tv_write_chu_weight;
    //体脂
    @Required(order = 2, message = "体脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    //内脂
    @Required(order = 3, message = "内脂为必填项，请选择")
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

    String gender = "1";//性别
    private static final int GET_BODY = 2;//身体维度
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
    boolean isExistP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_delete.setOnClickListener(this);

    }

    @Override
    protected void initViews() {
        context = this;
        tv_right.setText("保存");
        progressDialog = new ProgressDialog(this);
        im_retestwrite_showphoto.setOnClickListener(this);
        vi_noweight.setVisibility(View.GONE);
        ll_retestWrite_nowweight.setVisibility(View.GONE);
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
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                im_delete.setVisibility(View.VISIBLE);
                Picasso.with(WriteFCActivity.this).load(new File(file)).fit().into(im_retestwrite_showphoto);
                filest = file;
                isExistP = false;


            }

            @Override
            public void onMutilSuccess(List<String> files) {
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                im_delete.setVisibility(View.VISIBLE);
                file = new File(files.get(0));
                Picasso.with(WriteFCActivity.this).load(file).fit().into(im_retestwrite_showphoto);
                filest = file.toString();
                isExistP = false;
            }

            @Override
            public void onError() {

            }
        });


        iv_email.setVisibility(View.INVISIBLE);


    }


    private static final int CAMERA_PREMISSION = 100;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //删除照片
            case R.id.im_delete:
                im_retestwrite_showphoto.setVisibility(View.GONE);
                im_delete.setVisibility(View.GONE);
                filest = "";
                break;
            //标题栏左返回
            case R.id.ll_left:
                finish();
                break;
            //标题栏右提交保存事件
            case R.id.tv_right:
                if (TextUtils.isEmpty(tv_write_chu_weight.getText())) {
                    String message = "初始体重为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else {
                    validateLife.validate();
                }
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
            //添加身体围度
            case R.id.btn_retest_write_addbody:
                Intent intent = new Intent(WriteFCActivity.this, BodyweiduActivity.class);
                intent.putExtra("retestWrite", fcStDataModel);
                intent.putExtra("Audited", 2);
                intent.putExtra("IsEdit", IsEdit);
                startActivityForResult(intent, GET_BODY);
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
        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            Log.i("》》》》》requestCode：" + requestCode + "resultCode：" + resultCode);
            fcStDataModel = (FcStDataModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite" + fcStDataModel);
        }
        if (requestCode == BODY && resultCode == RESULT_OK) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
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
                        imageFileSelector.selectImage(WriteFCActivity.this);
                    }
                }
            }).create().show();

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
                if (num == 0) {
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                } else if (num == 1) {

                } else if (num == 2) {
                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 3) {
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
        //验证成功
        //上传图片为空
        if (TextUtils.isEmpty(filest)) {
            //判断是否已经存在已上传图片
            if (isExistP) {
                progressDialog.setMessage("正在提交数据，请等待");
                progressDialog.show();
                doSetPostData(true);
            } else {
                String message = "请上传图片";
                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .create().show();
            }

        } else {
            progressDialog.setMessage("正在提交数据，请等待");
            progressDialog.show();
            doSetPostData(false);
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
                im_retestwrite_takephoto.setVisibility(View.INVISIBLE);
                btn_retest_write_addbody.setText("查看身体围度");
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
                tv_write_nick.setText(fcStDataModel.getUserName());//设置用户名
                tv_write_phone.setText(fcStDataModel.getMobile());//手机号
                if (!TextUtils.isEmpty(fcStDataModel.getPhoto())) {

                    Picasso.with(context).load(url + fcStDataModel.getPhoto()).fit().into(iv_write_head);//头像
                }
                if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail())) {
                    im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(url + fcStDataModel.getImgThumbnail()).fit().placeholder(R.drawable.default_icon_square).into(im_retestwrite_showphoto);//图片
                    photourl = fcStDataModel.getImgThumbnail();
                    Log.i("看看图片地址是什么" + photourl);
                    uri = fcStDataModel.getImgThumbnail();
                    isExistP = true;
                } else if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(url + fcStDataModel.getImg()).fit().placeholder(R.drawable.default_icon_square).into(im_retestwrite_showphoto);//图片
                    photourl = fcStDataModel.getImgThumbnail();
                    Log.i("看看图片地址是什么" + photourl);
                    uri = fcStDataModel.getImgThumbnail();
                    isExistP = true;
                }
                if (!TextUtils.isEmpty(fcStDataModel.getImg())) {
                    photoname = fcStDataModel.getImg();
                }
                tv_write_class.setText(fcStDataModel.getClassName());//班级名
                tv_retest_write_weekth.setText("班级周期");//当前周
                String Stardata[] = fcStDataModel.getStartDate().split("-");
                tv_write_starm.setText(Long.parseLong(Stardata[1]) + "");//开班月
                tv_write_stard.setText(Long.parseLong(Stardata[2]) + "");//开班日
                String Enddata[] = fcStDataModel.getEndDate().split("-");
                tv_write_endm.setText(Long.parseLong(Enddata[1]) + "");//结束月
                tv_write_endd.setText(Long.parseLong(Enddata[2]) + "");//结束日
                tv_write_chu_weight.setText("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight());//初始体重
                tv_retestWrite_tizhi.setText("0.0".equals(fcStDataModel.getPysical()) ? "" : fcStDataModel.getPysical());//体脂
                tv_retestWrite_neizhi.setText("0.0".equals(fcStDataModel.getFat()) ? "" : fcStDataModel.getFat());//内脂
                gender = fcStDataModel.getGender();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    /*l录入*/
    void doSetPostData(boolean status) {
        Log.i("图片文件" + "身体维度上传" + "胸围" + fcStDataModel.getCircum() + "腰围 " + fcStDataModel.getWaistline() + "臀围" + fcStDataModel.getHiplie() + "上臂围" + fcStDataModel.getUpArmGirth() + "大腿围" + fcStDataModel.getUpLegGirth() + "小腿围" + fcStDataModel.getDoLegGirth());
        multipartTypedOutput.addPart("accountId", new TypedString(userId + ""));
        multipartTypedOutput.addPart("classId", new TypedString(classId));
        if (!status) {
            multipartTypedOutput.addPart("image", new TypedFile("image/png", new File(filest)));
        }
        multipartTypedOutput.addPart("pysical", new TypedString(tv_retestWrite_tizhi.getText().toString()));//体脂
        multipartTypedOutput.addPart("fat", new TypedString(tv_retestWrite_neizhi.getText().toString()));//内脂
        multipartTypedOutput.addPart("ChuWeight", new TypedString(tv_write_chu_weight.getText().toString()));//初始体重
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
