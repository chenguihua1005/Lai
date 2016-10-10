package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.HealthyCommunityFocusAdapter;
import com.softtek.lai.module.community.eventModel.DeleteFocusEvent;
import com.softtek.lai.module.community.eventModel.RefreshRecommedEvent;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.presenter.CommunityManager;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
@InjectLayout(R.layout.fragment_mine_healthy)
public class MineHealthyFragment extends LazyBaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>,CommunityManager.CommunityManagerCallback<HealthyRecommendModel>,View.OnClickListener{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;
    @InjectView(R.id.but_login)
    Button but_login;
    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    private CommunityManager community;
    private HealthyCommunityFocusAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;
    boolean isLogin=false;

    @Override
    protected void lazyLoad() {
        if(isLogin){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if(ptrlv!=null) {
                        ptrlv.setRefreshing();
                    }
                }
            });
        }
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        but_login.setOnClickListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setEmptyView(img_mo_message);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新中");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
//        endLabelsr.setLastUpdatedLabel("正在刷新数据");// 刷新时
        endLabelsr.setRefreshingLabel("正在刷新数据中");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshList(DeleteFocusEvent event){
        List<HealthyCommunityModel> models=new ArrayList<>();
        for (int i=0,j=communityModels.size();i<j;i++){
            HealthyCommunityModel item = communityModels.get(i);
            if(item.getAccountId().equals(event.getAccountId())){
                models.add(item);
            }
        }
        communityModels.removeAll(models);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initDatas() {
        community=new CommunityManager(this);
        //加载数据适配器
        adapter=new HealthyCommunityFocusAdapter(this,getContext(),communityModels);
        ptrlv.setAdapter(adapter);
        String token=UserInfoModel.getInstance().getToken();
        //判断token是否为空
        if(StringUtils.isEmpty(token)){
            //token为空，游客模式显示立即登陆页面
            isLogin=false;
            lin_is_vr.setVisibility(View.VISIBLE);
            ptrlv.setVisibility(View.GONE);
        }else{
            //token不为空，非游客模式，隐藏立即登陆页面
            isLogin=true;
            lin_is_vr.setVisibility(View.GONE);
            ptrlv.setVisibility(View.VISIBLE);
        }
        //自动加载


    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康我的动态
        Log.i("加载健康圈我的动态");
        pageIndex=1;
        community.getHealthyFocus(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            community.getHealthyFocus(pageIndex);
        }else{
            pageIndex--;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(ptrlv!=null){
                        ptrlv.onRefreshComplete();
                    }

                }
            },300);
        }
    }

    private static final int LIST_JUMP=1;
    private static final int LIST_JUMP_2=2;

    @Override
    public void getMineDynamic(HealthyRecommendModel model) {
        try {
            ptrlv.onRefreshComplete();
            if(model==null){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            if(model.getTotalPage()==null&&model.getHealthList()==null){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            totalPage=Integer.parseInt(model.getTotalPage());
            List<HealthyCommunityModel> models=model.getHealthList();
            if(models==null||models.isEmpty()){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            if(pageIndex==1){
                this.communityModels.clear();
            }

            this.communityModels.addAll(models);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_login:
                Intent toLoginIntent=new Intent(getContext(), LoginActivity.class);
                toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toLoginIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1){
            if(requestCode==LIST_JUMP){
                int position=data.getIntExtra("position",-1);
                HealthyDynamicModel model=data.getParcelableExtra("dynamicModel");
                if(position!=-1&&model!=null){
                    communityModels.get(position).setIsPraise(model.getIsPraise());
                    communityModels.get(position).setUsernameSet(model.getUsernameSet());
                    communityModels.get(position).setPraiseNum(model.getPraiseNum());
                    adapter.notifyDataSetChanged();
                }
            }else if(requestCode==LIST_JUMP_2){
                int position=data.getIntExtra("position",-1);
                LossWeightStoryModel model=data.getParcelableExtra("log");
                if(position!=-1&&model!=null){
                    communityModels.get(position).setIsPraise(model.getIsClicked());
                    communityModels.get(position).setUsernameSet(model.getUsernameSet());
                    communityModels.get(position).setPraiseNum(model.getPriase());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}
