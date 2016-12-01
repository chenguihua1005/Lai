/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.forlong401.log.transaction.log.manager.LogManager;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.utils.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import zilla.libcore.Zilla;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.exit.AppExitLife;
import zilla.libcore.ui.LayoutInjectUtil;

/**
 * Created by zilla on 14/12/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 生命周期管理
     */
    @LifeCircleInject
    public AppExitLife lifeCicleExit;
    /**
     * Toobar
     */
    protected Toolbar mToolbar;
    protected SystemBarTintManager tintManager;

    protected ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(LayoutInjectUtil.getInjectLayoutId(this));
        Zilla.ACTIVITY = this;
        LifeCircle.onCreate(this);
        initToolbars();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
//        try {
//            getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
//        } catch (NoSuchFieldException e) {
//            // Ignore since this field won't exist in most versions of Android
//        } catch (IllegalAccessException e) {
//            Log.w("feelyou.info", "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()", e);
//        }
        ButterKnife.inject(this);
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        initViews();
        initDatas();
        //有盟统计
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        Log.i("当前界面名称=" + getClass().getCanonicalName());

    }

    public void onResume() {
        super.onResume();
        Zilla.ACTIVITY = this;
        LaiApplication.getInstance().setContext(new WeakReference<Context>(this));
        LifeCircle.onResume(this);
        MobclickAgent.onResume(this);

    }

    public void onPause() {
        super.onPause();
        LifeCircle.onPause(this);
        MobclickAgent.onPause(this);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            //progressDialog.setCancelable(false);
            progressDialog.setMessage(value);
            progressDialog.show();
        }
    }

    public void setProgressValue(String value) {
        progressDialog.setMessage(value);
    }

    public void dialogShow() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("载入中");
            progressDialog.show();
        }
    }

    public void dialogDissmiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        LifeCircle.onDestory(this);
        ButterKnife.reset(this);
        LogManager.getManager(getApplicationContext()).unregisterActivity(this);
        super.onDestroy();
        //LaiApplication.getInstance().getContext().clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initToolbars() {
        View view = findViewById(R.id.toolbar);
        if (view != null) {
            mToolbar = (Toolbar) view;
            mToolbar.setTitle("");
            mToolbar.setSubtitle("");
            mToolbar.setLogo(null);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setSupportActionBar(mToolbar);

        }
    }

    protected abstract void initViews();

    protected abstract void initDatas();

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //showPopupWindow();
            //do something
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showPopupWindow() {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.menu_pop, null);
        // 设置按钮的点击事件
        Button button = (Button) contentView.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity.this, "button is pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x47000000));
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(this.findViewById(android.R.id.content),
                Gravity.BOTTOM, 0, 0);

    }

}
