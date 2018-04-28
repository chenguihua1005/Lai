/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.eventModel.ActivityEvent;
import com.softtek.lai.module.home.eventModel.HomeEvent;
import com.softtek.lai.module.home.eventModel.ProductEvent;
import com.softtek.lai.module.home.eventModel.SaleEvent;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.net.HomeService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public class HomeInfoImpl implements IHomeInfoPresenter {

    private HomeService homeService;

    public HomeInfoImpl(Context context) {
        homeService = ZillaApi.NormalRestAdapter.create(HomeService.class);
    }

    @Override
    public void getHomeInfoData(final SwipeRefreshLayout pull) {
        homeService.doLoadHomeData(new Callback<ResponseData<List<HomeInfoModel>>>() {
            @Override
            public void success(ResponseData<List<HomeInfoModel>> data, Response response) {
                pull.setRefreshing(false);
                int status = data.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new HomeEvent(data.getData()));
                        break;
                    default:
                        Util.toastMsg(data.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                pull.setRefreshing(false);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getContentByPage( final int page, final int img_type) {
        homeService.getActivityByPage(img_type, page, new Callback<ResponseData<List<HomeInfoModel>>>() {
            @Override
            public void success(ResponseData<List<HomeInfoModel>> homeInfoResponseData, Response response) {
                switch (homeInfoResponseData.getStatus()) {
                    case 200:
                        if (img_type == Constants.ACTIVITY_RECORD) {
                            EventBus.getDefault().postSticky(new ActivityEvent(homeInfoResponseData.getData()));
                        } else if (img_type == Constants.PRODUCT_INFO) {
                            EventBus.getDefault().postSticky(new ProductEvent(homeInfoResponseData.getData()));
                        } else if (img_type == Constants.SALE_INFO) {
                            EventBus.getDefault().postSticky(new SaleEvent(homeInfoResponseData.getData()));
                        } else if (img_type == 0) {
                            EventBus.getDefault().postSticky(new HomeEvent(homeInfoResponseData.getData()));
                        }
                        break;
                    default:
                        if (img_type == Constants.ACTIVITY_RECORD) {
                            EventBus.getDefault().post(new ActivityEvent( null));
                        } else if (img_type == Constants.PRODUCT_INFO) {
                            EventBus.getDefault().post(new ProductEvent( null));
                        } else if (img_type == Constants.SALE_INFO) {
                            EventBus.getDefault().post(new SaleEvent( null));
                        } else if (img_type == 0) {
                            EventBus.getDefault().post(new HomeEvent(null));
                        }
                        Util.toastMsg(homeInfoResponseData.getMsg());
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
