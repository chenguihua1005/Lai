package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
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
public class FcAuditStuActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {
    @InjectView(R.id.iv_write_head)
    ImageView iv_write_head;
    @InjectView(R.id.tv_write_nick)
    TextView tv_write_nick;
    @InjectView(R.id.tv_write_phone)
    TextView tv_write_phone;
    @InjectView(R.id.tv_retest_write_weekth)
    TextView tv_retest_write_weekth;
    @InjectView(R.id.tv_write_starm)
    TextView tv_write_starm;
    @InjectView(R.id.tv_write_stard)
    TextView tv_write_stard;
    @InjectView(R.id.tv_write_endm)
    TextView tv_write_endm;
    @InjectView(R.id.tv_write_endd)
    TextView tv_write_endd;
    @InjectView(R.id.tv_write_class)
    TextView tv_write_class;
    @InjectView(R.id.tv_write)
    TextView tv_write;
    @InjectView(R.id.tv_write_chu_weight)
    TextView tv_write_chu_weight;
    @InjectView(R.id.tv_retestWrite_nowweight)
    TextView tv_retestWrite_nowweight;
    @Required(order = 1, message = "体脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    @Required(order = 2, message = "内脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;
    @InjectView(R.id.im_retestwrite_takephoto)
    ImageView im_retestwrite_takephoto;
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    @InjectView(R.id.im_delete)
    ImageView im_delete;
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;
    @InjectView(R.id.ll_retestWrite_nowweight)
    RelativeLayout ll_retestWrite_nowweight;
    @InjectView(R.id.ll_retestWrite_tizhi)
    RelativeLayout ll_retestWrite_tizhi;
    @InjectView(R.id.ll_retestWrite_neizhi)
    RelativeLayout ll_retestWrite_neizhi;

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    FuceSevice fuceSevice;
    MeasuredDetailsModel measuredDetailsModel;
    FcAuditPostModel fcAuditPostModel;
    private static final int BODY = 3;
    private static final int GET_BODY = 1;
    private ImageFileSelector imageFileSelector;

    private int IsAudit = 0;
    String gender = "0";
    int resetdatestatus=1;

    private CharSequence[] items = {"拍照", "从相册选择照片"};

    Long accountId;
    String acmId, classId;
    String filest;
    File file;

    @Override
    protected void initViews() {
        tv_title.setText("复测录入");
        tv_write.setText("初始体重");
        tv_right.setText("保存");
        IsAudit = getIntent().getIntExtra("IsAudit", 0);
        if (IsAudit != 0) {
            tv_right.setVisibility(View.INVISIBLE);
        } else {
            resetdatestatus=getIntent().getIntExtra("resetdatestatus",resetdatestatus);
            switch (resetdatestatus)
            {
                //过去复测日，只能查看
                case 1:
                    tv_right.setVisibility(View.INVISIBLE);
                    btn_retest_write_addbody.setText("查看身体围度");
                    break;
                case 2:
                    break;

            }

        }
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        im_delete.setOnClickListener(this);
        ll_retestWrite_nowweight.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        int px=DisplayUtil.dip2px(this,300);
        //*************************
        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                im_delete.setVisibility(View.VISIBLE);
                Picasso.with(FcAuditStuActivity.this).load(new File(file)).fit().into(im_retestwrite_showphoto);
                filest=file;

            }

            @Override
            public void onMutilSuccess(List<String> files) {
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                im_delete.setVisibility(View.VISIBLE);
                file=new File(files.get(0));
                Picasso.with(FcAuditStuActivity.this).load(file).fit().into(im_retestwrite_showphoto);
                filest=file.toString();

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    protected void initDatas() {
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        acmId = getIntent().getStringExtra("ACMId");
        accountId = getIntent().getLongExtra("accountId", 0);
        classId = getIntent().getStringExtra("classId");
        doData();
    }

    private void doData() {
        fuceSevice.doGetMeasuredDetails(UserInfoModel.getInstance().getToken(), acmId, new RequestCallback<ResponseData<MeasuredDetailsModel>>() {
            @Override
            public void success(ResponseData<MeasuredDetailsModel> measuredDetailsModelResponseData, Response response) {
                int status = measuredDetailsModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        measuredDetailsModel = measuredDetailsModelResponseData.getData();
                        doSetData();
                        break;
                    default:
                        Util.toastMsg(measuredDetailsModelResponseData.getMsg());
                        break;
                }
            }
        });
    }

    private void doSetData() {
        if (measuredDetailsModel != null) {
            String url = AddressManager.getUrl("photoHost");
            gender = measuredDetailsModel.getGender();
            if (!TextUtils.isEmpty(measuredDetailsModel.getPhoto())) {
                Picasso.with(this).load(url + measuredDetailsModel.getPhoto()).fit().into(iv_write_head);
            }
            if (!TextUtils.isEmpty(measuredDetailsModel.getImgThumbnail())) {
                Picasso.with(this).load(url + measuredDetailsModel.getImgThumbnail()).fit().into(im_retestwrite_showphoto);
            }

            tv_write_class.setText(measuredDetailsModel.getClassName());
            tv_write_nick.setText(measuredDetailsModel.getUserName());
            tv_write_phone.setText(measuredDetailsModel.getMobile());
            tv_retest_write_weekth.setText(measuredDetailsModel.getWeekNum());
            if (!TextUtils.isEmpty(measuredDetailsModel.getStartDate())) {
                String[] stardate = measuredDetailsModel.getStartDate().split("-");
                String[] stardate1 = stardate[2].split(" ");
                tv_write_starm.setText(Long.parseLong(stardate[1]) + "");
                tv_write_stard.setText(Long.parseLong(stardate1[0]) + "");
            }
            if (!TextUtils.isEmpty(measuredDetailsModel.getEndDate())) {
                String[] enddate = measuredDetailsModel.getEndDate().split("-");
                String[] enddate1 = enddate[2].split(" ");
                tv_write_endm.setText(Long.parseLong(enddate[1]) + "");
                tv_write_endd.setText(Long.parseLong(enddate1[0]) + "");
            }
            tv_write_chu_weight.setText("0.0".equals(measuredDetailsModel.getInitWeight()) ? "" : measuredDetailsModel.getInitWeight());
            tv_retestWrite_nowweight.setText("0.0".equals(measuredDetailsModel.getWeight()) ? "" : measuredDetailsModel.getWeight());
            tv_retestWrite_tizhi.setText("0.0".equals(measuredDetailsModel.getPysical()) ? "" : measuredDetailsModel.getPysical());
            tv_retestWrite_neizhi.setText("0.0".equals(measuredDetailsModel.getFat()) ? "" : measuredDetailsModel.getFat());

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);
        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            Log.i("》》》》》requestCode：" + requestCode + "resultCode：" + resultCode);
            measuredDetailsModel = (MeasuredDetailsModel) data.getSerializableExtra("initaudit");
            Log.i("新学员录入围度:initaudit" + measuredDetailsModel);
        }
        if (requestCode == BODY && resultCode == RESULT_OK) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        if (ActivityCompat.checkSelfPermission(FcAuditStuActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                //允许弹出提示
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                            } else {
                                //不允许弹出提示
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                            }
                        } else {
                            imageFileSelector.takePhoto(FcAuditStuActivity.this);
                        }
                    } else if (which == 1) {
                        //照片
                        imageFileSelector.selectImage(FcAuditStuActivity.this);
                    }
                }
            }).create().show();
            Log.d("debug", "不是第一次运行");
        }
    }

    private static final int CAMERA_PREMISSION = 100;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //保存
            case R.id.tv_right:
                if (TextUtils.isEmpty(tv_retestWrite_nowweight.getText())) {
                    String message = "现在体重为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                } else {
                    validateLife.validate();
                }
                break;
            case R.id.ll_retestWrite_nowweight:
                if (IsAudit == 0) {
                    if (gender.equals("1")) {
                        show_information("现在体重（斤）", 600, 100, 50, 9, 0, 0, 1);
                    } else {
                        show_information("现在体重（斤）", 600, 150, 50, 9, 0, 0, 1);
                    }
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                if (IsAudit == 0) {

                    show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                }
                break;
            case R.id.ll_retestWrite_neizhi:
                if (IsAudit == 0) {

                    show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                }
                break;
            case R.id.im_delete:
                im_retestwrite_showphoto.setVisibility(View.GONE);
                im_delete.setVisibility(View.GONE);
                filest = "";
                break;
            //添加身体围度
            case R.id.btn_retest_write_addbody:
                Intent intent = new Intent(FcAuditStuActivity.this, BodyweiduActivity.class);
                intent.putExtra("initaudit", measuredDetailsModel);
                intent.putExtra("Audited", IsAudit == 0 ? 3 : 4);
                startActivityForResult(intent, GET_BODY);
                break;
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
                                if (ActivityCompat.checkSelfPermission(FcAuditStuActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                        //允许弹出提示
                                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                    } else {
                                        //不允许弹出提示
                                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                    }
                                } else {
                                    imageFileSelector.takePhoto(FcAuditStuActivity.this);
                                }
                            } else if (which == 1) {
                                //照片
                                imageFileSelector.selectMutilImage(FcAuditStuActivity.this,1);
                            }
                        }
                    }).create().show();
                    Log.d("debug", "不是第一次运行");
                }


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
                imageFileSelector.takePhoto(FcAuditStuActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
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
                if (num == 0) {
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                    tv_write_chu_weight.setError(null);
                } else if (num == 1) {
                    tv_retestWrite_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    tv_retestWrite_nowweight.setError(null);
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
        if (!TextUtils.isEmpty(filest)) {
            doSetPostData();
        } else {
            String message = "请上传图片";
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

    private void doSetPostData() {
        fcAuditPostModel = new FcAuditPostModel();
        fcAuditPostModel.setACMId(acmId);
        fcAuditPostModel.setAccountId(accountId + "");
        fcAuditPostModel.setReviewerId(UserInfoModel.getInstance().getUserId() + "");
        fcAuditPostModel.setWeight(tv_retestWrite_nowweight.getText().toString());
        fcAuditPostModel.setPysical(tv_retestWrite_tizhi.getText().toString());
        fcAuditPostModel.setFat(tv_retestWrite_neizhi.getText().toString());
        fcAuditPostModel.setCircum(measuredDetailsModel.getCircum());
        fcAuditPostModel.setWaistline(measuredDetailsModel.getWaistline());
        fcAuditPostModel.setHipline(measuredDetailsModel.getHiplie());
        fcAuditPostModel.setUpArmGirth(measuredDetailsModel.getUpArmGirth());
        fcAuditPostModel.setUpLegGirth(measuredDetailsModel.getUpLegGirth());
        fcAuditPostModel.setDoLegGirth(measuredDetailsModel.getDoLegGirth());
        doPostInitData();
    }

    void doPostInitData() {
        fuceSevice.doReviewMeasuredRecord(UserInfoModel.getInstance().getToken(), fcAuditPostModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
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
}
