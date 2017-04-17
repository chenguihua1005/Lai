package com.softtek.lai.module.healthyreport;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.model.HealthModel;
import com.softtek.lai.module.healthyreport.model.LastestRecordModel;
import com.softtek.lai.module.healthyreport.presenter.HealthyEntryPresenter;
import com.softtek.lai.utils.StringUtil;

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
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_weight)
    LinearLayout ll_weight;

    @Required(order = 1, message = "请填写体重")
    @InjectView(R.id.ll_pysical)
    LinearLayout ll_pysical;

    @InjectView(R.id.ll_fat)
    LinearLayout ll_fat;

    @InjectView(R.id.ll_circum)
    LinearLayout ll_circum;

    @InjectView(R.id.ll_waistline)
    LinearLayout ll_waistline;

    @InjectView(R.id.ll_hiplie)
    LinearLayout ll_hiplie;

    @InjectView(R.id.ll_uparmgirth)
    LinearLayout ll_uparmgirth;

    @InjectView(R.id.ll_upleggirth)
    LinearLayout ll_upleggirth;

    @InjectView(R.id.ll_doleggirth)
    LinearLayout ll_doleggirth;

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


    @InjectView(R.id.ll_doBMI)
    LinearLayout ll_doBMI;
    @InjectView(R.id.tv_doBmi)
    TextView tv_doBmi;

    @InjectView(R.id.ll_doFatFreeMass)
    LinearLayout ll_doFatFreeMass;
    @InjectView(R.id.tv_doFatFreeMass)
    TextView tv_doFatFreeMass;

    @InjectView(R.id.ll_doViscusFatIndex)
    LinearLayout ll_doViscusFatIndex;
    @InjectView(R.id.tv_doViscusFatIndex)
    TextView tv_doViscusFatIndex;

    @InjectView(R.id.ll_doBodyWaterRate)
    LinearLayout ll_doBodyWaterRate;
    @InjectView(R.id.tv_doBodyWaterRate)
    TextView tv_doBodyWaterRate;

    @InjectView(R.id.ll_doBodyWater)
    LinearLayout ll_doBodyWater;
    @InjectView(R.id.tv_doBodyWater)
    TextView tv_doBodyWater;

    @InjectView(R.id.ll_doMuscleMass)
    LinearLayout ll_doMuscleMass;
    @InjectView(R.id.tv_doMuscleMass)
    TextView tv_doMuscleMass;

    @InjectView(R.id.ll_doBoneMass)
    LinearLayout ll_doBoneMass;
    @InjectView(R.id.tv_doBoneMass)
    TextView tv_doBoneMass;

    @InjectView(R.id.ll_doBasalMetabolism)
    LinearLayout ll_doBasalMetabolism;
    @InjectView(R.id.tv_doBasalMetabolism)
    TextView tv_doBasalMetabolism;

    @InjectView(R.id.ll_doPhysicalAge)
    LinearLayout ll_doPhysicalAge;
    @InjectView(R.id.tv_doPhysicalAge)
    TextView tv_doPhysicalAge;


    @InjectView(R.id.btn_sure)
    Button btn_sure;

    private HealthModel healthModele;
    private LastestRecordModel lastestRecordModel;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
        ll_pysical.setOnClickListener(this);
        ll_fat.setOnClickListener(this);
        ll_circum.setOnClickListener(this);
        ll_waistline.setOnClickListener(this);
        ll_hiplie.setOnClickListener(this);
        ll_uparmgirth.setOnClickListener(this);
        ll_upleggirth.setOnClickListener(this);
        ll_doleggirth.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("健康记录录入");
        setPresenter(new HealthyEntryPresenter(this));
        getPresenter().doGetLastestRecord(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_weight:
                show_weight_dialog();
                break;
            case R.id.ll_pysical:
                show_pysical_dialog();
                break;
            case R.id.ll_fat:
                show_fat_dialog();
                break;
            case R.id.ll_circum:
                show_circum_dialog();
                break;
            case R.id.ll_waistline:
                show_waistline_dialog();
                break;
            case R.id.ll_hiplie:
                show_hiplie_dialog();
                break;
            case R.id.ll_uparmgirth:
                show_uparmgirth_dialog();
                break;
            case R.id.ll_upleggirth:
                show_upleggirth_dialog();
                break;
            case R.id.ll_doleggirth:
                show_doleggirth_dialog();
                break;
            case R.id.btn_sure:
                validateLife.validate();
                break;
        }
    }


    public void show_weight_dialog() {
        AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(600);
        String gender = UserInfoModel.getInstance().getUser().getGender();
        if ("0".equals(gender)) {//男
            np1.setValue(150);
        } else {
            np1.setValue(100);
        }
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (np1.getValue() < 80) {
                    new AlertDialog.Builder(HealthEntryActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            show_weight_dialog();
                                        }
                                    }).create().show();
                } else {
                    tv_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();

    }

    //体脂对话框
    public void show_pysical_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(50);
        np1.setValue(25);
        np1.setMinValue(1);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体脂").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_pysical.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    //内脂对话框
    public void show_fat_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(30);
        np1.setValue(2);
        np1.setMinValue(1);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择内脂").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_fat.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    //围度dialog
    public void show_circum_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(200);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择胸围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_circum.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_waistline_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(200);
        np1.setValue(80);
        np1.setMinValue(40);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择腰围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_waistline.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_hiplie_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(250);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择臀围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_hiplie.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_uparmgirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(70);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择上臂围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_uparmgirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    public void show_upleggirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(90);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择大腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_upleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_doleggirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(70);
        np1.setValue(50);
        np1.setMinValue(10);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择小腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_doleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
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
        healthModele.setAccountId(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
        getPresenter().entryhealthrecord(healthModele);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        new AlertDialog.Builder(this).setMessage(failedRule.getFailureMessage()).create().show();
    }


    @Override
    public void getData(LastestRecordModel data) {
        lastestRecordModel = data;
        tv_weight.setText(StringUtil.getValue(lastestRecordModel.getWeight()));
        et_pysical.setText(StringUtil.getValue(lastestRecordModel.getPysical()));
        et_fat.setText(StringUtil.getValue(lastestRecordModel.getFat()));
        tv_circum.setText(StringUtil.getValue(lastestRecordModel.getCircum()));
        tv_waistline.setText(StringUtil.getValue(lastestRecordModel.getWaistline()));
        tv_hiplie.setText(StringUtil.getValue(lastestRecordModel.getHiplie()));
        tv_uparmgirth.setText(StringUtil.getValue(lastestRecordModel.getUpArmGirth()));
        tv_upleggirth.setText(StringUtil.getValue(lastestRecordModel.getUpLegGirth()));
        tv_doleggirth.setText(StringUtil.getValue(lastestRecordModel.getDoLegGirth()));

        tv_doBmi.setText(StringUtil.getValue(lastestRecordModel.getBmi()));
        tv_doFatFreeMass.setText(StringUtil.getValue(lastestRecordModel.getFatFreeMass()));
        tv_doViscusFatIndex.setText(StringUtil.getValue(lastestRecordModel.getViscusFatIndex()));
        tv_doBodyWaterRate.setText(StringUtil.getValue(lastestRecordModel.getBodyWaterRate()));
        tv_doBodyWater.setText(StringUtil.getValue(lastestRecordModel.getBodyWater()));

        tv_doMuscleMass.setText(StringUtil.getValue(lastestRecordModel.getMuscleMass()));
        tv_doBoneMass.setText(StringUtil.getValue(lastestRecordModel.getBoneMass()));
        tv_doBasalMetabolism.setText(StringUtil.getValue(lastestRecordModel.getBasalMetabolism()));
        tv_doPhysicalAge.setText(StringUtil.getValue(lastestRecordModel.getPhysicalAge()));
    }

    @Override
    public void commitSuccess() {
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
