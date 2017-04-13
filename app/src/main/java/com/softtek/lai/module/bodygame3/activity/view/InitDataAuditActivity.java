package com.softtek.lai.module.bodygame3.activity.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
import com.softtek.lai.module.bodygame3.activity.model.InitAuditPModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_initaudit)
public class InitDataAuditActivity extends BaseActivity implements View.OnClickListener,
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
    //显示照片
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
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

    //BMI
    @InjectView(R.id.ll_doBMI)
    RelativeLayout ll_doBMI;
    @InjectView(R.id.tv_doBMI)
    TextView tv_doBMI;

    //去脂体重
    @InjectView(R.id.ll_fatFreeMass)
    RelativeLayout ll_fatFreeMass;
    @InjectView(R.id.tv_fatFreeMass)
    TextView tv_fatFreeMass;

    //内脏脂肪指数
    @InjectView(R.id.ll_viscusFatIndex)
    RelativeLayout ll_viscusFatIndex;
    @InjectView(R.id.tv_viscusFatIndex)
    TextView tv_viscusFatIndex;

    //身体水分率
    @InjectView(R.id.ll_bodyWaterRate)
    RelativeLayout ll_bodyWaterRate;
    @InjectView(R.id.tv_bodyWaterRate)
    TextView tv_bodyWaterRate;

    //身体水分
    @InjectView(R.id.ll_bodyWater)
    RelativeLayout ll_bodyWater;
    @InjectView(R.id.tv_bodyWater)
    TextView tv_bodyWater;

    //肌肉量
    @InjectView(R.id.ll_muscleMass)
    RelativeLayout ll_muscleMass;
    @InjectView(R.id.tv_muscleMass)
    TextView tv_muscleMass;

    //骨量
    @InjectView(R.id.ll_boneMass)
    RelativeLayout ll_boneMass;
    @InjectView(R.id.tv_boneMass)
    TextView tv_boneMass;

    //基础代谢
    @InjectView(R.id.ll_basalMetabolism)
    RelativeLayout ll_basalMetabolism;
    @InjectView(R.id.tv_basalMetabolism)
    TextView tv_basalMetabolism;

    //身体年龄
    @InjectView(R.id.ll_physicalAge)
    RelativeLayout ll_physicalAge;
    @InjectView(R.id.tv_physicalAge)
    TextView tv_physicalAge;

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
    //开始日期
    @InjectView(R.id.tv_write_star)
    TextView tv_write_star;
    //结束riqi
    @InjectView(R.id.tv_write_end)
    TextView tv_write_end;
    @InjectView(R.id.rootlayout)
    RelativeLayout rootlayout;
    @InjectView(R.id.vi_noweight)
    View vi_noweight;
    @InjectView(R.id.im_audit_states)
    ImageView im_audit_states;
    //删除照片
//    @InjectView(R.id.im_delete)
//    ImageView im_delete;

    String gender = "1";//性别
    private static final int GET_BODY = 1;//身体维度
    FuceSevice service;
    private ProgressDialog progressDialog;
    InitAuditPModel initAuditPModel;
    MeasuredDetailsModel measuredDetailsModel;
    Long AccountId;//用户id
    String classId = " ";//班级id
    Context context;
    String files, ACMID;
    String photoname;
    int IsAudit;

    @Override
    protected void initViews() {
        context = this;
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
//        im_delete.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        im_retestwrite_showphoto.setOnClickListener(this);
        vi_noweight.setVisibility(View.GONE);
        ll_retestWrite_nowweight.setVisibility(View.GONE);
        ll_retestWrite_crium.setOnClickListener(this);
        ll_retestWrite_waisline.setOnClickListener(this);
        ll_retestWrite_hiplie.setOnClickListener(this);
        ll_retestWrite_Uparm.setOnClickListener(this);
        ll_retestWrite_UpLeg.setOnClickListener(this);
        ll_retestWrite_doleg.setOnClickListener(this);

        ll_doBMI.setOnClickListener(this);
        ll_fatFreeMass.setOnClickListener(this);
        ll_viscusFatIndex.setOnClickListener(this);
        ll_bodyWaterRate.setOnClickListener(this);
        ll_bodyWater.setOnClickListener(this);
        ll_muscleMass.setOnClickListener(this);
        ll_boneMass.setOnClickListener(this);
        ll_basalMetabolism.setOnClickListener(this);
        ll_physicalAge.setOnClickListener(this);
    }


    @Override
    protected void initDatas() {
        title.setText("初始数据审核");//设置标题栏标题
        classId = getIntent().getStringExtra("classId");
        AccountId = getIntent().getLongExtra("AccountId", 0);
        ACMID = getIntent().getStringExtra("ACMID");
        IsAudit = getIntent().getIntExtra("Audited", 1);
        if (IsAudit == 1) {
            tv_right.setVisibility(View.INVISIBLE);
            im_audit_states.setImageResource(R.drawable.passed);

        } else {
            tv_right.setText("审核通过");//保存数据
        }

        service = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        //获取数据接口
        doGetInfo();

        iv_email.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //删除照片
//            case R.id.im_delete:
//                im_retestwrite_showphoto.setVisibility(View.GONE);
//                im_delete.setVisibility(View.GONE);
//                files = "";
//                break;
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


            case R.id.ll_retestWrite_chu_weight:
                if (IsAudit != 1) {
                    if (gender.equals("0")) {
                        show_information("初始体重（斤）", 600, 100, 50, 9, 0, 0, 0);
                    } else {
                        show_information("初始体重（斤）", 600, 150, 50, 9, 0, 0, 0);
                    }
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                if (IsAudit != 1) {
                    show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                }
                break;
            case R.id.ll_retestWrite_neizhi:
                if (IsAudit != 1) {
                    show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                }
                break;
            case R.id.ll_retestWrite_crium:
                if (IsAudit != 1) {
                    //最大值，默认值，最小值；最大值，默认值，最小值
                    show_information("胸围", 200, 90, 50, 9, 0, 0, 4);
                }
                break;
            case R.id.ll_retestWrite_waisline:
                if (IsAudit != 1) {
                    show_information("腰围", 200, 80, 40, 9, 0, 0, 5);
                }
                break;
            case R.id.ll_retestWrite_hiplie:
                if (IsAudit != 1) {
                    show_information("臀围", 250, 90, 50, 9, 0, 0, 6);
                }
                break;
            case R.id.ll_retestWrite_Uparm:
                if (IsAudit != 1) {
                    show_information("上臂围", 70, 50, 10, 9, 0, 0, 7);
                }
                break;
            case R.id.ll_retestWrite_UpLeg:
                if (IsAudit != 1) {
                    show_information("大腿围", 90, 50, 10, 9, 0, 0, 8);
                }
                break;
            case R.id.ll_retestWrite_doleg:
                if (IsAudit != 1) {
                    show_information("小腿围", 70, 50, 10, 9, 0, 0, 9);
                }
                break;
            case R.id.im_retestwrite_showphoto:
                Intent intent1 = new Intent(this, PreViewPicActivity.class);
                intent1.putExtra("images", files);
                intent1.putExtra("photoname", photoname);
                intent1.putExtra("position", 1);
                startActivity(intent1);
                break;

            case R.id.ll_doBMI:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("BMI", 50, 25, 0, 9, 0, 0, 10);
                    } else {
                        show_information("BMI", 50, 27, 0, 9, 0, 0, 10);
                    }
                }
                break;
            case R.id.ll_fatFreeMass:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("去脂体重", 180, 40, 0, 9, 0, 0, 11);
                    } else {
                        show_information("去脂体重", 180, 60, 0, 9, 0, 0, 11);
                    }
                }
                break;
            case R.id.ll_viscusFatIndex:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("内脏脂肪指数", 30, 10, 0, 9, 0, 0, 12);
                    } else {
                        show_information("内脏脂肪指数", 30, 10, 0, 9, 0, 0, 12);
                    }
                }
                break;
            case R.id.ll_bodyWaterRate:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("身体水分率", 80, 50, 0, 9, 0, 0, 13);
                    } else {
                        show_information("身体水分率", 80, 55, 0, 9, 0, 0, 13);
                    }
                }
                break;
            case R.id.ll_bodyWater:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("身体水分", 160, 30, 0, 9, 0, 0, 14);
                    } else {
                        show_information("身体水分", 160, 40, 0, 9, 0, 0, 14);
                    }
                }
                break;
            case R.id.ll_muscleMass:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("肌肉量", 180, 40, 0, 9, 0, 0, 15);
                    } else {
                        show_information("肌肉量", 180, 60, 0, 9, 0, 0, 15);
                    }
                }
                break;
            case R.id.ll_boneMass:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("骨量", 6, 2, 0, 9, 5, 0, 16);
                    } else {
                        show_information("骨量", 6, 3, 0, 9, 0, 0, 16);
                    }
                }
                break;
            case R.id.ll_basalMetabolism:
                if (IsAudit != 1) {
                    if ("1".equals(gender)) { //女的
                        show_information("基础代谢", 2500, 1280, 0, 0, 0, 0, 17);
                    } else {
                        show_information("基础代谢", 2500, 1700, 0, 0, 0, 0, 17);
                    }
                }
                break;
            case R.id.ll_physicalAge:
                if (IsAudit != 1) {
                    show_information("身体年龄", 150, 30, 0, 0, 0, 0, 18);
                }
                break;


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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

                } else if (num == 4) {
                    tv_retestWrite_crium.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 5) {
                    tv_retestWrite_waisline.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 6) {
                    tv_retestWrite_hiplie.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 7) {
                    tv_retestWrite_Uparm.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 8) {
                    tv_retestWrite_UpLeg.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 9) {
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
        //验证成功

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

    /*
        * 获取初始基本数据
        * */
    private void doGetInfo() {
        service.doGetMeasuredDetails(UserInfoModel.getInstance().getToken(), ACMID, new RequestCallback<ResponseData<MeasuredDetailsModel>>() {
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
    /*
    * 获取数据值
    * */

    void doSetData() {
        if (measuredDetailsModel != null) {
            try {
                final String url = AddressManager.get("photoHost");

                if (!TextUtils.isEmpty(measuredDetailsModel.getPhoto())) {

                    Picasso.with(context).load(url + measuredDetailsModel.getPhoto()).fit().into(iv_write_head);//头像
                }
                if (!TextUtils.isEmpty(measuredDetailsModel.getImgThumbnail())) {

                    im_retestwrite_showphoto.setVisibility(View.VISIBLE);

                    Picasso.with(context).load(url + measuredDetailsModel.getImgThumbnail()).fit().placeholder(R.drawable.default_icon_square).into(im_retestwrite_showphoto);//图片
                    Log.i("加载图片啦" + url + measuredDetailsModel.getImgThumbnail());

                } else if (!TextUtils.isEmpty(measuredDetailsModel.getImg())) {

                    im_retestwrite_showphoto.setVisibility(View.VISIBLE);

                    Picasso.with(context).load(url + measuredDetailsModel.getImg()).fit().placeholder(R.drawable.default_icon_square).into(im_retestwrite_showphoto);//图片
                }
                if (!TextUtils.isEmpty(measuredDetailsModel.getImg())) {
                    photoname = measuredDetailsModel.getImg();
                }
                tv_write_nick.setText(measuredDetailsModel.getUserName());//设置用户名
                tv_write_phone.setText(measuredDetailsModel.getMobile());//手机号
                tv_write_class.setText("所属班级：" + measuredDetailsModel.getClassName());//班级名
                if (!TextUtils.isEmpty(measuredDetailsModel.getStartDate())) {
                    String Stardata[] = measuredDetailsModel.getStartDate().split("-");
                    tv_write_star.setText(Stardata[0] + "." + Long.parseLong(Stardata[1]) + "." + Long.parseLong(Stardata[2]));//开班月
                }
                if (!TextUtils.isEmpty(measuredDetailsModel.getEndDate())) {
                    String Enddata[] = measuredDetailsModel.getEndDate().split("-");
                    tv_write_end.setText(Enddata[0] + "." + Long.parseLong(Enddata[1]) + "." + Long.parseLong(Enddata[2]));//结束月
                }
                tv_write_chu_weight.setText("0.0".equals(measuredDetailsModel.getWeight()) ? "" : measuredDetailsModel.getWeight());//初始体重
                tv_retestWrite_tizhi.setText("0.0".equals(measuredDetailsModel.getPysical()) ? "" : measuredDetailsModel.getPysical());//体脂
                tv_retestWrite_neizhi.setText("0.0".equals(measuredDetailsModel.getFat()) ? "" : measuredDetailsModel.getFat());//内脂
                tv_retestWrite_crium.setText("0.0".equals(measuredDetailsModel.getCircum()) ? "" : measuredDetailsModel.getCircum());
                tv_retestWrite_waisline.setText("0.0".equals(measuredDetailsModel.getWaistline()) ? "" : measuredDetailsModel.getWaistline());
                tv_retestWrite_hiplie.setText("0.0".equals(measuredDetailsModel.getHiplie()) ? "" : measuredDetailsModel.getHiplie());
                tv_retestWrite_Uparm.setText("0.0".equals(measuredDetailsModel.getUpArmGirth()) ? "" : measuredDetailsModel.getUpArmGirth());
                tv_retestWrite_UpLeg.setText("0.0".equals(measuredDetailsModel.getUpLegGirth()) ? "" : measuredDetailsModel.getUpLegGirth());
                tv_retestWrite_doleg.setText("0.0".equals(measuredDetailsModel.getDoLegGirth()) ? "" : measuredDetailsModel.getDoLegGirth());
                gender = measuredDetailsModel.getGender();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
//    private void loadImageCache() {
//         final String imageCacheDir = /* 自定义目录 */+"image";
//        Picasso picasso = new Picasso.Builder(this).downloader(
//         new OkHttpDownloader(new File(imageCacheDir))).build();
//        Picasso.setSingletonInstance(picasso);
//         }


    /*l录入*/
    void doSetPostData() {
        progressDialog.setMessage("正在提交数据，请等待");
        progressDialog.show();
        initAuditPModel = new InitAuditPModel();
        initAuditPModel.setACMId(ACMID);
        initAuditPModel.setReviewerId(UserInfoModel.getInstance().getUser().getUserid());
        initAuditPModel.setWeight(tv_write_chu_weight.getText().toString());//体重
        initAuditPModel.setPysical(tv_retestWrite_tizhi.getText().toString());//体脂
        initAuditPModel.setFat(tv_retestWrite_neizhi.getText().toString());//内脂
        initAuditPModel.setCircum(tv_retestWrite_crium.getText().toString());//胸围
        initAuditPModel.setHiplie(tv_retestWrite_hiplie.getText().toString());//臀围
        initAuditPModel.setWaistline(tv_retestWrite_waisline.getText().toString());//腰围
        initAuditPModel.setUpArmGirth(tv_retestWrite_Uparm.getText().toString());
        initAuditPModel.setUpLegGirth(tv_retestWrite_UpLeg.getText().toString());
        initAuditPModel.setDoLegGirth(tv_retestWrite_doleg.getText().toString());
        Log.i("上传数据" + initAuditPModel.toString());
        Log.i("AccountId" + AccountId + "classId" + classId + "ACMID" + ACMID + "身体维度上传" + "胸围" + measuredDetailsModel.getCircum() + "腰围 " + measuredDetailsModel.getWaistline() + "臀围" + measuredDetailsModel.getHiplie() + "上臂围" + measuredDetailsModel.getUpArmGirth() + "大腿围" + measuredDetailsModel.getUpLegGirth() + "小腿围" + measuredDetailsModel.getDoLegGirth());
        doPostInitData();
    }

    //录入请求
    private void doPostInitData() {
        service.doReviewInitData(UserInfoModel.getInstance().getToken(), initAuditPModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                try {
                    int status = responseData.getStatus();
                    switch (status) {
                        case 200:
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            intent.putExtra("ACMID", ACMID);
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
