/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.InjectView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.SimpleFragmentPagerAdapter;
import com.softtek.lai.module.counselor.model.AssistantClassInfoModel;
import com.softtek.lai.module.counselor.model.AssistantInfoModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_assistant)
public class AssistantActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_apply)
    TextView text_apply;

    @InjectView(R.id.text_list)
    TextView text_list;

    //
//    @InjectView(R.id.viewpager)
//    ViewPager viewpager;
//
//    @InjectView(R.id.sliding_tabs)
//    TabLayout sliding_tabs;

    private boolean isShow = false;

    List<AssistantInfoModel> list;

    @InjectView(R.id.rel_all_class_more)
    RelativeLayout rel_all_class_more;

    @InjectView(R.id.list_assistant)
    ListView list_assistant;

    @InjectView(R.id.list_class)
    ListView list_class;

    @InjectView(R.id.lin_class)
    LinearLayout lin_class;

    @InjectView(R.id.list)
    ListView list_apply;

    @InjectView(R.id.lin_assistant)
    LinearLayout lin_assistant;

    UserModel userModel;

    private IAssistantPresenter assistantPresenter;
    private ACache aCache;

    private SimpleFragmentPagerAdapter pagerAdapter;

    List<Fragment> lists = new ArrayList<Fragment>();
    Fragment assistantListFragment;
    Fragment assistantApplyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        rel_all_class_more.setOnClickListener(this);

        lin_class.setOnClickListener(this);
        text_apply.setOnClickListener(this);
        text_list.setOnClickListener(this);

        EventBus.getDefault().register(this);

    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        tv_title.setText("助教管理");

//        assistantListFragment = new AssistantListFragment();
//        assistantApplyFragment = new AssistantApplyFragment();
//        lists.add(assistantApplyFragment);
//        lists.add(assistantListFragment);
//        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, lists);

        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position:" + position);
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.img_selceted));
                AssistantClassInfoModel assistantClassInfo = (AssistantClassInfoModel) list_class.getAdapter().getItem(position);
                assistantPresenter.showAssistantByClass(userModel.getUserid(), assistantClassInfo.getClassId(), list_assistant);
            }
        });
        list_assistant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AssistantInfoModel assistantInfo = list.get(position);
                Intent intent = new Intent(AssistantActivity.this, AssistantDetailActivity.class);
                intent.putExtra("assistantId", assistantInfo.getAccountId().toString());
                intent.putExtra("classId", assistantInfo.getClassId().toString());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(List<AssistantInfoModel> list) {
        System.out.println("list:" + list);
        this.list = list;
    }

    @Override
    protected void initDatas() {
        //        viewpager.setAdapter(pagerAdapter);
//        sliding_tabs.setupWithViewPager(viewpager);
//        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);

        assistantPresenter = new AssistantImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);

        userModel = (UserModel) aCache.getAsObject(Constants.USER_ACACHE_KEY);
        String id = userModel.getUserid();
        assistantPresenter.showAllApplyAssistants(id, list_apply);
        assistantPresenter.showAllClassList(userModel.getUserid(), list_class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.rel_all_class_more:
                if (isShow) {
                    isShow = false;
                    lin_class.setVisibility(View.GONE);
                } else {
                    isShow = true;
                    lin_class.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.text_list:
                list_apply.setVisibility(View.GONE);
                lin_assistant.setVisibility(View.VISIBLE);
                break;

            case R.id.text_apply:
                list_apply.setVisibility(View.VISIBLE);
                lin_assistant.setVisibility(View.GONE);
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
