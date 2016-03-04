package com.softtek.lai.module.login.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;

/**
 * Created by jerry.guan on 3/4/2016.
 */
public class PasswordPresnter implements IPasswordPresenter{

    private LoginService service;

    public PasswordPresnter(){
        service= ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    @Override
    public void resetPassword(String password1, String password2) {
        if(!password1.equals(password2)){
            return;
        }
        service.doResetPassword(PropertiesManager.get("appid"), password1, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i("重置成功");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("重置失败");
            }
        });

    }
}
