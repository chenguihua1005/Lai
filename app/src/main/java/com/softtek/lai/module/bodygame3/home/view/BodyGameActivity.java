package com.softtek.lai.module.bodygame3.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.customermanagement.view.GymClubActivity;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.widgets.SimpleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

import static java.security.AccessController.getContext;

@InjectLayout(R.layout.activity_bodygame3)
public class BodyGameActivity extends BaseActivity implements View.OnClickListener {

    List<ContactClassModel> classModels;

    @InjectView(R.id.btn_bodygame)
    SimpleButton btn_bodygame;
    @InjectView(R.id.btn_honorroll)
    SimpleButton btn_honorroll;
    //    @InjectView(R.id.btn_chat)
//    SimpleButton btn_chat;
//    @InjectView(R.id.btn_contact)
//    SimpleButton btn_contact;
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

    private String classId;
    MoreFragment moreFragment ;
    BodyGameFragment gameFragment;
    HonorTabFragment honorTabFragment ;
    ActivityFragment activityFragment ;


    @Override
    protected void initViews() {
        classId = getIntent().getStringExtra("classId");

        Bundle bundle = new Bundle();
        bundle.putString("classId", classId);
        moreFragment = new MoreFragment();
        gameFragment = new BodyGameFragment();
        honorTabFragment = new HonorTabFragment();
        activityFragment = new ActivityFragment();
        moreFragment.setArguments(bundle);
        if (classId != null){
            gameFragment.setArguments(bundle);
            honorTabFragment.setArguments(bundle);
            activityFragment.setArguments(bundle);
        }
        MobclickAgent.openActivityDurationTrack(false);
        btn_bodygame.setOnClickListener(this);
        btn_honorroll.setOnClickListener(this);
//        btn_chat.setOnClickListener(this);
//        btn_contact.setOnClickListener(this);
        btn_activity.setOnClickListener(this);
        btn_more.setOnClickListener(this);

        fragments = new ArrayList<>();
        fragments.add(gameFragment);
        fragments.add(honorTabFragment);
//        fragments.add(new ChatFragment());
//        fragments.add(new ContactFragment());
        fragments.add(activityFragment);
        fragments.add(moreFragment);
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
                btn_honorroll.setProgress(1);
                break;
//            case 1:
//                btn_chat.setProgress(1);
//                break;
//            case 2:
//                btn_contact.setProgress(1);
//                break;
            case 2:
                btn_activity.setProgress(1);
                break;
            case 3:
                btn_more.setProgress(1);
                break;

        }
        content.setCurrentItem(current, false);


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


    private void setChildProgress(int position, float progress) {
        switch (position) {
            case 0:
                btn_bodygame.setProgress(progress);
                break;
            case 1:
                btn_honorroll.setProgress(progress);
                break;
//            case 1:
//                btn_chat.setProgress(progress);
//                break;
//            case 2:
//                btn_contact.setProgress(progress);
//                break;
            case 2:
                btn_activity.setProgress(progress);
                break;
            case 3:
                btn_more.setProgress(progress);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type", 0);
        current = type;
        Log.i("消息中心发来通知");
        if (content != null) {
            restoreState();
            switch (type) {
                case 0:
                    btn_bodygame.setProgress(1);
                    break;
                case 1:
                    btn_honorroll.setProgress(1);
                    break;
//                case 1:
//                    btn_chat.setProgress(1);
//                    break;
//                case 2:
//                    btn_contact.setProgress(1);
//                    break;
                case 2:
                    btn_activity.setProgress(1);
                    break;
                case 3:
                    btn_more.setProgress(1);
                    break;

            }
            content.setCurrentItem(current, false);
        }
    }


    @Override
    protected void initDatas() {
        registerMessageReceiver();
    }


    @Override
    public void onResume() {
        if (content != null){
            if (content.getCurrentItem() == 3){
                moreFragment.itemFresh();
            }
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferenceService.getInstance(this).put("ClassId", classId);
        unregisterReceiver(mMessageReceiver);
    }

    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        //filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_CHAT_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getClass().getCanonicalName() + "接收到还信的消息");
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
            case R.id.btn_honorroll:
                btn_honorroll.setProgress(1);
                current = 1;
                break;
//            case btn_chat:
//                btn_chat.setProgress(1);
//                current = 1;
//                break;
//            case btn_contact:
//                btn_contact.setProgress(1);
//                current = 2;
//                break;
            case R.id.btn_activity:
                btn_activity.setProgress(1);
                current = 2;
                break;
            case R.id.btn_more:
                btn_more.setProgress(1);
                current = 3;
                break;
        }
        content.setCurrentItem(current, false);

    }

    private void restoreState() {
        btn_bodygame.setProgress(0);
        btn_honorroll.setProgress(0);
//        btn_chat.setProgress(0);
//        btn_contact.setProgress(0);
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
            if (classId != null) {
                startActivity(new Intent(this, GymClubActivity.class));
            }else {
                startActivity(new Intent(this, HomeActviity.class));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
