/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.message.adapter.MessageFcRemindAdapter;
import com.softtek.lai.module.message.adapter.MessageRemoveSrRemindAdapter;
import com.softtek.lai.module.message.model.MeasureRemindInfo;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.retest.view.RetestActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 荣誉榜
 */
@InjectLayout(R.layout.activity_remind_list)
public class MessageRemoveSrRemindActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.list)
    ListView list;

    ArrayList<MessageDetailInfo> listRemind;
    private IMessagePresenter messagePresenter;
    MessageDetailInfo measureRemindInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        EventBus.getDefault().register(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                measureRemindInfo = listRemind.get(position);
                String type = measureRemindInfo.getMsgType();
                if ("1".equals(type)) {
                    messagePresenter.upReadTime("1", measureRemindInfo.getInviterId(), measureRemindInfo.getSenderId(), measureRemindInfo.getClassId());

                } else {
                    messagePresenter.delNoticeOrMeasureMsg(measureRemindInfo.getMessageId());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ResponseData listResponseData) {
        System.out.println("此条标记已读");
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        Intent intent = new Intent(this, RemoveSRActivity.class);
        intent.putExtra("messageDetailInfo", measureRemindInfo);
        startActivity(intent);
    }

    @Override
    protected void initViews() {
        tv_title.setText("体管赛小助手");
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
    }

    @Override
    protected void initDatas() {
        messagePresenter = new MessageImpl(this);
        Intent intent = getIntent();
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        String type = intent.getStringExtra("type");
        listRemind = (ArrayList<MessageDetailInfo>) intent.getSerializableExtra("list");
        MessageRemoveSrRemindAdapter adapter = new MessageRemoveSrRemindAdapter(this, listRemind);
        list.setAdapter(adapter);
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

}
