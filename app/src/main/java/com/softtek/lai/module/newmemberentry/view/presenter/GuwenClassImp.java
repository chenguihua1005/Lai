/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.view.model.Pargrade;
import com.softtek.lai.module.newmemberentry.view.net.GuwenService;
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
        Log.i("service>>>>>>>>>>>>>>>>>>>>>>>>>>" + service);
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.doGetGuwenClass(token, managerId, new Callback<ResponseData<List<Pargrade>>>() {
            @Override
            public void success(ResponseData<List<Pargrade>> listResponseData, retrofit.client.Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new ClassEvent(listResponseData.getData()));
                        System.out.println(listResponseData);

                        Util.toastMsg("列表查询成功");
                        break;
                    case 201:
                        Util.toastMsg("未查询到结果");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });

    }
}
