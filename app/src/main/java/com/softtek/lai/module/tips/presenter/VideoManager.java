package com.softtek.lai.module.tips.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.net.TipService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/27/2016.
 */
public class VideoManager {

    private VideoManagerCallback cb;
    private TipService tipService;

    public VideoManager(VideoManagerCallback cb) {
        this.cb = cb;
        tipService= ZillaApi.NormalRestAdapter.create(TipService.class);
    }

    public void getVideoList(int pageIndex){
        tipService.getTipList("1",pageIndex, new RequestCallback<ResponseData<AskHealthyResponseModel>>() {
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


    public interface VideoManagerCallback{
        void getHealthyList(AskHealthyResponseModel models);
    }
}
