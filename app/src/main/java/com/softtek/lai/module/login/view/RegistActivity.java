package com.softtek.lai.module.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.model.UserFile;
import com.softtek.lai.module.login.presenter.IRegistPresenter;
import com.softtek.lai.module.login.presenter.RegistPresenterImpl;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_regist)
public class RegistActivity extends BaseActivity implements IRegistView,View.OnClickListener{

    private IRegistPresenter registPresenter;

    @InjectView(R.id.et_phone)
    EditText et_phone;

    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.et_identify)
    EditText et_identify;

    @InjectView(R.id.tv_get_identify)
    TextView tv_get_identify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_get_identify.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        registPresenter=new RegistPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_get_identify:
                String phone=et_phone.getText().toString();
                registPresenter.getIdentify(phone,"0");
                /*String phoneNum=et_phone.getText().toString();
                String password=et_password.getText().toString();
                registPresenter.doRegist(phoneNum,password,"0");*/
                break;
            case R.id.tv_regist:

                break;
        }
    }
}
