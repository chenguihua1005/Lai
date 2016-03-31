package com.softtek.lai.module.home.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.SimpleFragmentPagerAdapter;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.counselor.view.AssistantApplyFragment;
import com.softtek.lai.module.counselor.view.AssistantListFragment;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

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


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @Required(order = 1,messageResId = R.string.accountValidateNull)
    @InjectView(R.id.edit_account)
    EditText edit_account;

    @Required(order = 2,messageResId = R.string.passwordValidateNull)
    @InjectView(R.id.edit_password)
    EditText edit_password;

    @InjectView(R.id.but_validate)
    Button but_validate;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    private ILoginPresenter loginPresenter;
    private ACache aCache;

    private SimpleFragmentPagerAdapter pagerAdapter;

    List<Fragment> list = new ArrayList<Fragment>();
    Fragment assistantListFragment;
    Fragment assistantApplyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        but_validate.setOnClickListener(this);


    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("身份认证");

    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);


    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.but_validate:
                validateLife.validate();
                break;
            case R.id.tv_left:
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
        String account=edit_account.getText().toString();
        String password=edit_password.getText().toString();
        String memberId="CN1357499";
        loginPresenter.alidateCertification(memberId,password,account);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
