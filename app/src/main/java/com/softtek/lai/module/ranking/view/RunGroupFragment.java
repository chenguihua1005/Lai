package com.softtek.lai.module.ranking.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.ranking.adapter.RankingRecyclerViewAdapter;
import com.softtek.lai.module.ranking.model.OrderData;
import com.softtek.lai.module.ranking.model.OrderInfo;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.module.ranking.net.RankingService;
import com.softtek.lai.module.ranking.persenter.RankManager;
import com.softtek.lai.module.sportchart.view.ChartActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_run_group)
public class RunGroupFragment extends LazyBaseFragment implements RankManager.RankManagerCallback{

    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;
    @InjectView(R.id.header_image)
    CircleImageView header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_rank)
    TextView tv_rank;
    @InjectView(R.id.tv_step)
    TextView tv_step;
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectView(R.id.cb_zan)
    CheckBox cb_zan;
    @InjectView(R.id.recycleView)
    RecyclerView recyclerView;

    private RankingRecyclerViewAdapter adapter;
    private List<OrderData> infos;
    private RankManager manager;
    private int rankType;
    private int pageIndex=1;

    private static final int LOADCOUNT=5;
    private int lastVisitableItem;
    private boolean isLoading=false;
    private int totalPage=0;

    private RankModel result;

    public RunGroupFragment() {

    }

    public static RunGroupFragment getInstance(int rankType){
        RunGroupFragment fragment=new RunGroupFragment();
        Bundle data=new Bundle();
        data.putInt("rankType",rankType);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        pull.setRefreshing(true);
        pageIndex=1;
        if(isDayRank(rankType)){
            //跑团日排名
            manager.getDayRank(1,pageIndex);
        }else {
            //跑团周排名
            manager.getWeekRank(1,pageIndex);
        }
    }

    @Override
    protected void initViews() {
        Bundle data=getArguments();
        rankType=data.getInt("rankType",RankingActivity.DAY_RANKING);//默认是日排名
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset>=0){
                    pull.setEnabled(true);
                }else{
                    pull.setEnabled(false);
                }
            }
        });
        header_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),ChartActivity.class);
                intent1.putExtra("isFocusid",UserInfoModel.getInstance().getUser().getUserid());
                getActivity().startActivity(intent1);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count=adapter.getItemCount();
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&count>LOADCOUNT&&lastVisitableItem+1==count){

                    if(!isLoading&&pageIndex<=totalPage){
                        pageIndex++;
                        if(pageIndex<=totalPage){
                            isLoading=true;
                            //加载更多数据
                            if(isDayRank(rankType)){
                                //跑团日排名
                                manager.getDayRank(1,pageIndex);
                            }else {
                                //跑团周排名
                                manager.getWeekRank(1,pageIndex);
                            }
                        }else {
                            pageIndex--;
                            adapter.notifyItemRemoved(adapter.getItemCount());
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm= (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisitableItem=llm.findLastVisibleItemPosition();
            }
        });
        pull.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex=1;
                if(isDayRank(rankType)){
                    //跑团日排名
                    manager.getDayRank(1,pageIndex);
                }else {
                    //跑团周排名
                    manager.getWeekRank(1,pageIndex);
                }
            }
        });
        cb_zan.setEnabled(false);
        cb_zan.setChecked(false);
        cb_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result!=null){
                    cb_zan.setEnabled(false);
                    cb_zan.setChecked(true);
                    String prasieNum=String.valueOf(Integer.parseInt(result.getPrasieNum())+1);
                    cb_zan.setText(prasieNum);
                    result.setPrasieNum(prasieNum);
                    for(int i=0,j=infos.size();i<j;i++){
                        OrderData orderData=infos.get(i);
                        if(orderData.getAcStepGuid().equals(result.getAcStepGuid())){
                            orderData.setIsPrasie("1");
                            orderData.setPrasieNum(prasieNum);
                            adapter.notifyItemChanged(i);
                            break;
                        }
                    }
                    ZillaApi.NormalRestAdapter.create(RankingService.class)
                            .dayRankZan(UserInfoModel.getInstance().getToken(),
                                    UserInfoModel.getInstance().getUserId(),
                                    result.getAcStepGuid(),
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData o, Response response) {
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            cb_zan.setEnabled(true);
                                            cb_zan.setChecked(false);
                                            String prasieNum=String.valueOf(Integer.parseInt(result.getPrasieNum())-1);
                                            cb_zan.setText(prasieNum);
                                            result.setPrasieNum(prasieNum);
                                            super.failure(error);
                                        }
                                    });
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        infos=new ArrayList<>();
        manager=new RankManager(this);
        adapter=new RankingRecyclerViewAdapter(getContext(),infos);
        adapter.setOnItemClickListener(new RankingRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1=new Intent(getActivity(),ChartActivity.class);
                intent1.putExtra("isFocusid",infos.get(position).getAccountId());
                getActivity().startActivity(intent1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        if(isDayRank(rankType)){
            //跑团日排名
            manager.getDayOrder(1, new RequestCallback<ResponseData<OrderInfo>>() {
                @Override
                public void success(ResponseData<OrderInfo> orderInfoResponseData, Response response) {
                    try {
                        tv_rank.setText("跑团排名第");
                        tv_rank.append(orderInfoResponseData.getData().getOrderInfo());
                        tv_rank.append("名");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            //跑团周排名
            manager.getWeekOrder(1, new RequestCallback<ResponseData<OrderInfo>>() {
                @Override
                public void success(ResponseData<OrderInfo> orderInfoResponseData, Response response) {
                    try {
                        tv_rank.setText("跑团排名第");
                        tv_rank.append(orderInfoResponseData.getData().getOrderInfo());
                        tv_rank.append("名");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        manager.setCallback(null);
        super.onDestroyView();
    }

    @Override
    public void getResult(RankModel result) {
        if(isLoading){
            isLoading=false;
            adapter.notifyItemRemoved(adapter.getItemCount());
        }else {
            pull.setRefreshing(false);
        }
        if(result==null){
            return;
        }
        this.result=result;
        if(TextUtils.isEmpty(result.getPageCount())){
            totalPage=0;
        }else{
            totalPage=Integer.parseInt(result.getPageCount());
        }
        if(TextUtils.isEmpty(result.getOrderPhoto())){
            Picasso.with(getContext()).load(R.drawable.img_default).into(header_image);
        }else {
            Picasso.with(getContext()).load(AddressManager.get("photoHost")+result.getOrderPhoto())
                    .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(header_image);
        }
        tv_name.setText(result.getOrderName());
        tv_step.setText(result.getOrderSteps());
        SpannableString ss=new SpannableString("步");
        ss.setSpan(new AbsoluteSizeSpan(9,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_step.append(ss);
        if("0".equals(result.getIsPrasie())){
            //未点赞
            cb_zan.setEnabled(true);
            cb_zan.setChecked(false);
        }else {
            cb_zan.setEnabled(false);
            cb_zan.setChecked(true);
        }
        if(TextUtils.isEmpty(result.getAcStepGuid())){
            cb_zan.setEnabled(false);
            cb_zan.setChecked(false);
        }
        if(!isDayRank(rankType)){
            cb_zan.setEnabled(false);
            cb_zan.setChecked(true);
        }
        cb_zan.setText(result.getPrasieNum());
        //计算进度条
        List<OrderData> orderDatas=result.getOrderData();
        if(orderDatas==null||orderDatas.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        float stepPer;
        OrderData firstDate=orderDatas.get(0);
        float step = Float.parseFloat(firstDate.getStepCount());
        stepPer=90/step;
        int mineStep=Integer.parseInt(result.getOrderSteps());
        progressBar.setProgress((int) (stepPer*mineStep));
        if(pageIndex==1){
            infos.clear();
        }
        infos.addAll(orderDatas);
        adapter.notifyDataSetChanged();

    }

    private boolean isDayRank(int rankType){
        return rankType==RankingActivity.DAY_RANKING;
    }
}
