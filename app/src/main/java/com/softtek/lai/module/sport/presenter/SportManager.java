package com.softtek.lai.module.sport.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.model.ActDetailModel;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.act.net.ActService;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.net.SportService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 */
public class SportManager {

    private String token;
    private SportService service;
    private GetMovementListCallBack getMovementListCallBack;

    public SportManager(GetMovementListCallBack getMovementListCallBack) {
        this.getMovementListCallBack = getMovementListCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public void getMovementList() {
        service.getMovementList(token, new RequestCallback<ResponseData<List<HistorySportModel>>>() {
            @Override
            public void success(ResponseData<List<HistorySportModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("true", listResponseData.getData());
                        }

                        break;
                    case 100:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("false", new ArrayList<HistorySportModel>());
                        }

                        break;
                    default:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("false", new ArrayList<HistorySportModel>());
                            Util.toastMsg(listResponseData.getMsg());
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getMovementListCallBack != null) {
                    getMovementListCallBack.getMovementList("false", new ArrayList<HistorySportModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface GetMovementListCallBack {

        void getMovementList(String type, List<HistorySportModel> list);
    }

}
