/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.jingdu.EventModel.RankEvent;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.net.JingduService;
import org.greenrobot.eventbus.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.util.List;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public class GetProinfoImpl implements IGetProinfopresenter {
    private JingduService service;

    public GetProinfoImpl() {
        service = ZillaApi.NormalRestAdapter.create(JingduService.class);
    }

    @Override
    public void getproinfo() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getproinfo(token, new Callback<ResponseData<RankModel>>() {
            @Override
            public void success(ResponseData<RankModel> rankModelResponseData, Response response) {
                int status=rankModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("获取成功");
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
