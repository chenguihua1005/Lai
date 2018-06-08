package com.softtek.lai.module.laiClassroom.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.laiClassroom.model.VideoDetailModel;
import com.softtek.lai.module.laiClassroom.net.LaiClassroomService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 3/13/2017.
 */

public class VideoDetailPresenter extends BasePresenter<VideoDetailPresenter.VideoDetailView> {

    private LaiClassroomService service;

    public VideoDetailPresenter(VideoDetailView baseView) {
        super(baseView);
        service=ZillaApi.NormalRestAdapter.create(LaiClassroomService.class);
    }

    public void getVideoDetailData(String articleId){
        if(getView()!=null){
            getView().dialogShow("载入数据");
        }
      service.getVideoDetail(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        articleId,
                        new RequestCallback<ResponseData<VideoDetailModel>>() {
                            @Override
                            public void success(ResponseData<VideoDetailModel> data, Response response) {
                                if(getView()!=null){
                                    getView().dialogDissmiss();
                                }
                                if(data.getStatus()==200){
                                    if(getView()!=null){
                                        getView().getData(data.getData());
                                    }
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                if(getView()!=null){
                                    getView().dialogDissmiss();
                                }
                                super.failure(error);
                            }
                        });
    }

    public void doLike(String articleId){
        service.doLike(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                articleId,
                new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        if (getView()!=null){
                            getView().canLike();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (getView()!=null){
                            getView().canLike();
                        }
                        super.failure(error);
                    }
                });
    }

    public void unLike(String articleId){
        service.unLike(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                articleId,
                new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        if (getView()!=null){
                            getView().canLike();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (getView()!=null){
                            getView().canLike();
                        }
                        super.failure(error);
                    }
                });
    }

    public boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }

    public interface VideoDetailView extends BaseView1<VideoDetailModel> {

        void canLike();
    }
}
