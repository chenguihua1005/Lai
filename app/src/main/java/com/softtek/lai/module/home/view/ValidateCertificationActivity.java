package com.softtek.lai.module.home.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

import static com.softtek.lai.R.id.context;

/**
 * 资格号认证
 */
@InjectLayout(R.layout.activity_validate_certification)
public class ValidateCertificationActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener{

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @Required(order = 1, messageResId = R.string.accountValidateNull)
    @InjectView(R.id.edit_account)
    EditText edit_account;

    @Required(order = 2, messageResId = R.string.passwordValidateNull)
    @InjectView(R.id.edit_password)
    EditText edit_password;

    @InjectView(R.id.but_validate)
    Button but_validate;

    @InjectView(R.id.text_time)
    TextView text_time;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_value)
    TextView text_value;


    private ILoginPresenter loginPresenter;


    private UserModel model;



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RoleInfo roleInfo) {
        edit_password.setText("");
        edit_account.setText("");
        setData();

        Intent intent=new Intent(this, HomeActviity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        tv_title.setText("身份认证");
        ll_left.setOnClickListener(this);
        but_validate.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(this);
        setData();
        edit_password.setText("");
        edit_account.setText("");
    }

    private void setData() {
        model = UserInfoModel.getInstance().getUser();
        if (TextUtils.isEmpty(model.getCertTime())) {
            text_time.setText("");
        } else {
            text_time.setText("(上次认证时间：" + model.getCertTime().split(" ")[0] + ")");
        }
        text_value.setText(model.getRoleName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_validate:
                validateLife.validate();
                break;
            case R.id.ll_left:
                startActivity(new Intent(this, HomeActviity.class));
                finish();
                break;
        }
    }


    @Override
    public void onValidationSucceeded() {
        String account = model.getUserid().toString();
        String password = edit_password.getText().toString();
        String memberId = edit_account.getText().toString();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("认证中...");
        progressDialog.show();
        loginPresenter.alidateCertification(memberId, password, account, progressDialog);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(this, HomeActviity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

}
