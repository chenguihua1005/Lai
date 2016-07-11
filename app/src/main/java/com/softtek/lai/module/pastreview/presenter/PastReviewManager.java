package com.softtek.lai.module.pastreview.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.model.PastBaseData;
import com.softtek.lai.module.pastreview.model.PastClass;
import com.softtek.lai.module.pastreview.model.StoryList;
import com.softtek.lai.module.pastreview.model.StoryModel;
import com.softtek.lai.module.pastreview.net.PCPastReview;
import com.softtek.lai.module.pastreview.view.HistoryHomeActivity;
import com.softtek.lai.module.pastreview.view.PcPastBaseDataActivity;
import com.softtek.lai.module.pastreview.view.StoryActivity;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 6/28/2016.
 */
public class PastReviewManager {

    private PCPastReview service;
    private UserInfoModel userInfoModel;

    public PastReviewManager() {
        userInfoModel=UserInfoModel.getInstance();
        this.service = ZillaApi.NormalRestAdapter.create(PCPastReview.class);
    }

    public void getBaseDataInfo(final PcPastBaseDataActivity activity, long userId, long classId){
        service.getBaseInfo(userInfoModel.getToken(), userId, classId, new RequestCallback<ResponseData<PastBaseData>>() {
            @Override
            public void success(ResponseData<PastBaseData> pastBaseDataResponseData, Response response) {
                if(pastBaseDataResponseData.getStatus()==200){
                    if(activity!=null){
                        activity.onGetData(pastBaseDataResponseData.getData());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(activity!=null){
                    activity.onGetData(null);
                }
                super.failure(error);
            }
        });
    }

    public void getStoryList(final StoryActivity activity, long userId, long classId,int pageIndex){
        service.getPastStory(userInfoModel.getToken(), userId, classId, pageIndex, new RequestCallback<ResponseData<StoryList>>() {
            @Override
            public void success(ResponseData<StoryList> storyListResponseData, Response response) {
                if(activity!=null){
                    if(storyListResponseData.getStatus()==200){
                        activity.getLogList(storyListResponseData.getData());
                    }else{
                        activity.getLogList(null);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(activity!=null){
                    activity.getLogList(null);
                }
                super.failure(error);
            }
        });
    }

    public void loadClassDetail(final HistoryHomeActivity activity, long userId, long classId){
        service.getPastClassDetail(userInfoModel.getToken(), userId, classId, new RequestCallback<ResponseData<PastClass>>() {
            @Override
            public void success(ResponseData<PastClass> pastClassResponseData, Response response) {
                if(activity!=null){
                    if(pastClassResponseData.getStatus()==200){
                        activity.loadData(pastClassResponseData.getData());
                    }else{
                        activity.loadData(null);
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if(activity!=null){
                    activity.loadData(null);
                }
                super.failure(error);
            }
        });
    }
}
