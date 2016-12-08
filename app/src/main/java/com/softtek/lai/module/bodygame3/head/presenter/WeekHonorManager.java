package com.softtek.lai.module.bodygame3.head.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.retest.model.LaichModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;

/**
 * Created by curry.zhang on 12/7/2016.
 */

public class WeekHonorManager {

    private WeekHonnorCallback callback;
    private final HeadService service;


    public WeekHonorManager(WeekHonnorCallback callback){
        this.callback=callback;
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
    }

    public void getWeekHonnorInfo(Long UID,String classId,String byWhichRatio,String sortTimeType,int whichTime,Boolean isFirst ){
        String token= UserInfoModel.getInstance().getToken();
        service.doGetHonorRoll(token,UID,classId,byWhichRatio,sortTimeType,whichTime,isFirst,
                new Callback<ResponseData<HonorRankModel>>(){
                    @Override
                    public void success(ResponseData<HonorRankModel> honorRankModelResponseData, Response response) {
                        int status=honorRankModelResponseData.getStatus();
                        switch (status)
                        {
                            case 200:
                                HonorRankModel honorRankModel= honorRankModelResponseData.getData();
                                if(callback!=null)
                                    callback.getModel(honorRankModel);
                                break;
                            default:
                                if(callback!=null)
                                    callback.getModel(null);
                                break;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(callback!=null)
                            callback.getModel(null);
                        ZillaApi.dealNetError(error);
                    }
                });


    }

    public interface WeekHonnorCallback{
        void getModel(HonorRankModel model);
    }
}
