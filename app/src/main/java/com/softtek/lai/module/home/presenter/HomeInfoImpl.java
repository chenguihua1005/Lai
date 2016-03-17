package com.softtek.lai.module.home.presenter;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.net.HomeService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public class HomeInfoImpl implements IHomeInfoPresenter{

    private HomeService homeService;

    public HomeInfoImpl(){
        homeService= ZillaApi.NormalRestAdapter.create(HomeService.class);
    }

    @Override
    public void getHomeInfoData(final PullToRefreshScrollView pull) {
        homeService.doLoadHomeData(new Callback<ResponseData<List<HomeInfo>>>() {
            @Override
            public void success(ResponseData<List<HomeInfo>> data, Response response) {
                pull.onRefreshComplete();
                System.out.println(data);
                int status=data.getStatus();
                switch (status){
                    case 200:
                        EventBus.getDefault().post(data.getData());
                        break;
                    default:
                        Util.toastMsg(data.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                pull.onRefreshComplete();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }
}
