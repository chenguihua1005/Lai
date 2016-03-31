/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.ACache;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class LoginPresenterImpl implements ILoginPresenter {

    private Context context;
    private ACache aCache;
    private LoginService service;

    public LoginPresenterImpl(Context context) {
        this.context = context;
        aCache = ACache.get(context, Constants.USER_ACACHE_DATA_DIR);
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    @Override
    public void doLogin(String userName, String password, final ProgressDialog dialog) {

        service.doLogin(userName, password, new Callback<ResponseData<User>>() {
            @Override
            public void success(ResponseData<User> userResponseData, Response response) {
                if (dialog != null) dialog.dismiss();
                System.out.println(userResponseData);
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        SharedPreferenceService.getInstance().put("token", userResponseData.getData().getToken());
                        aCache.put(Constants.USER_ACACHE_KEY, userResponseData.getData());
                        context.startActivity(new Intent(context, HomeActviity.class));
                        ((AppCompatActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) dialog.dismiss();
                error.printStackTrace();
                Util.toastMsg("登录失败");
            }
        });
    }

    public void autoLogin(String phone, String password, Callback<ResponseData<User>> callback) {
        service.doLogin(phone, password, callback);
    }


}
