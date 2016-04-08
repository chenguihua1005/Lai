package com.softtek.lai.module.lossweightstory.presenter;

import android.content.Context;
import android.widget.CheckBox;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class LossWeightStoryImpl implements ILossWeightStory{

    private String token;
    private LossWeightLogService service;
    private Context context;

    public LossWeightStoryImpl(Context context) {
        this.context = context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
    }

    @Override
    public void getLossWeightLogForClass(long accoundId) {
        service.getCompetitionLogList(token, accoundId, new Callback<ResponseData<List<LossWeightLogModel>>>() {
            @Override
            public void success(ResponseData<List<LossWeightLogModel>> listResponseData, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
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
}
