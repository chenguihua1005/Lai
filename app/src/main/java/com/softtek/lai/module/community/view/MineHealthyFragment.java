package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.presenter.CommunityManager;
import com.softtek.lai.module.login.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
@InjectLayout(R.layout.fragment_mine_healthy)
public class MineHealthyFragment extends BaseFragment  implements  AdapterView.OnItemClickListener
        ,PullToRefreshBase.OnRefreshListener2<ListView>,CommunityManager.CommunityManagerCallback,View.OnClickListener{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;
    @InjectView(R.id.but_login)
    Button but_login;

    private CommunityManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    boolean isLogin=false;
    @Override
    protected void initViews() {
        but_login.setOnClickListener(this);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);

    }

    @Override
    public void onStart() {
        super.onStart();
        String token=UserInfoModel.getInstance().getToken();
        if(token==null||"".equals(token)){
            isLogin=false;
            lin_is_vr.setVisibility(View.VISIBLE);
            ptrlv.setVisibility(View.GONE);
        }else{
            isLogin=true;
            lin_is_vr.setVisibility(View.GONE);
            ptrlv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initDatas() {
        community=new CommunityManager(this);
        //加载数据适配器
        adapter=new HealthyCommunityAdapter(getContext(),communityModels,false);
        ptrlv.setAdapter(adapter);
        //自动加载
        if(isLogin){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.setRefreshing();
                }
            }, 500);
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康我的动态
        Log.i("加载健康圈我的动态");
        pageIndex=1;
        community.getHealthyMine(pageIndex);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        community.getHealthyMine(pageIndex);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void getMineDynamic(List<HealthyCommunityModel> models) {
        ptrlv.onRefreshComplete();
        if(communityModels==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(communityModels.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
            this.communityModels.clear();
        }
        this.communityModels.addAll(communityModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
    }
}
