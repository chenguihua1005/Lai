package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.File.view.CreatFlleActivity;
import com.softtek.lai.module.login.model.Identify;
import com.softtek.lai.module.login.model.Regist;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class RegistPresenterImpl implements IRegistPresenter{

    private LoginService service;
    private Context context;

    public RegistPresenterImpl(Context context){
        service=ZillaApi.NormalRestAdapter.create(LoginService.class);
        this.context=context;
    }

    @Override
    public void doRegist(String userName, String password, String identify) {
        String key=SharedPreferenceService.getInstance().get("identify","");
        if(!"".equals(key)&&identify.equals(key)){
            SharedPreferenceService.getInstance().put("identify","");
            service.doRegist(userName, password, new Callback<ResponseData<Regist>>() {
                @Override
                public void success(ResponseData<Regist> userResponseData, Response response) {
                    Log.i("注册成功");
                    Log.i(userResponseData.toString());
                    context.startActivity(new Intent(context, CreatFlleActivity.class));

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("注册失败");
                    error.printStackTrace();

                }
            });
       }else{
            Log.i("注册停止验证码为填写");
        }

    }

    @Override
    public void getIdentify(String phone, String state) {
        service.getIdentify(phone, state, new Callback<ResponseData<Identify>>() {
            @Override
            public void success(ResponseData<Identify> stringResponseData, Response response) {
                SharedPreferenceService.getInstance().put("identify",stringResponseData.getData().getIdentify());
                Log.i("验证码获取成功>>>>"+stringResponseData.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("验证码获取失败");
            }
        });
    }
}
