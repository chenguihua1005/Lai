package com.softtek.lai.module.laicheng;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.presenter.VisitorPresenter;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RegexUtil;
import com.softtek.lai.utils.SoftInputUtil;

import org.joda.time.DateTime;

import java.util.Date;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visitorinfo)
public class VisitorinfoActivity extends BaseActivity<VisitorPresenter> implements VisitorPresenter.VisitorView, View.OnClickListener, Validator.ValidationListener, View.OnTouchListener {
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

    @InjectView(R.id.et_mobile)
    EditText et_mobile;
    @InjectView(R.id.btn_commit)
    Button btn_commit;
    private int gender;
    VisitorModel visitorModel = new VisitorModel();
    private String date;
    String currentDate = DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();
    private int choose_year;

    @Override
    protected void initViews() {
        tv_title.setText("访客信息");
//        et_height.setInputType(InputType.TYPE_NULL);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(et_old.getWindowToken(), 0);
//        et_old.setInputType(InputType.TYPE_NULL);
        btn_commit.setOnClickListener(this);
//        et_old.setOnClickListener(this);
        et_old.setOnTouchListener(this);
        iv_email.setOnClickListener(this);
//        et_height.setOnClickListener(this);
        et_height.setOnTouchListener(this);

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
                et_old.setError(null);
                et_height.setError(null);
                validateLife.validate();
                break;
            case R.id.iv_email:
//                Intent intent = new Intent();
//                intent.putExtra("type", 110);
//                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void commit(Visitsmodel visitsmodel, VisitorModel Model) {
        Model.setVisitorId(visitsmodel.getVisitorId());
        LocalBroadcastManager.getInstance(LaiApplication.getInstance().getApplicationContext()).
                sendBroadcast(new Intent().setAction("visitorinfo").putExtra("visitorModel", Model).putExtra("choose", choose_year));
//        Intent intent = new Intent();
//        intent.putExtra("choose", choose_year);
//        intent.putExtra("visitorModel", Model);
//        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        String phone = et_mobile.getText().toString();
        if (!TextUtils.isEmpty(phone) && !RegexUtil.match(getResources().getString(R.string.phonePattern), phone)) {
            et_mobile.requestFocus();
            et_mobile.setError(Html.fromHtml("<font color=#FFFFFF>" + getResources().getString(R.string.phoneValidate) + "</font>"));
            return;
        }
        visitorModel.setName(et_name.getText().toString());
        visitorModel.setBirthDate(et_old.getText().toString());
        visitorModel.setHeight(Float.parseFloat(et_height.getText().toString()));
        visitorModel.setPhoneNo(phone);
        visitorModel.setGender(gender);
        getPresenter().commitData(UserInfoModel.getInstance().getToken(), visitorModel);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            Intent intent = new Intent();
//            intent.putExtra("type", 110);
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.et_old:
                    DateTime dt=new DateTime(1900,1,1,0,0);
                    final DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, dt.getYear(), dt.getMonthOfYear()-1, dt.getDayOfMonth());
                    datePickerDialog.getDatePicker().setMinDate(dt.getMillis());
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
                            choose_year = year;
                            Log.i("choose", choose_year + "");
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
                case R.id.et_height:
                    if (!TextUtils.isEmpty(et_old.getText().toString())) {
                        et_old.setError(null);
                    }
                    final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
                    View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
                    final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
                    final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
                    np1.setMaxValue(240);
                    if (gender == 0) {
                        np1.setValue(170);
                    } else if (gender == 1) {
                        np1.setValue(155);
                    } else {
                        np1.setValue(155);
                    }
                    np1.setMinValue(100);
                    np1.setWrapSelectorWheel(false);
                    np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                    np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    np2.setMaxValue(9);
                    np2.setValue(0);
                    np2.setMinValue(0);
                    np2.setWrapSelectorWheel(false);

                    birdialog.setTitle("选择身高(单位：cm)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            et_height.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                            et_height.setError(null);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                    break;
            }
        }
        return true;
    }
}
