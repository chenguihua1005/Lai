package com.softtek.lai.module.ranking.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.ranking.adapter.RankingRecyclerViewAdapter;
import com.softtek.lai.module.ranking.model.OrderData;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.module.ranking.persenter.RankManager;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
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

    private RankingRecyclerViewAdapter adapter;
    private List<OrderData> infos;
    private RankManager manager;
    private int rankType;
    private int pageIndex=1;

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
            //全国日排名
            manager.getDayRank(0,pageIndex);
        }else {
            //全国周排名
            manager.getWeekRank(0,pageIndex);
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
        pull.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex=1;
                if(isDayRank(rankType)){
                    //全国日排名
                    manager.getDayRank(0,pageIndex);
                }else {
                    //全国周排名
                    manager.getWeekRank(0,pageIndex);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        infos=new ArrayList<>();
        manager=new RankManager(this);
        adapter=new RankingRecyclerViewAdapter(getContext(),infos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        manager.setCallback(null);
        super.onDestroyView();
    }

    @Override
    public void getResult(RankModel result) {
        pull.setRefreshing(false);
        if(result==null){
            return;
        }
        if(TextUtils.isEmpty(result.getOrderPhoto())){
            Picasso.with(getContext()).load(R.drawable.img_default).into(header_image);
        }else {
            Picasso.with(getContext()).load(AddressManager.get("photoHost")+result.getOrderPhoto())
                    .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(header_image);
        }
        tv_name.setText(result.getOrderName());
        tv_rank.setText("跑团排名第");
        tv_rank.append(result.getOrderInfo());
        tv_rank.append("名");
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
        cb_zan.setText(result.getPrasieNum());
        //计算进度条
        float stepPer=0;
        List<OrderData> orderDatas=result.getOrderData();
        if(orderDatas!=null&&!orderDatas.isEmpty()){
            OrderData firstDate=orderDatas.get(0);
            float step = Float.parseFloat(firstDate.getStepCount());
            stepPer=90/step;
        }
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
