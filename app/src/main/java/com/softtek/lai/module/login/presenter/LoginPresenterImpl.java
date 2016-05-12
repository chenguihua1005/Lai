/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.jpush.JpushSet;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
import com.softtek.lai.module.login.model.PhotoModel;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.message.model.PhotosModel;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
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
    public void alidateCertification(String memberId, String password, String accountId, final ProgressDialog progressDialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.alidateCertification(token, memberId, password, accountId, new Callback<ResponseData<RoleInfo>>() {
            @Override
            public void success(ResponseData<RoleInfo> userResponseData, Response response) {
                System.out.println("userResponseData:" + userResponseData);
                int status = userResponseData.getStatus();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                switch (status) {
                    case 200:
                        UserModel model = UserInfoModel.getInstance().getUser();
                        model.setCertTime(userResponseData.getData().getCertTime());
                        model.setCertification(userResponseData.getData().getCertification());
                        String role = userResponseData.getData().getRole();
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
                        EventBus.getDefault().post(userResponseData.getData());
                        ((AppCompatActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void modifyPicture(String accountId, final String upimg, final ProgressDialog dialog, final ImageView imgV) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.modifyPicture(token, accountId, new TypedFile("image/png", new File(upimg)), new Callback<ResponseData<PhotoModel>>() {
            @Override
            public void success(ResponseData<PhotoModel> responseData, Response response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                System.out.println("responseData:" + responseData);
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        PhotoModel photoModel = responseData.getData();
                        File files = new File(upimg);
                        Picasso.with(context).load(files).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(imgV);
                        UserModel userModel = UserInfoModel.getInstance().getUser();
                        userModel.setPhoto(photoModel.getImg());
                        UserInfoModel.getInstance().saveUserCache(userModel);
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void modifyPictures(String accountId, final String upimg, final ProgressDialog dialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.modifyPicture(token, accountId, new TypedFile("image/png", new File(upimg)), new Callback<ResponseData<PhotoModel>>() {
            @Override
            public void success(ResponseData<PhotoModel> responseData, Response response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                System.out.println("responseData:" + responseData);
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        PhotoModel photoModel = responseData.getData();
                        UserModel userModel = UserInfoModel.getInstance().getUser();
                        userModel.setPhoto(photoModel.getImg());
                        UserInfoModel.getInstance().saveUserCache(userModel);
                        ((AppCompatActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getUpdateName(String accountId, final String userName, final ProgressDialog dialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getUpdateName(token, accountId,userName, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                System.out.println("responseData:" + responseData);
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        UserModel userModel = UserInfoModel.getInstance().getUser();
                        System.out.println("userName:"+userName);
                        userModel.setNickname(userName);
                        UserInfoModel.getInstance().saveUserCache(userModel);
                        ((AppCompatActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void doLogin(String userName, String password, final ProgressDialog dialog) {

        service.doLogin(userName, password, new Callback<ResponseData<UserModel>>() {
            @Override
            public void success(ResponseData<UserModel> userResponseData, Response response) {
                if (dialog != null) dialog.dismiss();
                System.out.println(userResponseData);
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        JPushInterface.init(context);
                        JpushSet set = new JpushSet(context);
                        set.setAlias(userResponseData.getData().getToken());
                        set.setStyleBasic();

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
                ZillaApi.dealNetError(error);
            }
        });
    }


}
