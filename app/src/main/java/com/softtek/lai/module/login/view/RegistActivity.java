package com.softtek.lai.module.login.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.module.login.presenter.IRegistPresenter;
import com.softtek.lai.module.login.presenter.RegistPresenterImpl;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_regist)
public class RegistActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private IRegistPresenter registPresenter;
    private MyCountDown countDown;

    @LifeCircleInject
    ValidateLife validateLife;

    @Regex(order = 1,patternResId = R.string.phonePattern,messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Password(order = 2)
    @TextRule(order = 3,minLength = 6,messageResId = R.string.passwordValidate)
    @InjectView(R.id.et_password)
    EditText et_password;

    @Required(order = 4,messageResId = R.string.identiftValidtae)
    @InjectView(R.id.et_identify)
    EditText et_identify;

    @InjectView(R.id.tv_get_identify)
    TextView tv_get_identify;

    @Checked(order = 5,messageResId = R.string.termValidate)
    @InjectView(R.id.cb_term)
    CheckBox cb_term;

    @InjectView(R.id.btn_regist)
    Button btn_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_get_identify.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
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
                Log.i("点击了按钮");
                countDown=new MyCountDown(60000,1000);
                countDown.start();
                String phone=et_phone.getText().toString();
                tv_get_identify.setEnabled(false);
                registPresenter.getIdentify(phone, Constants.REGIST_IDENTIFY);
                break;
            case R.id.btn_regist:
                validateLife.validate();
                break;

        }
    }

    @Override
    protected void onStop() {
        if(countDown!=null){
            countDown.cancel();
        }
        super.onStop();
    }

    //验证
    @Override
    public void onValidationSucceeded() {
        String phoneNum=et_phone.getText().toString();
        String password=et_password.getText().toString();
        String identify=et_identify.getText().toString();
        registPresenter.doRegist(phoneNum,password,identify);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

        validateLife.onValidationFailed(failedView,failedRule);
    }

    public class MyCountDown extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //更新获取验证码按钮文字
            Log.i("倒计时工作中-----"+millisUntilFinished/1000);
            tv_get_identify.setText("("+millisUntilFinished/1000+"s)后重新获取");
        }

        @Override
        public void onFinish() {
            tv_get_identify.setText("获取验证码");
            tv_get_identify.setEnabled(true);


        }
    }


}
