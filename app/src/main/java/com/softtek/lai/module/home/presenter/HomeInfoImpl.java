package com.softtek.lai.module.home.presenter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.cache.HomeInfoCache;
import com.softtek.lai.module.home.model.FunctionModel;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.net.HomeService;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.utils.ACache;
import com.squareup.picasso.Picasso;

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
            System.out.println("没有缓存数据");
        }
        String[] models_name=context.getResources().getStringArray(R.array.models);
        List<FunctionModel> models=new ArrayList<>();
        for(int i=0;i<models_name.length;i++){
            FunctionModel model=new FunctionModel();
            model.setName_model(models_name[i]);
            models.add(model);
        }
        ZillaAdapter<FunctionModel> adapter=new ZillaAdapter<FunctionModel>(context,models,R.layout.gridview_item,ViewHolderModel.class);
        EventBus.getDefault().post(adapter);
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
                pull.onRefreshComplete();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    static class ViewHolderModel {

        @InjectView(R.id.tv_name)
        TextView name_model;

        public ViewHolderModel(View view){
            ButterKnife.inject(this,view);
        }
    }
}
