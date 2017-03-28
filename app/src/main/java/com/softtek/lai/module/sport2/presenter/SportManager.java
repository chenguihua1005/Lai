package com.softtek.lai.module.sport2.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sport2.model.SportMineModel;
import com.softtek.lai.module.sport2.net.SportService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 10/17/2016.
 */
public class SportManager {

    private SportManagerCallback callback;
    private SportService service;

    public SportManager(SportManagerCallback callback) {
        this.callback = callback;
        service= ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public void getMineData(long accountId,String stepAndDate){
        service.getSportIndex(UserInfoModel.getInstance().getToken(), accountId, stepAndDate,
                new RequestCallback<ResponseData<SportMineModel>>() {
                    @Override
                    public void success(ResponseData<SportMineModel> sportMineModelResponseData, Response response) {
                        if(sportMineModelResponseData.getStatus()==200){
                            if(callback!=null){
                                callback.getResult(sportMineModelResponseData.getData());
                            }
                        }else {
                            Util.toastMsg(sportMineModelResponseData.getMsg());
                            if(callback!=null){
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

    public void setCallback(SportManagerCallback callback) {
        this.callback = callback;
    }

    public interface SportManagerCallback{
        void getResult(SportMineModel result);
    }
}
