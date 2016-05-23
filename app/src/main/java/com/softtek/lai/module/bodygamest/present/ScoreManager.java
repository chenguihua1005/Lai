package com.softtek.lai.module.bodygamest.present;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.model.ActDetailModel;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.act.net.ActService;
import com.softtek.lai.module.bodygamest.model.HistoryClassModel;
import com.softtek.lai.module.bodygamest.net.ScoresService;
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
public class ScoreManager {

    private String token;
    private ScoresService service;
    private GetClassListCallBack getClassListCallBack;

    public ScoreManager(GetClassListCallBack getClassListCallBack) {
        this.getClassListCallBack = getClassListCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(ScoresService.class);
    }

    public void getHistoryClassList(String accountid) {
        service.getHistoryClassList(token,accountid, new RequestCallback<ResponseData<List<HistoryClassModel>>>() {
            @Override
            public void success(ResponseData<List<HistoryClassModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getClassListCallBack.getclassList("true", listResponseData.getData());
                        break;
                    case 100:
                        getClassListCallBack.getclassList("false", new ArrayList<HistoryClassModel>());
                        break;
                    default:
                        getClassListCallBack.getclassList("false", new ArrayList<HistoryClassModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getClassListCallBack != null) {
                    getClassListCallBack.getclassList("false", new ArrayList<HistoryClassModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface GetClassListCallBack {

        void getclassList(String type, List<HistoryClassModel> list);
    }

}
