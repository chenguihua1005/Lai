/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.chat.adapter.SelectGroupSentAdapter;
import com.softtek.lai.chat.model.SelectContactInfoModel;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_select_group_list)
public class SeceltGroupSentActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl)
    FrameLayout fl;
    @InjectView(R.id.et_search)
    TextView et_search;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.lin_next)
    LinearLayout lin_next;

    @InjectView(R.id.list_contant)
    ListView list_contant;

    SelectGroupSentAdapter adapter;
    List<SelectContactInfoModel> list;

    boolean isSelectAll = false;
    public AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        fl.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        et_search.setOnClickListener(this);
        lin_next.setOnClickListener(this);

        list = new ArrayList<SelectContactInfoModel>();
        setData();

        adapter = new SelectGroupSentAdapter(this, list);
        list_contant.setAdapter(adapter);
    }

    private void setData() {
        List<ChatContactModel> lists = (ArrayList<ChatContactModel>) getIntent().getSerializableExtra("list");
        for (int i = 0; i < lists.size(); i++) {
            SelectContactInfoModel model = new SelectContactInfoModel();
            model.setSelected(false);
            model.setModel(lists.get(i));
            list.add(model);
        }
    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("选择联系人");
        tv_right.setText("全选");

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_next:
                List<ChatContactModel> select_list = new ArrayList<ChatContactModel>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelected()) {
                        select_list.add(list.get(i).getModel());
                    }
                }
                if (select_list.size() != 0) {
                    Intent intent = new Intent(this, GroupSentActivity.class);
                    intent.putExtra("list", (Serializable) select_list);
                    startActivity(intent);
                } else {
                    Util.toastMsg("请选择联系人");
                }
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                if (isSelectAll) {
                    tv_right.setText("全选");
                    isSelectAll = false;
                    for (int i = 0; i < list.size(); i++) {
                        SelectContactInfoModel selectContactInfoModel = list.get(i);
                        selectContactInfoModel.setSelected(false);
                    }
                } else {
                    tv_right.setText("取消");
                    isSelectAll = true;
                    for (int i = 0; i < list.size(); i++) {
                        SelectContactInfoModel selectContactInfoModel = list.get(i);
                        selectContactInfoModel.setSelected(true);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onStop() {
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
