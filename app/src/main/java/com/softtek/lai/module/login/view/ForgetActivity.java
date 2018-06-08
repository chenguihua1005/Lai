/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.view;

import android.content.Intent;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.softtek.lai.module.login.presenter.ForgetPasswordPresenter;
import com.softtek.lai.utils.JCountDownTimer;
import com.softtek.lai.utils.RegexUtil;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_forget)
public class ForgetActivity extends BaseActivity<ForgetPasswordPresenter> implements View.OnClickListener, Validator.ValidationListener,ForgetPasswordPresenter.ForgetPasswordView {

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, messageResId = R.string.phoneValidateNull)
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Required(order = 3, messageResId = R.string.identiftValidtae)
    @InjectView(R.id.et_identify)
    EditText et_identify;

    @InjectView(R.id.tv_get_identify)
    TextView tv_get_identify;

    @InjectView(R.id.btn_next)
    Button btn_next;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    private CountDown countDown;


    @Override
    protected void initViews() {
        tv_get_identify.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_title.setText("重置密码");
    }

    @Override
    protected void initDatas() {
        countDown = new CountDown(60000, 1000);
        setPresenter(new ForgetPasswordPresenter(this));
    }
    /** 点击屏幕隐藏软键盘**/
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
                //验证手机号
                if ("".equals(phone) || !RegexUtil.match(getString(R.string.phonePattern), phone)) {
                    et_phone.setError(Html.fromHtml("<font color=#FFFFFF>" + getString(R.string.phoneValidate) + "</font>"));
                    return;
                }

                countDown.start();
                tv_get_identify.setEnabled(false);
                getPresenter().getIdentify(phone, Constants.RESET_PASSWORD_IDENTIFY);
                break;
            case R.id.ll_left:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.btn_next:
                validateLife.validate();
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

    @Override
    public void onValidationSucceeded() {
        String identify = et_identify.getText().toString();
        String phone = et_phone.getText().toString();
        getPresenter().checkIdentify(phone, identify);

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

    @Override
    public void checkIdentifySuccess(String phone,String identify) {
        Intent intent = new Intent(this, ForgetActivity2.class);
        intent.putExtra("phone", phone);
        intent.putExtra("identify", identify);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public class CountDown extends JCountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_get_identify.setText("(" + millisUntilFinished / 1000 + "s)后重新获取");
        }

        @Override
        public void onFinish() {
            if (tv_get_identify!=null){
                tv_get_identify.setText("发送验证码");
                tv_get_identify.setEnabled(true);
            }
        }
    }
}
