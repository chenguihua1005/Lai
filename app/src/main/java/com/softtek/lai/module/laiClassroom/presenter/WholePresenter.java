package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePersent;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.common.mvp.BaseView2;
import com.softtek.lai.module.laiClassroom.model.ArticalList;
import com.softtek.lai.module.laiClassroom.model.FilteData;
import com.softtek.lai.module.laiClassroom.net.LaiClassroomService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Query;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public class WholePresenter extends BasePersent<WholePresenter.WholeView>{

    private LaiClassroomService service;

    public WholePresenter(WholeView baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(LaiClassroomService.class);
    }

    public void getFilterData(){
        service.getFilteData(UserInfoModel.getInstance().getToken(),
                String.valueOf(UserInfoModel.getInstance().getUserId()),
                new RequestCallback<ResponseData<FilteData>>() {
            @Override
            public void success(ResponseData<FilteData> data, Response response) {
                if(data.getStatus()==200){
                    if(getView()!=null){
                        getView().getData(data.getData());
                    }
                }
            }
        });
    }

    public void getArticleList(String type, String subjectId, String order, int pageIndex, final int upOrDown){
        if(getView()!=null){
            getView().dialogShow("正在加载");
        }
        service.getArticleList(UserInfoModel.getInstance().getToken(),
                type,
                subjectId,
                order,
                pageIndex,
                10,
                new RequestCallback<ResponseData<ArticalList>>() {
                    @Override
                    public void success(ResponseData<ArticalList> data, Response response) {
                        if (getView()!=null){
                            getView().dialogDissmiss();
                        }
                        if(data.getStatus()==200){
                            if(getView()!=null){
                                getView().getArticles(data.getData(),upOrDown);
                            }
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (getView()!=null){
                            getView().dialogDissmiss();
                        }
                        super.failure(error);
                    }
                });

    }


    public interface WholeView extends BaseView1<FilteData> {
        void getArticles(ArticalList data,int upOrDown);
    }
}
