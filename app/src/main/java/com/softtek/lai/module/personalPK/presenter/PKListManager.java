package com.softtek.lai.module.personalPK.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.module.personalPK.net.PKService;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;
import com.softtek.lai.module.personalPK.view.PKListActivity;
import com.softtek.lai.module.personalPK.view.PKListMineActivity;
import com.softtek.lai.module.personalPK.view.SearchActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class PKListManager {

    private PKService service;
    private String token;

    public PKListManager() {
        this.service = ZillaApi.NormalRestAdapter.create(PKService.class);
        token= UserInfoModel.getInstance().getToken();
    }

    public void getPKList(final PKListActivity activity, int pageIndex){
        service.getPKList(
                token,
                pageIndex,
                new RequestCallback<ResponseData<List<PKListModel>>>() {
            @Override
            public void success(ResponseData<List<PKListModel>> listResponseData, Response response) {
                activity.getModels(listResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                activity.getModels(null);
                super.failure(error);

            }
        });
    }

    public void getPKListByPersonal(final PKListMineActivity activity, int pageIndex){
        service.getPKListForPersonal(
                token,
                pageIndex,
                Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()),
                new RequestCallback<ResponseData<List<PKListModel>>>() {
                    @Override
                    public void success(ResponseData<List<PKListModel>> listResponseData, Response response) {
                        activity.getModels(listResponseData);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        activity.getModels(null);
                        super.failure(error);

                    }
                });
    }

    public void getPKDetail(final PKDetailActivity activity,long pkId){
        service.getPKDetail(
                token,
                pkId,
                new RequestCallback<ResponseData<PKDetailMold>>() {
                    @Override
                    public void success(ResponseData<PKDetailMold> pkDetailMoldResponseData, Response response) {
                        activity.getPKDetail(pkDetailMoldResponseData.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        activity.getPKDetail(null);
                        super.failure(error);
                    }
                }
        );
    }

    public void searchPKObj(final SearchActivity activity, String key){
        service.searchPKObj(
                token,
                key,
                new RequestCallback<ResponseData<List<PKObjModel>>>() {
                    @Override
                    public void success(ResponseData<List<PKObjModel>> pkObjModelResponseData, Response response) {
                        activity.loadData(pkObjModelResponseData.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        activity.loadData(null);
                        super.failure(error);
                    }
                }
        );

    }
}
