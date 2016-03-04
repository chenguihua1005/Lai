/*
 * Copyright (c)  2015. Softtek
 */
package com.softtek.lai.module.home.tab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.msg.MsgFragment;
import com.softtek.lai.widget.MyFragmentTabHost;

import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

/**
 * 首页
 */
@InjectLayout(R.layout.activity_main_new)
public class TabMainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener, TabHost.OnTabChangeListener {

    int index = 0;

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isMessage = SharedPreferenceService.getInstance().get("isMessage", false);
        if (isMessage) {
            mTabHost.setCurrentTab(3);
            SharedPreferenceService.getInstance().put("isMessage", false);
        }
    }

    private MyFragmentTabHost mTabHost = null;
    private View indicator = null;

    private static final String TAB_1 = "tab_order";
    private static final String TAB_2 = "tab_my_order";
    private static final String TAB_3 = "tab_stock";
    private static final String TAB_4 = "tab_msg";

    @Override
    protected void initDatas() {
        mTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        // 添加tab名称和图标
        indicator = getIndicatorView(getString(R.string.title_1), R.layout.tab_1);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1).setIndicator(indicator), MsgFragment.class, null);

        indicator = getIndicatorView(getString(R.string.title_2), R.layout.tab_2);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2).setIndicator(indicator), MsgFragment.class, null);

        indicator = getIndicatorView(getString(R.string.title_3), R.layout.tab_3);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_3).setIndicator(indicator), MsgFragment.class, null);

        indicator = getIndicatorView(getString(R.string.title_4), R.layout.tab_4);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_4).setIndicator(indicator), MsgFragment.class, null);

        mTabHost.setOnTabChangedListener(this);
    }

    private View getIndicatorView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(name);
        return v;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("onFragmentInteraction" + uri.toString());

    }

    @Override
    public void onTabChanged(String tabId) {
        if (TAB_1.equals(tabId)) {
            setTitle(getString(R.string.title_1));
            index = 0;
        } else if (TAB_2.equals(tabId)) {
            setTitle(getString(R.string.title_2));
            index = 1;
        } else if (TAB_3.equals(tabId)) {
            setTitle(getString(R.string.title_3));
            index = 2;
        } else if (TAB_4.equals(tabId)) {
            setTitle(getString(R.string.title_4));
            index = 3;
        }
        invalidateOptionsMenu();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_exit))
                    .setMessage(getString(R.string.app_exit) + " " + getResources().getString(R.string.app_name) + " ?")
                    .setPositiveButton(getString(R.string.app_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            dialogBuilder.create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
