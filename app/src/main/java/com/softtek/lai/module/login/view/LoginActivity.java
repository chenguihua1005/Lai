package com.softtek.lai.module.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.View.HomeActviity;
import com.softtek.lai.module.home.tab.TabMainActivity;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private ILoginPresenter loginPresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Regex(order = 1,patternResId = R.string.phonePattern,messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Password(order = 2)
    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.tv_login)
    TextView tv_login;

    @InjectView(R.id.tv_forgetpsd)
    TextView tv_forgetpsd;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_login.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_left.setText("注册");
        tv_title.setText("登录");
        tv_right.setText("游客模式");
    }

    @Override
    protected void initDatas() {
        loginPresenter=new LoginPresenterImpl(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                validateLife.validate();
                startActivity(new Intent(LoginActivity.this, CreatFlleActivity.class));
                break;
            case R.id.tv_forgetpsd:
                startActivity(new Intent(this,ForgetActivity.class));
                break;
            case R.id.tv_left:
                startActivity(new Intent(this,RegistActivity.class));
                break;
            case R.id.tv_right:
                startActivity(new Intent(this, HomeActviity.class));
                break;

        }
    }



    @Override
    public void onValidationSucceeded() {

        String phone=et_phone.getText().toString();
        String password=et_password.getText().toString();
        loginPresenter.doLogin(phone,password);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }

    
}
