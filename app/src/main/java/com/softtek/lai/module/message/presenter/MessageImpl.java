/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.GameAdapter;
import com.softtek.lai.module.counselor.model.MarchInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.presenter.IGamePresenter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.message.model.MessageModel;
import com.softtek.lai.module.message.net.MessageService;
import com.softtek.lai.module.message.view.MessageActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessageImpl implements IMessagePresenter {

    private MessageService messageService;
    private Context context;

    public MessageImpl(Context context) {
        this.context = context;
        messageService = ZillaApi.NormalRestAdapter.create(MessageService.class);
    }


    @Override
    public void getMsgList() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        messageService.getMsgList(token, new Callback<ResponseData<MessageModel>>() {
            @Override
            public void success(ResponseData<MessageModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                MessageModel messageModel = listResponseData.getData();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(messageModel);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("");
            }
        });
    }

    @Override
    public void acceptInviterToClass(String inviters, String classId, final String acceptType, final MessageDetailInfo messageDetailInfo) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        messageService.acceptInviterToClass(token, inviters, classId, acceptType, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if ("0".equals(acceptType)) {
                            String userrole = UserInfoModel.getInstance().getUser().getUserrole();
                            if (String.valueOf(Constants.INC).equals(userrole)) {
                                context.startActivity(new Intent(context, LoginActivity.class));

                            } else {
                                context.startActivity(new Intent(context, MessageActivity.class));

                            }
                        } else {
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("messageDetailInfo", messageDetailInfo);
                            context.startActivity(intent);
                        }
                        ((AppCompatActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("");
            }
        });
    }
}
