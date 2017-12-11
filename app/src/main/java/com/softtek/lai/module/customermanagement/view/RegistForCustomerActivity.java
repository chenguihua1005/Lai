/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.customermanagement.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.customermanagement.presenter.RegistCustomerPresenter;
import com.softtek.lai.module.login.view.TermActivity;
import com.softtek.lai.utils.JCountDownTimer;
import com.softtek.lai.utils.RegexUtil;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_regist_customer)
public class RegistForCustomerActivity extends BaseActivity<RegistCustomerPresenter> implements View.OnClickListener,
        Validator.ValidationListener, RegistCustomerPresenter.RegisterForCustomerCallback {

    private MyCountDown countDown;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, messageResId = R.string.phoneValidateNull)
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

//    @Password(order = 3, messageResId = R.string.passwordValidateNull)
//    @Regex(order = 4, pattern = "(?![^a-zA-Z]+$)(?!\\D+$).{6,16}", messageResId = R.string.passwordValidate)
//    @InjectView(R.id.et_password)
//    EditText et_password;
//
//    @Required(order = 5, messageResId = R.string.confirmPasswordNull)
//    @ConfirmPassword(order = 6, messageResId = R.string.confirmPassword)
//    @InjectView(R.id.et_repassword)
//    EditText et_repassword;

    @Required(order = 7, messageResId = R.string.identiftValidtae)
    @InjectView(R.id.et_identify)
    EditText et_identify;

    @InjectView(R.id.tv_get_identify)
    TextView tv_get_identify;

    @InjectView(R.id.cb_term)
    CheckBox cb_term;

    @InjectView(R.id.btn_regist)
    Button btn_regist;

    @InjectView(R.id.tv_protocol)
    TextView tv_protocol;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;


    @Override
    protected void initViews() {
        tv_title.setText("注册");
        tv_get_identify.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        tv_protocol.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        //初始化倒计时
        setPresenter(new RegistCustomerPresenter(this));
        countDown = new MyCountDown(60000, 1000);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_identify:
                String phone = et_phone.getText().toString();
                boolean validate = validateGetIdentify(this, et_phone);
                if (!validate) {
                    return;
                }

                countDown.start();
                tv_get_identify.setEnabled(false);
                getPresenter().getIdentify(phone, Constants.REGIST_IDENTIFY);
                break;
            case R.id.btn_regist:
                validateLife.validate();
                break;
            case R.id.tv_protocol:
                startActivity(new Intent(this, TermActivity.class));
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (countDown != null && countDown.isRunning()) {
            countDown.reStart();

        }
    }

    @Override
    public void onStop() {

        if (countDown != null && countDown.isRunning()) {
            countDown.pause();

        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (countDown != null) {
            countDown.cancel();
        }
        super.onDestroy();
    }

    //验证
    @Override
    public void onValidationSucceeded() {
        if (!cb_term.isChecked()) {
            Util.toastMsg("请先勾选用户协议");
            return;
        }
        btn_regist.setEnabled(false);
        final String phoneNum = et_phone.getText().toString();

        Intent intent = new Intent(this, RegistForCustomerInfoActivity.class);
        intent.putExtra("mobile", phoneNum);
        startActivity(intent);
        finish();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

        validateLife.onValidationFailed(failedView, failedRule);
    }

    @Override
    public void getIdentifyCallback(boolean result, int status) {
        if (!result) {
            if (countDown != null) {
                countDown.cancel();
                countDown.onFinish();
            }
        }
        if (status == 100) {//已注册
            new AlertDialog.Builder(RegistForCustomerActivity.this).setTitle("温馨提示").setMessage("是否直接添加为意向客户？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String phoneNum = et_phone.getText().toString().trim();
                            Intent intent = new Intent(RegistForCustomerActivity.this, NewCustomerActivity.class);
                            intent.putExtra("mobile", phoneNum);
                            intent.putExtra("needQuery", true);//需要查询基础数据
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create().show();

        }
    }

    public boolean validateGetIdentify(Context context, EditText et_phone) {
        String phone = et_phone.getText().toString();

        if ("".equals(phone)) {
            et_phone.requestFocus();
            et_phone.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.phoneValidateNull) + "</font>"));
            return false;
        }
        if (!RegexUtil.match(context.getString(R.string.phonePattern), phone)) {
            et_phone.requestFocus();
            et_phone.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.phoneValidate) + "</font>"));
            return false;
        }
//        String password = et_password.getText().toString();
//        if ("".equals(password)) {
//            et_password.requestFocus();
//            et_password.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.passwordValidateNull) + "</font>"));
//            return false;
//        }
//        if (!RegexUtil.match(context.getString(R.string.psdReg), password)) {
//            et_password.requestFocus();
//            et_password.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.passwordValidate) + "</font>"));
//            return false;
//        }
//        String rePassword = et_rePassword.getText().toString();
//        if ("".equals(rePassword)) {
//            et_rePassword.requestFocus();
//            et_rePassword.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.confirmPasswordNull) + "</font>"));
//            return false;
//        }
//        if (!rePassword.equals(password)) {
//            et_rePassword.requestFocus();
//            et_rePassword.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.confirmPassword) + "</font>"));
//            return false;
//        }
        return true;
    }


    public class MyCountDown extends JCountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            try {
                tv_get_identify.setText("(" + millisUntilFinished / 1000 + "s)后重新获取");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
            try {
                tv_get_identify.setText("发送验证码");
                tv_get_identify.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
