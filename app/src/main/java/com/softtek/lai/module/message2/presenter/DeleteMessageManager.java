package com.softtek.lai.module.message2.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.NoticeMsgModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.model.UnreadMsgModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 */
public class DeleteMessageManager {

    private Message2Service service;
    private DeleteMsgCallBack deleteMsgCallBack;

    public DeleteMessageManager(DeleteMsgCallBack deleteMsgCallBack) {
        this.deleteMsgCallBack = deleteMsgCallBack;
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
    }

    public void DodeleteOneMsg(String msgtype, String msgid) {
        service.deleteOneMsg(UserInfoModel.getInstance().getToken(), msgtype, msgid, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (deleteMsgCallBack != null) {
                    switch (status) {
                        case 200:
                            deleteMsgCallBack.deleteMsg("true");
                            break;
                        default:
                            deleteMsgCallBack.deleteMsg("false");
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (deleteMsgCallBack != null) {
                    deleteMsgCallBack.deleteMsg("false");
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface DeleteMsgCallBack {

        void deleteMsg(String type);
    }

}
