/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.presenter.ModifyPasswordPresenter;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenter> implements View.OnClickListener, Validator.ValidationListener {

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

    private String old_psd;
    private String new_psd;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.fragmentResetPassword);
        iv_email.setVisibility(View.GONE);
    }

    @Override
    protected void initDatas() {
        String type=getIntent().getStringExtra("type");
        token= getIntent().getStringExtra("token");
        if("1".equals(type)){
            ll_left.setVisibility(View.VISIBLE);
        }else {
            ll_left.setOnClickListener(this);
        }
        setPresenter(new ModifyPasswordPresenter(this));
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
        String type=getIntent().getStringExtra("type");
        if("1".equals(type)){
            getPresenter().changePsd(this,MD5.md5WithEncoder(new_psd), MD5.md5WithEncoder(old_psd),token,"1");
        }else {
            getPresenter().changePsd(this,MD5.md5WithEncoder(new_psd), MD5.md5WithEncoder(old_psd),token,"2");
        }
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            String type=getIntent().getStringExtra("type");
            if("1".equals(type)){

            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
