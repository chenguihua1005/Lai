package com.softtek.lai.module.lossweightstory.presenter;

import android.content.Context;
import android.widget.CheckBox;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.lossweightstory.model.LogList;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class LossWeightStoryManager {

    private String token;
    private LossWeightLogService service;
    private Context context;
    private StoryManagerCallBack cb;

    public LossWeightStoryManager(Context context) {
        this.context = context;
        cb= (StoryManagerCallBack) context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
    }


    public void getLossWeightLogForClass(long accoundId,int pageIndex) {
        service.getCompetitionLogList(token, accoundId,pageIndex, new Callback<ResponseData<LogList>>() {
            @Override
            public void success(ResponseData<LogList> listResponseData, Response response) {
                cb.getStroyList(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getStroyList(null);
                ZillaApi.dealNetError(error);
            }
        });
    }


    public void doZan(long accountId, long logId, final CheckBox zan) {
        service.clickLike(token, accountId, logId, new Callback<ResponseData<Zan>>() {
            @Override
            public void success(ResponseData<Zan> zanResponseData, Response response) {
                if(zanResponseData.getStatus()==200){
                    zan.setText(zanResponseData.getData().getTotalNum());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                zan.setChecked(false);
                zan.setText(Integer.parseInt(zan.getText().toString())-1+"");
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface StoryManagerCallBack{

        void getStroyList(LogList logList);
    }
}
