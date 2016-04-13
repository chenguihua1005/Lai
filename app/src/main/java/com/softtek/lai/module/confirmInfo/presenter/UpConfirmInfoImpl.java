package com.softtek.lai.module.confirmInfo.presenter;


import android.content.Context;
import zilla.libcore.util.Util;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.net.ConfirmInfoService;
import com.softtek.lai.module.confirmInfo.view.CansaiActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by zcy on 2016/4/13.
 */
public class UpConfirmInfoImpl implements IUpConfirmInfopresenter{
    private ConfirmInfoService confirmInfoService;
    private Context context;

    public UpConfirmInfoImpl(CansaiActivity cansaiActivity) {
        confirmInfoService= ZillaApi.NormalRestAdapter.create(ConfirmInfoService.class);
        context=cansaiActivity;
    }

    @Override
    public void changeUpConfirmInfo(ConinfoModel coninfoModel) {
        Log.i("ConfirmInfoService>>>>>>>>>>>>>>" + confirmInfoService);
        String token = SharedPreferenceService.getInstance().get("token", "");
        confirmInfoService.changeUpConfirmInfo(token, coninfoModel, new Callback<ResponseData<ConinfoModel>>() {
            @Override
            public void success(ResponseData<ConinfoModel> coninfoModelResponseData, Response response) {
                int status = coninfoModelResponseData.getStatus();
                switch (status) {
                    case 200:
//                        Intent intent = new Intent(context, CounselorActivity.class);
//                        context.startActivity(intent);
                        Util.toastMsg("修改成功");
                        break;
                    case 500:
                        Util.toastMsg("修改失败");
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
}
