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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.ranking.adapter.RankingRecyclerViewAdapter;
import com.softtek.lai.module.ranking.event.RankZan;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_national)
public class NationalFragment extends LazyBaseFragment implements RankManager.RankManagerCallback{

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
    @InjectView(R.id.rl_mine)
    RelativeLayout rl_mine;

    private RankingRecyclerViewAdapter adapter;
    private List<OrderData> infos;
    private RankManager manager;
    private int rankType;
    private int pageIndex=1;

    private static final int LOADCOUNT=5;
    private int lastVisitableItem;
    private boolean isLoading=false;
    private int totalPage=0;

    private OrderInfo info;

    public NationalFragment() {
        // Required empty public constructor
    }

    public static NationalFragment getInstance(int rankType){
        NationalFragment fragment=new NationalFragment();
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
            manager.getDayOrder(0, new RequestCallback<ResponseData<OrderInfo>>() {
                @Override
                public void success(ResponseData<OrderInfo> orderInfoResponseData, Response response) {
                    if(orderInfoResponseData.getStatus()==200){
                        try {
                            info=orderInfoResponseData.getData();
                            tv_rank.setText("全国排名第");
                            tv_rank.append(info.getOrderInfo());
                            tv_rank.append("名");
                            tv_step.setText(info.getSteps());
                            SpannableString ss=new SpannableString("步");
                            ss.setSpan(new AbsoluteSizeSpan(9,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv_step.append(ss);
                            if("0".equals(info.getIsPrasie())){
                                //未点赞
                                cb_zan.setEnabled(true);
                                cb_zan.setChecked(false);
                            }else {
                                cb_zan.setEnabled(false);
                                cb_zan.setChecked(true);
                            }
                            if("0".equals(info.getSteps())){
                                cb_zan.setEnabled(false);
                                cb_zan.setChecked(true);
                            }
                            cb_zan.setText(info.getPrasieNum());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }else {
            //跑团周排名
            manager.getWeekOrder(0, new RequestCallback<ResponseData<OrderInfo>>() {
                @Override
                public void success(ResponseData<OrderInfo> orderInfoResponseData, Response response) {
                    if(orderInfoResponseData.getStatus()==200){
                        try {
                            OrderInfo info=orderInfoResponseData.getData();
                            tv_rank.setText("全国排名第");
                            tv_rank.append(info.getOrderInfo());
                            tv_rank.append("名");
                            tv_step.setText(info.getSteps());
                            SpannableString ss=new SpannableString("步");
                            ss.setSpan(new AbsoluteSizeSpan(9,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv_step.append(ss);
                            if("0".equals(info.getIsPrasie())){
                                //未点赞
                                cb_zan.setEnabled(true);
                                cb_zan.setChecked(false);
                            }else {
                                cb_zan.setEnabled(false);
                                cb_zan.setChecked(true);
                            }
                            if("0".equals(info.getSteps())){
                                cb_zan.setEnabled(false);
                                cb_zan.setChecked(true);
                            }
                            cb_zan.setText(info.getPrasieNum());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
        if(isDayRank(rankType)){
            //全国日排名
            manager.getDayRank(0,pageIndex);
        }else {
            //全国周排名
            manager.getWeekRank(0,pageIndex);
        }
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
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
//                if(info!=null){
                    Intent intent1=new Intent(getActivity(),ChartActivity.class);
                    intent1.putExtra("isFocusid",UserInfoModel.getInstance().getUserId()+"");
                    getActivity().startActivity(intent1);
//                }
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
                                //全国日排名
                                manager.getDayRank(0,pageIndex);
                            }else {
                                //全国周排名
                                manager.getWeekRank(0,pageIndex);
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
                    manager.getDayOrder(0, new RequestCallback<ResponseData<OrderInfo>>() {
                        @Override
                        public void success(ResponseData<OrderInfo> orderInfoResponseData, Response response) {
                            if(orderInfoResponseData.getStatus()==200){
                                try {
                                    info=orderInfoResponseData.getData();
                                    tv_rank.setText("全国排名第");
                                    tv_rank.append(info.getOrderInfo());
                                    tv_rank.append("名");
                                    tv_step.setText(info.getSteps());
                                    SpannableString ss=new SpannableString("步");
                                    ss.setSpan(new AbsoluteSizeSpan(9,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    tv_step.append(ss);
                                    if("0".equals(info.getIsPrasie())){
                                        //未点赞
                                        cb_zan.setEnabled(true);
                                        cb_zan.setChecked(false);
                                    }else {
                                        cb_zan.setEnabled(false);
                                        cb_zan.setChecked(true);
                                    }
                                    if("0".equals(info.getSteps())){
                                        cb_zan.setEnabled(false);
                                        cb_zan.setChecked(true);
                                    }
                                    cb_zan.setText(info.getPrasieNum());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }else {
                    //跑团周排名
                    manager.getWeekOrder(0, new RequestCallback<ResponseData<OrderInfo>>() {
                        @Override
                        public void success(ResponseData<OrderInfo> orderInfoResponseData, Response response) {
                            if(orderInfoResponseData.getStatus()==200){
                                try {
                                    info=orderInfoResponseData.getData();
                                    tv_rank.setText("全国排名第");
                                    tv_rank.append(info.getOrderInfo());
                                    tv_rank.append("名");
                                    tv_step.setText(info.getSteps());
                                    SpannableString ss=new SpannableString("步");
                                    ss.setSpan(new AbsoluteSizeSpan(9,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    tv_step.append(ss);
                                    if("0".equals(info.getIsPrasie())){
                                        //未点赞
                                        cb_zan.setEnabled(true);
                                        cb_zan.setChecked(false);
                                    }else {
                                        cb_zan.setEnabled(false);
                                        cb_zan.setChecked(true);
                                    }
                                    if("0".equals(info.getSteps())){
                                        cb_zan.setEnabled(false);
                                        cb_zan.setChecked(true);
                                    }
                                    cb_zan.setText(info.getPrasieNum());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }
                if(isDayRank(rankType)){
                    //全国日排名
                    manager.getDayRank(0,pageIndex);
                }else {
                    //全国周排名
                    manager.getWeekRank(0,pageIndex);
                }
            }
        });
        cb_zan.setEnabled(false);
        cb_zan.setChecked(true);
        cb_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info!=null){
                    cb_zan.setEnabled(false);
                    cb_zan.setChecked(true);
                    String prasieNum=String.valueOf(Integer.parseInt(info.getPrasieNum())+1);
                    cb_zan.setText(prasieNum);
                    info.setPrasieNum(prasieNum);
                    for(int i=0,j=infos.size();i<j;i++){
                        OrderData orderData=infos.get(i);
                        if(orderData.getAcStepGuid().equals(info.getAcStepGuid())){
                            orderData.setIsPrasie("1");
                            orderData.setPrasieNum(prasieNum);
                            adapter.notifyItemChanged(i);
                            break;
                        }
                    }
                    EventBus.getDefault().post(new RankZan(info.getAcStepGuid(),false,true));
                    ZillaApi.NormalRestAdapter.create(RankingService.class)
                            .dayRankZan(UserInfoModel.getInstance().getToken(),
                                    UserInfoModel.getInstance().getUserId(),
                                    info.getAcStepGuid(),
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData o, Response response) {

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            cb_zan.setEnabled(true);
                                            cb_zan.setChecked(false);
                                            String prasieNum=String.valueOf(Integer.parseInt(info.getPrasieNum())-1);
                                            cb_zan.setText(prasieNum);
                                            info.setPrasieNum(prasieNum);
                                            super.failure(error);
                                        }
                                    });
                }
            }
        });

        rl_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(info!=null){
                    Intent intent1=new Intent(getActivity(),ChartActivity.class);
                    intent1.putExtra("isFocusid",UserInfoModel.getInstance().getUserId()+"");
                    getActivity().startActivity(intent1);
//                }
            }
        });
    }

    @Override
    protected void initDatas() {
        infos=new ArrayList<>();
        manager=new RankManager(this);
        adapter=new RankingRecyclerViewAdapter(getContext(),false,infos);
        adapter.setOnItemClickListener(new RankingRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1=new Intent(getActivity(),ChartActivity.class);
                intent1.putExtra("isFocusid",infos.get(position).getAccountId());
                intent1.putExtra("step",Integer.parseInt(infos.get(position).getStepCount()));
                getActivity().startActivity(intent1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        manager.setCallback(null);
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onRefreshZan(RankZan zan){
        if(zan.isRunGroup()&&info!=null){//表示是跑团那边发送过来的通知并且当前界面已经请求过了，就可以做处理
            if(zan.isMine()){//如果是自己给自己点咱
                cb_zan.setEnabled(false);
                cb_zan.setChecked(true);
                String prasieNum=String.valueOf(Integer.parseInt(info.getPrasieNum())+1);
                cb_zan.setText(prasieNum);
            }
            //更新当前界面列表
            for(int i=0,j=infos.size();i<j;i++){
                OrderData orderData=infos.get(i);
                if(orderData.getAcStepGuid().equals(zan.getGuid())){
                    orderData.setIsPrasie("1");
                    String prasieNum=String.valueOf(Integer.parseInt(orderData.getPrasieNum())+1);
                    orderData.setPrasieNum(prasieNum);
                    adapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    @Override
    public void getResult(RankModel result) {
        if(isLoading==true){
            isLoading=false;
            adapter.notifyItemRemoved(adapter.getItemCount());
        }else {
            pull.setRefreshing(false);
        }
        if(result==null){
            return;
        }
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

//        if(TextUtils.isEmpty(result.getAcStepGuid())){
//            cb_zan.setEnabled(false);
//            cb_zan.setChecked(true);
//        }
        //不是日排名就不可以点赞
        if(!isDayRank(rankType)){
            cb_zan.setEnabled(false);
            cb_zan.setChecked(true);
        }
        //计算进度条
        List<OrderData> orderDatas=result.getOrderData();
        if(orderDatas==null||orderDatas.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
            float stepPer;
            OrderData firstDate=orderDatas.get(0);
            float step = Float.parseFloat(firstDate.getStepCount());
            stepPer=90/step;
            int mineStep=Integer.parseInt(result.getOrderSteps());
            progressBar.setProgress((int) (stepPer*mineStep));
        }
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
