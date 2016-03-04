package com.softtek.lai.module.login.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
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

    public RegistPresenterImpl(){
        service=ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    @Override
    public void doRegist(String userName, String password, String identify) {
        String key=SharedPreferenceService.getInstance().get("identify","");
       // if(!"".equals(key)&&identify.equals(key)){
            SharedPreferenceService.getInstance().put("identify","");
            service.doRegist(PropertiesManager.get("appid"),userName, password, new Callback<ResponseData<Regist>>() {
                @Override
                public void success(ResponseData<Regist> userResponseData, Response response) {
                    Log.i(userResponseData.toString());
                    Log.i("注册成功");


                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("注册失败");
                    error.printStackTrace();

                }
            });
       // }

    }

    @Override
    public void getIdentify(String phone, String state) {
        service.getIdentify(PropertiesManager.get("appid"),phone, state, new Callback<ResponseData<Identify>>() {
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
