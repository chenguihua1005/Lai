package com.softtek.lai.module.community.view;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.presenter.CommunityManager;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
@InjectLayout(R.layout.fragment_recommend_healthy)
public class RecommendHealthyFragment extends BaseFragment implements AdapterView.OnItemClickListener
        ,PullToRefreshBase.OnRefreshListener2<ListView>,RecommentHealthyManager.RecommentHealthyManagerCallback{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();

    @Override
    protected void initViews() {
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        community=new RecommentHealthyManager(this);
        //adapter=new HealthyCommunityAdapter(this,communityModels);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 200);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康推荐动态
        community.getRecommendDynamic();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void getRecommendDynamic(List<HealthyCommunityModel> communityModels) {
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
