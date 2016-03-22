package com.softtek.lai.module.login.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
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

    @Required(order = 1,messageResId = R.string.newPasswordNull)
    @Password(order = 2)
    @TextRule(order = 3,minLength = 6,maxLength = 16,messageResId = R.string.passwordValidate)
    @InjectView(R.id.et_password)
    EditText et_password;

    @Required(order = 4,messageResId = R.string.resetpasswordNull)
    @ConfirmPassword(order = 5,messageResId = R.string.confirmPassword)
    @InjectView(R.id.et_repassword)
    EditText et_repassword;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.btn_submit)
    Button btn_submit;

    private IPasswordPresenter passwordPresenter;
    private String phone="";
    private String identify="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_title.setText("输入新密码");
    }

    @Override
    protected void initDatas() {
        passwordPresenter=new PasswordPresnter(this);
        phone=getIntent().getStringExtra("phone");
        identify=getIntent().getStringExtra("identify");
        Log.i("phone:"+phone+";identify:"+identify);
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()){
            case R.id.ll_left:
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
        passwordPresenter.resetPassword(phone,psd,identify);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }
}
