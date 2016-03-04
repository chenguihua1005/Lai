package com.softtek.lai.module.login.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.presenter.IPasswordPresenter;
import com.softtek.lai.module.login.presenter.PasswordPresnter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_forget2)
public class ForgetActivity2 extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.et_repassword)
    EditText et_repassword;

    private IPasswordPresenter passwordPresenter;

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
        passwordPresenter=new PasswordPresnter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                String psd1=et_password.getText().toString();
                String psd2=et_repassword.getText().toString();
                passwordPresenter.resetPassword(psd1,psd2);
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


}
