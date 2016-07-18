/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRActivity;
import com.softtek.lai.module.bodygamezj.view.BodygameSRActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message.model.CheckClassEvent;
import com.softtek.lai.module.message.model.CheckMobileEvent;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.message.model.MessageModel;
import com.softtek.lai.module.message.net.MessageService;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.message.view.MessageActivity;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessageImpl implements IMessagePresenter {

    private MessageService messageService;
    private BaseActivity context;
    private Context contexts;

    public MessageImpl(BaseActivity context) {
        this.context = context;
        messageService = ZillaApi.NormalRestAdapter.create(MessageService.class);
    }

    public MessageImpl(Context contexts) {
        this.contexts = contexts;
        messageService = ZillaApi.NormalRestAdapter.create(MessageService.class);
    }


    @Override
    public void accIsJoinClass(String accountid,String classid) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.accIsJoinClass(token, accountid, classid,new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new CheckClassEvent(true));
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void upReadTime(String msgtype, String recevieid, String senderid, String classid) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.upReadTime(token, msgtype, recevieid, senderid, classid, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData);
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);

            }
        });
    }

    @Override
    public void getMessageRead(final ImageView img_red) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.getMessageRead(token, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        img_red.setVisibility(View.VISIBLE);
                        break;
                    case 100:
                        img_red.setVisibility(View.GONE);
                        break;
                    default:
                        img_red.setVisibility(View.GONE);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void delNoticeOrMeasureMsg(String messageId,String type) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.delNoticeOrMeasureMsg(token, messageId, type, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData);
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void getMsgList(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.getMsgList(token, accountid, new Callback<ResponseData<MessageModel>>() {
            @Override
            public void success(ResponseData<MessageModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                context.dialogDissmiss();
                int status = listResponseData.getStatus();
                MessageModel messageModel = listResponseData.getData();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(messageModel);
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void acceptInviterToClass(String inviters, String classId, final String acceptType, final MessageDetailInfo messageDetailInfo) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.acceptInviterToClass(token, inviters, classId, acceptType, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        if ("0".equals(acceptType)) {
                            String userrole = UserInfoModel.getInstance().getUser().getUserrole();
                            System.out.println("userrole-------------:" + userrole);
                            if (String.valueOf(Constants.INC).equals(userrole)) {
                                context.startActivity(new Intent(context, LoginActivity.class));

                            } else {
                                Intent intent = new Intent(context, MessageActivity.class);
                                context.startActivity(intent);

                            }
                        } else {
                            Intent intent = new Intent(context, JoinGameDetailActivity.class);
                            intent.putExtra("messageDetailInfo", messageDetailInfo);
                            intent.putExtra("type", "1");
                            context.startActivity(intent);
                        }
                        context.finish();
                        break;
                    case 100:
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void acceptInviter(String inviters, String classId, final String acceptType) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.acceptInviter(token, inviters, classId, acceptType, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        if ("1".equals(acceptType)) {
                            Intent intent = new Intent(context, BodyGameSRActivity.class);
                            intent.putExtra("type", "0");
                            context.startActivity(intent);
                            context.finish();
                        } else {
                            Intent intent = new Intent(context, MessageActivity.class);
                            context.startActivity(intent);
                            context.finish();
                        }
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void phoneIsExist(String mobile, final ProgressDialog dialog, final int id) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.phoneIsExist(token, mobile, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                dialog.dismiss();
                switch (status) {
                    case 200:
                        CheckMobileEvent checkMobileEvent=new CheckMobileEvent(id,true);
                        EventBus.getDefault().post(checkMobileEvent);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                    case 100:
                        CheckMobileEvent event=new CheckMobileEvent(id,false);
                        EventBus.getDefault().post(event);
                        break;
                    default:
                        CheckMobileEvent events=new CheckMobileEvent(id,false);
                        EventBus.getDefault().post(events);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                CheckMobileEvent events=new CheckMobileEvent(id,false);
                EventBus.getDefault().post(events);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
}
