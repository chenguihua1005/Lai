package com.softtek.lai.module.bodygame3.home.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.widgets.SimpleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame3)
public class BodyGameActivity extends BaseActivity implements View.OnClickListener {

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

    private List<Fragment> fragments ;

    private int current = 0;
    private boolean isClick = false;

    @Override
    protected void initViews() {
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
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
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
            case R.id.btn_activity:
                btn_activity.setProgress(1);
                if (current == 3) {
                    return;
                }
                current = 3;
                break;
            case R.id.btn_more:
                btn_more.setProgress(1);
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
