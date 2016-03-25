package com.softtek.lai.module.home.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.cache.HomeInfoCache;
import com.softtek.lai.module.home.eventModel.ActivityEvent;
import com.softtek.lai.module.home.eventModel.ProductEvent;
import com.softtek.lai.module.home.eventModel.RefreshEvent;
import com.softtek.lai.module.home.eventModel.SaleEvent;
import com.softtek.lai.module.home.model.FunctionModel;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.net.HomeService;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.SuperSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public class HomeInfoImpl implements IHomeInfoPresenter{

    private HomeService homeService;
    private ACache aCache;
    private Context context;

    public HomeInfoImpl(Context context){
        this.context=context;
        homeService= ZillaApi.NormalRestAdapter.create(HomeService.class);
        aCache=ACache.get(context, Constants.HOME_CACHE_DATA_DIR);
    }

    //加载本地缓存数据
    @Override
    public void loadCacheData() {
        aCache=ACache.get(context,Constants.HOME_CACHE_DATA_DIR);
        String json=aCache.getAsString(Constants.HOEM_ACACHE_KEY);
        if(json!=null&&!json.equals("")){
            Gson gson=new Gson();
            HomeInfoCache infoCache=gson.fromJson(json,HomeInfoCache.class);
            EventBus.getDefault().post(infoCache.getInfos());
        }else{
            List<HomeInfo> infos=new ArrayList<>();
            EventBus.getDefault().post(infos);
            System.out.println("没有缓存数据");
        }
        EventBus.getDefault().post(new ModelAdapter(context));
    }

    @Override
    public void getHomeInfoData(final SwipeRefreshLayout pull) {
        homeService.doLoadHomeData(new Callback<ResponseData<List<HomeInfo>>>() {
            @Override
            public void success(ResponseData<List<HomeInfo>> data, Response response) {
                pull.setRefreshing(false);
                System.out.println(data);
                int status=data.getStatus();
                switch (status){
                    case 200:
                        aCache.put(Constants.HOEM_ACACHE_KEY,new Gson().toJson(new HomeInfoCache(data.getData())));
                        EventBus.getDefault().post(data.getData());
                        break;
                    default:
                        Util.toastMsg(data.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                pull.setRefreshing(false);
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void getContentByPage(final int flag,int page, final int img_type) {
        homeService.getActivityByPage(img_type, page, new Callback<ResponseData<List<HomeInfo>>>() {
            @Override
            public void success(ResponseData<List<HomeInfo>> homeInfoResponseData, Response response) {
                EventBus.getDefault().post(new RefreshEvent());
                switch (homeInfoResponseData.getStatus()){
                    case 200:
                        if(img_type==1){
                            EventBus.getDefault().post(new ActivityEvent(flag,homeInfoResponseData.getData()));
                        }else if(img_type==2){
                            EventBus.getDefault().post(new ProductEvent(flag,homeInfoResponseData.getData()));
                        }else if(img_type==6){
                            EventBus.getDefault().post(new SaleEvent(flag,homeInfoResponseData.getData()));
                        }
                        break;
                    default:
                        Util.toastMsg(homeInfoResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RefreshEvent());
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }


}
