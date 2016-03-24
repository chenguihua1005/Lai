package com.softtek.lai.module.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.home.adapter.FragementAdapter;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.eventModel.ActivityEvent;
import com.softtek.lai.module.home.eventModel.ProductEvent;
import com.softtek.lai.module.home.eventModel.SaleEvent;
import com.softtek.lai.module.home.model.FunctionModel;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.RollHeaderView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/15/2016.
 *
 */
@InjectLayout(R.layout.fragment_home2)
public class HomeFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener,SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rhv_adv)
    RollHeaderView rhv_adv;

    @InjectView(R.id.gv_model)
    CustomGridView gv_model;

    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;

    @InjectView(R.id.page)
    ViewPager page;

    @InjectView(R.id.appbar)
    AppBarLayout appBar;

    @InjectView(R.id.tabs)
    TabLayout tab;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    /*@InjectView(R.id.view)
    View view;*/

    private ACache aCache;

    private IHomeInfoPresenter homeInfoPresenter;

    private List<String> advList=new ArrayList<>();

    @Override
    protected void initViews() {
        tv_left.setVisibility(View.INVISIBLE);
        iv_email.setVisibility(View.VISIBLE);
        page.setAdapter(new FragementAdapter(getFragmentManager()));
        //设置tabLayout和viewpage关联
        tab.setupWithViewPager(page);
        tab.setTabMode(TabLayout.MODE_FIXED);
        appBar.addOnOffsetChangedListener(this);
        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(),100));
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        pull.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("莱聚+");

        //载入缓存数据
        homeInfoPresenter.loadCacheData();

        //第一次加载自动刷新

        pull.post(new Runnable() {
            @Override
            public void run() {
                pull.setRefreshing(true);
            }
        });
        onRefresh();
    }



    @Subscribe
    public void onLoadModelFunction(ModelAdapter adapter){
        gv_model.setAdapter(adapter);
    }

    @Subscribe
    public void onEventRefresh(List<HomeInfo> infos){
        advList.clear();
        List<HomeInfo> activitys=new ArrayList<>();
        List<HomeInfo> products=new ArrayList<>();
        List<HomeInfo> sales=new ArrayList<>();
        HomeInfo in=null;
        for(HomeInfo info:infos){
            switch (info.getImg_Type()){
                case "0":
                    advList.add(info.getImg_Addr());
                    break;
                case "1":
                    in=info;
                    activitys.add(info);
                    break;
                case "2":
                    products.add(info);
                    break;
                case "6":
                    sales.add(info);
                    break;
            }
        }
        rhv_adv.setImgUrlData(advList);
        activitys.add(in);
        activitys.add(in);activitys.add(in);
        activitys.add(in);
        activitys.add(in);activitys.add(in);


        EventBus.getDefault().post(new ActivityEvent(activitys));
        EventBus.getDefault().post(new ProductEvent(products));
        EventBus.getDefault().post(new SaleEvent(sales));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        homeInfoPresenter=new HomeInfoImpl(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float num=Math.abs(1f*Math.abs(verticalOffset)/1000);
        //toolbar.setAlpha(num);
        /*if(verticalOffset<0){
            toolbar.setVisibility(View.VISIBLE);
        }

        if(num<=0){
            toolbar.setVisibility(View.GONE);
        }*/

        if(verticalOffset>=0){
            pull.setEnabled(true);

        }else{
            pull.setEnabled(false);

        }
    }

    @Override
    public void onRefresh() {
        System.out.println("正在加载......");
        homeInfoPresenter.getHomeInfoData(pull);
    }
}
