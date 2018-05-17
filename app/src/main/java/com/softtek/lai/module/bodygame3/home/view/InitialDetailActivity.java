package com.softtek.lai.module.bodygame3.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.File.view.ExplainActivity;
import com.softtek.lai.module.bodygame3.activity.model.PostInitialData;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.customermanagement.view.MakiBaseActivity;
import com.softtek.lai.module.healthyreport.HealthEntryActivity;
import com.softtek.lai.module.healthyreport.model.LastestRecordModel;
import com.softtek.lai.module.healthyreport.net.HealthyRecordService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jia.lu on 5/15/2018.
 */
public class InitialDetailActivity extends MakiBaseActivity implements View.OnClickListener {
    private ScrollView mScrollView;
    private LinearLayout ll_left;
    private TextView tv_title;
    private RelativeLayout rl_weight;
    private RelativeLayout rl_pysical;
    private RelativeLayout rl_fat;
    private RelativeLayout rl_circum;
    private RelativeLayout rl_waistline;
    private RelativeLayout rl_hiplie;
    private RelativeLayout rl_uparmgirth;
    private RelativeLayout rl_upleggirth;
    private RelativeLayout rl_doleggirth;
    private RelativeLayout rl_bmi;
    private RelativeLayout rl_quzhi;
    private RelativeLayout rl_body_water_per;
    private RelativeLayout rl_body_water;
    private RelativeLayout rl_muscle_mass;
    private RelativeLayout rl_bone;
    private RelativeLayout rl_base_metabolize;
    private RelativeLayout rl_body_age;
    private LinearLayout ll_explain;
    private TextView tv_weight;
    private TextView tv_pysical;
    private TextView tv_fat;
    private TextView tv_circum;
    private TextView tv_waistline;
    private TextView tv_hiplie;
    private TextView tv_uparmgirth;
    private TextView tv_upleggirth;
    private TextView tv_doleggirth;
    private TextView tv_bmi;
    private TextView tv_quzhi;
    private TextView tv_body_water_per;
    private TextView tv_body_water;
    private TextView tv_muscle_mass;
    private TextView tv_bone;
    private TextView tv_base_metabolize;
    private TextView tv_body_age;
    private Button btn_sure;
    private ImageView mExpand;
    private LinearLayout mGirth;
    private String phone;
    private String gender = UserInfoModel.getInstance().getUser().getGender();
    private String classId;
    private PostInitialData postInitialData;
    private int accountId;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_detail);
        initView();
        initData();
    }


    private void initView() {
        phone = getIntent().getStringExtra("phone");
        classId = getIntent().getStringExtra("classId");
        accountId = getIntent().getIntExtra("accountId", -1);
        userName = getIntent().getStringExtra("userName");
        mScrollView = findViewById(R.id.sv_content);
        ll_left = findViewById(R.id.ll_left);
        tv_title = findViewById(R.id.tv_title);
        rl_weight = findViewById(R.id.rl_weight);
        rl_pysical = findViewById(R.id.rl_pysical);
        rl_fat = findViewById(R.id.rl_fat);
        rl_circum = findViewById(R.id.rl_circum);
        rl_waistline = findViewById(R.id.rl_waistline);
        rl_hiplie = findViewById(R.id.rl_hiplie);
        rl_uparmgirth = findViewById(R.id.rl_uparmgirth);
        rl_upleggirth = findViewById(R.id.rl_upleggirth);
        rl_doleggirth = findViewById(R.id.rl_doleggirth);
        rl_bmi = findViewById(R.id.rl_bmi);
        rl_quzhi = findViewById(R.id.rl_quzhi);
        rl_body_water_per = findViewById(R.id.rl_body_water_per);
        rl_body_water = findViewById(R.id.rl_body_water);
        rl_muscle_mass = findViewById(R.id.rl_muscle_mass);
        rl_bone = findViewById(R.id.rl_bone);
        rl_base_metabolize = findViewById(R.id.rl_base_metabolize);
        rl_body_age = findViewById(R.id.rl_body_age);
        ll_explain = findViewById(R.id.ll_explain);
        tv_weight = findViewById(R.id.tv_weight);
        tv_pysical = findViewById(R.id.tv_pysical);
        tv_fat = findViewById(R.id.tv_fat);
        tv_circum = findViewById(R.id.tv_circum);
        tv_waistline = findViewById(R.id.tv_waistline);
        tv_hiplie = findViewById(R.id.tv_hiplie);
        tv_uparmgirth = findViewById(R.id.tv_uparmgirth);
        tv_upleggirth = findViewById(R.id.tv_upleggirth);
        tv_doleggirth = findViewById(R.id.tv_doleggirth);
        tv_bmi = findViewById(R.id.tv_bmi);
        tv_quzhi = findViewById(R.id.tv_quzhi);
        tv_body_water_per = findViewById(R.id.tv_body_water_per);
        tv_body_water = findViewById(R.id.tv_body_water);
        tv_muscle_mass = findViewById(R.id.tv_muscle_mass);
        tv_bone = findViewById(R.id.tv_bone);
        tv_base_metabolize = findViewById(R.id.tv_base_metabolize);
        tv_body_age = findViewById(R.id.tv_body_age);
        btn_sure = findViewById(R.id.btn_sure);
        mExpand = findViewById(R.id.iv_expand);
        mGirth = findViewById(R.id.ll_girth);
        ll_left.setOnClickListener(this);
        rl_weight.setOnClickListener(this);
        rl_pysical.setOnClickListener(this);
        ll_explain.setOnClickListener(this);
        rl_fat.setOnClickListener(this);
        rl_circum.setOnClickListener(this);
        rl_waistline.setOnClickListener(this);
        rl_hiplie.setOnClickListener(this);
        rl_uparmgirth.setOnClickListener(this);
        rl_upleggirth.setOnClickListener(this);
        rl_doleggirth.setOnClickListener(this);

        rl_bmi.setOnClickListener(this);
        rl_quzhi.setOnClickListener(this);
//        rl_visceral_fat.setOnClickListener(this);
        rl_body_water_per.setOnClickListener(this);
        rl_body_water.setOnClickListener(this);
        rl_muscle_mass.setOnClickListener(this);
        rl_bone.setOnClickListener(this);
        rl_base_metabolize.setOnClickListener(this);
        rl_body_age.setOnClickListener(this);


        btn_sure.setOnClickListener(this);

        mExpand.setOnClickListener(this);

//        from = getIntent().getStringExtra("from");
//        accountId = getIntent().getLongExtra("accountId", UserInfoModel.getInstance().getUserId());//UserInfoModel.getInstance().getUser().getUserid()
    }

    private void initData() {
        postInitialData = new PostInitialData();
        postInitialData.setClassId(classId);
        postInitialData.setAccountId(accountId);
        tv_title.setText(userName);
        if (phone == null) {
            phone = UserInfoModel.getInstance().getUser().getMobile();
        }
        dialogShow("数据加载中...");
        ZillaApi.NormalRestAdapter.create(HealthyRecordService.class).getUserMeasuredInfo(
                UserInfoModel.getInstance().getToken(), phone, new Callback<ResponseData<LastestRecordModel>>() {
                    @Override
                    public void success(ResponseData<LastestRecordModel> responseData, Response response) {
                        dialogDismiss();
                        if (responseData.getStatus() == 200) {
                            getData(responseData.getData());
                        } else if (responseData.getStatus() == 100) {
                            Util.toastMsg("暂无健康记录数据");
                        } else {
                            Util.toastMsg(responseData.getMsg());
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        dialogDismiss();
                        ZillaApi.dealNetError(retrofitError);
                    }
                });
    }

    AlertDialog weightDialog;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_explain:
                startActivity(new Intent(InitialDetailActivity.this, ExplainActivity.class));
                break;
            case R.id.rl_weight:
                HealthEntryActivity.DoSelectedListener weightListener = new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(final float value) {
                        if (value < 90) {
                            new AlertDialog.Builder(InitialDetailActivity.this)
                                    .setMessage("体重单位为斤,是否确认数值?")
                                    .setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int which) {
                                                    tv_weight.setText(String.valueOf(value));
                                                    postInitialData.setWeight(String.valueOf(value));

                                                }
                                            })
                                    .setNegativeButton("取消",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (weightDialog != null) {
                                                        weightDialog.show();
                                                    }
                                                }
                                            }).create().show();
                        } else {
                            tv_weight.setText(String.valueOf(value));
                            postInitialData.setWeight(String.valueOf(value));
                        }
                    }
                };
                weightDialog = createDialog("选择体重(单位：斤)", 50, 600, "0".equals(gender) ? 150 : 100, weightListener);
                weightDialog.show();
                break;
            case R.id.rl_pysical:
                createDialog("选择体脂", 1, 50, 25, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_pysical.setText(String.valueOf(value));
                        postInitialData.setPysical(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_fat:
                createDialog("选择内脂", 1, 30, 2, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_fat.setText(String.valueOf(value));
                        postInitialData.setViscusFatIndex(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_circum:
                createDialog("选择胸围", 50, 200, 90, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_circum.setText(String.valueOf(value));
                        postInitialData.setCircum(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_waistline:
                createDialog("选择腰围", 40, 200, 80, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_waistline.setText(String.valueOf(value));
                        postInitialData.setWaistline(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_hiplie:
                createDialog("选择臀围", 50, 250, 90, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_hiplie.setText(String.valueOf(value));
                        postInitialData.setHiplie(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_uparmgirth:
                createDialog("选择上臂围", 10, 70, 50, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_uparmgirth.setText(String.valueOf(value));
                        postInitialData.setUpArmGirth(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_upleggirth:
                createDialog("选择大腿围", 10, 90, 50, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_upleggirth.setText(String.valueOf(value));
                        postInitialData.setUpLegGirth(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_doleggirth:
                createDialog("选择小腿围", 10, 70, 50, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_doleggirth.setText(String.valueOf(value));
                        postInitialData.setDoLegGirth(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_bmi:
                if ("1".equals(gender)) { //女的
                    createDialog("选择BMI", 0, 50, 25, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bmi.setText(String.valueOf(value));
                            postInitialData.setBmi(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择BMI", 0, 50, 27, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bmi.setText(String.valueOf(value));
                            postInitialData.setBmi(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_quzhi:
                if ("1".equals(gender)) { //女的
                    createDialog("选择去脂体重", 0, 180, 40, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_quzhi.setText(String.valueOf(value));
                            postInitialData.setFatFreeMass(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择去脂体重", 0, 180, 60, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_quzhi.setText(String.valueOf(value));
                            postInitialData.setFatFreeMass(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_body_water_per:
                if ("1".equals(gender)) { //女的
                    createDialog("选择身体水份率", 0, 80, 50, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water_per.setText(String.valueOf(value));
                            postInitialData.setBodyWaterRate(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择身体水份率", 0, 80, 55, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water_per.setText(String.valueOf(value));
                            postInitialData.setBodyWaterRate(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_body_water:
                if ("1".equals(gender)) { //女的
                    createDialog("选择身体水份", 0, 160, 30, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water.setText(String.valueOf(value));
                            postInitialData.setBodyWater(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择身体水份", 0, 160, 40, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water.setText(String.valueOf(value));
                            postInitialData.setBodyWater(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_muscle_mass:
                if ("1".equals(gender)) { //女的
                    createDialog("选择肌肉量", 0, 180, 40, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_muscle_mass.setText(String.valueOf(value));
                            postInitialData.setMuscleMass(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择肌肉量", 0, 180, 60, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_muscle_mass.setText(String.valueOf(value));
                            postInitialData.setMuscleMass(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_bone:
                if ("1".equals(gender)) { //女的
                    createDialog("选择骨量", 0, 6, 2, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bone.setText(String.valueOf(value));
                            postInitialData.setBoneMass(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择骨量", 0, 6, 3, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bone.setText(String.valueOf(value));
                            postInitialData.setBoneMass(String.valueOf(value));
                        }
                    }).show();
                }
                break;

            case R.id.rl_base_metabolize:
                if ("1".equals(gender)) { //女的
                    createDialog("选择基础代谢", 0, 2500, 1280, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_base_metabolize.setText(String.valueOf(value));
                            postInitialData.setBasalMetabolism(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择基础代谢", 0, 2500, 1700, new HealthEntryActivity.DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_base_metabolize.setText(String.valueOf(value));
                            postInitialData.setBasalMetabolism(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_body_age:
                createDialog("选择身体年龄", 0, 150, 30, new HealthEntryActivity.DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_body_age.setText(String.valueOf(value));
                        postInitialData.setPhysicalAge(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.iv_expand:
                if (mGirth.getVisibility() == View.VISIBLE) {
                    mGirth.setVisibility(View.GONE);
                    mExpand.setImageDrawable(getResources().getDrawable(R.drawable.expand_down));
                } else {
                    mGirth.setVisibility(View.VISIBLE);
                    mExpand.setImageDrawable(getResources().getDrawable(R.drawable.expand_up));
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                }
                break;
            case R.id.btn_sure:
                dialogShow("提交数据中...");
                ZillaApi.NormalRestAdapter.create(ActivityService.class).saveClassInitHealthRecord(UserInfoModel.getInstance().getToken(), postInitialData,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                dialogDismiss();
                                if (responseData.getStatus() == 200) {
                                    Util.toastMsg("提交成功");
                                } else {
                                    Util.toastMsg(responseData.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                                dialogDismiss();
                            }
                        });
        }
    }

    private AlertDialog createDialog(String title, int min, int max, int defaultValue, final HealthEntryActivity.DoSelectedListener listener) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(max);
        np1.setValue(defaultValue);
        np1.setMinValue(min);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        return dialog.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    int v1 = np1.getValue();
                    listener.onClick(v1 + Float.valueOf("0." + np2.getValue()));
                }

            }
        }).setNegativeButton("取消", null).create();
    }

    public void getData(LastestRecordModel data) {
        if (data == null) {
            return;
        }
        tv_weight.setText(StringUtil.getValue(data.getWeight()));
        postInitialData.setWeight(data.getWeight());

        tv_pysical.setText(StringUtil.getValue(data.getPysical()));
        postInitialData.setPysical(data.getPysical());

        tv_fat.setText(StringUtil.getValue(data.getFat()));
        postInitialData.setViscusFatIndex(data.getFat());

        tv_circum.setText(StringUtil.getValue(data.getCircum()));
        postInitialData.setCircum(data.getCircum());

        tv_waistline.setText(StringUtil.getValue(data.getWaistline()));
        postInitialData.setWaistline(data.getWaistline());

        tv_hiplie.setText(StringUtil.getValue(data.getHiplie()));
        postInitialData.setHiplie(data.getHiplie());

        tv_uparmgirth.setText(StringUtil.getValue(data.getUpArmGirth()));
        postInitialData.setUpArmGirth(data.getUpArmGirth());

        tv_upleggirth.setText(StringUtil.getValue(data.getUpLegGirth()));
        postInitialData.setUpLegGirth(data.getUpLegGirth());

        tv_doleggirth.setText(StringUtil.getValue(data.getDoLegGirth()));
        postInitialData.setDoLegGirth(data.getDoLegGirth());

        tv_bmi.setText(StringUtil.getValue(data.getBmi()));
        postInitialData.setBmi(data.getBmi());

        tv_quzhi.setText(StringUtil.getValue(data.getFatFreeMass()));
        postInitialData.setFatFreeMass(data.getFatFreeMass());

//        tv_visceral_fat.setText(StringUtil.getValue(data.getViscusFatIndex()));
        tv_body_water_per.setText(StringUtil.getValue(data.getBodyWaterRate()));
        postInitialData.setBodyWaterRate(data.getBodyWaterRate());

        tv_body_water.setText(StringUtil.getValue(data.getBodyWater()));
        postInitialData.setBodyWater(data.getBodyWater());

        tv_muscle_mass.setText(StringUtil.getValue(data.getMuscleMass()));
        postInitialData.setMuscleMass(data.getMuscleMass());

        tv_bone.setText(StringUtil.getValue(data.getBoneMass()));
        postInitialData.setBoneMass(data.getBoneMass());

        tv_base_metabolize.setText(StringUtil.getValue(data.getBasalMetabolism()));
        postInitialData.setBasalMetabolism(data.getBasalMetabolism());

        tv_body_age.setText(StringUtil.getValue(data.getPhysicalAge()));
        postInitialData.setPhysicalAge(data.getPhysicalAge());
    }
}
