package com.softtek.lai.module.healthrecords.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthrecords.EventModel.RecordEvent;
import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.model.LastestRecordModel;
import com.softtek.lai.module.healthrecords.presenter.EntryHealthImpl;
import com.softtek.lai.module.healthrecords.presenter.IEntryHealthpresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_health_entry)
public class HealthEntryActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener,EntryHealthImpl.EntryHealthyCallback{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_weight)
    LinearLayout ll_weight;

    @Required(order = 1,message = "请填写体重")
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

    @InjectView(R.id.btn_sure)
    Button btn_sure;

    private HealthModel healthModele;
    private LastestRecordModel lastestRecordModel;

    private IEntryHealthpresenter iEntryHealthpresenter;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
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
        iEntryHealthpresenter = new EntryHealthImpl(this);
        dialogShow("获取数据中...");
        iEntryHealthpresenter.doGetLastestRecord(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.ll_left:
                    finish();
                break;
            case  R.id.ll_weight:
                show_weight_dialog();
                break;
            case  R.id.ll_pysical:
                show_pysical_dialog();
                break;
            case  R.id.ll_fat:
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(RecordEvent recordEvent) {
        dialogDissmiss();
        if(recordEvent.lastestRecordModel()==null){
            return;
        }
        lastestRecordModel = recordEvent.lastestRecordModel();
        tv_weight.setText(Float.parseFloat(lastestRecordModel.getWeight())+"");
        et_pysical.setText(Float.parseFloat(lastestRecordModel.getPysical())+"");
        et_fat.setText(Float.parseFloat(lastestRecordModel.getFat())+"");
        tv_circum.setText(Float.parseFloat(lastestRecordModel.getCircum())+"");
        tv_waistline.setText(Float.parseFloat(lastestRecordModel.getWaistline())+"");
        tv_hiplie.setText(Float.parseFloat(lastestRecordModel.getHiplie())+"");
        tv_uparmgirth.setText(Float.parseFloat(lastestRecordModel.getUpArmGirth())+"");
        tv_upleggirth.setText(Float.parseFloat(lastestRecordModel.getUpLegGirth())+"");
        tv_doleggirth.setText(Float.parseFloat(lastestRecordModel.getDoLegGirth())+"");

    }

    //体重对话框
    public void show_weight_dialog() {
        final AlertDialog.Builder birdialog=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog,null);
        final NumberPicker np = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        np.setValue(150);
        np.setMinValue(20);
        np.setWrapSelectorWheel(false);
        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (np.getValue() < 80) {
                    Dialog dialog1 = new AlertDialog.Builder(HealthEntryActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                                            tv_weight.setError(null);
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            show_weight_dialog();
                                        }
                                    }).create();
                    dialog1.show();
                    dialog1.setCanceledOnTouchOutside(false);
                } else {
                    tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                    tv_weight.setError(null);
                }
                dialog.dismiss();
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
        np1.setMaxValue(99);
        np1.setValue(50);
        np1.setMinValue(0);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
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
        np1.setMaxValue(220);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
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
        np1.setMaxValue(220);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
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
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
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
        String fat =et_fat.getText().toString();
        String circum = tv_circum.getText().toString();
        String waistline =tv_waistline.getText().toString();
        String hiplie =tv_hiplie.getText().toString();
        String uparmgirth =tv_uparmgirth.getText().toString();
        String upleggirth = tv_upleggirth.getText().toString();
        String doleggirth =tv_doleggirth.getText().toString();

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
        dialogShow("正在提交");
        iEntryHealthpresenter.entryhealthrecord(healthModele);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        new AlertDialog.Builder(this).setMessage(failedRule.getFailureMessage()).create().show();
    }

    @Override
    public void saveSuccess(boolean res) {
        dialogDissmiss();
        if(res) {
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }
}
