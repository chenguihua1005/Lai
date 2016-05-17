package com.softtek.lai.module.mygrades.view;

import android.app.Activity;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.adapter.RankAdapter;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/16/2016.
 */

@InjectLayout(R.layout.fagment_rank_sport)
public class WeekRankFragment extends BaseFragment {

    @InjectView(R.id.list_rank)
    ListView list_rank;

    private List<OrderDataModel> orderDataModelList = new ArrayList<OrderDataModel>();
    private OrderDataModel orderDataModel;
    public RankAdapter rankAdapter;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;

    @Override
    protected void initViews() {
        initrankdate();
        rankAdapter = new RankAdapter(getContext(),orderDataModelList);
        list_rank.setAdapter(rankAdapter);
    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);
        getCurrentWeekOrder(23);

    }

    private void initrankdate() {
        OrderDataModel p1 = new OrderDataModel("1","20898983403","1","18329726809","23435","zhang");
        orderDataModelList.add(p1);
        OrderDataModel p2 = new OrderDataModel("2","20898983403","2","18329726809","97556","wang");
        orderDataModelList.add(p2);
        OrderDataModel p3 = new OrderDataModel("3","20898983403","3","18329726809","86343","li");
        orderDataModelList.add(p3);
        OrderDataModel p4 = new OrderDataModel("4","20898983403","4","18329726809","45766","cheng");
        orderDataModelList.add(p4);
        OrderDataModel p5 = new OrderDataModel("5","20898983403","5","18329726809","64783","xu");
        orderDataModelList.add(p5);

    }

    public void getCurrentWeekOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentWeekOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
            @Override
            public void success(ResponseData<DayRankModel> dayRankModelResponseData, Response response) {
                int status=dayRankModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("我的周排名--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的周排名--查询出bug");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
