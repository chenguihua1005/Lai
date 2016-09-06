/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;


import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.message.view.ZQSActivity;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.presenter.MessageMainManager;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 荣誉榜
 */
@InjectLayout(R.layout.activity_message_operator)
public class MessageOperatorActivity extends BaseActivity implements View.OnClickListener, MessageMainManager.OperatorCallBack {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_value)
    TextView text_value;

    @InjectView(R.id.text_zqs)
    TextView text_zqs;

    @InjectView(R.id.text_zqs1)
    TextView text_zqs1;

    @InjectView(R.id.but_yes)
    Button but_yes;

    @InjectView(R.id.but_no)
    Button but_no;

    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.lin)
    LinearLayout lin;

    private boolean isSelect = true;

    private OperateMsgModel model;
    private String msg_type;
    private IMessagePresenter messagePresenter;
    private IAssistantPresenter assistantPresenter;
    private MessageMainManager manager;

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        ll_left.setOnClickListener(this);
        but_no.setOnClickListener(this);
        but_yes.setOnClickListener(this);
        text_zqs.setOnClickListener(this);
        text_zqs1.setOnClickListener(this);
        img.setOnClickListener(this);


    }

    @Override
    protected void initDatas() {
        model = (OperateMsgModel) getIntent().getSerializableExtra("model");
        System.out.println("model:" + model);
        assistantPresenter = new AssistantImpl(this);
        messagePresenter = new MessageImpl(this);
        text_value.setText(model.getContent());
        msg_type = model.getMsgType();
        if ("0".equals(msg_type)) {
            tv_title.setText("助教申请");
        } else if ("1".equals(msg_type)) {
            tv_title.setText("助教移除");
            manager = new MessageMainManager(this);
        } else if ("2".equals(msg_type)) {
            tv_title.setText("助教邀请");
        } else if ("3".equals(msg_type)) {
            tv_title.setText("确认参赛");
        }
        if("1".equals(model.getIsDo())){
            but_no.setVisibility(View.GONE);
            but_yes.setVisibility(View.GONE);
            lin.setVisibility(View.GONE);
        }else {
            but_no.setVisibility(View.VISIBLE);
            but_yes.setVisibility(View.VISIBLE);
            lin.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent =getIntent();
            //把返回数据存入Intent
            intent.putExtra("type", "xzs");
            //设置返回数据
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                Intent intents = new Intent();
                //把返回数据存入Intent
                intents.putExtra("type", "xzs");
                //设置返回数据
                setResult(RESULT_OK, intents);
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
                if ("0".equals(msg_type)) {
                    assistantPresenter.reviewAssistantApplyList(Long.parseLong(model.getMsgId()), 0, 0, "message");
                } else if ("1".equals(msg_type)) {
                    manager.doRefuseRemoveSR(model.getMsgId());
                } else if ("2".equals(msg_type)) {
                    messagePresenter.acceptInviter(model.getSenderId(), model.getClassId(), "0");
                } else if ("3".equals(msg_type)) {
                    messagePresenter.acceptInviterToClass(model.getReceId(), model.getClassId(), "0");
                }

                break;
            case R.id.but_yes:
                if (isSelect) {
                    dialogShow("加载中");
                    if ("0".equals(msg_type)) {
                        assistantPresenter.reviewAssistantApplyList(Long.parseLong(model.getMsgId()), 1, 0, "message");
                    } else if ("1".equals(msg_type)) {
                        assistantPresenter.removeAssistantRoleByClass(model.getSenderId(), model.getClassId(), "message");
                    } else if ("2".equals(msg_type)) {
                        messagePresenter.acceptInviter(model.getSenderId(), model.getClassId(), "1");
                    } else if ("3".equals(msg_type)) {
                        Intent intent = new Intent(this, JoinGameDetailActivity.class);
                        intent.putExtra("classId", model.getClassId());
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    }

                } else {
                    Util.toastMsg(R.string.joinGameQ);
                }
                break;

        }
    }

    @Override
    public void refuseRemoveSR(String type) {
        dialogDissmiss();
        if ("true".equals(type)) {
            Intent intent =getIntent();
            //把返回数据存入Intent
            intent.putExtra("type", "xzs");
            //设置返回数据
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
