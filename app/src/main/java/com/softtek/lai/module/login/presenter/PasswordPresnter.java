package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.login.view.ForgetActivity2;
import com.softtek.lai.module.login.view.LoginActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/4/2016.
 */
public class PasswordPresnter implements IPasswordPresenter{

    private LoginService service;
    private Context context;

    public PasswordPresnter(Context context){
        this.context=context;
        service= ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    @Override
    public void resetPassword(String phone,String password) {
        service.doResetPassword(phone, password, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i("重置成功");
                Util.toastMsg(R.string.psdResetY);

                context.startActivity(new Intent(context, LoginActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("重置失败");
                Util.toastMsg(R.string.psdResetN);
            }
        });

    }

    @Override
    public void checkIdentify(final String phone, String identify) {
        service.checkIdentify(phone, identify, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i("校验成功>>>"+responseData.toString());
                Intent intent=new Intent(context, ForgetActivity2.class);
                intent.putExtra("phone",phone);
                context.startActivity(new Intent(context, ForgetActivity2.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("校验失败");
            }
        });
    }
}
