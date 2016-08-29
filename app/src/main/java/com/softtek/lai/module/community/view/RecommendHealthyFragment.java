package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.view.LogStoryDetailActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 * 健康圈推荐
 *
 */
@InjectLayout(R.layout.fragment_recommend_healthy)
public class RecommendHealthyFragment extends LazyBaseFragment implements AdapterView.OnItemClickListener
        ,PullToRefreshBase.OnRefreshListener2<ListView>,RecommentHealthyManager.RecommentHealthyManagerCallback{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;

    @Override
    protected void lazyLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ptrlv!=null){
                    ptrlv.setRefreshing();
                }
            }
        }, 400);
        pageIndex=1;
        community.getRecommendDynamic(accountId,1);
    }

    @Override
    protected void initViews() {
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnItemClickListener(this);
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
        adapter=new HealthyCommunityAdapter(getContext(),communityModels,accountId==-1?true:false,2);
        ptrlv.setAdapter(adapter);

    }
    private static final int LIST_JUMP=1;
    private static final int LIST_JUMP_2=2;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HealthyCommunityModel model=communityModels.get(position-1);
        if("1".equals(model.getMinetype())){//减重日志
            Intent logDetail=new Intent(getContext(), LogStoryDetailActivity.class);
            logDetail.putExtra("log",copyModel(model));
            logDetail.putExtra("position",position-1);
            logDetail.putExtra("type","1");
            startActivityForResult(logDetail,LIST_JUMP_2);
        }else if("0".equals(model.getMinetype())){//动态
            Intent logDetail=new Intent(getContext(), HealthyDetailActivity.class);
            logDetail.putExtra("dynamicModel",copyModeltoDynamci(model));
            logDetail.putExtra("position",position-1);
            logDetail.putExtra("type","1");
            startActivityForResult(logDetail,LIST_JUMP);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康推荐动态
        Log.i("推荐记录开始刷新拉");
        pageIndex=1;
        community.getRecommendDynamic(accountId,1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        Log.i("推荐记录开始加载更多");
        pageIndex++;
        if(pageIndex<=totalPage){
            community.getRecommendDynamic(accountId,pageIndex);
        }else{
            pageIndex--;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();

                }
            },300);
        }
    }
    public  void updateList(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ptrlv!=null){
                    ptrlv.setRefreshing();
                }
            }
        }, 300);
    }

    @Override
    public void getRecommendDynamic(HealthyRecommendModel model) {
        Log.i("推荐记录请求结束");
        ptrlv.onRefreshComplete();
        try {
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private LossWeightStoryModel copyModel(HealthyCommunityModel model){
        LossWeightStoryModel storyModel=new LossWeightStoryModel();
        storyModel.setPriase(model.getPraiseNum());
        storyModel.setLogContent(model.getContent());
        storyModel.setLogTitle(model.getTitle());
        storyModel.setAfterWeight("0");
        storyModel.setCreateDate(model.getCreateDate());
        storyModel.setImgCollection(model.getImgCollection());
        storyModel.setIsClicked(model.getIsPraise());
        storyModel.setLossLogId(model.getID());
        storyModel.setPhoto(model.getPhoto());
        storyModel.setUserName(model.getUserName());
        storyModel.setUsernameSet(model.getUsernameSet());

        return storyModel;
    }

    private HealthyDynamicModel copyModeltoDynamci(HealthyCommunityModel model){
        HealthyDynamicModel dynamicModel=new HealthyDynamicModel();
        dynamicModel.setPraiseNum(model.getPraiseNum());
        dynamicModel.setUsernameSet(model.getUsernameSet());
        dynamicModel.setContent(model.getContent());
        dynamicModel.setUserName(model.getUserName());
        dynamicModel.setHealtId(model.getID());
        dynamicModel.setIsPraise(model.getIsPraise());
        dynamicModel.setCreateDate(model.getCreateDate());
        dynamicModel.setImgCollection(model.getImgCollection());
        dynamicModel.setPhoto(model.getPhoto());
        return dynamicModel;
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
