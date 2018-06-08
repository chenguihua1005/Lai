/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.view.NewFriendActivity;
import com.softtek.lai.module.laisportmine.view.MyPkListActivity;
import com.softtek.lai.module.laisportmine.view.MyPublicwelfareActivity;
import com.softtek.lai.module.message2.model.UnreadMsgModel;
import com.softtek.lai.module.message2.presenter.MessageMainManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 *
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_message2)
public class Message2Activity extends BaseActivity implements View.OnClickListener, MessageMainManager.GetUnreadMsgCallBack ,PullToRefreshBase.OnRefreshListener{


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

    @InjectView(R.id.rl_friend)
    RelativeLayout rl_friend;
    @InjectView(R.id.text_value_friend)
    TextView text_value_friend;
    @InjectView(R.id.text_unread_count_friend)
    TextView text_unread_count_friend;

    @InjectView(R.id.ptrsv)
    PullToRefreshScrollView ptrsv;

    private MessageMainManager manager;

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        tv_title.setText("消息中心");

        ll_left.setOnClickListener(this);
        rel_fwc.setOnClickListener(this);
        rl_xzs.setOnClickListener(this);
        rel_fc.setOnClickListener(this);
        rel_gs.setOnClickListener(this);
        rel_act.setOnClickListener(this);
        rel_pk.setOnClickListener(this);
        rl_friend.setOnClickListener(this);
        ptrsv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ptrsv.setOnRefreshListener(this);
        ILoadingLayout startLabelse = ptrsv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
    }


    @Override
    protected void initDatas() {
        manager = new MessageMainManager(this);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ptrsv!=null){
                    ptrsv.setRefreshing();
                }
            }
        },300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rel_fwc://服务窗
                startActivityForResult(new Intent(this, NoticeServerActivity.class),100);
                break;
            case R.id.rl_xzs://小助手
                startActivityForResult(new Intent(this, MessageOperatorActivity.class),100);
                break;
            case R.id.rel_fc://复测提醒
                startActivityForResult(new Intent(this, NoticeFCActivity.class),100);
                break;
            case R.id.rel_gs://爱心慈善
                startActivityForResult(new Intent(this, MyPublicwelfareActivity.class),100);
                break;
            case R.id.rel_act://活动
                startActivityForResult(new Intent(this, ActionActivity.class),100);
                break;
            case R.id.rel_pk://Pk列表
                startActivityForResult(new Intent(this, MyPkListActivity.class),100);
                break;
            case R.id.rl_friend://新朋友列表
                startActivityForResult(new Intent(this, NewFriendActivity.class), 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK){
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(ptrsv!=null){
                        ptrsv.setRefreshing();
                    }
                }
            },300);
        }
    }

    @Override
    public void getUnreadMsg(String type, UnreadMsgModel unreadMsgModel) {
        try {
            ptrsv.onRefreshComplete();
            if ("true".equals(type)) {
                String noticeMsg = unreadMsgModel.getNoticeMsg();
                if (!TextUtils.isEmpty(noticeMsg)) {
                    rel_fwc.setVisibility(View.VISIBLE);
                    text_value_fwc.setText(noticeMsg);
                    if ("0".equals(unreadMsgModel.getIsHasNoticeMsg())) {
                        text_unread_count_fwc.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_fwc.setText(unreadMsgModel.getIsHasNoticeMsg());
                        text_unread_count_fwc.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_fwc.setVisibility(View.GONE);
                }

                String operateMsg = unreadMsgModel.getOperateMsg();
                if (!TextUtils.isEmpty(operateMsg)) {
                    rl_xzs.setVisibility(View.VISIBLE);
                    text_value_xzs.setText(operateMsg);
                    if ("0".equals(unreadMsgModel.getIsHasOperateMsg())) {
                        text_unread_count_xzs.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_xzs.setText(unreadMsgModel.getIsHasOperateMsg());
                        text_unread_count_xzs.setVisibility(View.VISIBLE);
                    }

                } else {
                    rl_xzs.setVisibility(View.GONE);
                }

                String measureMsg = unreadMsgModel.getMeasureMsg();
                if (!TextUtils.isEmpty(measureMsg)) {
                    rel_fc.setVisibility(View.VISIBLE);
                    text_value_fc.setText(measureMsg);
                    if ("0".equals(unreadMsgModel.getIsHasMeasureMsg())) {
                        text_unread_count_fc.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_fc.setText(unreadMsgModel.getIsHasMeasureMsg());
                        text_unread_count_fc.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_fc.setVisibility(View.GONE);
                }

                String angleMsg = unreadMsgModel.getAngleMsg();
                if (!TextUtils.isEmpty(angleMsg)) {
                    rel_gs.setVisibility(View.VISIBLE);
                    text_value_gs.setText(angleMsg);
                    if ("0".equals(unreadMsgModel.getIsHasAngelMsg())) {
                        text_unread_count_gs.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_gs.setText(unreadMsgModel.getIsHasAngelMsg());
                        text_unread_count_gs.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_gs.setVisibility(View.GONE);
                }

                String actMsg = unreadMsgModel.getActiveMsg();
                if (!TextUtils.isEmpty(actMsg)) {
                    rel_act.setVisibility(View.VISIBLE);
                    text_value_act.setText(actMsg);
                    if ("0".equals(unreadMsgModel.getIsHasActMsg())) {
                        text_unread_count_act.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_act.setText(unreadMsgModel.getIsHasActMsg());
                        text_unread_count_act.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_act.setVisibility(View.GONE);
                }

                String chaMsg = unreadMsgModel.getChallMsg();
                if (!TextUtils.isEmpty(chaMsg)) {
                    rel_pk.setVisibility(View.VISIBLE);
                    text_value_pk.setText(chaMsg);
                    if ("0".equals(unreadMsgModel.getIsHasChaMsg())) {
                        text_unread_count_pk.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_pk.setText(unreadMsgModel.getIsHasChaMsg());
                        text_unread_count_pk.setVisibility(View.VISIBLE);
                    }
                } else {
                    rel_pk.setVisibility(View.GONE);

                }
                String friendMsg = unreadMsgModel.getFriendMsg();
                if (!TextUtils.isEmpty(friendMsg)) {
                    rl_friend.setVisibility(View.VISIBLE);
                    text_value_friend.setText(friendMsg);
                    if ("0".equals(unreadMsgModel.getIsHasFriendMsg())) {
                        text_unread_count_friend.setVisibility(View.INVISIBLE);
                    } else {
                        text_unread_count_friend.setText(unreadMsgModel.getIsHasFriendMsg());
                        text_unread_count_friend.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl_friend.setVisibility(View.GONE);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        manager.doGetUnreadMsg(UserInfoModel.getInstance().getUserId()+"");
    }
}
