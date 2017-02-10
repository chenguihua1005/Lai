/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.Version;
import com.softtek.lai.module.home.net.HomeService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_about_me)
public class AboutMeActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_version)
    TextView tv_version;

    @Override
    protected void initViews() {
        tv_title.setText("关于我们");
        tv_right.setText("检查更新");
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_version.setText("V "+ DisplayUtil.getAppVersionName(this));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                ZillaApi.NormalRestAdapter.create(HomeService.class)
                        .checkNew(new RequestCallback<ResponseData<Version>>() {
                            @Override
                            public void success(ResponseData<Version> versionResponseData, Response response) {
                                if(versionResponseData.getStatus()==200){
                                    Version version=versionResponseData.getData();
                                    try {
                                        show(version);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                break;

        }
    }
    private void show(Version version){
        int v_code=DisplayUtil.getAppVersionCode(this);
        if(v_code<version.getAppVisionCode()){
            String str="莱聚+ v "+version.getAppVisionNum()+"版本\n新版本已发布快去应用市场更新吧！\n更新于："+version.getUpdateTime();
            new AlertDialog.Builder(this)
                    .setTitle("版本有更新")
                    .setMessage(str)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }else {
            new AlertDialog.Builder(this)
                    .setMessage("当前已是最新版本！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


}
