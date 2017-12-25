package com.softtek.lai.module.home.presenter;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/11/2017.
 */

public class CertificationPresenter extends BasePresenter<CertificationPresenter.CertificationView>{
    private LoginService service;
    private final String baseUrl = "http://115.29.187.163:8042/api/HerbUser/ValidateCertification";

    public CertificationPresenter(CertificationView baseView) {
        super(baseView);
//        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
        service = LaiApplication.getRESTAdapter(baseUrl).create(LoginService.class);
    }

    public void validateCertification(String memberId, String password, String accountId){
        if(getView()!=null){
            getView().dialogShow("认证中");
        }
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.alidateCertification(token, PropertiesManager.get("appid"),memberId, password, accountId, new Callback<ResponseData<RoleInfo>>() {
            @Override
            public void success(ResponseData<RoleInfo> userResponseData, Response response) {
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        UserModel model = UserInfoModel.getInstance().getUser();
                        model.setCertTime(userResponseData.getData().getCertTime());
                        model.setCertification(userResponseData.getData().getCertification());
                        String role = userResponseData.getData().getRole();
                        model.setRoleName(role);
                        if ("NC".equals(role)) {
                            model.setUserrole("0");
                        } else if ("PC".equals(role)) {
                            model.setUserrole("1");
                        } else if ("SR".equals(role)) {
                            model.setUserrole("2");
                        } else if ("SP".equals(role)) {
                            model.setUserrole("3");
                        } else if ("INC".equals(role)) {
                            model.setUserrole("4");
                        } else if ("VR".equals(role)) {
                            model.setUserrole("5");
                        }
                        UserInfoModel.getInstance().saveUserCache(model);
                        Util.toastMsg("认证成功");
                        if (getView()!=null){
                            getView().getData(userResponseData.getData());
                        }
                        break;
                    default:
                        if (getView()!=null){
                            getView().dialogDissmiss();
                        }
                        Util.toastMsg(userResponseData.getMsg());
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

    public interface CertificationView extends BaseView1<RoleInfo> {

    }
}
