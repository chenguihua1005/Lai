package com.softtek.lai.module.sport.presenter;

import android.content.Context;
import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.adapter.ApplyAssistantAdapter;
import com.softtek.lai.module.counselor.model.ApplyAssistantModel;
import com.softtek.lai.module.lossweightstory.model.LogList;
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.sport.net.SportGroupService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 */
public class SportGroupManager {

    private String token;
    private SportGroupService service;
    private IsJoinRunGroupManagerCallBack isJoinRunGroupManagerCallBack;

    public SportGroupManager(IsJoinRunGroupManagerCallBack isJoinRunGroupManagerCallBack) {
        this.isJoinRunGroupManagerCallBack = isJoinRunGroupManagerCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

    public void isJoinRunGroup(String accoundId) {
        service.isJoinRunGroup(token, accoundId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(true);
                        break;
                    default:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(isJoinRunGroupManagerCallBack!=null){
                    isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface IsJoinRunGroupManagerCallBack {

        void isJoinRunGroup(boolean b);
    }
}
