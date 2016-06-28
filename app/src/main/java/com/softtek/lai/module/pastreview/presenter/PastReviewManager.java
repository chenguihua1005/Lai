package com.softtek.lai.module.pastreview.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.model.PastBaseData;
import com.softtek.lai.module.pastreview.net.PCPastReview;
import com.softtek.lai.module.pastreview.view.PcPastBaseDataActivity;
import com.softtek.lai.utils.RequestCallback;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 6/28/2016.
 */
public class PastReviewManager {

    private PCPastReview service;

    public PastReviewManager() {
        this.service = ZillaApi.NormalRestAdapter.create(PCPastReview.class);
    }

    public void getBaseDataInfo(final PcPastBaseDataActivity activity, long userId, long classId){
        service.getBaseInfo(UserInfoModel.getInstance().getToken(), userId, classId, new RequestCallback<ResponseData<PastBaseData>>() {
            @Override
            public void success(ResponseData<PastBaseData> pastBaseDataResponseData, Response response) {
                if(pastBaseDataResponseData.getStatus()==200){
                    if(activity!=null){
                        activity.onGetData(pastBaseDataResponseData.getData());
                    }
                }
            }
        });
    }
}
