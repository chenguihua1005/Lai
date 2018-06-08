/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame3.more.present;

import android.util.Log;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.HnumsModel;
import com.softtek.lai.module.bodygame3.more.model.HonorModel;
import com.softtek.lai.module.bodygame3.more.net.StudentService;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;


public class StudentImpl implements IStudentPresenter {

    private StudentService studentService;
    private BaseActivity base;


    public StudentImpl(BaseActivity context) {
        this.base = context;
        studentService = ZillaApi.NormalRestAdapter.create(StudentService.class);
    }


    @Override
    public void getStudentHonours() {
        String token = UserInfoModel.getInstance().getToken();
        studentService.getStudentHonours(token,new Callback<ResponseData<HnumsModel>>() {
            @Override
            public void success(ResponseData<HnumsModel> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                base.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                base.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getStudentHonorPC(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        studentService.getStudentHonorPC(token,accountid,new Callback<ResponseData<HonorModel>>() {
            @Override
            public void success(ResponseData<HonorModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                base.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                base.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

}
