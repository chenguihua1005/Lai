/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.util.Log;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.model.HonorInfoModel;
import com.softtek.lai.module.counselor.model.ShareSRHonorModel;
import com.softtek.lai.module.counselor.model.UserHonorModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.jingdu.model.DangQiShare;

import org.greenrobot.eventbus.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HonorImpl implements IHonorPresenter {

    private CounselorService counselorService;
    private BaseActivity context;

    public HonorImpl(BaseActivity context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }
    @Override
    public void getShareSRHonor() {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getShareSRHonor(token, new Callback<ResponseData<ShareSRHonorModel>>() {

            @Override
            public void success(ResponseData<ShareSRHonorModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                ShareSRHonorModel honorInfo = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
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
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getSPHonor() {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getSPHonor(token, new Callback<ResponseData<HonorInfoModel>>() {

            @Override
            public void success(ResponseData<HonorInfoModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                HonorInfoModel honorInfo = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
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
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getSRHonor() {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getSRHonor(token, new Callback<ResponseData<HonorInfoModel>>() {

            @Override
            public void success(ResponseData<HonorInfoModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                HonorInfoModel honorInfo = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
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
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getUserHonors() {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getUserHonors(token, new Callback<ResponseData<UserHonorModel>>() {

            @Override
            public void success(ResponseData<UserHonorModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
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

    @Override
    public void getSPDangQiHonor() {
        counselorService.getSPCurrentProgressServer(UserInfoModel.getInstance().getToken(),
                new Callback<ResponseData<DangQiShare>>() {
                    @Override
                    public void success(ResponseData<DangQiShare> data, Response response) {
                        context.dialogDissmiss();
                        switch (data.getStatus()) {
                            case 200:
                                EventBus.getDefault().post(data.getData());
                                break;
                            default:
                                Util.toastMsg(data.getMsg());
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
