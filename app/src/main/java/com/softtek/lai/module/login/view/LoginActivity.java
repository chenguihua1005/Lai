package com.softtek.lai.module.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.ACache;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private ILoginPresenter loginPresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1,messageResId = R.string.phoneValidateNull)
    @Regex(order = 2,patternResId = R.string.phonePattern,messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Required(order = 3,messageResId = R.string.passwordValidateNull)
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

    @InjectView(R.id.cb_remember)
    CheckBox cb_remember;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_login.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
        ll_visitor.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("登录中，请稍候...");
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
                break;
            case R.id.tv_forgetpsd:
                startActivity(new Intent(this,ForgetActivity.class));
                break;
            case R.id.tv_regist:
                startActivity(new Intent(this,RegistActivity.class));
                break;
            case R.id.ll_visitor:
                User user=new User();
                user.setUserrole(String.valueOf(Constants.VR));
                user.setNickname("游客");
                ACache.get(this, Constants.USER_ACACHE_DATA_DIR).put(Constants.USER_ACACHE_KEY,user);
                startActivity(new Intent(this, HomeActviity.class));
                break;

        }
    }



    @Override
    public void onValidationSucceeded() {

        String phone=et_phone.getText().toString();
        String password=et_password.getText().toString();
        if(cb_remember.isChecked()){
            SharedPreferenceService.getInstance().put(Constants.AUTO_LOGIN,true);
            SharedPreferenceService.getInstance().put(Constants.AUTO_USER_NAME,phone);
            SharedPreferenceService.getInstance().put(Constants.AUTO_PASSWORD,password);
        }else{
            SharedPreferenceService.getInstance().put(Constants.AUTO_LOGIN,false);
            SharedPreferenceService.getInstance().put(Constants.AUTO_USER_NAME,"");
            SharedPreferenceService.getInstance().put(Constants.AUTO_PASSWORD,"");
        }
        progressDialog.show();
        loginPresenter.doLogin(phone,password,progressDialog);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }

    
}
