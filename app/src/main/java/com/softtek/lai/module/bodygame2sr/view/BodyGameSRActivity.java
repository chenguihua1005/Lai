package com.softtek.lai.module.bodygame2sr.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.view.ChatFragment;
import com.softtek.lai.module.bodygame2.view.ContactFragment;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame)
public class BodyGameSRActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.btn_bodygame)
    SimpleButton btn_bodygame;
    @InjectView(R.id.btn_chat)
    SimpleButton btn_chat;
    @InjectView(R.id.btn_contact)
    SimpleButton btn_contact;
    @InjectView(R.id.btn_fuce)
    SimpleButton btn_fuce;
    @InjectView(R.id.btn_class)
    SimpleButton btn_class;
    @InjectView(R.id.nsvp)
    NoSlidingViewPage content;
    @InjectView(R.id.tv_unread_num)
    TextView tv_umread;
    private EMConnectionListener connectionListener;
    private List<Fragment> fragments = new ArrayList<>();
    public AlertDialog.Builder builder = null;
    private int current = 0;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                if (builder != null) {
                    return;
                }
                builder = new AlertDialog.Builder(BodyGameSRActivity.this)
                        .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                        .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder = null;
                                UserInfoModel.getInstance().loginOut();
                                stopService(new Intent(BodyGameSRActivity.this, StepService.class));
                                Intent intent = new Intent(BodyGameSRActivity.this, LoginActivity.class);
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
        }

    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type=intent.getIntExtra("type",0);
        current=type;
        Log.i("消息中心发来通知");
        if(content!=null){
            restoreState();
            switch (type){
                case 0:
                    btn_bodygame.setProgress(1);
                    break;
                case 1:
                    btn_chat.setProgress(1);
                    break;
                case 2:
                    btn_contact.setProgress(1);
                    break;
                case 3:
                    btn_fuce.setProgress(1);
                    break;
                case 4:
                    btn_class.setProgress(1);
                    break;

            }
            content.setCurrentItem(current, false);
        }
    }

    @Override
    protected void initViews() {
        btn_bodygame.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_fuce.setOnClickListener(this);
        btn_class.setOnClickListener(this);

        fragments.add(new BodyGameSRFragment());
        fragments.add(new ChatFragment());
        fragments.add(new ContactFragment());
        fragments.add(new FuCeFragment());
        fragments.add(new ClassSRFragment());
        content.setOffscreenPageLimit(4);
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
        //设置第一个fragment
        int type=getIntent().getIntExtra("type",0);
        current=type;
        restoreState();
        switch (type){
            case 0:
                btn_bodygame.setProgress(1);
                break;
            case 1:
                btn_chat.setProgress(1);
                break;
            case 2:
                btn_contact.setProgress(1);
                break;
            case 3:
                btn_fuce.setProgress(1);
                break;
            case 4:
                btn_class.setProgress(1);
                break;

        }
        content.setCurrentItem(current, false);

        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(final int error) {
                if (error == EMError.CONNECTION_CONFLICT) {
                    if (!isFinishing()) {
                        EMChatManager.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                System.out.println("ChatFragment onSuccess-------");
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
            }
        };
        EMChatManager.getInstance().addConnectionListener(connectionListener);

        UserModel model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        ChatUserModel chatUserModel = new ChatUserModel();
        chatUserModel.setUserName(model.getNickname());
        chatUserModel.setUserPhone(path + model.getPhoto());
        chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
        ChatUserInfoModel.getInstance().setUser(chatUserModel);

    }

    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    private MessageReceiver mMessageReceiver;
    @Override
    protected void initDatas() {
        registerMessageReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (EMChat.getInstance().isLoggedIn()) {
            int unreadNum = EMChatManager.getInstance().getUnreadMsgsCount();
            updateMessage(unreadNum);
        }
    }

    @Override
    public void onClick(View v) {
        restoreState();
        switch (v.getId()) {
            case R.id.btn_bodygame:
                btn_bodygame.setProgress(1);
                if (current == 0) {
                    return;
                }
                current = 0;
                break;
            case R.id.btn_chat:
                btn_chat.setProgress(1);
                if (current == 1) {
                    return;
                }
                current = 1;
                break;
            case R.id.btn_contact:
                btn_contact.setProgress(1);
                if (current == 2) {
                    return;
                }
                current = 2;
                break;
            case R.id.btn_fuce:
                btn_fuce.setProgress(1);
                if (current == 3) {
                    return;
                }
                current = 3;
                break;
            case R.id.btn_class:
                btn_class.setProgress(1);
                if (current == 4) {
                    return;
                }
                current = 4;
                break;
        }
        content.setCurrentItem(current, false);

    }

    private void restoreState() {
        btn_bodygame.setProgress(0);
        btn_chat.setProgress(0);
        btn_contact.setProgress(0);
        btn_fuce.setProgress(0);
        btn_class.setProgress(0);

    }

    public void setAlpha(float alpha){
        tintManager.setStatusBarAlpha(alpha);
    }

    public void updateMessage(int num){
        //显示
        if(num<=0){
            tv_umread.setVisibility(View.GONE);
        }else {
            String read=num >= 100 ? "99+" : num + "";
            tv_umread.setText(read);
            tv_umread.setVisibility(View.VISIBLE);
        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_CHAT_ACTION);
        registerReceiver(mMessageReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.MESSAGE_CHAT_ACTION.equals(intent.getAction())) {
                int unreadNum = intent.getIntExtra("count", 0);
                //更新小红点
                updateMessage(unreadNum);
            }
        }
    }
}
