package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.model.CollectModel;
import com.softtek.lai.module.laiClassroom.net.CollectService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by Lenovo-G400 on 2017/3/11.
 */

public class HistoryPresenter extends BasePresenter<HistoryPresenter.getHistorydata> {
    CollectService service;
    CollectModel collectModel;

    public HistoryPresenter(getHistorydata baseView) {
        super(baseView);
    }
    public void getVisitHistory(String token, long userid, int pageindex, int pagesize, final int from){
        service= ZillaApi.NormalRestAdapter.create(CollectService.class);
        service.getVisitArticleHistory(token, userid, pageindex, pagesize, new Callback<ResponseData<CollectModel>>() {
            @Override
            public void success(ResponseData<CollectModel> Data, Response response) {
                int status=Data.getStatus();
                if(200==status){
                    if(Data.getData()!=null){
                        collectModel=Data.getData();
                        if(getView()!=null){
                            getView().gethistorydata(collectModel,from);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()!=null){
                    getView().dialogDissmiss();
                }
            }
        });
    }

    public interface getHistorydata extends BaseView{
          void gethistorydata(CollectModel collectModel,int from);
    }
}
