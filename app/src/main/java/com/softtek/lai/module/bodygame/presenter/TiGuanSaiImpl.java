/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame.presenter;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.animation.Animation;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.eventmodel.TotalEventModel;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TipsDetailModel;
import com.softtek.lai.module.bodygame.model.TipsModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.net.BodyGameService;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 3/17/2016.
 */
public class TiGuanSaiImpl implements ITiGuanSai {

    private BodyGameService service;

    public TiGuanSaiImpl() {
        service = ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    @Override
    public void getTiGuanSai() {

        service.doGetTiGuanSaiClickw( new Callback<ResponseData<TiGuanSaiModel>>() {
            @Override
            public void success(ResponseData<TiGuanSaiModel> tiGuanSaiResponseData, Response response) {

                int status = tiGuanSaiResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(tiGuanSaiResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(tiGuanSaiResponseData.getMsg());
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
    public void doGetFuceNum(long id) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.doGetFuceNum(token, id, new Callback<ResponseData<FuceNumModel>>() {
            @Override
            public void success(ResponseData<FuceNumModel> fuceNumResponseData, Response response) {
                int status = fuceNumResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(fuceNumResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(fuceNumResponseData.getMsg());
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
    public void doGetTips() {

        service.doGetTips(new Callback<ResponseData<List<TipsModel>>>() {


            @Override
            public void success(ResponseData<List<TipsModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
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

    @Override
    public void doGetTipsDetail(long id) {
        service.doGetTipsDetail( id, new Callback<ResponseData<List<TipsDetailModel>>>() {
            @Override
            public void success(ResponseData<List<TipsDetailModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
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
//参赛总人数及减重总数
    @Override
    public void doGetTotal(final ProgressDialog progressDialog) {
        service.doGetTotal(new Callback<ResponseData<List<TotolModel>>>() {
            @Override
            public void success(ResponseData<List<TotolModel>> listResponseData, Response response) {
                progressDialog.dismiss();
                int status=listResponseData.getStatus();
//                if (rotate!=null) {
//                    rotate.cancel();
//                }
                switch (status)
                {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
//                if (rotate!=null) {
//                    rotate.cancel();
//                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });

    }
}
