package com.softtek.lai.module.home.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.SimpleFragmentPagerAdapter;
import com.softtek.lai.module.counselor.model.HonorInfoModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.counselor.view.AssistantApplyFragment;
import com.softtek.lai.module.counselor.view.AssistantListFragment;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_validate_certification)
public class ValidateCertificationActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

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
    private ACache aCache;


    private UserModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        but_validate.setOnClickListener(this);
        EventBus.getDefault().register(this);

    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RoleInfo roleInfo) {
        System.out.println("roleInfo:" + roleInfo);
        setData();

    }
    @Override
    protected void initViews() {
        tv_title.setText("身份认证");

    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);

        setData();
        edit_password.setText("");
        edit_account.setText("");
    }
    private void setData(){
        model = UserInfoModel.getInstance().getUser();
        if(model.getCertTime()==null||"".equals(model.getCertTime())){
            text_time.setText("");
        }else {
            text_time.setText("(上次认证时间："+model.getCertTime().split(" ")[0]+")");
        }
        String userrole=model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {
            text_value.setText("游客");
        }else if (String.valueOf(Constants.INC).equals(userrole)) {
            text_value.setText("受邀用户");
        }else if (String.valueOf(Constants.SP).equals(userrole)) {
            text_value.setText("顾问");
        }else if (String.valueOf(Constants.SR).equals(userrole)) {
            text_value.setText("助教");
        }else if (String.valueOf(Constants.PC).equals(userrole)) {
            text_value.setText("贵宾顾客");
        }else if (String.valueOf(Constants.NC).equals(userrole)) {
            text_value.setText("未认证用户");
        }
    }
    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.but_validate:
                validateLife.validate();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {
        String account = model.getUserid().toString();
        String password = edit_password.getText().toString();
        String memberId = edit_account.getText().toString();
        loginPresenter.alidateCertification(memberId, password, account);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
