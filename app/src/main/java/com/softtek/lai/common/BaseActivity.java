/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ggx.widgets.view.CustomProgress;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.utils.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import zilla.libcore.Zilla;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.ui.LayoutInjectUtil;

/**
 * Created by zilla on 14/12/1.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    /**
     * Toobar
     */
    public static String TAG;
    protected Toolbar mToolbar;
    protected SystemBarTintManager tintManager;

    protected CustomProgress progressDialog;
    private T presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE );
        super.onCreate(savedInstanceState);
        TAG = getClass().getCanonicalName();
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(typedValue.data);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(LayoutInjectUtil.getInjectLayoutId(this));
        Zilla.ACTIVITY = this;
        LifeCircle.onCreate(this);
        initToolbars();

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

//        tintManager.setStatusBarTintColor(typedValue.data);
        tintManager.setStatusBarTintResource(typedValue.resourceId);
        //设置显示系统菜单键
//        try {
//            getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
//        } catch (NoSuchFieldException e) {
//            // Ignore since this field won't exist in most versions of Android
//        } catch (IllegalAccessException e) {
//            Log.w("feelyou.info", "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()", e);
//        }
        ButterKnife.inject(this);
        initViews();
        initDatas();
        //有盟统计
        MobclickAgent.setDebugMode(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        Log.i("当前界面名称=" + getClass().getCanonicalName());

    }

    public void onResume() {
        super.onResume();
        Zilla.ACTIVITY = this;
        if (LaiApplication.getInstance().getContext() != null) {
            LaiApplication.getInstance().getContext().clear();
        }
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

    @Override
    public void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = CustomProgress.build(this, value);
            progressDialog.show();

        }
    }

    public void dialogShow() {
        if (progressDialog == null) {
            progressDialog = CustomProgress.build(this, null);
            progressDialog.show();
        }
    }

    @Override
    public void dialogDissmiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }


    @Override
    protected void onDestroy() {
        LifeCircle.onDestory(this);
        ButterKnife.reset(this);
        if (presenter != null) {
            presenter.recycle();
        }
        super.onDestroy();
//        LaiApplication.getWatch(this).watch(this);
    }

    protected void initToolbars() {
        View view = findViewById(R.id.toolbar);
        if (view != null) {
            mToolbar = (Toolbar) view;
            mToolbar.setTitle("");
            mToolbar.setSubtitle("");
            mToolbar.setLogo(null);
            //去除内间距
            mToolbar.setContentInsetsAbsolute(0, 0);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setSupportActionBar(mToolbar);

        }
    }

    protected abstract void initViews();

    protected abstract void initDatas();


}
