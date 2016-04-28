/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.common;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.utils.SystemBarTintManager;

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
        super.onCreate(savedInstanceState);
        setContentView(LayoutInjectUtil.getInjectLayoutId(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Zilla.ACTIVITY = this;
        LifeCircle.onCreate(this);
        initToolbars();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);

        ButterKnife.inject(this);
        initViews();
        initDatas();

    }

    public void onResume() {
        super.onResume();
        Zilla.ACTIVITY = this;
        LaiApplication.getInstance().setContext(this);
        LifeCircle.onResume(this);

    }

    public void onPause() {
        super.onPause();
        LifeCircle.onPause(this);
    }

    public void dialogShow(String value) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(value);
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
        super.onDestroy();
        LifeCircle.onDestory(this);
        ButterKnife.reset(this);
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
}
