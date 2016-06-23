/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat;


import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.InviteContantAdapter;
import com.softtek.lai.module.counselor.model.ContactListInfoModel;
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.softtek.lai.module.counselor.view.SearchContantActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.HanziToPinyin;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_chat_contant_list)
public class ContantListActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

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

    @InjectView(R.id.list_contant)
    ListView list_contant;
    @InjectView(R.id.lin_group_send)
    LinearLayout lin_group_send;

    ChatContantAdapter adapter;
    List<ChatContactInfoModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        lin_group_send.setOnClickListener(this);
        fl.setOnClickListener(this);
        et_search.setOnClickListener(this);

        list = new ArrayList<ChatContactInfoModel>();
        setData();
        adapter = new ChatContantAdapter(this, list);
        list_contant.setAdapter(adapter);

        list_contant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatContactInfoModel model=list.get(position);
                Intent intent = new Intent(ContantListActivity.this, ChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, model.getUserId());
                intent.putExtra("name", model.getUserName());
                intent.putExtra("photo", model.getUserPhoto());
                startActivity(intent);
            }
        });
    }

    private void setData() {
        ChatContactInfoModel model1 = new ChatContactInfoModel();
        model1.setUserId("18261576085");
        model1.setUserName("aaaaaaa");
        model1.setUserPhoto("http://172.16.98.167/UpFiles/tgs_banner.png");
        list.add(model1);

        ChatContactInfoModel model2 = new ChatContactInfoModel();
        model2.setUserId("18261576086");
        model2.setUserName("bbbbbbbbb");
        model2.setUserPhoto("http://172.16.98.167/UpFiles/tgs_banner.png");
        list.add(model2);

        ChatContactInfoModel model3 = new ChatContactInfoModel();
        model3.setUserId("18261576087");
        model3.setUserName("cccccccccc");
        model3.setUserPhoto("http://172.16.98.167/UpFiles/tgs_banner.png");
        list.add(model3);

        ChatContactInfoModel model4 = new ChatContactInfoModel();
        model4.setUserId("18261576088");
        model4.setUserName("dddddddddd");
        model4.setUserPhoto("http://172.16.98.167/UpFiles/tgs_banner.png");
        list.add(model4);
    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("通讯录");

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.lin_group_send:
                Intent intent=new Intent(this,SeceltGroupSentActivity.class);
                intent.putExtra("list",(Serializable)list);
                startActivity(intent);
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
