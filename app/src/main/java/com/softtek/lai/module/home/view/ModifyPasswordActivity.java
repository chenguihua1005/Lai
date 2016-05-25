/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.google.gson.annotations.Until;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.presenter.IPasswordPresenter;
import com.softtek.lai.module.login.presenter.PasswordPresnter;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;
    @Required(order = 1, messageResId = R.string.oldPasswordNull)
    @InjectView(R.id.et_old_password)
    EditText et_old_password;

    @Required(order = 2, messageResId = R.string.newPasswordNull)
    @Password(order = 3)
    @Regex(order = 4, pattern = "(?![^a-zA-Z]+$)(?!\\D+$).{6,16}", messageResId = R.string.npasswordValidate)
    //@TextRule(order = 3,minLength = 6,maxLength = 16,messageResId = R.string.passwordValidate)
    @InjectView(R.id.et_password)
    EditText et_password;

    @Required(order = 4, messageResId = R.string.resetNewpasswordNull)
    @ConfirmPassword(order = 5, messageResId = R.string.confirmPassword)
    @InjectView(R.id.et_repassword)
    EditText et_repassword;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.btn_submit)
    Button btn_submit;

    private IPasswordPresenter passwordPresenter;
    private String phone = "";
    private String identify = "";

    private String old_psd;
    private String new_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.fragmentResetPassword);
        iv_email.setVisibility(View.GONE);
    }

    @Override
    protected void initDatas() {
        passwordPresenter = new PasswordPresnter(this);
        phone = getIntent().getStringExtra("phone");
        identify = getIntent().getStringExtra("identify");
        Log.i("phone:" + phone + ";identify:" + identify);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_submit:
                validateLife.validate();
                break;
        }
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
    public void onValidationSucceeded() {
        old_psd = et_old_password.getText().toString();
        new_psd = et_password.getText().toString();
        System.out.println("old_psd:"+old_psd+"   new_psd:"+new_psd);
        passwordPresenter.changePsd(MD5.md5WithEncoder(new_psd), MD5.md5WithEncoder(old_psd));
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if(failedView instanceof EditText && failedView.getId()==R.id.et_password){
            String message=failedRule.getFailureMessage();
            failedView.requestFocus();
            ((EditText)failedView).setError(Html.fromHtml("<small>"+message+"</small>"));
        }else {
            validateLife.onValidationFailed(failedView, failedRule);
        }

    }
}
