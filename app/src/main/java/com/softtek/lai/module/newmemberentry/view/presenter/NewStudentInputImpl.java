/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.presenter;


import android.content.Context;
import android.content.Intent;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.CounselorActivity;
import com.softtek.lai.module.newmemberentry.view.EntryActivity;
import com.softtek.lai.module.newmemberentry.view.model.NewstudentsModel;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.newmemberentry.view.net.NewstudentsService;
import org.greenrobot.eventbus.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.io.File;
import java.util.List;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class NewStudentInputImpl implements INewStudentpresenter {

    private NewstudentsService newstudentsService;
    private Context context;

    public NewStudentInputImpl(EntryActivity entryActivity) {
        newstudentsService = ZillaApi.NormalRestAdapter.create(NewstudentsService.class);
        context = entryActivity;
    }

    @Override
    public void input(NewstudentsModel newstudentsModel) {
        Log.i("NewstudentsService>>>>>>>>>>>>>>" + newstudentsService);
        String token = SharedPreferenceService.getInstance().get("token", "");
        newstudentsService.memberentry(token, newstudentsModel, new Callback<ResponseData<NewstudentsModel>>() {


            @Override
            public void success(ResponseData<NewstudentsModel> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        Intent intent = new Intent(context, CounselorActivity.class);
                        context.startActivity(intent);
                        Util.toastMsg("录入成功");
                        break;
                    case 500:
                        Util.toastMsg("录入失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }

    @Override
    public void upload(final String upimg) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        newstudentsService.upimg(token, new TypedFile("image/png", new File(upimg)), new Callback<ResponseData<PhotModel>>() {
            @Override
            public void success(ResponseData upimgResponseData, Response response) {

                int status = upimgResponseData.getStatus();
                switch (status) {
                    case 200:
                        PhotModel photModel = (PhotModel) upimgResponseData.getData();
                        EventBus.getDefault().post(photModel);
                        Util.toastMsg("获取成功");
                        break;
                    case 500:
                        Util.toastMsg("上传图片异常");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {

                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
}
