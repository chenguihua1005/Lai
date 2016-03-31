/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
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
public class LoginPresenterImpl implements ILoginPresenter {

    private Context context;
    private LoginService service;

    public LoginPresenterImpl(Context context) {
        this.context = context;
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    @Override
    public void alidateCertification(String memberId, String password, String accountId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.alidateCertification(token, memberId, password, accountId, new Callback<ResponseData<RoleInfo>>() {
            @Override
            public void success(ResponseData<RoleInfo> userResponseData, Response response) {

                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:

                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("????");
            }
        });
    }

    @Override
    public void doLogin(String userName, String password,final ProgressDialog dialog) {

        service.doLogin(userName, password, new Callback<ResponseData<UserModel>>() {
            @Override
            public void success(ResponseData<UserModel> userResponseData, Response response) {
                if (dialog != null) dialog.dismiss();
                System.out.println(userResponseData);
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        UserInfoModel.getInstance().saveUserCache(userResponseData.getData());
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
                Util.toastMsg(R.string.neterror);
            }
        });
    }


}
