/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.view.MyActionListActivity;
import com.softtek.lai.module.laisportmine.view.MyPkListActivity;
import com.softtek.lai.module.laisportmine.view.MyPublicwelfareActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.model.UnreadMsgModel;
import com.softtek.lai.module.message2.presenter.MessageMainManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_message2)
public class Message2Activity extends BaseActivity implements View.OnClickListener, MessageMainManager.GetUnreadMsgCallBack {


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.rel_fwc)
    RelativeLayout rel_fwc;
    @InjectView(R.id.text_value_fwc)
    TextView text_value_fwc;
    @InjectView(R.id.text_unread_count_fwc)
    TextView text_unread_count_fwc;

    @InjectView(R.id.rl_xzs)
    RelativeLayout rl_xzs;
    @InjectView(R.id.text_value_xzs)
    TextView text_value_xzs;
    @InjectView(R.id.text_unread_count_xzs)
    TextView text_unread_count_xzs;

    @InjectView(R.id.rel_fc)
    RelativeLayout rel_fc;
    @InjectView(R.id.text_value_fc)
    TextView text_value_fc;
    @InjectView(R.id.text_unread_count_fc)
    TextView text_unread_count_fc;

    @InjectView(R.id.rel_gs)
    RelativeLayout rel_gs;
    @InjectView(R.id.text_value_gs)
    TextView text_value_gs;
    @InjectView(R.id.text_unread_count_gs)
    TextView text_unread_count_gs;

    @InjectView(R.id.rel_act)
    RelativeLayout rel_act;
    @InjectView(R.id.text_value_act)
    TextView text_value_act;
    @InjectView(R.id.text_unread_count_act)
    TextView text_unread_count_act;

    @InjectView(R.id.rel_pk)
    RelativeLayout rel_pk;
    @InjectView(R.id.text_value_pk)
    TextView text_value_pk;
    @InjectView(R.id.text_unread_count_pk)
    TextView text_unread_count_pk;

    private MessageMainManager manager;
    private UserModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new MessageMainManager(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.CounselorJ);

        ll_left.setOnClickListener(this);
        rel_fwc.setOnClickListener(this);
        rl_xzs.setOnClickListener(this);
        rel_fc.setOnClickListener(this);
        rel_gs.setOnClickListener(this);
        rel_act.setOnClickListener(this);
        rel_pk.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        String id = model.getUserid();
        dialogShow("加载中");
        manager.doGetUnreadMsg(id);
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
            case R.id.rel_fwc://服务窗
                startActivity(new Intent(this, NoticeServerActivity.class));
                break;
            case R.id.rl_xzs://小助手
                startActivity(new Intent(this, MessageOperatorActivity.class));
                break;
            case R.id.rel_fc://复测提醒
                startActivity(new Intent(this, NoticeFCActivity.class));
                break;
            case R.id.rel_gs://爱心慈善
                startActivity(new Intent(this, MyPublicwelfareActivity.class));
                break;
            case R.id.rel_act://活动
                startActivity(new Intent(this, MyActionListActivity.class));
                break;
            case R.id.rel_pk://Pk列表
                startActivity(new Intent(this, MyPkListActivity.class));
                break;
        }
    }

    @Override
    public void getUnreadMsg(String type, UnreadMsgModel unreadMsgModel) {
        dialogDissmiss();
        try {
            if ("true".equals(type)) {
                String noticeMsg = unreadMsgModel.getNoticeMsg();
                if (!TextUtils.isEmpty(noticeMsg)) {
                    rel_fwc.setVisibility(View.VISIBLE);
                    text_value_fwc.setText(noticeMsg);
                    text_unread_count_fwc.setText(unreadMsgModel.getIsHasNoticeMsg());
                    if ("0".equals(unreadMsgModel.getIsHasNoticeMsg())) {
                        text_unread_count_fwc.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_fwc.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_fwc.setVisibility(View.GONE);
                }

                String operateMsg = unreadMsgModel.getOperateMsg();
                if (!TextUtils.isEmpty(operateMsg)) {
                    rl_xzs.setVisibility(View.VISIBLE);
                    text_value_xzs.setText(operateMsg);
                    text_unread_count_xzs.setText(unreadMsgModel.getIsHasOperateMsg());
                    if ("0".equals(unreadMsgModel.getIsHasOperateMsg())) {
                        text_unread_count_xzs.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_xzs.setVisibility(View.VISIBLE);
                    }

                } else {
                    rl_xzs.setVisibility(View.GONE);
                }

                String measureMsg = unreadMsgModel.getMeasureMsg();
                if (!TextUtils.isEmpty(measureMsg)) {
                    rel_fc.setVisibility(View.VISIBLE);
                    text_value_fc.setText(measureMsg);
                    text_unread_count_fc.setText(unreadMsgModel.getIsHasMeasureMsg());
                    if ("0".equals(unreadMsgModel.getIsHasMeasureMsg())) {
                        text_unread_count_fc.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_fc.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_fc.setVisibility(View.GONE);
                }

                String angleMsg = unreadMsgModel.getAngleMsg();
                if (!TextUtils.isEmpty(angleMsg)) {
                    rel_gs.setVisibility(View.VISIBLE);
                    text_value_gs.setText(angleMsg);
                    text_unread_count_gs.setText(unreadMsgModel.getIsHasAngelMsg());
                    if ("0".equals(unreadMsgModel.getIsHasAngelMsg())) {
                        text_unread_count_gs.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_gs.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_gs.setVisibility(View.GONE);
                }

                String actMsg = unreadMsgModel.getActiveMsg();
                if (!TextUtils.isEmpty(actMsg)) {
                    rel_act.setVisibility(View.VISIBLE);
                    text_value_act.setText(actMsg);
                    text_unread_count_act.setText(unreadMsgModel.getIsHasActMsg());
                    if ("0".equals(unreadMsgModel.getIsHasActMsg())) {
                        text_unread_count_act.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_act.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_act.setVisibility(View.GONE);
                }

                String chaMsg = unreadMsgModel.getChallMsg();
                if (!TextUtils.isEmpty(chaMsg)) {
                    rel_pk.setVisibility(View.VISIBLE);
                    text_value_pk.setText(chaMsg);
                    text_unread_count_pk.setText(unreadMsgModel.getIsHasChaMsg());
                    if ("0".equals(unreadMsgModel.getIsHasChaMsg())) {
                        text_unread_count_pk.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_pk.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_pk.setVisibility(View.GONE);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
