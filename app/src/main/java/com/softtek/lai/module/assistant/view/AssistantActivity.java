package com.softtek.lai.module.assistant.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.assistant.adapter.SimpleFragmentPagerAdapter;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_assistant)
public class AssistantActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener,BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;


    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    @InjectView(R.id.sliding_tabs)
    TabLayout sliding_tabs;



    private IAssistantPresenter assistantPresenter;
    private ACache aCache;

    private SimpleFragmentPagerAdapter pagerAdapter;

    List<Fragment> list=new ArrayList<Fragment>();
    Fragment assistantListFragment;
    Fragment assistantApplyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);


    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("创建班级");

        assistantListFragment=new AssistantListFragment();
        assistantApplyFragment=new AssistantApplyFragment();
        list.add(assistantApplyFragment);
        list.add(assistantListFragment);
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),this,list);

    }

    @Override
    protected void initDatas() {
        assistantPresenter = new AssistantImpl(this);
        aCache= ACache.get(this, Constants.USER_ACACHE_DATA_DIR);

        viewpager.setAdapter(pagerAdapter);
        sliding_tabs.setupWithViewPager(viewpager);
        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
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

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
