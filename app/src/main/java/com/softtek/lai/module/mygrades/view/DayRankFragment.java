package com.softtek.lai.module.mygrades.view;

import android.app.Activity;
import android.app.Fragment;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;

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
public class DayRankFragment extends BaseFragment {

    @InjectView(R.id.list_rank)
    ListView list_rank;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);
        getCurrentDateOrder(1);
    }

    public void getCurrentDateOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentDateOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
            @Override
            public void success(ResponseData<DayRankModel> dayRankModelResponseData, Response response) {
                int status=dayRankModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("我的日排名--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的日排名--查询出bug");
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
