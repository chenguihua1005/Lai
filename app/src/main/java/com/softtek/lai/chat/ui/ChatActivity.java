/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.bodygame3.conversation.database.ClassGroupUtil;
import com.softtek.lai.module.bodygame3.conversation.database.GroupModel;
import com.softtek.lai.module.bodygame3.conversation.database.GroupTable;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.view.ClassDetailActivity;
import com.softtek.lai.module.bodygame3.home.view.BodyGameNewActivity;
import com.softtek.lai.runtimepermissions.PermissionsManager;

import butterknife.InjectView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EaseConstant.IS_GROUP_SENT = "flase";
        activityInstance = this;
        //聊天人或群id（环信）
        toChatUsername = getIntent().getExtras().getString("userId");
        //可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        //传入参数
        final String title_value = getIntent().getStringExtra("name");
//        if ("".equals(title_value)) {
//            title_value = "";
//        }


        //获取聊天类型
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        classId = getIntent().getStringExtra("classId");

        classModel = (ContactClassModel) getIntent().getSerializableExtra("classModel");//这是从通讯库- 班级群-中传过来的
        Log.i(TAG, "聊天人或群id userId = " + toChatUsername + "  title_value = " + title_value + " chatType =" + chatType + " classId = " + classId);
        Log.i(TAG, "classModel = " + new Gson().toJson(classModel));


        ll_left.setOnClickListener(this);


        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            fl_right.setVisibility(View.VISIBLE);
            iv_email.setBackground(ContextCompat.getDrawable(this, R.drawable.groupicon));

            if (classModel != null) {
                tv_title.setText(classModel.getClassName());
            } else if (ClassGroupUtil.getInstance().tableIsExist(GroupTable.TABLE_NAME) && ClassGroupUtil.getInstance().findGroup(toChatUsername) != null) {
                GroupModel model = ClassGroupUtil.getInstance().findGroup(toChatUsername);
                Log.i(TAG, "here 查询数据库 = " + model.getClassName());
                tv_title.setText(model.getClassName());
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //根据群组ID从本地获取群组基本信息
                            group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
//                        group = EMClient.getInstance().groupManager().getGroupFromServer(toChatUsername);
                            tv_title.setText(group.getGroupName());
//                            tv_title.setText(title_value);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } else {
            fl_right.setVisibility(View.GONE);
            tv_title.setText(title_value);
        }

        fl_right.setOnClickListener(this);

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
//                Intent intent1 = new Intent(this, BodyGameActivity.class);
                Intent intent1 = new Intent(this, BodyGameNewActivity.class);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                break;
            case R.id.fl_right:
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
//            Intent intent = new Intent(this, BodyGameActivity.class);
            Intent intent = new Intent(this, BodyGameNewActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
