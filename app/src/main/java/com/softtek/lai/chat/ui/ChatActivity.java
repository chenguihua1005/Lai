/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.view.ClassDetailActivity;
import com.softtek.lai.module.bodygame3.home.view.BodyGameActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.em_activity_chat)
public class ChatActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {
    private static final String TAG = "ChatActivity";

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    private String toChatUsername;
    protected int chatType;
    private String classId;

    private ContactClassModel classModel;

    private EMGroup group;


    public AlertDialog.Builder builder = null;
    private EMConnectionListener connectionListener;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (builder != null) {
                return;
            }
            builder = new AlertDialog.Builder(ChatActivity.this)
                    .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                    .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder = null;
                            UserInfoModel.getInstance().loginOut();
                            LocalBroadcastManager.getInstance(LaiApplication.getInstance().getContext().get()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).setCancelable(false);
            Dialog dialog = builder.create();
            if (!isFinishing()) {
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EaseConstant.IS_GROUP_SENT = "flase";
        activityInstance = this;
        //聊天人或群id（环信）
        toChatUsername = getIntent().getExtras().getString("userId");
        //可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        //传入参数
        String title_value = getIntent().getStringExtra("name");
        if ("".equals(title_value)) {
            title_value = "";
        }


        //获取聊天类型
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);

        classId = getIntent().getStringExtra("classId");

        classModel = (ContactClassModel) getIntent().getSerializableExtra("classModel");
        Log.i(TAG, "聊天人或群id userId = " + toChatUsername + "  title_value = " + title_value + " chatType =" + chatType + " classId = " + classId);
        Log.i(TAG, "classModel = " + new Gson().toJson(classModel));


        ll_left.setOnClickListener(this);
        fl_right.setVisibility(View.VISIBLE);
        iv_email.setBackground(ContextCompat.getDrawable(this, R.drawable.groupicon));
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            if (classModel != null) {
                tv_title.setText(classModel.getClassName());

            }

            if (TextUtils.isEmpty(tv_title.getText().toString().trim())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //根据群组ID从本地获取群组基本信息
                            group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
//                        group = EMClient.getInstance().groupManager().getGroupFromServer(toChatUsername);
                            tv_title.setText(group.getGroupName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } else {
            tv_title.setText(title_value);
        }

        fl_right.setOnClickListener(this);

        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(final int error) {
                if (error == EMError.USER_ALREADY_LOGIN) {
                    SharedPreferenceService.getInstance().put("HXID", "-1");
                    if (!isFinishing()) {
                        EMClient.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub


                                handler.sendEmptyMessage(0);

                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                // TODO Auto-generated method stub

                            }

                        });
                    }
                }
            }

            @Override
            public void onConnected() {
                // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，
//                EaseACKUtil.getInstance(ChatActivity.this).checkACKData();

            }
        };
        EMClient.getInstance().addConnectionListener(connectionListener);

        //设置参数（"name"  "photo"）
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                Intent intent1 = new Intent(this, BodyGameActivity.class);
                intent1.putExtra("tab", 1);
                startActivity(intent1);
                break;
            case R.id.fl_right:

                Log.i(TAG, "toChatUsername = " + toChatUsername + " classModel = " + classModel);
                Intent intent = new Intent(ChatActivity.this, ClassDetailActivity.class);
                intent.putExtra("toChatUsername", toChatUsername);
                intent.putExtra("classId", classId);
                intent.putExtra("classModel", classModel);//班级Model

                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, BodyGameActivity.class);
            intent.putExtra("tab", 1);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
