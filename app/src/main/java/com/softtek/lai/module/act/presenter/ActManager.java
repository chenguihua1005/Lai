package com.softtek.lai.module.act.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.model.ActDetailModel;
import com.softtek.lai.module.act.model.ActZKModel;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.act.net.ActService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 */
public class ActManager {

    private String token;
    private ActService service;
    private GetactivityListCallBack getactivityListCallBack;
    private GetActDetailsCallBack getActDetailsCallBack;
    private GetActivitySituationCallBack getActivitySituationCallBack;

    public ActManager(GetActivitySituationCallBack getActivitySituationCallBack) {
        this.getActivitySituationCallBack = getActivitySituationCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(ActService.class);
    }
    public ActManager(GetActDetailsCallBack getActDetailsCallBack) {
        this.getActDetailsCallBack = getActDetailsCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(ActService.class);
    }

    public ActManager(GetactivityListCallBack getactivityListCallBack) {
        this.getactivityListCallBack = getactivityListCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(ActService.class);
    }

    public void getActivitySituation(String pageIndex, String accountid,String activityid) {
        service.getActivitySituation(token, pageIndex, accountid, activityid,new RequestCallback<ResponseData<ActZKModel>>() {
            @Override
            public void success(ResponseData<ActZKModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getActivitySituationCallBack.getActivitySituation("true", listResponseData.getData());
                        break;
                    case 100:
                        getActivitySituationCallBack.getActivitySituation("false", null);
                        break;
                    default:
                        getActivitySituationCallBack.getActivitySituation("false", null);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivitySituationCallBack != null) {
                    getActivitySituationCallBack.getActivitySituation("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void activityList(String pageIndex, String accountid) {
        service.activityList(token, pageIndex, accountid, new RequestCallback<ResponseData<ActivityModel>>() {
            @Override
            public void success(ResponseData<ActivityModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:

                        getactivityListCallBack.activityList("true", listResponseData.getData());
                        break;
                    case 100:
                        getactivityListCallBack.activityList("false", null);
                        break;
                    default:
                        getactivityListCallBack.activityList("false", null);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getactivityListCallBack != null) {
                    getactivityListCallBack.activityList("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getActDetails(String activityid) {
        service.getActDetails(token, activityid, new RequestCallback<ResponseData<ActDetailModel>>() {
            @Override
            public void success(ResponseData<ActDetailModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getActDetailsCallBack.getActDetails("true", listResponseData.getData());
                        break;
                    case 100:
                        getActDetailsCallBack.getActDetails("false", null);
                        break;
                    default:
                        getActDetailsCallBack.getActDetails("false", null);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActDetailsCallBack != null) {
                    getActDetailsCallBack.getActDetails("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface GetActDetailsCallBack {

        void getActDetails(String type, ActDetailModel model);
    }

    public interface GetactivityListCallBack {

        void activityList(String type, ActivityModel model);
    }

    public interface GetActivitySituationCallBack {

        void getActivitySituation(String type, ActZKModel model);
    }

}
