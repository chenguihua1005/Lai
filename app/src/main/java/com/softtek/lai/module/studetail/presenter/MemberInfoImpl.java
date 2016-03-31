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
import com.softtek.lai.module.studetail.model.Member;
import com.softtek.lai.module.studetail.net.MemberInfoService;
import org.greenrobot.eventbus.EventBus;
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
    private Context context;

    public MemberInfoImpl(Context context) {
        service = ZillaApi.NormalRestAdapter.create(MemberInfoService.class);
        this.context = context;
    }

    @Override
    public void getMemberinfo(String classId, String userId, final ProgressDialog progressDialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getmemberInfo(token, userId, classId, new Callback<ResponseData<Member>>() {
            @Override
            public void success(ResponseData<Member> memberResponseData, Response response) {
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
}
