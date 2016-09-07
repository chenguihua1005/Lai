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
public class MessageMainManager {

    private Message2Service service;
    private GetUnreadMsgCallBack getUnreadMsgCallBack;
    private GetNoticeFCMsgCallBack getNoticeFCMsgCallBack;
    private OperatorCallBack operatorCallBack;

    public MessageMainManager(OperatorCallBack operatorCallBack) {
        this.operatorCallBack = operatorCallBack;
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
    }

    public MessageMainManager(GetUnreadMsgCallBack getUnreadMsgCallBack) {
        this.getUnreadMsgCallBack = getUnreadMsgCallBack;
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
    }

    public MessageMainManager(GetNoticeFCMsgCallBack getNoticeFCMsgCallBack) {
        this.getNoticeFCMsgCallBack = getNoticeFCMsgCallBack;
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
    }
    public void doRefuseRemoveSR(String msgid) {
        service.refuseRemoveSR(UserInfoModel.getInstance().getToken(), msgid, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (operatorCallBack != null) {
                    switch (status) {
                        case 200:
                            operatorCallBack.refuseRemoveSR("true");
                            break;
                        case 500:
                            operatorCallBack.refuseRemoveSR("false");
                            break;
                        default:
                            operatorCallBack.refuseRemoveSR("false");
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (operatorCallBack != null) {
                    operatorCallBack.refuseRemoveSR("false");
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void doUpReadTime(String msgtype, String msgid) {
        service.upRedTime(UserInfoModel.getInstance().getToken(), msgtype, msgid, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (getNoticeFCMsgCallBack != null) {
                    switch (status) {
                        case 200:
                            getNoticeFCMsgCallBack.upRedTime("true");
                            break;
                        case 500:
                            getNoticeFCMsgCallBack.upRedTime("false");
                            break;
                        default:
                            getNoticeFCMsgCallBack.upRedTime("false");
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getNoticeFCMsgCallBack != null) {
                    getNoticeFCMsgCallBack.upRedTime("false");
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void doGetMeasureMsg(String accountid) {
        service.getMeasureMsg(UserInfoModel.getInstance().getToken(), accountid, new RequestCallback<ResponseData<List<NoticeMsgModel>>>() {
            @Override
            public void success(ResponseData<List<NoticeMsgModel>> responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (getNoticeFCMsgCallBack != null) {
                    switch (status) {
                        case 200:
                            getNoticeFCMsgCallBack.getMeasureMsg("true", responseData.getData());
                            break;
                        case 100:
                            getNoticeFCMsgCallBack.getMeasureMsg("false", null);
                            break;
                        default:
                            getNoticeFCMsgCallBack.getMeasureMsg("false", null);
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getNoticeFCMsgCallBack != null) {
                    getNoticeFCMsgCallBack.getMeasureMsg("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void doGetNoticeMsg(String accountid) {
        service.getNoticeMsg(UserInfoModel.getInstance().getToken(), accountid, new RequestCallback<ResponseData<List<NoticeMsgModel>>>() {
            @Override
            public void success(ResponseData<List<NoticeMsgModel>> responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (getNoticeFCMsgCallBack != null) {
                    switch (status) {
                        case 200:
                            getNoticeFCMsgCallBack.getNoticeMsg("true", responseData.getData());
                            break;
                        case 100:
                            getNoticeFCMsgCallBack.getNoticeMsg("false", null);
                            break;
                        default:
                            getNoticeFCMsgCallBack.getNoticeMsg("false", null);
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getNoticeFCMsgCallBack != null) {
                    getNoticeFCMsgCallBack.getNoticeMsg("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void doGetOperateMsg(String accountid) {
        service.getOperateMsg(UserInfoModel.getInstance().getToken(), accountid, new RequestCallback<ResponseData<List<OperateMsgModel>>>() {
            @Override
            public void success(ResponseData<List<OperateMsgModel>> responseData, Response response) {
                Log.e("jarvis", responseData.toString());
                int status = responseData.getStatus();
                if (getNoticeFCMsgCallBack != null) {
                    switch (status) {
                        case 200:
                            getNoticeFCMsgCallBack.getOperateMsg("true", responseData.getData());
                            break;
                        case 100:
                            getNoticeFCMsgCallBack.getOperateMsg("false", null);
                            break;
                        default:
                            getNoticeFCMsgCallBack.getOperateMsg("false", null);
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getNoticeFCMsgCallBack != null) {
                    getNoticeFCMsgCallBack.getOperateMsg("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
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

    public interface OperatorCallBack {

        void refuseRemoveSR(String type);
    }

    public interface GetNoticeFCMsgCallBack {
        void getNoticeMsg(String type, List<NoticeMsgModel> list);

        void getMeasureMsg(String type, List<NoticeMsgModel> list);

        void getOperateMsg(String type, List<OperateMsgModel> list);

        void upRedTime(String type);
    }

}
