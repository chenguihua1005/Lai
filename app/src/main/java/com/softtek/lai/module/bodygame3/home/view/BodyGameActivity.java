package com.softtek.lai.module.bodygame3.home.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.widgets.SimpleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame3)
public class BodyGameActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "BodyGameActivity";

    @InjectView(R.id.btn_bodygame)
    SimpleButton btn_bodygame;
    @InjectView(R.id.btn_chat)
    SimpleButton btn_chat;
    @InjectView(R.id.btn_contact)
    SimpleButton btn_contact;
    @InjectView(R.id.btn_activity)
    SimpleButton btn_activity;
    @InjectView(R.id.btn_more)
    SimpleButton btn_more;
    @InjectView(R.id.nsvp)
    ViewPager content;
    @InjectView(R.id.tv_unread_num)
    TextView tv_umread;

    private List<Fragment> fragments;
    private FragmentPagerAdapter adapter;

    private int current = 0;
    private boolean isClick = false;

    private EMConnectionListener connectionListener;
    public AlertDialog.Builder builder = null;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                if (builder != null) {
                    return;
                }
                builder = new AlertDialog.Builder(BodyGameActivity.this)
                        .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                        .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder = null;
                                UserInfoModel.getInstance().loginOut();
                                LocalBroadcastManager.getInstance(LaiApplication.getInstance().getContext().get()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                Intent intent = new Intent(BodyGameActivity.this, LoginActivity.class);
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
    protected void initViews() {

        Log.i(TAG, "initViews   ......");
        MobclickAgent.openActivityDurationTrack(false);
        btn_bodygame.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_activity.setOnClickListener(this);
        btn_more.setOnClickListener(this);

        fragments = new ArrayList<>();
        fragments.add(new BodyGameFragment());
        fragments.add(new ChatFragment());
        fragments.add(new ContactFragment());
        fragments.add(new ActivityFragment());
        fragments.add(new MoreFragment());
        content.setOffscreenPageLimit(4);
        adapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
        content.setAdapter(adapter);

        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isClick) {
                    setChildProgress(position, 1 - positionOffset);
                    setChildProgress(position + 1, positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {
                //页面切换了
                isClick = false;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                current = state;
            }
        });

        //设置第一个fragment
        int type = getIntent().getIntExtra("type", 0);
        current = type;
        restoreState();
        switch (type) {
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
                btn_activity.setProgress(1);
                break;
            case 4:
                btn_more.setProgress(1);
                break;

        }
        content.setCurrentItem(current, false);

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
            }
        };

        EMClient.getInstance().addConnectionListener(connectionListener);

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

        //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
//                    for (EMGroup group : grouplist) {
//                        String groupId = group.getGroupId();
//                        Log.i(TAG,"groupId= " +groupId);
//                        EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
//                        Log.i(TAG, " 解散成功！" + groupId);
//                    }
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                    Log.i(TAG, " 解散失败！");
//                }
//
//            }
//        }).start();


    }

    private void setChildProgress(int position, float progress) {
        switch (position) {
            case 0:
                btn_bodygame.setProgress(progress);
                break;
            case 1:
                btn_chat.setProgress(progress);
                break;
            case 2:
                btn_contact.setProgress(progress);
                break;
            case 3:
                btn_activity.setProgress(progress);
                break;
            case 4:
                btn_more.setProgress(progress);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("tab", 0);
        current = type;
        Log.i("消息中心发来通知");
        if (content != null) {
            restoreState();
            switch (type) {
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
                    btn_activity.setProgress(1);
                    break;
                case 4:
                    btn_more.setProgress(1);
                    break;

            }
            content.setCurrentItem(current, false);
        }
    }


    @Override
    protected void initDatas() {
        Log.i(TAG, "注册监听......");
        registerMessageReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume is running...");
        if (EMClient.getInstance().isLoggedInBefore()) {
            int unreadNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();


            Log.i(TAG, "已登录,未读消息数是： " + unreadNum);
            updateMessage(unreadNum);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_CHAT_ACTION);
        registerReceiver(mMessageReceiver, filter);

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.MESSAGE_CHAT_ACTION.equals(intent.getAction())) {
                int unreadNum = intent.getIntExtra("count", 0);
                Log.i(TAG, "收到未读消息数= " + unreadNum);
                //更新小红点
                updateMessage(unreadNum);
            }
        }
    }

    @Override
    public void onClick(View v) {
        restoreState();
        switch (v.getId()) {
            case R.id.btn_bodygame:
                btn_bodygame.setProgress(1);
                current = 0;
                break;
            case R.id.btn_chat:
                btn_chat.setProgress(1);
                current = 1;
                break;
            case R.id.btn_contact:
                btn_contact.setProgress(1);
                current = 2;
                break;
            case R.id.btn_activity:
                btn_activity.setProgress(1);
                current = 3;
                break;
            case R.id.btn_more:
                btn_more.setProgress(1);
                current = 4;
                break;
        }
        content.setCurrentItem(current, false);

    }

    private void restoreState() {
        btn_bodygame.setProgress(0);
        btn_chat.setProgress(0);
        btn_contact.setProgress(0);
        btn_activity.setProgress(0);
        btn_more.setProgress(0);

    }

    public void setAlpha(float alpha) {
        tintManager.setStatusBarAlpha(alpha);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
    }

    public void updateMessage(int num) {
        //显示
        if (num <= 0) {
            tv_umread.setVisibility(View.GONE);
        } else {
            String read = num >= 100 ? "99+" : num + "";
            tv_umread.setText(read);
            tv_umread.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, HomeActviity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
