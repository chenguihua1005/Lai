package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.ProgressDialog;
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
import retrofit.RetrofitError;
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

@InjectLayout(R.layout.activity_initaudit)
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
    @InjectView(R.id.tv_retestWrite_crium)
    TextView tv_retestWrite_crium;
    @InjectView(R.id.tv_retestWrite_waisline)
    TextView tv_retestWrite_waisline;
    @InjectView(R.id.tv_retestWrite_hiplie)
    TextView tv_retestWrite_hiplie;
    @InjectView(R.id.tv_retestWrite_Uparm)
    TextView tv_retestWrite_Uparm;
    @InjectView(R.id.tv_retestWrite_UpLeg)
    TextView tv_retestWrite_UpLeg;
    @InjectView(R.id.tv_retestWrite_doleg)
    TextView tv_retestWrite_doleg;
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    @InjectView(R.id.im_delete)
    ImageView im_delete;
    @InjectView(R.id.ll_retestWrite_nowweight)
    RelativeLayout ll_retestWrite_nowweight;
    @InjectView(R.id.ll_retestWrite_tizhi)
    RelativeLayout ll_retestWrite_tizhi;
    @InjectView(R.id.ll_retestWrite_neizhi)
    RelativeLayout ll_retestWrite_neizhi;
    @InjectView(R.id.ll_retestWrite_crium)
    RelativeLayout ll_retestWrite_crium;
    @InjectView(R.id.ll_retestWrite_waisline)
    RelativeLayout ll_retestWrite_waisline;
    @InjectView(R.id.ll_retestWrite_hiplie)
    RelativeLayout ll_retestWrite_hiplie;
    @InjectView(R.id.ll_retestWrite_Uparm)
    RelativeLayout ll_retestWrite_Uparm;
    @InjectView(R.id.ll_retestWrite_UpLeg)
    RelativeLayout ll_retestWrite_UpLeg;
    @InjectView(R.id.ll_retestWrite_doleg)
    RelativeLayout ll_retestWrite_doleg;

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    ProgressDialog progressDialog;

    FuceSevice fuceSevice;
    MeasuredDetailsModel measuredDetailsModel;
    FcAuditPostModel fcAuditPostModel;
    private static final int GET_BODY = 1;

    private int IsAudit = 0;
    String gender = "0";
    int resetdatestatus=1;

    private CharSequence[] items = {"拍照", "从相册选择照片"};

    Long accountId;
    String acmId, classId;
    String filest,photoname;
    File file;

    @Override
    protected void initViews() {
        tv_title.setText("复测审核");
        tv_write.setText("初始体重：");
        tv_right.setText("保存");
        progressDialog =new ProgressDialog(this);
        ll_retestWrite_crium.setOnClickListener(this);
        ll_retestWrite_waisline.setOnClickListener(this);
        ll_retestWrite_hiplie.setOnClickListener(this);
        ll_retestWrite_Uparm.setOnClickListener(this);
        ll_retestWrite_UpLeg.setOnClickListener(this);
        ll_retestWrite_doleg.setOnClickListener(this);
        im_retestwrite_showphoto.setOnClickListener(this);
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
            String url = AddressManager.get("photoHost");
            gender = measuredDetailsModel.getGender();
            if (!TextUtils.isEmpty(measuredDetailsModel.getPhoto())) {
                Picasso.with(this).load(url + measuredDetailsModel.getPhoto()).fit().into(iv_write_head);
            }
            if (!TextUtils.isEmpty(measuredDetailsModel.getImgThumbnail())) {
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                Picasso.with(this).load(url + measuredDetailsModel.getImgThumbnail()).fit().placeholder(R.drawable.default_icon_square).centerCrop().into(im_retestwrite_showphoto);
                Log.i("图片测试啦"+url+measuredDetailsModel.getImgThumbnail());
            }
            else if (!TextUtils.isEmpty(measuredDetailsModel.getImg()))
            {
                im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                Picasso.with(this).load(url + measuredDetailsModel.getImgThumbnail()).fit().placeholder(R.drawable.default_icon_square).centerCrop().into(im_retestwrite_showphoto);
                Log.i("加载原图啦"+url+measuredDetailsModel.getImg());
            }
            if (!TextUtils.isEmpty(measuredDetailsModel.getImg()))
            {
                photoname=measuredDetailsModel.getImg();
            }

            tv_write_class.setText(measuredDetailsModel.getClassName());
            tv_write_nick.setText(measuredDetailsModel.getUserName());
            tv_write_phone.setText(measuredDetailsModel.getMobile());
            tv_retest_write_weekth.setText("第"+measuredDetailsModel.getWeekNum()+"周");
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
            tv_retestWrite_crium.setText("0.0".equals(measuredDetailsModel.getCircum())?"":measuredDetailsModel.getCircum());
            tv_retestWrite_waisline.setText("0.0".equals(measuredDetailsModel.getWaistline())?"":measuredDetailsModel.getWaistline());
            tv_retestWrite_hiplie.setText("0.0".equals(measuredDetailsModel.getHiplie())?"":measuredDetailsModel.getHiplie());
            tv_retestWrite_Uparm.setText("0.0".equals(measuredDetailsModel.getUpArmGirth())?"":measuredDetailsModel.getUpArmGirth());
            tv_retestWrite_UpLeg.setText("0.0".equals(measuredDetailsModel.getUpLegGirth())?"":measuredDetailsModel.getUpLegGirth());
            tv_retestWrite_doleg.setText("0.0".equals(measuredDetailsModel.getDoLegGirth())?"":measuredDetailsModel.getDoLegGirth());

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //身体围度值传递
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            Log.i("》》》》》requestCode：" + requestCode + "resultCode：" + resultCode);
            measuredDetailsModel = (MeasuredDetailsModel) data.getSerializableExtra("initaudit");
            Log.i("新学员录入围度:initaudit" + measuredDetailsModel);
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
            case R.id.ll_retestWrite_crium:
                if (IsAudit!=1) {
                    //最大值，默认值，最小值；最大值，默认值，最小值
                    show_information("胸围", 200, 90, 50, 9, 0, 0, 4);
                }
                break;
            case R.id.ll_retestWrite_waisline:
                if (IsAudit!=1) {
                    show_information("腰围", 200, 80, 40, 9, 0, 0, 5);
                }
                break;
            case R.id.ll_retestWrite_hiplie:
                if (IsAudit!=1) {
                    show_information("臀围", 250, 90, 50, 9, 0, 0, 6);
                }
                break;
            case R.id.ll_retestWrite_Uparm:
                if (IsAudit!=1) {
                    show_information("上臂围", 70, 50, 10, 9, 0, 0, 7);
                }
                break;
            case R.id.ll_retestWrite_UpLeg:
                if (IsAudit!=1) {
                    show_information("大腿围", 90, 50, 10, 9, 0, 0, 8);
                }
                break;
            case R.id.ll_retestWrite_doleg:
                if (IsAudit!=1) {
                    show_information("小腿围", 70, 50, 10, 9, 0, 0, 9);
                }
                break;
            case R.id.im_retestwrite_showphoto:
                Intent intent1=new Intent(this, PreViewPicActivity.class);
//                ArrayList<String> images=new ArrayList<>();
                intent1.putExtra("photoname",photoname);
                intent1.putExtra("position",1);
                startActivity(intent1);
                break;

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
                else if(num==4)
                {
                    tv_retestWrite_crium.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==5)
                {
                    tv_retestWrite_waisline.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==6)
                {
                    tv_retestWrite_hiplie.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==7)
                {
                    tv_retestWrite_Uparm.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==8)
                {
                    tv_retestWrite_UpLeg.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==9)
                {
                    tv_retestWrite_doleg.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

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
        fcAuditPostModel.setCircum(tv_retestWrite_crium.getText().toString());//胸围
        fcAuditPostModel.setHiplie(tv_retestWrite_hiplie.getText().toString());//臀围
        fcAuditPostModel.setWaistline(tv_retestWrite_waisline.getText().toString());//腰围
        fcAuditPostModel.setUpArmGirth(tv_retestWrite_Uparm.getText().toString());
        fcAuditPostModel.setUpLegGirth(tv_retestWrite_UpLeg.getText().toString());
        fcAuditPostModel.setDoLegGirth(tv_retestWrite_doleg.getText().toString());
        doPostInitData();
    }

    void doPostInitData() {
        fuceSevice.doReviewMeasuredRecord(UserInfoModel.getInstance().getToken(), fcAuditPostModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                try {
                    int status = responseData.getStatus();
                    switch (status) {
                        case 200:
                            progressDialog.dismiss();
                            Intent intent=new Intent();
                            intent.putExtra("ACMID",acmId);
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
}
