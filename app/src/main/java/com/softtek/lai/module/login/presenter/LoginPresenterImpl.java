package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.tab.TabMainActivity;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.CountDownTimer;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class LoginPresenterImpl implements ILoginPresenter {

    private Context context;
    public LoginPresenterImpl(Context context){
        this.context=context;
    }

    @Override
    public void doLogin(String userName, String password) {
        LoginService service= ZillaApi.NormalRestAdapter.create(LoginService.class);
        service.doLogin(PropertiesManager.get("appid"),userName, password, new Callback<ResponseData<User>>() {
            @Override
            public void success(ResponseData<User> userResponseData, Response response) {
                System.out.println(userResponseData);
                context.startActivity(new Intent(context, TabMainActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("登录失败");
            }
        });
    }




}
