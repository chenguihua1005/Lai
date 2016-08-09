package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.presenter.CommunityManager;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.view.LogStoryDetailActivity;

import org.apache.commons.lang3.StringUtils;

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
    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    private CommunityManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;
    boolean isLogin=false;
    @Override
    protected void initViews() {
        but_login.setOnClickListener(this);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setEmptyView(img_mo_message);
    }


    @Override
    protected void initDatas() {
        community=new CommunityManager(this);
        //加载数据适配器
        adapter=new HealthyCommunityAdapter(getContext(),communityModels,false,1);
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
        if(isLogin){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(ptrlv!=null) {
                        ptrlv.setRefreshing();
                    }
                }
            }, 500);
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康我的动态
        Log.i("加载健康圈我的动态");
        pageIndex=1;
        community.getHealthyMine(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            community.getHealthyMine(pageIndex);
        }else{
            pageIndex--;
            new Handler().postDelayed(new Runnable() {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HealthyCommunityModel model=communityModels.get(position-1);
        if("1".equals(model.getMinetype())){//减重日志
            Intent logDetail=new Intent(getContext(), LogStoryDetailActivity.class);
            logDetail.putExtra("log",copyModel(model));
            logDetail.putExtra("position",position-1);
            logDetail.putExtra("type","0");
            startActivityForResult(logDetail,LIST_JUMP_2);
        }else if("0".equals(model.getMinetype())){//动态
            Intent logDetail=new Intent(getContext(), HealthyDetailActivity.class);
            logDetail.putExtra("dynamicModel",copyModeltoDynamci(model));
            logDetail.putExtra("position",position-1);
            logDetail.putExtra("type","0");
            startActivityForResult(logDetail,LIST_JUMP);
        }
    }

    @Override
    public void getMineDynamic(HealthyRecommendModel model) {
        ptrlv.onRefreshComplete();
        try {
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
        } catch (NumberFormatException e) {
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

    public  void updateList(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ptrlv!=null){
                    ptrlv.setRefreshing();
                    pageIndex=1;
                    community.getHealthyMine(1);
                }
            }
        }, 300);
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
