package com.softtek.lai.module.message2.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.UnreadMsgModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 * 
 */
public class MessageMainManager {

    private Message2Service service;
    private GetUnreadMsgCallBack getUnreadMsgCallBack;


    public MessageMainManager(GetUnreadMsgCallBack getUnreadMsgCallBack) {
        this.getUnreadMsgCallBack = getUnreadMsgCallBack;
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
    }

    public void doGetUnreadMsg(String accountid) {
        service.getUnreadMsg(UserInfoModel.getInstance().getToken(), accountid, new RequestCallback<ResponseData<UnreadMsgModel>>() {
            @Override
            public void success(ResponseData<UnreadMsgModel> responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (getUnreadMsgCallBack != null) {
                    switch (status) {
                        case 200:
                            getUnreadMsgCallBack.getUnreadMsg("true", responseData.getData());
                            break;
                        case 100:
                            getUnreadMsgCallBack.getUnreadMsg("false", null);
                            break;
                        default:
                            getUnreadMsgCallBack.getUnreadMsg("false", null);
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getUnreadMsgCallBack != null) {
                    getUnreadMsgCallBack.getUnreadMsg("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface GetUnreadMsgCallBack {

        void getUnreadMsg(String type, UnreadMsgModel unreadMsgModel);
    }

}
