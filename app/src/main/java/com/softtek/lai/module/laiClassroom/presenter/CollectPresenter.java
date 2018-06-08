package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.model.CollectModel;
import com.softtek.lai.module.laiClassroom.net.CollectService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class CollectPresenter extends BasePresenter<CollectPresenter.getcollect> {
    CollectService service;
    CollectModel collectModel;

    public CollectPresenter(getcollect baseView) {
        super(baseView);
    }

    public void getcollectarticle(String token, long userId, int pageindex, int pagesize, final int from) {
        service = ZillaApi.NormalRestAdapter.create(CollectService.class);
        service.getBookmarkArticle(token, userId, pageindex, pagesize, new RequestCallback<ResponseData<CollectModel>>() {
            @Override
            public void success(ResponseData<CollectModel> Data, Response response) {
                int status = Data.getStatus();
                if (200 == status) {
                    if (Data.getData() != null) {
                        collectModel = Data.getData();
                        if (getView() != null) {
                            getView().getcollects(collectModel,from);
                        }
                    }

                } else {
                    Util.toastMsg(Data.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()!=null){
                    getView().dialogDissmiss();
                }
                super.failure(error);
            }
        });

    }

    public interface getcollect extends BaseView {
        void getcollects(CollectModel collectModel,int from);
    }
}
