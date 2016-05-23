/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.module.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.group.net.SportGroupService;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;

import org.apache.commons.lang3.StringUtils;

import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_guide)
public class GuideActivity extends BaseActivity implements Runnable{

    private  String token=null;
    private SportGroupService service;

    @Override
    protected void initViews() {
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(SportGroupService.class);
        //检查是否存在token
        token= UserInfoModel.getInstance().getToken();
        Log.i("token="+token);
        new Handler().postDelayed(this,1500);
    }

    @Override
    public void run() {
        if(StringUtils.isEmpty(token)){
            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            //登陆完后直接去主页
            UserModel model=UserInfoModel.getInstance().getUser();
            if(model==null){
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                //检查改用户是否加入过跑团
                Intent intent = new Intent(GuideActivity.this, HomeActviity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
