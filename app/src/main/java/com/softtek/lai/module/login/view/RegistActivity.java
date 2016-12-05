/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.view;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.presenter.IRegistPresenter;
import com.softtek.lai.module.login.presenter.RegistPresenterImpl;
import com.softtek.lai.utils.JCountDownTimer;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_regist)
public class RegistActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, RegistPresenterImpl.IdentifyCallBack {
    private static final String TAG = "RegistActivity";

    private IRegistPresenter registPresenter;
    private MyCountDown countDown;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, messageResId = R.string.phoneValidateNull)
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Password(order = 3, messageResId = R.string.passwordValidateNull)
    @Regex(order = 4, pattern = "(?![^a-zA-Z]+$)(?!\\D+$).{6,16}", messageResId = R.string.passwordValidate)
    @InjectView(R.id.et_password)
    EditText et_password;

    @Required(order = 5, messageResId = R.string.confirmPasswordNull)
    @ConfirmPassword(order = 6, messageResId = R.string.confirmPassword)
    @InjectView(R.id.et_repassword)
    EditText et_repassword;

    @Required(order = 7, messageResId = R.string.identiftValidtae)
    @InjectView(R.id.et_identify)
    EditText et_identify;

    @InjectView(R.id.tv_get_identify)
    TextView tv_get_identify;

    //    @Checked(order = 8)
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
//        if (!isTaskRoot()) {
//            finish();
//            return;
//        }
    }

    @Override
    protected void initDatas() {
        //初始化倒计时
        countDown = new MyCountDown(60000, 1000);
        registPresenter = new RegistPresenterImpl(this, this);
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
                boolean validate = registPresenter.validateGetIdentify(et_phone, et_password, et_repassword);
                if (!validate) {
                    return;
                }
                countDown.start();
                tv_get_identify.setEnabled(false);
                registPresenter.getIdentify(phone, Constants.REGIST_IDENTIFY);
                break;
            case R.id.btn_regist:
                et_password.setError(null);
                et_repassword.setError(null);
                validateLife.validate();
                break;
            case R.id.tv_protocol:
                startActivity(new Intent(this, TermActivity.class));
                break;
            case R.id.ll_left:
                startActivity(new Intent(this, LoginActivity.class));
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
        final String phoneNum = et_phone.getText().toString();
        final String password = et_password.getText().toString();


        Log.i(TAG, "HXAccountId =" + MD5.md5WithEncoder(phoneNum));
        // 欢心环信注册
        new Thread(new Runnable() {
            @Override
            public void run() {
                // call method in SDK
                try {
                    EMClient.getInstance().createAccount(MD5.md5WithEncoder(phoneNum), "HBL_SOFTTEK#321");
                    registPresenter.doRegist(phoneNum, MD5.md5WithEncoder(password), MD5.md5WithEncoder(phoneNum), et_identify);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        }).start();


//        registPresenter.doRegist(phoneNum, MD5.md5WithEncoder(password), MD5.md5WithEncoder(phoneNum), et_identify);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

        validateLife.onValidationFailed(failedView, failedRule);
    }

    @Override
    public void getIdentifyCallback(boolean result) {
        if (!result) {
            if (countDown != null) {
                countDown.cancel();
                countDown.onFinish();
            }
        }
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
            tv_get_identify.setText("(" + millisUntilFinished / 1000 + "s)后重新获取");
        }

        @Override
        public void onFinish() {
            tv_get_identify.setText("发送验证码");
            tv_get_identify.setEnabled(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
