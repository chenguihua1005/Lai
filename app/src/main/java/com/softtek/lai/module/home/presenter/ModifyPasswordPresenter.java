package com.softtek.lai.module.home.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/11/2017.
 */

public class ModifyPasswordPresenter extends BasePresenter{
    private LoginService service;

    public ModifyPasswordPresenter(BaseView baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    public void changePsd(final Context context, String newpsd, String psd, final String token, final String type) {
        if(getView()!=null){
            getView().dialogShow("修改密码");
        }
        service.changePsd(token, newpsd, psd, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        UserInfoModel.getInstance().setToken(token);
                        if(getView()!=null){
                            ((AppCompatActivity) context).finish();
                            if ("1".equals(type)) {
                                context.startActivity(new Intent(context, HomeActviity.class));
                            }
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                ZillaApi.dealNetError(error);
            }
        });
    }
}
