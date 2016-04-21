package com.softtek.lai.module.bodygamest.present;

import android.app.ProgressDialog;
import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.net.PhotoListService;
import com.softtek.lai.module.lossweightstory.model.LogList;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;

import java.util.List;

import cn.jpush.android.service.DownloadService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class DownloadManager {

    private String token;
    private PhotoListService service;
    private Context context;
    private DownloadCallBack cb;

    public DownloadManager(Context context) {
        this.context = context;
        cb= (DownloadCallBack) context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(PhotoListService.class);
    }


    public void doGetDownPhoto(String AccountId, int pageIndex) {
        service.doGetDownPhoto(token, AccountId, pageIndex, new Callback<ResponseData<DownPhotoModel>>() {
            @Override
            public void success(ResponseData<DownPhotoModel> listResponseData, Response response) {
                Log.i(listResponseData.toString());
                cb.getStroyList(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getStroyList(null);
                ZillaApi.dealNetError(error);

            }
        });
//        service.getCompetitionLogList(token, accoundId,pageIndex, new Callback<ResponseData<LogList>>() {
//            @Override
//            public void success(ResponseData<LogList> listResponseData, Response response) {
//                cb.getStroyList(listResponseData.getData());
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                cb.getStroyList(null);
//                ZillaApi.dealNetError(error);
//            }
//        });
    }



    public interface DownloadCallBack{

        void getStroyList(DownPhotoModel downPhotoModel);
    }
}
