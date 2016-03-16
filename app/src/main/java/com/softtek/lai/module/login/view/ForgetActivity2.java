package com.softtek.lai.module.login.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.presenter.IPasswordPresenter;
import com.softtek.lai.module.login.presenter.PasswordPresnter;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_forget2)
public class ForgetActivity2 extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    @LifeCircleInject
    ValidateLife validateLife;

    @Password(order = 1)
    @TextRule(order = 2,minLength = 6,maxLength = 16,messageResId = R.string.passwordValidate)
    @InjectView(R.id.et_password)
    EditText et_password;

    @ConfirmPassword(order = 3,messageResId = R.string.confirmPassword)
    @InjectView(R.id.et_repassword)
    EditText et_repassword;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.btn_submit)
    Button btn_submit;

    private IPasswordPresenter passwordPresenter;
    private String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("输入新密码");
    }

    @Override
    protected void initDatas() {
        passwordPresenter=new PasswordPresnter(this);
        phone=getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.btn_submit:
                validateLife.validate();
                break;
        }
    }






    @Override
    public void onValidationSucceeded() {
        String psd=et_password.getText().toString();
        Log.i("手机号码>>>>"+phone);
        passwordPresenter.resetPassword(phone,psd);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }
}
