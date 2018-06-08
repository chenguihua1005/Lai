package com.softtek.lai.module.ranking.persenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.ranking.model.OrderInfo;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.module.ranking.net.RankingService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 10/18/2016.
 */
public class RankManager {

    private RankManagerCallback callback;
    private RankingService service;

    public RankManager(RankManagerCallback callback) {
        this.callback = callback;
        service= ZillaApi.NormalRestAdapter.create(RankingService.class);
    }
    //获取日排名
    public void getDayRank(int RGIdType,int pageIndex){
        service.getCurrentDateOrder(UserInfoModel.getInstance().getToken(), RGIdType, pageIndex,
                new RequestCallback<ResponseData<RankModel>>() {
                    @Override
                    public void success(ResponseData<RankModel> responseData, Response response) {
                        if(callback!=null){
                            if(responseData.getStatus()==200){
                                callback.getResult(responseData.getData());
                            }else {
                                callback.getResult(null);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if(callback!=null){
                            callback.getResult(null);
                        }
                    }
                });
    }

    //获取周排名
    public void getWeekRank(int RGIdType,int pageIndex){
        service.getCurrentWeekOrder(UserInfoModel.getInstance().getToken(), RGIdType, pageIndex,
                new RequestCallback<ResponseData<RankModel>>() {
                    @Override
                    public void success(ResponseData<RankModel> responseData, Response response) {
                        if(callback!=null){
                            if(responseData.getStatus()==200){
                                callback.getResult(responseData.getData());
                            }else {
                                callback.getResult(null);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if(callback!=null){
                            callback.getResult(null);
                        }
                    }
                });
    }

    //获取日排名
    public void getDayOrder(int rgIdType, RequestCallback<ResponseData<OrderInfo>> responseDataRequestCallback){
        service.getDayOrder(UserInfoModel.getInstance().getToken(),rgIdType,responseDataRequestCallback);

    }

    //获取周排名
    public void getWeekOrder(int rgIdType, RequestCallback<ResponseData<OrderInfo>> responseDataRequestCallback){
        service.getWeekOrder(UserInfoModel.getInstance().getToken(),rgIdType,responseDataRequestCallback);

    }


    public void setCallback(RankManagerCallback callback) {
        this.callback = callback;
    }

    public interface RankManagerCallback{
        void getResult(RankModel result);
    }
}
