package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.HonorInfo;
import com.softtek.lai.module.counselor.net.CounselorService;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HonorImpl implements IHonorPresenter {

    private CounselorService counselorService;
    private Context context;

    public HonorImpl(Context context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }


    @Override
    public void getSPHonor() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.getSPHonor(token, new Callback<ResponseData<HonorInfo>>() {

            @Override
            public void success(ResponseData<HonorInfo> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                HonorInfo honorInfo = listResponseData.getData();
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
                error.printStackTrace();

                Util.toastMsg("获取列表失败");
            }
        });
    }

}
