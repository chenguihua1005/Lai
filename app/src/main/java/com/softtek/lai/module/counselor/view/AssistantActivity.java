/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.AssistantApplyAdapter;
import com.softtek.lai.module.counselor.adapter.AssistantClassAdapter;
import com.softtek.lai.module.counselor.adapter.AssistantClassListAdapter;
import com.softtek.lai.module.counselor.adapter.SimpleFragmentPagerAdapter;
import com.softtek.lai.module.counselor.model.AssistantApplyEvent;
import com.softtek.lai.module.counselor.model.AssistantApplyInfoModel;
import com.softtek.lai.module.counselor.model.AssistantClassEvent;
import com.softtek.lai.module.counselor.model.AssistantClassInfoModel;
import com.softtek.lai.module.counselor.model.AssistantInfoEvent;
import com.softtek.lai.module.counselor.model.AssistantInfoModel;
import com.softtek.lai.module.counselor.model.ReviewAssistantApplyEvent;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.sso.UMSsoHandler;

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


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_apply)
    TextView text_apply;

    @InjectView(R.id.text_list)
    TextView text_list;

    @InjectView(R.id.img_more)
    ImageView img_more;

    //
//    @InjectView(R.id.viewpager)
//    ViewPager viewpager;
//
//    @InjectView(R.id.sliding_tabs)
//    TabLayout sliding_tabs;

    private boolean isShow = false;

    List<AssistantInfoModel> list_ai;
    List<AssistantApplyInfoModel> list_aa;
    List<AssistantClassInfoModel> list_ac;

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
    AssistantClassInfoModel assistantClassInfo;
    AssistantApplyAdapter assistantApplyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        rel_all_class_more.setOnClickListener(this);

        lin_class.setOnClickListener(this);
        text_apply.setOnClickListener(this);
        text_list.setOnClickListener(this);

        EventBus.getDefault().register(this);

    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.assistantManage);

//        assistantListFragment = new AssistantListFragment();
//        assistantApplyFragment = new AssistantApplyFragment();
//        lists.add(assistantApplyFragment);
//        lists.add(assistantListFragment);
//        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, lists);

        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position:" + position);
                isShow = false;
                lin_class.setVisibility(View.GONE);
                img_more.setImageResource(R.drawable.more_down);
                for (int i = 0; i < parent.getChildCount(); i++) {
                    ImageView imageView = (ImageView) parent.getChildAt(i).findViewById(R.id.img);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.img_select));
                }
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.img_selceted));
                assistantClassInfo = list_ac.get(position);
                dialogShow("加载中");
                assistantPresenter.showAssistantByClass(userModel.getUserid(), assistantClassInfo.getClassId(), list_assistant);
            }
        });
        list_assistant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AssistantInfoModel assistantInfo = list_ai.get(position);
                Intent intent = new Intent(AssistantActivity.this, AssistantDetailActivity.class);
                intent.putExtra("assistantId", assistantInfo.getAccountId().toString());
                intent.putExtra("classId", assistantInfo.getClassId().toString());
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ReviewAssistantApplyEvent reviewAssistantApplyEvent) {
        System.out.println("reviewAssistantApplyEvent:" + reviewAssistantApplyEvent);
        int p = reviewAssistantApplyEvent.getPosion();
        list_aa.remove(p);
        assistantApplyAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(AssistantInfoEvent assistantInfoEvent) {
        System.out.println("assistantInfoEvent:" + assistantInfoEvent);
        list_ai = assistantInfoEvent.getList();
        AssistantClassListAdapter adapter = new AssistantClassListAdapter(this, list_ai);
        list_assistant.setAdapter(adapter);
    }

    @Subscribe
    public void onEvent(AssistantClassEvent assistantClassEvent) {
        System.out.println("assistantClassEvent:" + assistantClassEvent);
        list_ac = assistantClassEvent.getList();
        AssistantClassAdapter adapter = new AssistantClassAdapter(this, list_ac);
        list_class.setAdapter(adapter);
    }

    @Subscribe
    public void onEvent(AssistantApplyEvent assistantApplyEvent) {
        System.out.println("assistantApplyEvent:" + assistantApplyEvent);
        list_aa = assistantApplyEvent.getLists();
        assistantApplyAdapter = new AssistantApplyAdapter(this, list_aa);
        list_apply.setAdapter(assistantApplyAdapter);
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
        dialogShow("加载中");
        assistantPresenter.showAllApplyAssistants(id, list_apply);
        dialogShow("加载中");
        assistantPresenter.showAllClassList(userModel.getUserid(), list_class);

    }

    @Override
    public void onResume() {
        super.onResume();
//        System.out.println("assistantClassInfo:"+assistantClassInfo);
//        if (assistantClassInfo != null) {
//            assistantPresenter.showAssistantByClass(userModel.getUserid(), assistantClassInfo.getClassId(), list_assistant);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rel_all_class_more:
                if (isShow) {
                    isShow = false;
                    lin_class.setVisibility(View.GONE);
                    img_more.setImageResource(R.drawable.more_down);

                } else {
                    isShow = true;
                    lin_class.setVisibility(View.VISIBLE);
                    img_more.setImageResource(R.drawable.img_up);
                }
                break;

            case R.id.text_list:

                list_apply.setVisibility(View.GONE);
                lin_assistant.setVisibility(View.VISIBLE);
                text_apply.setBackground(getResources().getDrawable(R.drawable.img_select_grey));
                text_list.setBackground(getResources().getDrawable(R.drawable.img_select_white));
                text_apply.setTextColor(getResources().getColor(R.color.white));
                text_list.setTextColor(getResources().getColor(R.color.black));

                break;

            case R.id.text_apply:
                list_apply.setVisibility(View.VISIBLE);
                lin_assistant.setVisibility(View.GONE);
                text_apply.setBackground(getResources().getDrawable(R.drawable.img_select_white));
                text_list.setBackground(getResources().getDrawable(R.drawable.img_select_grey));
                text_apply.setTextColor(getResources().getColor(R.color.black));
                text_list.setTextColor(getResources().getColor(R.color.white));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String classId = data.getExtras().getString("classId");//得到新Activity 关闭后返回的数据
            System.out.println("classId");
            dialogShow("加载中");
            assistantPresenter.showAssistantByClass(userModel.getUserid(), classId, list_assistant);
        }
    }


}
