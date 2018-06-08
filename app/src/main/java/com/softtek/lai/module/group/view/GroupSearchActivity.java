/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.group.adapter.GroupAdapter;
import com.softtek.lai.module.group.model.GroupModel;
import com.softtek.lai.module.group.presenter.SportGroupManager;
import com.softtek.lai.utils.SoftInputUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 搜索跑团
 */
@InjectLayout(R.layout.activity_group_search)
public class GroupSearchActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, SportGroupManager.GetRGByNameOrCodeCallBack {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_search)
    TextView text_search;

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    @InjectView(R.id.edit_search)
    EditText edit_search;

    @InjectView(R.id.list_group)
    ListView list_group;

    List<GroupModel> group_list = new ArrayList<GroupModel>();
    GroupAdapter adapter;
    SportGroupManager sportGroupManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        text_search.setOnClickListener(this);

        list_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupModel groupModel = group_list.get(position);
                if ("1".equals(groupModel.getIsHasSonRG())) {
                    Intent intent = new Intent(GroupSearchActivity.this, GroupSecActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("select_name", "");
                    intent.putExtra("parent_name", groupModel.getRGName());
                    intent.putExtra("id", groupModel.getRGId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initViews() {
        tv_title.setText("搜索跑团");
    }

    @Override
    protected void initDatas() {
        sportGroupManager = new SportGroupManager(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.text_search:
                String str = edit_search.getText().toString().trim();
                if ("".equals(str)) {
                    Util.toastMsg("请输入跑团号或跑团名称再试");
                } else {
                    dialogShow("加载中");
                    sportGroupManager.getRGByNameOrCode(str);
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getRGByNameOrCode(String type, List<GroupModel> list) {
        dialogDissmiss();
        try {
            if("success".equals(type)) {
                list_group.setEmptyView(img_mo_message);
                group_list = list;
                adapter = new GroupAdapter(this, group_list);
                list_group.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
