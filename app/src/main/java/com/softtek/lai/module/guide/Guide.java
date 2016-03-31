package com.softtek.lai.module.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfo;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.ACache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

public class Guide extends AppCompatActivity implements Runnable{

    private  String token=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(getLayoutInflater().inflate(R.layout.activity_guide, null, false));
        //检查是否存在token
        token= UserInfo.getInstance().getToken();
        new Handler().postDelayed(this,1500);


    }

    @Override
    public void run() {
        if(token==null||"".equals(token)){
            Intent intent = new Intent(Guide.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            //登陆完后直接去主页
            Intent intent = new Intent(Guide.this, HomeActviity.class);
            startActivity(intent);
            finish();
        }
    }
}
