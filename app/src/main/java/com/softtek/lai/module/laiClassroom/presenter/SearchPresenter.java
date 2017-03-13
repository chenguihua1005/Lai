package com.softtek.lai.module.laiClassroom.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.laiClassroom.model.SearchModel;
import com.softtek.lai.module.laiClassroom.net.SearchService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jia.lu on 2017/3/10.
 */

public class SearchPresenter extends BasePresenter<SearchPresenter.SearchView> {
    SearchService service;
    public SearchPresenter(SearchView searchView) {
        super(searchView);
    }

    public void getChaosData(String keywords) {
        service = ZillaApi.NormalRestAdapter.create(SearchService.class);
        if (getView() != null){
            service.getChaosInfo(
                    UserInfoModel.getInstance().getToken(),
                    keywords,
                    1,
                    1000,
                    new RequestCallback<ResponseData<SearchModel>>() {
                        @Override
                        public void success(ResponseData<SearchModel> responseData, Response response) {
                            if (responseData.getStatus() == 200){
                                if (responseData.getData().getArticleList() != null){
                                    getView().getData(responseData.getData().getArticleList());
                                }
                            }else {
                                Util.toastMsg(responseData.getMsg());
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            super.failure(error);
                            Log.d("SearchPresenter",error.toString());
                        }
                    });
        }
    }

    public interface SearchView extends BaseView1<List<SearchModel.ArticleListBean>> {

    }
}
