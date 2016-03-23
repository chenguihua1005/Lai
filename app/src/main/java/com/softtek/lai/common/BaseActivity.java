/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.softtek.lai.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zilla.libcore.Zilla;
import zilla.libcore.file.FileHelper;
import zilla.libcore.file.SharedPreferenceService;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInjectUtil.getInjectLayoutId(this));
        Zilla.ACTIVITY = this;
        LifeCircle.onCreate(this);
        initToolbars();
        ButterKnife.inject(this);
        initViews();
        initDatas();
        
    }

    public void onResume() {
        super.onResume();
        LifeCircle.onResume(this);

    }

    public void onPause() {
        super.onPause();
        LifeCircle.onPause(this);
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
