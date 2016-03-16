package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.login.model.Identify;
import com.softtek.lai.module.login.model.Regist;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

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
            service.doRegist(userName, password,identify, new Callback<ResponseData<User>>() {
                @Override
                public void success(ResponseData<User> userResponseData, Response response) {
                    Log.i("注册成功");
                    Log.i(userResponseData.toString());
                    int status=userResponseData.getStatus();
                    Util.toastMsg(userResponseData.getMsg());
                    switch (status){
                        case 200:
                            SharedPreferenceService.getInstance().put("token",userResponseData.getData().getToken());
                            context.startActivity(new Intent(context, CreatFlleActivity.class));
                            break;
                        default:
                            break;
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("注册失败");
                    Util.toastMsg("服务器异常,注册失败");
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
                if(stringResponseData.getStatus()!=200){
                    Util.toastMsg(stringResponseData.getMsg());
                }
                Log.i("验证码获取结果>>>>"+stringResponseData.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg(R.string.neterror);
                Log.i("验证码获取失败");
            }
        });
    }
}
