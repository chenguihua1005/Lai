package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePersent;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.model.CollectModel;
import com.softtek.lai.module.laiClassroom.net.CollectService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class CollectPresenter extends BasePersent<CollectPresenter.getcollect> {
    CollectService service;
    CollectModel collectModel;

    public CollectPresenter(getcollect baseView) {
        super(baseView);
    }

    public void getcollectarticle(String token, long userId, int pageindex, int pagesize) {
        service = ZillaApi.NormalRestAdapter.create(CollectService.class);
        service.getBookmarkArticle(token, userId, pageindex, pagesize, new Callback<ResponseData<CollectModel>>() {
            @Override
            public void success(ResponseData<CollectModel> Data, Response response) {
                int status = Data.getStatus();
                if (200 == status) {
                    if (Data.getData() != null) {
                        collectModel = Data.getData();
                        if (getView() != null) {
                            getView().getcollects(collectModel);
                        }
                    }

                }else {
                    Util.toastMsg(Data.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public interface getcollect extends BaseView {
        void getcollects(CollectModel collectModel);
    }
}
