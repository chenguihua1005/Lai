/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_modify_name)
public class ModifyNameActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Required(order = 1, messageResId = R.string.nameNull)
    @InjectView(R.id.et_name)
    EditText et_name;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    private UserModel model;
    private String name;

    private ILoginPresenter presenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }


    @Override
    protected void initViews() {
        tv_title.setText("姓名");
        tv_right.setText("保存");
    }

    @Override
    protected void initDatas() {
        presenter = new LoginPresenterImpl(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");

        model = UserInfoModel.getInstance().getUser();
        name = model.getNickname();
        et_name.setText(name);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.tv_right:
            case R.id.fl_right:
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
        String str=et_name.getText().toString().trim();
        if(length(str)>12) {
            Util.toastMsg("姓名不能超过6个汉字");
        }else {
            progressDialog.show();
            presenter.getUpdateName(model.getUserid(), et_name.getText().toString(), progressDialog);
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }
    private boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    private int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }
}
