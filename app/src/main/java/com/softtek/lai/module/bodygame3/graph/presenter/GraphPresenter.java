package com.softtek.lai.module.bodygame3.graph.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.graph.model.GirthModel;
import com.softtek.lai.module.bodygame3.graph.model.WeightModel;
import com.softtek.lai.module.bodygame3.graph.net.GraphService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 3/16/2017.
 */

public class GraphPresenter extends BasePresenter<GraphPresenter.GraphView>{

    GraphService service;

    public GraphPresenter(GraphView baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(GraphService.class);
    }

    public void getGraph(long accountId,String classsId){
                service.getClassMemberWeightChart(
                        UserInfoModel.getInstance().getToken(),
                        accountId,
                        classsId,
                        new RequestCallback<ResponseData<List<WeightModel>>>() {
                            @Override
                            public void success(ResponseData<List<WeightModel>> data, Response response) {
                                if(getView()!=null){
                                    getView().dialogDissmiss();
                                    getView().onSuccess(data.getData());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                if(getView()!=null){
                                    getView().onFaile();
                                    super.failure(error);
                                }
                            }
                        }
                );
    }


    public interface GraphView extends BaseView{

        void onSuccess(List<WeightModel> data);

        void onFaile();
    }
}
