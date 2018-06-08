package com.softtek.lai.module.personalPK.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.model.PKForm;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.module.personalPK.model.PKObjRequest;
import com.softtek.lai.module.personalPK.model.SavePK;
import com.softtek.lai.module.personalPK.net.PKService;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;
import com.softtek.lai.module.personalPK.view.PKListMineActivity;
import com.softtek.lai.module.personalPK.view.SearchActivity;
import com.softtek.lai.module.personalPK.view.SelectOpponentActivity;
import com.softtek.lai.module.sport2.view.PKListFragment;
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

    public void getPKList(final PKListFragment activity, int pageIndex){
        service.getPKList(
                token,
                pageIndex,
                new RequestCallback<ResponseData<List<PKListModel>>>() {
            @Override
            public void success(ResponseData<List<PKListModel>> listResponseData, Response response) {
                if(activity!=null)
                    activity.getModels(listResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                if(activity!=null)
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
                        if(activity!=null)
                            activity.getModels(listResponseData);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(activity!=null)
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
                        if(pkDetailMoldResponseData.getStatus()==200){
                            if(activity!=null)
                                activity.getPKDetail(pkDetailMoldResponseData.getData(),200);
                        }else if(pkDetailMoldResponseData.getStatus()==100){
                            if(activity!=null)
                                activity.getPKDetail(null,100);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(activity!=null)
                            activity.getPKDetail(null,-1);
                        super.failure(error);
                    }
                }
        );
    }

    public void searchPKObj(final SearchActivity activity, String key,int pageIndex){
        service.searchPKObj(
                token,
                key,
                pageIndex,
                new RequestCallback<ResponseData<PKObjRequest>>() {
                    @Override
                    public void success(ResponseData<PKObjRequest> pkObjModelResponseData, Response response) {
                        if(activity!=null)
                            activity.loadData(pkObjModelResponseData.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(activity!=null)
                            activity.loadData(null);
                        super.failure(error);
                    }
                }
        );

    }

    public void getCurrentPaoTuanMember(final SelectOpponentActivity activity){
        service.getCurrentPaoTuanMember(
                token,
                new RequestCallback<ResponseData<List<PKObjModel>>>() {
                    @Override
                    public void success(ResponseData<List<PKObjModel>> listResponseData, Response response) {
                        Log.i(listResponseData.toString());
                        if(activity!=null)
                            activity.loadData(listResponseData.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(activity!=null)
                            activity.loadData(null);
                        super.failure(error);
                    }
                }
        );

    }

    public void savePK( PKForm form,RequestCallback<ResponseData<SavePK>> callback){
        service.savePK(
                token,
                form,
                callback
        );
    }

    public void promiseOrRefuse(long pkId,int status,RequestCallback<ResponseData> callback){
        service.promiseOrRefuse(
                token,
                pkId,
                status,
                callback
        );
    }

    public void doZan(long pkId,int chall,RequestCallback<ResponseData> callback){
        service.doZan(token,pkId,chall,callback);
    }

    public void cancelPK(long pkId,RequestCallback<ResponseData> callback){
        service.cancelPK(token,pkId,callback);
    }

    public void resetPK(long pkId,RequestCallback<ResponseData> callback){
        service.resetPK(token,pkId,callback);
    }
}
