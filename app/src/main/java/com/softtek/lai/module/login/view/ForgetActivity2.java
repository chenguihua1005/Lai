package com.softtek.lai.module.login.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    @TextRule(order = 2,minLength = 6,messageResId = R.string.passwordValidate)
    @InjectView(R.id.et_password)
    EditText et_password;

    @ConfirmPassword(order = 3,messageResId = R.string.confirmPassword)
    @InjectView(R.id.et_repassword)
    EditText et_repassword;

    private IPasswordPresenter passwordPresenter;
    private String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        setActionBarLayout(R.layout.actionbar);
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
            case R.id.tv_right:
                validateLife.validate();
                break;
        }
    }

    private void setActionBarLayout(int layoutId){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);

            actionBar.setCustomView(layoutId);
            View v=actionBar.getCustomView();
            TextView btn_left= (TextView) v.findViewById(R.id.tv_left);
            btn_left.setOnClickListener(this);
            TextView btn_right= (TextView) v.findViewById(R.id.tv_right);
            btn_right.setOnClickListener(this);
            TextView btn_title= (TextView) v.findViewById(R.id.tv_title);
            btn_title.setText("输入新密码");
            btn_left.setText("返回");
            btn_right.setText("提交");

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
