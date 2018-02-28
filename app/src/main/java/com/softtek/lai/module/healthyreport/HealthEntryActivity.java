package com.softtek.lai.module.healthyreport;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.File.view.ExplainActivity;
import com.softtek.lai.module.healthyreport.model.HealthModel;
import com.softtek.lai.module.healthyreport.model.LastestRecordModel;
import com.softtek.lai.module.healthyreport.presenter.HealthyEntryPresenter;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.StringUtil;

import org.joda.time.DateTime;

import java.util.Calendar;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_health_entry)
public class HealthEntryActivity extends BaseActivity<HealthyEntryPresenter> implements View.OnClickListener, Validator.ValidationListener
        , HealthyEntryPresenter.HealthyEntryView {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout rl_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.rl_weight)
    RelativeLayout rl_weight;

    @Required(order = 1, message = "请填写体重")
    @InjectView(R.id.rl_pysical)
    RelativeLayout rl_pysical;

    @InjectView(R.id.rl_fat)
    RelativeLayout rl_fat;

    @InjectView(R.id.rl_circum)
    RelativeLayout rl_circum;

    @InjectView(R.id.rl_waistline)
    RelativeLayout rl_waistline;

    @InjectView(R.id.rl_hiplie)
    RelativeLayout rl_hiplie;

    @InjectView(R.id.rl_uparmgirth)
    RelativeLayout rl_uparmgirth;

    @InjectView(R.id.rl_upleggirth)
    RelativeLayout rl_upleggirth;

    @InjectView(R.id.rl_doleggirth)
    RelativeLayout rl_doleggirth;

    @InjectView(R.id.rl_bmi)
    RelativeLayout rl_bmi;
    @InjectView(R.id.rl_quzhi)
    RelativeLayout rl_quzhi;

    @InjectView(R.id.rl_body_water_per)
    RelativeLayout rl_body_water_per;
    @InjectView(R.id.rl_body_water)
    RelativeLayout rl_body_water;
    @InjectView(R.id.rl_muscle_mass)
    RelativeLayout rl_muscle_mass;
    @InjectView(R.id.rl_bone)
    RelativeLayout rl_bone;
    @InjectView(R.id.rl_base_metabolize)
    RelativeLayout rl_base_metabolize;
    @InjectView(R.id.rl_body_age)
    RelativeLayout rl_body_age;
    @InjectView(R.id.ll_explain)
    LinearLayout ll_explain;

    @InjectView(R.id.rl_measure_date) //测量日期
            RelativeLayout rl_measure_date;
    @InjectView(R.id.tv_measure_date)
    TextView tv_measure_date;


    @InjectView(R.id.tv_weight)
    TextView tv_weight;

    @InjectView(R.id.tv_pysical)
    TextView et_pysical;

    @InjectView(R.id.tv_fat)
    TextView et_fat;

    @InjectView(R.id.tv_circum)
    TextView tv_circum;

    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;

    @InjectView(R.id.tv_hiplie)
    TextView tv_hiplie;

    @InjectView(R.id.tv_uparmgirth)
    TextView tv_uparmgirth;

    @InjectView(R.id.tv_upleggirth)
    TextView tv_upleggirth;

    @InjectView(R.id.tv_doleggirth)
    TextView tv_doleggirth;

    @InjectView(R.id.tv_bmi)
    TextView tv_bmi;
    @InjectView(R.id.tv_quzhi)
    TextView tv_quzhi;
    //    @InjectView(R.id.tv_visceral_fat)
//    TextView tv_visceral_fat;
    @InjectView(R.id.tv_body_water_per)
    TextView tv_body_water_per;
    @InjectView(R.id.tv_body_water)
    TextView tv_body_water;
    @InjectView(R.id.tv_muscle_mass)
    TextView tv_muscle_mass;
    @InjectView(R.id.tv_bone)
    TextView tv_bone;
    @InjectView(R.id.tv_base_metabolize)
    TextView tv_base_metabolize;
    @InjectView(R.id.tv_body_age)
    TextView tv_body_age;

    @InjectView(R.id.btn_sure)
    Button btn_sure;
    @InjectView(R.id.iv_expand)
    ImageView mExpand;
    @InjectView(R.id.ll_girth)
    LinearLayout mGirth;

    private HealthModel healthModele;
    private LastestRecordModel lastestRecordModel;

    final String gender = UserInfoModel.getInstance().getUser().getGender();

    private String from = "";
    private long accountId;

    @Override
    protected void initViews() {
        rl_left.setOnClickListener(this);
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
        rl_measure_date.setOnClickListener(this);


        btn_sure.setOnClickListener(this);

        mExpand.setOnClickListener(this);

        from = getIntent().getStringExtra("from");
        accountId = getIntent().getLongExtra("accountId", UserInfoModel.getInstance().getUserId());//UserInfoModel.getInstance().getUser().getUserid()
    }

    @Override
    protected void initDatas() {
        tv_title.setText("健康记录录入");
        setPresenter(new HealthyEntryPresenter(this));
        getPresenter().doGetLastestRecord(UserInfoModel.getInstance().getUserId());

        //获取当前年月日
        currentYear = DateUtil.getInstance().getCurrentYear();
        currentMonth = DateUtil.getInstance().getCurrentMonth();
        currentDay = DateUtil.getInstance().getCurrentDay();

//        tv_measure_date.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate());
        tv_measure_date.setText("请选择日期");


    }

    AlertDialog weightDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_explain:
                startActivity(new Intent(HealthEntryActivity.this, ExplainActivity.class));
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_weight:

                DoSelectedListener weightListener = new DoSelectedListener() {
                    @Override
                    public void onClick(final float value) {
                        if (value < 90) {
                            new AlertDialog.Builder(HealthEntryActivity.this)
                                    .setMessage("体重单位为斤,是否确认数值?")
                                    .setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int which) {
                                                    tv_weight.setText(String.valueOf(value));

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
                        }
                    }
                };
                weightDialog = createDialog("选择体重(单位：斤)", 50, 600, "0".equals(gender) ? 150 : 100, weightListener);
                weightDialog.show();
                break;
            case R.id.rl_pysical:
                createDialog("选择体脂", 1, 50, 25, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        et_pysical.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_fat:
                createDialog("选择内脂", 1, 30, 2, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        et_fat.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_circum:
                createDialog("选择胸围", 50, 200, 90, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_circum.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_waistline:
                createDialog("选择腰围", 40, 200, 80, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_waistline.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_hiplie:
                createDialog("选择臀围", 50, 250, 90, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_hiplie.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_uparmgirth:
                createDialog("选择上臂围", 10, 70, 50, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_uparmgirth.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_upleggirth:
                createDialog("选择大腿围", 10, 90, 50, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_upleggirth.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_doleggirth:
                createDialog("选择小腿围", 10, 70, 50, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_doleggirth.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_bmi:
                if ("1".equals(gender)) { //女的
                    createDialog("选择BMI", 0, 50, 25, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bmi.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择BMI", 0, 50, 27, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bmi.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_quzhi:
                if ("1".equals(gender)) { //女的
                    createDialog("选择去脂体重", 0, 180, 40, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_quzhi.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择去脂体重", 0, 180, 60, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_quzhi.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;
//            case R.id.rl_visceral_fat:
//                if ("1".equals(gender)) { //女的
//                    createDialog("选择内脏脂肪指数", 0, 30, 10, new DoSelectedListener() {
//                        @Override
//                        public void onClick(float value) {
//                            tv_visceral_fat.setText(String.valueOf(value));
//                        }
//                    }).show();
//                } else {
//                    createDialog("选择内脏脂肪指数", 0, 30, 10, new DoSelectedListener() {
//                        @Override
//                        public void onClick(float value) {
//                            tv_visceral_fat.setText(String.valueOf(value));
//                        }
//                    }).show();
//                }
//                break;
            case R.id.rl_body_water_per:
                if ("1".equals(gender)) { //女的
                    createDialog("选择身体水份率", 0, 80, 50, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water_per.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择身体水份率", 0, 80, 55, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water_per.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_body_water:
                if ("1".equals(gender)) { //女的
                    createDialog("选择身体水份", 0, 160, 30, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择身体水份", 0, 160, 40, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_body_water.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_muscle_mass:
                if ("1".equals(gender)) { //女的
                    createDialog("选择肌肉量", 0, 180, 40, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_muscle_mass.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择肌肉量", 0, 180, 60, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_muscle_mass.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_bone:
                if ("1".equals(gender)) { //女的
                    createDialog("选择骨量", 0, 6, 2, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bone.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择骨量", 0, 6, 3, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_bone.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;

            case R.id.rl_base_metabolize:
                if ("1".equals(gender)) { //女的
                    createDialog("选择基础代谢", 0, 2500, 1280, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_base_metabolize.setText(String.valueOf(value));
                        }
                    }).show();
                } else {
                    createDialog("选择基础代谢", 0, 2500, 1700, new DoSelectedListener() {
                        @Override
                        public void onClick(float value) {
                            tv_base_metabolize.setText(String.valueOf(value));
                        }
                    }).show();
                }
                break;
            case R.id.rl_body_age:
                createDialog("选择身体年龄", 0, 150, 30, new DoSelectedListener() {
                    @Override
                    public void onClick(float value) {
                        tv_body_age.setText(String.valueOf(value));
                    }
                }).show();
                break;
            case R.id.rl_measure_date:
                showDateDialog();
                break;
            case R.id.btn_sure:
                validateLife.validate();
                break;
            case R.id.iv_expand:
                if (mGirth.getVisibility() == View.VISIBLE){
                    mGirth.setVisibility(View.GONE);
                    mExpand.setImageDrawable(getResources().getDrawable(R.drawable.expand_down));
                }else {
                    mGirth.setVisibility(View.VISIBLE);
                    mExpand.setImageDrawable(getResources().getDrawable(R.drawable.expand_up));
                }
        }
    }

    int currentYear;
    int currentMonth;
    int currentDay;

    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);
        DateTime minTime = new DateTime(1900, 1, 1, 0, 0);
        DateTime maxTime = new DateTime();

        DateTime defaultTime;
        if (currentMonth == 1) {
            defaultTime = new DateTime(1990, currentMonth, currentDay, 0, 0);
        } else {
            defaultTime = new DateTime(1990, currentMonth - 1, currentDay, 0, 0);
        }
//        DateTime defaultTime = new DateTime(currentYear, currentMonth - 1, currentDay, 0, 0);
        final DatePickerDialog dialog =
                new DatePickerDialog(this, null, defaultTime.year().get(), defaultTime.monthOfYear().get(), defaultTime.getDayOfMonth());
        dialog.getDatePicker().setMinDate(minTime.getMillis());
        dialog.getDatePicker().setMaxDate(maxTime.getMillis());
        dialog.setTitle("选择测量日期(年-月-日)");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = dialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                String date = year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
                int compare = DateUtil.getInstance(DateUtil.yyyy_MM_dd).compare(date, DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate());
                if (compare == 1) {
//                    show_warn_dialog();
                    return;
                }
                //输出当前日期
                tv_measure_date.setText(date);
                tv_measure_date.setError(null);

            }
        });
        dialog.show();

    }

    private AlertDialog createDialog(String title, int min, int max, int defaultValue, final DoSelectedListener listener) {
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

    @Override
    public void onValidationSucceeded() {
        String weight = tv_weight.getText().toString();
        String pysical = et_pysical.getText().toString();
        String fat = et_fat.getText().toString();
        String circum = tv_circum.getText().toString();
        String waistline = tv_waistline.getText().toString();
        String hiplie = tv_hiplie.getText().toString();
        String uparmgirth = tv_uparmgirth.getText().toString();
        String upleggirth = tv_upleggirth.getText().toString();
        String doleggirth = tv_doleggirth.getText().toString();
        if (tv_measure_date.getText().equals("请选择日期")){
            Toast.makeText(this,"请选择日期",Toast.LENGTH_SHORT).show();
            return;
        }
        String MeasuredTime = tv_measure_date.getText().toString();

        String str = DateUtil.getInstance(DateUtil.yyyy_MM_dd_HH_mm_ss).getCurrentDate();
        String[] result = str.split(" ");
        MeasuredTime = MeasuredTime + " " + result[1];


        healthModele = new HealthModel();
        healthModele.setWeight(weight);
        healthModele.setPysical(pysical);
        healthModele.setFat(fat);
        healthModele.setCircum(circum);
        healthModele.setWaistline(waistline);
        healthModele.setHiplie(hiplie);
        healthModele.setUpArmGirth(uparmgirth);
        healthModele.setUpLegGirth(upleggirth);
        healthModele.setDoLegGirth(doleggirth);

        healthModele.setBmi(tv_bmi.getText().toString());
        healthModele.setFatFreeMass(tv_quzhi.getText().toString());
//        healthModele.setViscusFatIndex(tv_visceral_fat.getText().toString());
        healthModele.setBodyWaterRate(tv_body_water_per.getText().toString());
        healthModele.setBodyWater(tv_body_water.getText().toString());

        healthModele.setMuscleMass(tv_muscle_mass.getText().toString());
        healthModele.setBoneMass(tv_bone.getText().toString());
        healthModele.setBasalMetabolism(tv_base_metabolize.getText().toString());
        healthModele.setPhysicalAge(tv_body_age.getText().toString());

//        healthModele.setAccountId(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
        healthModele.setAccountId(accountId);


        healthModele.setMeasuredTime(MeasuredTime);
        getPresenter().entryhealthrecord(healthModele);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        new AlertDialog.Builder(this).setMessage(failedRule.getFailureMessage()).create().show();
    }


    @Override
    public void getData(LastestRecordModel data) {
        lastestRecordModel = data;
        if (data == null){
            return;
        }
        tv_weight.setText(StringUtil.getValue(lastestRecordModel.getWeight()));
        et_pysical.setText(StringUtil.getValue(lastestRecordModel.getPysical()));
        et_fat.setText(StringUtil.getValue(lastestRecordModel.getFat()));
        tv_circum.setText(StringUtil.getValue(lastestRecordModel.getCircum()));
        tv_waistline.setText(StringUtil.getValue(lastestRecordModel.getWaistline()));
        tv_hiplie.setText(StringUtil.getValue(lastestRecordModel.getHiplie()));
        tv_uparmgirth.setText(StringUtil.getValue(lastestRecordModel.getUpArmGirth()));
        tv_upleggirth.setText(StringUtil.getValue(lastestRecordModel.getUpLegGirth()));
        tv_doleggirth.setText(StringUtil.getValue(lastestRecordModel.getDoLegGirth()));

        tv_bmi.setText(StringUtil.getValue(lastestRecordModel.getBmi()));
        tv_quzhi.setText(StringUtil.getValue(lastestRecordModel.getFatFreeMass()));
//        tv_visceral_fat.setText(StringUtil.getValue(lastestRecordModel.getViscusFatIndex()));
        tv_body_water_per.setText(StringUtil.getValue(lastestRecordModel.getBodyWaterRate()));
        tv_body_water.setText(StringUtil.getValue(lastestRecordModel.getBodyWater()));

        tv_muscle_mass.setText(StringUtil.getValue(lastestRecordModel.getMuscleMass()));
        tv_bone.setText(StringUtil.getValue(lastestRecordModel.getBoneMass()));
        tv_base_metabolize.setText(StringUtil.getValue(lastestRecordModel.getBasalMetabolism()));
        tv_body_age.setText(StringUtil.getValue(lastestRecordModel.getPhysicalAge()));

    }

    @Override
    public void commitSuccess() {
        if (TextUtils.isEmpty(from)) {
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }

    public interface DoSelectedListener {
        void onClick(float value);
    }
}
