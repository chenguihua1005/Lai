package com.softtek.lai.module.tips.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.net.TipService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/27/2016.
 */
public class AskHealthyManager {

    private AskHealthyManagerCallback cb;
    private TipService tipService;

    public AskHealthyManager(AskHealthyManagerCallback cb) {
        this.cb = cb;
        tipService= ZillaApi.NormalRestAdapter.create(TipService.class);
    }

    public void getAskHealthyList(int pageIndex){
        tipService.getTipList("0", pageIndex,new RequestCallback<ResponseData<AskHealthyResponseModel>>() {
            @Override
            public void success(ResponseData<AskHealthyResponseModel> askHealthyModelResponseData, Response response) {
                if(cb!=null)cb.getHealthyList(askHealthyModelResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                if(cb!=null)cb.getHealthyList(null);
                super.failure(error);
            }
        });
    }


    public interface AskHealthyManagerCallback{
        void getHealthyList(AskHealthyResponseModel models);
    }
}
