package com.softtek.lai.module.login.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.module.login.presenter.IRegistPresenter;
import com.softtek.lai.module.login.presenter.RegistPresenterImpl;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_forget)
public class ForgetActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.et_phone)
    EditText et_phone;

    @InjectView(R.id.et_identify)
    EditText et_identify;

    @InjectView(R.id.tv_get_identify)
    TextView tv_get_identify;

    private IRegistPresenter registPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_get_identify.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        setActionBarLayout(R.layout.actionbar);
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
                registPresenter.getIdentify(phone, Constants.RESET_PASSWORD_IDENTIFY);
                break;
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(this,ForgetActivity2.class));
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
            btn_title.setText("重置密码");
            btn_left.setText("返回");
            btn_right.setText("下一步");

        }
    }
}
