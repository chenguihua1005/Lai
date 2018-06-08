package com.softtek.lai.module.login.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 *
 * Created by jerry.guan on 4/11/2017.
 */

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordPresenter.ChangePasswordView>{

    private LoginService service;
    public ChangePasswordPresenter(ChangePasswordView baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    public void resetPassword(String phone, String password, String identify) {
        if(getView()!=null){
            getView().dialogShow("修改密码");
        }
        service.doResetPassword(phone, password, identify, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                Log.i("重置成功");
                Util.toastMsg(R.string.psdResetY);
                if(getView()!=null){
                    getView().changeSuccess();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                Log.i("重置失败");
                Util.toastMsg(R.string.psdResetN);
            }
        });

    }

    public interface ChangePasswordView extends BaseView{
        void changeSuccess();
    }
}
