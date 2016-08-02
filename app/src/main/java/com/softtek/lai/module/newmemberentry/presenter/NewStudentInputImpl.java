/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.presenter;


import android.content.Context;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.newmemberentry.model.NewstudentsModel;
import com.softtek.lai.module.newmemberentry.model.PhotModel;
import com.softtek.lai.module.newmemberentry.net.NewstudentsService;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.io.File;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class NewStudentInputImpl implements INewStudentpresenter {

    private NewstudentsService newstudentsService;
    private Context context;

    public NewStudentInputImpl(Context context) {
        newstudentsService = ZillaApi.NormalRestAdapter.create(NewstudentsService.class);
        this.context = context;
    }

    @Override
    public void input(NewstudentsModel newstudentsModel) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        newstudentsService.memberentry(token, newstudentsModel, new Callback<ResponseData<NewstudentsModel>>() {
            @Override
            public void success(ResponseData<NewstudentsModel> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                ((JoinGameDetailActivity) context).dialogDissmiss();
                switch (status) {
                    case 200:
                        ((JoinGameDetailActivity) context).finish();
                        break;
                    case 500:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ((JoinGameDetailActivity) context).dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void upload(final String upimg) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        newstudentsService.upimg(token, new TypedFile("image/png", new File(upimg)), new Callback<ResponseData<PhotModel>>() {
            @Override
            public void success(ResponseData upimgResponseData, Response response) {
                int status = upimgResponseData.getStatus();
                switch (status) {
                    case 200:
                        PhotModel photModel = (PhotModel) upimgResponseData.getData();
                        EventBus.getDefault().post(photModel);
                        break;
                    case 500:
                        Util.toastMsg(upimgResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
}
