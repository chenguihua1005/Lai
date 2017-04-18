package com.softtek.lai.module.laicheng;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.presenter.VisitorPresenter;
import com.softtek.lai.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visitorinfo)
public class VisitorinfoActivity extends BaseActivity<VisitorPresenter> implements VisitorPresenter.VisitorView, View.OnClickListener, Validator.ValidationListener {
    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @Required(order = 1, message = "请输入姓名")
    @InjectView(R.id.et_name)
    EditText et_name;

    @Required(order = 2, message = "请选择生日")
    @InjectView(R.id.et_old)
    EditText et_old;

    @Required(order = 3, message = "请输入身高")
    @InjectView(R.id.et_height)
    EditText et_height;

    @InjectView(R.id.rg_up)
    RadioGroup rg_up;

    @Regex(order = 4, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_mobile)
    EditText et_mobile;
    @InjectView(R.id.btn_commit)
    Button btn_commit;
    private int gender;
    VisitorModel visitorModel = new VisitorModel();
    private String date;
    String currentDate = DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();

    @Override
    protected void initViews() {
        tv_title.setText("访客信息");
        btn_commit.setOnClickListener(this);
        et_old.setOnClickListener(this);
        iv_email.setOnClickListener(this);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(et_old.getWindowToken(), 0);
        et_old.setInputType(InputType.TYPE_NULL);
    }

    @Override
    protected void initDatas() {
        setPresenter(new VisitorPresenter(this));
        rg_up.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int groupId = group.getCheckedRadioButtonId();
                switch (groupId) {
                    case R.id.rb_male:
                        gender = 0;//男
                        break;
                    case R.id.rb_female:
                        gender = 1; //女
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                Log.i("提交访客信息", visitorModel.toString());
                validateLife.validate();
                break;
            case R.id.et_old:
                final Calendar c = Calendar.getInstance();
                c.setTime(new Date(1900-01-01));
                final DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(c.getTime().getTime());
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datePickerDialog.cancel();
                    }
                });
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatePicker datePicker = datePickerDialog.getDatePicker();
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        int day = datePicker.getDayOfMonth();
                        date = year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
                        Log.i("日期", currentDate);
                        int compare = DateUtil.getInstance(DateUtil.yyyy_MM_dd).compare(date, currentDate);
                        Log.e("132", compare + "");
                        if (compare <= 0) {
                            Log.i("日期", date);
                            et_old.setText(date);
                        }

                    }
                });
                datePickerDialog.show();
                break;
            case R.id.iv_email:
                finish();
                break;
        }
    }

    @Override
    public void commit(Visitsmodel visitsmodel, VisitorModel Model) {
        Model.setVisitorId(visitsmodel.getVisitorId());
        Intent intent = new Intent();
        intent.putExtra("visitorModel", Model);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        visitorModel.setName(et_name.getText().toString());
        visitorModel.setHeight(Float.parseFloat(et_height.getText().toString()));
        visitorModel.setBirthDate(et_old.getText().toString());
        visitorModel.setPhoneNo(et_mobile.getText().toString());
        visitorModel.setGender(gender);
        getPresenter().commitData(UserInfoModel.getInstance().getToken(), visitorModel);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }
}
