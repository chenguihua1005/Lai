package com.softtek.lai.module.home.view;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.presenter.CertificationPresenter;
import com.softtek.lai.module.login.model.RefreshCertificationModel;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * 资格号认证
 */
@InjectLayout(R.layout.activity_validate_certification)
public class ValidateCertificationActivity extends BaseActivity<CertificationPresenter> implements View.OnClickListener,
        Validator.ValidationListener, CertificationPresenter.CertificationView {

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

    @InjectView(R.id.tv_right)
    TextView mRefresh;

    private UserModel model;
    private boolean canRefresh = false;


    @Override
    public void getData(RoleInfo data) {
        edit_password.setText("");
        edit_account.setText("");
        setData();

        //更新客户管理页面   已认证
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("ALREADY_CERTIFICATION"));

        Intent intent = new Intent(this, HomeActviity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initViews() {
        tv_title.setText("身份认证");
        ll_left.setOnClickListener(this);
        but_validate.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        setPresenter(new CertificationPresenter(this));
        setData();
        edit_password.setText("");
        edit_account.setText("");
        mRefresh.setText("刷新");
        mRefresh.setTextColor(getResources().getColor(R.color.white));
        canRefresh = getIntent().getBooleanExtra("CanRefreshCertification",false);
        if (canRefresh){
            mRefresh.setVisibility(View.VISIBLE);
        }else {
            mRefresh.setVisibility(View.GONE);
        }
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
            case R.id.tv_right:
                dialogShow("刷新中");
                ZillaApi.NormalRestAdapter.create(LoginService.class).refreshCertification(UserInfoModel.getInstance().getToken(), new Callback<ResponseData<RefreshCertificationModel>>() {
                    @Override
                    public void success(ResponseData<RefreshCertificationModel> responseData, Response response) {
                        dialogDissmiss();
                        if (responseData.getStatus() == 200) {
                            if (responseData.getData() != null) {
                                text_value.setText(responseData.getData().getRole());
                                text_time.setText("(上次认证时间：" + responseData.getData().getCertTime().split(" ")[0] + ")");
                                Toast.makeText(ValidateCertificationActivity.this,"已获取最新认证结果",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(ValidateCertificationActivity.this,responseData.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        dialogDissmiss();
                        ZillaApi.dealNetError(retrofitError);
                    }
                });
        }
    }


    @Override
    public void onValidationSucceeded() {
        String account = model.getUserid().toString();
        String password = edit_password.getText().toString();
        password = Base64.encodeToString(password.getBytes(), Base64.NO_WRAP);
        String memberId = edit_account.getText().toString();
        dialogShow("认证中...");
        getPresenter().validateCertification(memberId, password, account);
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
