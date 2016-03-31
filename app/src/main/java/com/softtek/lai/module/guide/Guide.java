/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.ACache;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.file.SharedPreferenceService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Guide extends AppCompatActivity implements Runnable {

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ILoginPresenter loginPresenter;
    private boolean autoLogin = false;
    private boolean success = false;

    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(getLayoutInflater().inflate(R.layout.activity_guide, null, false));
        loginPresenter = new LoginPresenterImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        //检查是否设置了自动登陆
        autoLogin = SharedPreferenceService.getInstance().get(Constants.AUTO_LOGIN, false);
        if (autoLogin) {
            //获取用户名和密码
            String phone = SharedPreferenceService.getInstance().get(Constants.AUTO_USER_NAME, "");
            String password = SharedPreferenceService.getInstance().get(Constants.AUTO_PASSWORD, "");
            if ("".equals(phone) || "".equals(password)) {
                success = false;
                countDownLatch.countDown();
            } else {
                ((LoginPresenterImpl) loginPresenter)
                        .autoLogin(phone,
                                password,
                                new Callback<ResponseData<User>>() {
                                    @Override
                                    public void success(ResponseData<User> userResponseData, Response response) {
                                        switch (userResponseData.getStatus()) {
                                            case 200:
                                                success = true;
                                                SharedPreferenceService.getInstance().put("token", userResponseData.getData().getToken());
                                                aCache.put(Constants.USER_ACACHE_KEY, userResponseData.getData());
                                                break;
                                            default:
                                                success = false;
                                                break;
                                        }
                                        countDownLatch.countDown();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        success = false;
                                        countDownLatch.countDown();
                                    }
                                });
            }

        } else {
            countDownLatch.countDown();
        }
        new Thread(this).start();


    }

    @Override
    public void run() {
        try {
            //等待10秒阻塞
            countDownLatch.await(10, TimeUnit.SECONDS);
            if (!success) {
                //如果没有设置自动登陆就直接延迟2s进入登陆界面
                SystemClock.sleep(1500);
                Intent intent = new Intent(Guide.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                //登陆完后直接去主页
                Intent intent = new Intent(Guide.this, HomeActviity.class);
                startActivity(intent);
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
