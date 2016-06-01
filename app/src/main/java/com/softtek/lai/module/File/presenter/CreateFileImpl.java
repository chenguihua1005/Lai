/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.presenter;

import android.content.Context;
import android.content.Intent;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.module.File.net.FileService;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.view.HomeActviity;
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

    public CreateFileImpl(CreatFlleActivity creatFlleActivity) {
        service = ZillaApi.NormalRestAdapter.create(FileService.class);
        context = creatFlleActivity;
    }


    @Override
    public void createFile(String token, final FileModel file) {
        service.doFile(token, file, new Callback<ResponseData<FileModel>>() {
            @Override
            public void success(ResponseData<FileModel> fileResponseData, Response response) {
                int status = fileResponseData.getStatus();
                switch (status) {
                    case 200: {
                        UserModel model = UserInfoModel.getInstance().getUser();
                        model.setNickname(file.getNickname());
                        model.setGender(file.getGender() + "");
                        UserInfoModel.getInstance().saveUserCache(model);
                        Intent intent = new Intent(context, HomeActviity.class);
                        context.startActivity(intent);
                        break;
                    }

                    case 100:
                        Util.toastMsg(fileResponseData.getMsg());
                        break;
                    case 101:
                        Util.toastMsg(fileResponseData.getMsg());
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


//    public interface ICreateFileView {
//        void toActivity();
//    }
}
