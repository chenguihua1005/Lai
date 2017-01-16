package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.RefreshRecommedEvent;
import com.softtek.lai.module.community.eventModel.ZanEvent;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 * 莱聚+动态
 *
 */
@InjectLayout(R.layout.fragment_recommend_healthy)
public class DynamicFragment extends LazyBaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>,RecommentHealthyManager.RecommentHealthyManagerCallback{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.empty)
    FrameLayout empty;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;

    @Override
    protected void lazyLoad() {
        pageIndex=1;
        community.getRecommendDynamic(accountId,1);
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
    }
    long accountId=0;
    @Override
    protected void initDatas() {
        community=new RecommentHealthyManager(this);
        UserModel user= UserInfoModel.getInstance().getUser();
        String token=UserInfoModel.getInstance().getToken();
        if(StringUtils.isEmpty(token)){
            accountId=-1;
        }else{
            accountId=Long.parseLong(user.getUserid());
        }
        adapter=new HealthyCommunityAdapter(this,getContext(),communityModels);
        ptrlv.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshList(RefreshRecommedEvent event){
        for (HealthyCommunityModel model:communityModels){
            if(model.getAccountId().equals(event.getAccountId())){
                model.setIsFocus(event.getFocusStatus());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void refreshListDelete(DeleteRecommedEvent event){
        int position=-1;
        for (int i=0,j=communityModels.size();i<j;i++){
            HealthyCommunityModel model=communityModels.get(i);
            if(model.getID().equals(event.getDynamicId())){
                position=i;
                break;
            }
        }
        if(position>=0){
            communityModels.remove(position);
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void refreshListZan(ZanEvent event){
        if(event.getWhere()==0){
            for (HealthyCommunityModel model:communityModels){
                if(model.getID().equals(event.getDynamicId())){
                    model.setIsPraise(Constants.HAS_ZAN);
                    model.setPraiseNum(String.valueOf(Integer.parseInt(model.getPraiseNum()) + 1));
                    UserInfoModel infoModel = UserInfoModel.getInstance();
                    model.setUsernameSet(StringUtil.appendDot(model.getUsernameSet(), infoModel.getUser().getNickname(),
                            infoModel.getUser().getMobile()));
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private static final int LIST_JUMP=1;

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康推荐动态
        pageIndex=1;
        community.getRecommendDynamic(accountId,1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            community.getRecommendDynamic(accountId,pageIndex);
        }else{
            pageIndex--;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();

                }
            },300);
        }
    }
    public  void updateList(){
        if(ptrlv!=null){
            ptrlv.getRefreshableView().setSelection(0);
            pageIndex=1;
            community.getRecommendDynamic(accountId,1);
        }
    }

    @Override
    public void getRecommendDynamic(HealthyRecommendModel model) {
        try {
            ptrlv.onRefreshComplete();
            if(model==null){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            if(model.getTotalPage()==null||model.getHealthList()==null){
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
            }
        }
    }
}
