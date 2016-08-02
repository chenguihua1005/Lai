/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.model.PargradeModel;
import com.softtek.lai.module.newmemberentry.net.GuwenService;
import org.greenrobot.eventbus.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.util.List;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class GuwenClassImp implements GuwenClassPre {
    private GuwenService service;

    public GuwenClassImp() {
        service = ZillaApi.NormalRestAdapter.create(GuwenService.class);
    }

    @Override
    public void doGetGuwenClass(long managerId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.doGetGuwenClass(token, managerId, new Callback<ResponseData<List<PargradeModel>>>() {
            @Override
            public void success(ResponseData<List<PargradeModel>> listResponseData, retrofit.client.Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new ClassEvent(listResponseData.getData()));
                        System.out.println(listResponseData);
                        // Util.toastMsg("列表查询成功");
                        break;
                    case 201:
                        
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
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