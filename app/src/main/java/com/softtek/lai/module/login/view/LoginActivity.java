/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.exit.AppManager;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private ILoginPresenter loginPresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, messageResId = R.string.phoneValidateNullLogin)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Required(order = 2, messageResId = R.string.passwordValidateNull)
    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.tv_login)
    TextView tv_login;

    @InjectView(R.id.tv_forgetpsd)
    TextView tv_forgetpsd;

    @InjectView(R.id.tv_regist)
    TextView tv_regist;

    @InjectView(R.id.ll_visitor)
    LinearLayout ll_visitor;


    private ProgressDialog progressDialog;

    @Override
    protected void initViews() {
        /*if (!isTaskRoot()) {
            finish();
            return;
        }*/
        tintManager.setStatusBarTintResource(R.drawable.grey_white);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("登录中，请稍候...");
        tv_login.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
        ll_visitor.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(this);
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
            case R.id.tv_login:
                /*Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.APP_MARKET");
                PackageManager pm = this.getPackageManager();
                List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
                int size = infos.size();
                for (int i = 0; i < size; i++) {
                    ActivityInfo activityInfo = infos.get(i).activityInfo;
                    String packageName = activityInfo.packageName;
                    Log.i("packageName : " + packageName);
                }*/
                validateLife.validate();
                break;
            case R.id.tv_forgetpsd:
                finish();
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.tv_regist:
                finish();
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.ll_visitor:
                finish();
                UserInfoModel.getInstance().visitorLogin();
                startActivity(new Intent(this, HomeActviity.class));
                break;

        }
    }

    @Override
    public void onValidationSucceeded() {

        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        progressDialog.show();
        loginPresenter.doLogin(phone, MD5.md5WithEncoder(password), progressDialog);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            AppManager.getAppManager().AppExit(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
