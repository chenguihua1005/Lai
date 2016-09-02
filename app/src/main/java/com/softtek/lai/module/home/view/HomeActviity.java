package com.softtek.lai.module.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;

import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.home.adapter.MainPageAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_home_actviity)
public class HomeActviity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.btn_home)
    SimpleButton btn_home;

    @InjectView(R.id.btn_healthy)
    SimpleButton btn_healthy;

    @InjectView(R.id.btn_healthy_record)
    SimpleButton btn_healthy_record;

    @InjectView(R.id.btn_mine)
    SimpleButton btn_mine;

    private int currentId = 0;
    private boolean isClick = false;

    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews() {
        btn_home.setOnClickListener(this);
        btn_healthy.setOnClickListener(this);
        btn_healthy_record.setOnClickListener(this);
        btn_mine.setOnClickListener(this);
        content.setOffscreenPageLimit(4);

    }

    @Override
    protected void initDatas() {
        fragments.add(new HomeFragment());
        fragments.add(new HealthyFragment());
        fragments.add(new HealthyRecordFragment());
        fragments.add(new MineFragment());
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager(),fragments));
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
                currentId = state;
            }
        });

        restoreState();
        btn_home.setProgress(1);
        currentId = 0;
        content.setCurrentItem(0);
        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        isClick = true;
        restoreState();
        switch (v.getId()) {
            case R.id.btn_home:
                btn_home.setProgress(1);
                currentId = 0;
                break;
            case R.id.btn_healthy:
                btn_healthy.setProgress(1);
                currentId = 1;
                break;
            case R.id.btn_healthy_record:
                btn_healthy_record.setProgress(1);
                currentId = 2;
                break;
            case R.id.btn_mine:
                btn_mine.setProgress(1);
                currentId = 3;
                break;
        }
        content.setCurrentItem(currentId, false);
    }

    private void setChildProgress(int position, float progress) {
        switch (position) {
            case 0:
                btn_home.setProgress(progress);
                break;
            case 1:
                btn_healthy.setProgress(progress);
                break;
            case 2:
                btn_healthy_record.setProgress(progress);
                break;
            case 3:
                btn_mine.setProgress(progress);
                break;
        }
    }

    private void restoreState() {
        btn_home.setProgress(0);
        btn_healthy.setProgress(0);
        btn_healthy_record.setProgress(0);
        btn_mine.setProgress(0);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
