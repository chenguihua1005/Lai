/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.module.File.net.FileService;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
import com.softtek.lai.module.login.model.UserModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public class CreateFileImpl implements ICreateFilepresenter {

    private FileService service;
    private Context context;

    public CreateFileImpl(Context context) {
        service = ZillaApi.NormalRestAdapter.create(FileService.class);
        this.context = context;
    }


    @Override
    public void createFile(final String token, final FileModel file) {
        service.doFile(token, file, new Callback<ResponseData<FileModel>>() {
            @Override
            public void success(ResponseData<FileModel> fileResponseData, Response response) {
                int status = fileResponseData.getStatus();
                switch (status) {
                    case 200: {
                        UserModel model = UserInfoModel.getInstance().getUser();
                        model.setNickname(file.getNickname());
                        model.setGender(file.getGender() + "");
                        model.setToken(token);
                        model.setIsCreatInfo("1");
                        model.setBirthday(file.getBirthday());
                        model.setHight(String.valueOf(file.getHeight()));
                        model.setWeight(String.valueOf(file.getWeight()));
                        UserInfoModel.getInstance().saveUserCache(model);
                        ((AppCompatActivity) context).finish();
                        Intent intent=new Intent(context, ValidateCertificationActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    default:
                        Util.toastMsg(fileResponseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }


}
