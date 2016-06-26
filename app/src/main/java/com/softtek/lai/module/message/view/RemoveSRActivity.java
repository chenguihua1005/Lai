/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 荣誉榜
 */
@InjectLayout(R.layout.activity_join_game)
public class RemoveSRActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_value)
    TextView text_value;

    @InjectView(R.id.but_yes)
    Button but_yes;

    @InjectView(R.id.but_no)
    Button but_no;

    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.text_zqs)
    TextView text_zqs;

    @InjectView(R.id.text_zqs1)
    TextView text_zqs1;

    @InjectView(R.id.lin)
    LinearLayout lin;

    private boolean isSelect = true;
    private IMessagePresenter messagePresenter;
    private IAssistantPresenter assistantPresenter;
    MessageDetailInfo messageDetailInfo;
    String msg_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        but_no.setOnClickListener(this);
        but_yes.setOnClickListener(this);
        img.setOnClickListener(this);
        text_zqs.setOnClickListener(this);
        text_zqs1.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("体管赛小助手");

    }

    @Override
    protected void initDatas() {
        messagePresenter = new MessageImpl(this);
        assistantPresenter = new AssistantImpl(this);
        Intent intent = getIntent();
        messageDetailInfo = (MessageDetailInfo) intent.getSerializableExtra("messageDetailInfo");
        String msg_type = messageDetailInfo.getMsgType();
        if ("1".equals(msg_type)) {
            String comments = messageDetailInfo.getComments();
            String[] str = comments.split("\\|");
            text_value.setText(str[2]);
        } else {
            text_value.setText(messageDetailInfo.getComments());
        }
        msg_type = messageDetailInfo.getMsgType();
        if ("1".equals(msg_type)) {
            but_no.setVisibility(View.VISIBLE);
            but_yes.setVisibility(View.VISIBLE);
            lin.setVisibility(View.VISIBLE);
        } else {
            but_no.setVisibility(View.GONE);
            but_yes.setVisibility(View.GONE);
            lin.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ResponseData listResponseData) {
        System.out.println("此条标记已读");
        startActivity(new Intent(this, MessageActivity.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();

                break;
            case R.id.img:
                if (isSelect) {
                    img.setImageResource(R.drawable.img_join_game_select);
                    isSelect = false;
                } else {
                    img.setImageResource(R.drawable.img_join_game_selected);
                    isSelect = true;
                }
                break;
            case R.id.text_zqs:
                if (isSelect) {
                    img.setImageResource(R.drawable.img_join_game_select);
                    isSelect = false;
                } else {
                    img.setImageResource(R.drawable.img_join_game_selected);
                    isSelect = true;
                }
                break;
            case R.id.text_zqs1:
                startActivity(new Intent(this, ZQSActivity.class));
                break;
            case R.id.but_no:
                dialogShow("加载中");
                messagePresenter.delNoticeOrMeasureMsg(messageDetailInfo.getMessageId(),"0");
                break;
            case R.id.but_yes:
                if (isSelect) {
                    String comments = messageDetailInfo.getComments();
                    String[] str = comments.split("\\|");
                    dialogShow("加载中");
                    assistantPresenter.removeAssistantRoleByClass(str[0], str[1], messageDetailInfo.getMessageId(), "message");
                } else {
                    Util.toastMsg(R.string.joinGameQ);
                }
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
