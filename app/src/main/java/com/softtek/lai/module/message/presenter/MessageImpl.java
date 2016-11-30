/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message.model.CheckClassEvent;
import com.softtek.lai.module.message.net.MessageService;

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
    public void acceptInviterToClass(String inviters, final String classId,String acceptType) {
        String token = UserInfoModel.getInstance().getToken();
        messageService.acceptInviterToClass(token, inviters, classId, acceptType, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
                        System.out.println("userrole-------------:" + userrole);
                        if (String.valueOf(Constants.INC).equals(userrole)) {
                            context.startActivity(new Intent(context, LoginActivity.class));

                        }else {
                            Intent intent = context.getIntent();
                            //把返回数据存入Intent
                            intent.putExtra("type", "xzs");
                            //设置返回数据
                            context.setResult(context.RESULT_OK, intent);
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
                            intent.putExtra("type", 0);
                            context.startActivity(intent);
                        } else {
                            Intent intent = context.getIntent();
                            //把返回数据存入Intent
                            intent.putExtra("type", "xzs");
                            //设置返回数据
                            context.setResult(context.RESULT_OK, intent);
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

}
