package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.net.LoginService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
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
        service.doLogin(userName, password, new Callback<ResponseData<User>>() {
            @Override
            public void success(ResponseData<User> userResponseData, Response response) {
                System.out.println(userResponseData);
                List<Header> headers=response.getHeaders();
                for(Header header:headers){
                    System.out.println(header.toString());
                }
                int status=userResponseData.getStatus();
                switch (status){
                    case 200:
                        SharedPreferenceService.getInstance().put("token",userResponseData.getData().getToken());
                        context.startActivity(new Intent(context, HomeActviity.class));
                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("登录失败");
            }
        });
    }




}
