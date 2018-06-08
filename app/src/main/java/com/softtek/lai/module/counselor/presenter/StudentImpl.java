/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.util.Log;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class StudentImpl implements IStudentPresenter {

    private CounselorService counselorService;
    private BaseActivity context;

    public StudentImpl(BaseActivity context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }

    @Override
    public void sendInviterMsg(String inviters, String classId, final ImageView img_invite) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.sendInviterMsg(token, inviters, classId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        img_invite.setImageDrawable(context.getResources().getDrawable(R.drawable.img_invited));
                        Util.toastMsg("邀请成功");
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
