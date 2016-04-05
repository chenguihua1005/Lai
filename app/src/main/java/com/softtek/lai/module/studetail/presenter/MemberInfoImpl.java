/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.studetail.eventModel.LineChartEvent;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;
import com.softtek.lai.module.studetail.net.MemberInfoService;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class MemberInfoImpl implements IMemberInfopresenter {

    private MemberInfoService service;
    private UserInfoModel infoModel;
    private Context context;

    public MemberInfoImpl(Context context) {
        service = ZillaApi.NormalRestAdapter.create(MemberInfoService.class);
        this.context = context;
        infoModel=UserInfoModel.getInstance();
    }

    @Override
    public void getMemberinfo(String classId, String userId, final ProgressDialog progressDialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getmemberInfo(token, userId, classId, new Callback<ResponseData<MemberModel>>() {
            @Override
            public void success(ResponseData<MemberModel> memberResponseData, Response response) {
                Log.i("返回值>>>" + memberResponseData.toString());
                Log.i("请求url>>>" + response.getUrl());
                progressDialog.dismiss();
                int status = memberResponseData.getStatus();
                if (status == 200) {
                    EventBus.getDefault().post(memberResponseData.getData());
                } else {
                    Util.toastMsg(memberResponseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Util.toastMsg(R.string.neterror);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getLossWeightChatData(String userId,String classId) {
        String token=infoModel.getToken();
        System.out.println("token值是==="+token);
        service.getLineChartData(token, userId, classId, new Callback<ResponseData<List<StudentLinChartInfoModel>>>() {
            @Override
            public void success(ResponseData<List<StudentLinChartInfoModel>> listResponseData, Response response) {
                if(listResponseData.getStatus()==200){
                    EventBus.getDefault().post(new LineChartEvent(listResponseData.getData()));
                }else{
                    Util.toastMsg(listResponseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                ZillaApi.dealNetError(error);
            }
        });

    }
}
