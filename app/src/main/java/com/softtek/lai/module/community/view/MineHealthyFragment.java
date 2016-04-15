package com.softtek.lai.module.community.view;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.presenter.CommunityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
@InjectLayout(R.layout.fragment_recommend_healthy)
public class MineHealthyFragment extends BaseFragment  implements  AdapterView.OnItemClickListener
        ,PullToRefreshBase.OnRefreshListener2<ListView>,CommunityManager.CommunityManagerCallback{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private CommunityManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();

    @Override
    protected void initViews() {
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);

    }

    @Override
    protected void initDatas() {
        community=new CommunityManager(this);
        //加载数据适配器
        //自动加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 200);

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康我的动态
        Log.i("获取健康动态");
        community.getHealthyMine();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void getMineDynamic(List<HealthyCommunityModel> models) {
        ptrlv.onRefreshComplete();
        if(communityModels==null){
            return;
        }
        if(!communityModels.isEmpty()){
            this.communityModels.clear();
            this.communityModels.addAll(communityModels);
            //adapter.notifyDataSetChanged();
        }
    }
}
