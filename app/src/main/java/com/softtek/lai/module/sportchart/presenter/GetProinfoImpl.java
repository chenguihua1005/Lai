/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sportchart.presenter;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.message2.model.PhotosModel;
import com.softtek.lai.module.sportchart.net.ChartService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 *
 * Created by julie.zhu on 3/28/2016.
 */
public class GetProinfoImpl implements IGetProinfopresenter {
    private ChartService service;
    private BaseActivity context;

    public GetProinfoImpl(BaseActivity context) {
        this.context = context;
        service = ZillaApi.NormalRestAdapter.create(ChartService.class);
    }
    //上传图片文件
    @Override
    public void upload(final String upimg) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.upimg(token, new TypedFile("image/png", new File(upimg)), new Callback<ResponseData<PhotosModel>>() {
            @Override
            public void success(ResponseData<PhotosModel> upimgResponseData, Response response) {
                context.dialogDissmiss();
                int status = upimgResponseData.getStatus();
                switch (status) {
                    case 200:
                        PhotosModel photModel = upimgResponseData.getData();
                        EventBus.getDefault().post(photModel);
                        break;
                    default:
                        context.dialogDissmiss();
                        Util.toastMsg(upimgResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

}
