/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.presenter;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.model.SPModel;
import com.softtek.lai.module.jingdu.net.JingduService;
import com.softtek.lai.module.message.model.PhotosModel;

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
 * Created by julie.zhu on 3/28/2016.
 */
public class GetProinfoImpl implements IGetProinfopresenter {
    private JingduService service;
    private BaseActivity context;
    public GetProinfoImpl() {
        service = ZillaApi.NormalRestAdapter.create(JingduService.class);
    }

    public GetProinfoImpl(BaseActivity context) {
        this.context = context;
        service = ZillaApi.NormalRestAdapter.create(JingduService.class);
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
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getproinfo() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getproinfo(token, new Callback<ResponseData<RankModel>>() {
            @Override
            public void success(ResponseData<RankModel> rankModelResponseData, Response response) {
                EventBus.getDefault().post(rankModelResponseData.getData());
                int status=rankModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        //Util.toastMsg("获取成功");
                        break;
                    case 502:
                        Util.toastMsg("数据异常");
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

    @Override
    public void getspproinfo() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getspproinfo(token, new Callback<ResponseData<SPModel>>() {
            @Override
            public void success(ResponseData<SPModel> spModelResponseData, Response response) {
                EventBus.getDefault().post(spModelResponseData.getData());
//                LaichModel laichModel= (LaichModel)laichModelResponseData.getData();
//                EventBus.getDefault().post(laichModel);
                int status=spModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                       // Util.toastMsg("获取成功");
                        break;
                    case 502:
                        Util.toastMsg("数据异常");
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
