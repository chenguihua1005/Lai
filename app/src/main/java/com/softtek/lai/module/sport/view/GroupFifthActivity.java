/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.sport.adapter.GroupAdapter;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.module.sport.presenter.SportGroupManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 加入跑团 二级
 */
@InjectLayout(R.layout.activity_group)
public class GroupFifthActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener, SportGroupManager.GetRGListCallBack {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_name)
    TextView text_name;

    @InjectView(R.id.list_group)
    ListView list_group;

    @InjectView(R.id.lin)
    LinearLayout lin;

    List<GroupModel> group_list = new ArrayList<GroupModel>();
    GroupAdapter adapter;

    String select_name;
    String parent_name;
    String id;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        type=intent.getStringExtra("type");
        if("0".equals(type)){
            lin.setVisibility(View.VISIBLE);
        }else {
            lin.setVisibility(View.GONE);
        }

        select_name = intent.getStringExtra("select_name");
        parent_name = intent.getStringExtra("parent_name");
        id = intent.getStringExtra("id");
        tv_title.setText(parent_name);
        text_name.setText(select_name);

        dialogShow("加载中");
        SportGroupManager sportGroupManager = new SportGroupManager(this);
        sportGroupManager.getRGListByPId(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getRGList(String type, List<GroupModel> list) {
        dialogDissmiss();
        if ("success".equals(type)) {
            group_list = list;
            adapter = new GroupAdapter(this, group_list);
            list_group.setAdapter(adapter);
        }
    }
}
