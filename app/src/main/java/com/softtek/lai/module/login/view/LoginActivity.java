package com.softtek.lai.module.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.ZillaApplication;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.File.presenter.CreateFile;
import com.softtek.lai.module.home.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.File.view.Height;
import com.softtek.lai.module.home.File.view.Mydata;
import com.softtek.lai.module.login.model.UserFile;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;

import org.w3c.dom.Text;

import butterknife.InjectView;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private ILoginPresenter loginPresenter;

    @InjectView(R.id.et_phone)
    EditText et_phone;

    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.tv_login)
    TextView tv_login;

    @InjectView(R.id.tv_forgetpsd)
    TextView tv_forgetpsd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_login.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        setActionBarLayout(R.layout.actionbar);
    }

    @Override
    protected void initDatas() {
        loginPresenter=new LoginPresenterImpl();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                String phone=et_phone.getText().toString();
                String password=et_password.getText().toString();
                loginPresenter.doLogin(phone,password);
                Intent intent=new Intent(LoginActivity.this,CreatFlleActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forgetpsd:
                startActivity(new Intent(this,ForgetActivity.class));
                break;
            case R.id.tv_left:
                startActivity(new Intent(this,RegistActivity.class));
                break;
            case R.id.tv_right:
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
            v.findViewById(R.id.tv_left).setOnClickListener(this);
            v.findViewById(R.id.tv_right).setOnClickListener(this);
        }
    }

}
