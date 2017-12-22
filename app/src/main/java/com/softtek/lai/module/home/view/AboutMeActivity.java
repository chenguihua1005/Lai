/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.Version;
import com.softtek.lai.module.home.net.HomeService;
import com.softtek.lai.module.home.service.UpdateService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import retrofit.RetrofitError;
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
    private String vName;

    @Override
    protected void initViews() {
        tv_title.setText("关于我们");
        tv_right.setText("检查更新");
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        vName = DisplayUtil.getAppVersionName(this);
        tv_version.setText("V "+ DisplayUtil.getAppVersionName(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                dialogShow("检查版本");
                ZillaApi.NormalRestAdapter.create(HomeService.class)
                        .checkNew(vName, new RequestCallback<ResponseData<Version>>() {
                            @Override
                            public void success(ResponseData<Version> versionResponseData, Response response) {
                                dialogDissmiss();
                                if(versionResponseData.getStatus()==200){
                                    Version version=versionResponseData.getData();
                                    try {
                                        show(version);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                UpdateService.startUpdate(getApplicationContext(),apkUrl);
            }
        }
    }
private String apkUrl;
    private void show(final Version version){
        int v_code=DisplayUtil.getAppVersionCode(this);
        if(v_code<version.getAppVisionCode()){
            String str="莱聚+ v "+version.getAppVisionNum()+"版本\n最新的版本！请前去下载。\n更新于："+version.getUpdateTime();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("稍后更新", null);
            builder.setTitle("版本有更新")
                    .setMessage(str)
                    .setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            apkUrl=version.getAppFileUrl();
                            if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                //启动更新服务
                                UpdateService.startUpdate(getApplicationContext(),version.getAppFileUrl());
                            }
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

    private boolean hasPermission(String permission){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), permission)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{permission},100);
            return false;
        }
        return true;
    }

}
