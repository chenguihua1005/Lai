package com.softtek.lai.module.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ggx.jerryguan.viewflow.CircleFlowIndicator;
import com.ggx.jerryguan.viewflow.ViewFlow;
import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.home.adapter.AdvAdapter;
import com.softtek.lai.module.home.cache.HomeInfoCache;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.retest.Write;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.CustomGridView;
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
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements View.OnTouchListener,PullToRefreshBase.OnRefreshListener<ScrollView>{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.vf_adv)
    ViewFlow vf_adv;

    @InjectView(R.id.cfi_circle)
    CircleFlowIndicator cfi_circle;

    @InjectView(R.id.gv_model)
    CustomGridView gv_model;

    @InjectView(R.id.pull)
    PullToRefreshScrollView pull;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.iv_activity)
    ImageView iv_activity;

    @InjectView(R.id.iv_healthy)
    ImageView iv_healthy;

    @InjectView(R.id.button)
    Button button;

    private ACache aCache;

    private IHomeInfoPresenter homeInfoPresenter;

    private List<HomeInfo> advList=new ArrayList<>();

    @Override
    protected void initViews() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aCache=ACache.get(getActivity(), Constants.USER_ACACHE_DATA_DIR);
                User user= (User) aCache.getAsObject(Constants.USER_ACACHE_KEY);
                switch(user.getUserrole())
                {
                    case "0":
                    {
                        Intent intent = new Intent(getContext(), Counselor.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());

                    }
                    break;
                    case "1":
                    {
                        Intent intent = new Intent(getActivity(), Write.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "2":
                    {
                        Intent intent = new Intent(getActivity(), Write.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "3":
                    {
                        Intent intent = new Intent(getActivity(), Write.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "4":
                    {
                        Intent intent = new Intent(getActivity(), Write.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "5":
                    {
                        Intent intent = new Intent(getActivity(), Write.class);
                        startActivity(intent);
                        Log.i("用户角色",user.getUserrole());
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                }

            }
        });

    }

    @Override
    protected void initDatas() {
        homeInfoPresenter=new HomeInfoImpl(getContext());
        tv_title.setText("莱APP");
        vf_adv.setFlowIndicator(cfi_circle);
        vf_adv.setOnTouchListener(this);
        //加载本地缓存数据
        aCache=ACache.get(getContext(),Constants.HOME_CACHE_DATA_DIR);
        String json=aCache.getAsString(Constants.HOEM_ACACHE_KEY);
        if(json!=null&&!json.equals("")){
            Gson gson=new Gson();
            HomeInfoCache infoCache=gson.fromJson(json,HomeInfoCache.class);
            for(HomeInfo info:infoCache.getInfos()){
                switch (info.getImg_Type()){
                    case "0":
                        advList.add(info);
                        break;
                    case "1":
                        Picasso.with(getContext()).load(info.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_activity);
                        break;
                    case "2":
                        Picasso.with(getContext()).load(info.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_healthy);
                        break;
                }
            }
        }else{
            System.out.println("没有缓存数据");
        }
        vf_adv.setAdapter(new AdvAdapter(getContext(),advList));
        List<String> datas=new ArrayList<>();
        for(int i=0;i<10;i++){
            datas.add("item");
        }
        BaseAdapter adapter=new ZillaAdapter<String>(getContext(),datas,R.layout.gridview_item,ViewHolderModel.class);
        gv_model.setAdapter(adapter);
        pull.setOnRefreshListener(this);
        //第一次加载自动刷新
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pull.setRefreshing();
            }
        }, 1000);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        getViewPage(v.getParent()).requestDisallowInterceptTouchEvent(true);
        return true;
    }

    //下拉刷新回调
    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        System.out.println("正在加载......");
        homeInfoPresenter.getHomeInfoData(pull);
    }

    static class ViewHolderModel {

    }

    private ViewParent getViewPage(ViewParent v){

        if(v!=null&&v.getClass().getName().equals("android.support.v4.view.ViewPager")){
            Log.i(v.getClass().getName());
            return v;
        }
        return getViewPage(v.getParent());
    }

    private ViewParent getScrollView(ViewParent v){
        if(v!=null&&v.getClass().getName().equals("com.handmark.pulltorefresh.library.PullToRefreshScrollView")){
            Log.i(v.getClass().getName());
            return v;
        }
        return getScrollView(v.getParent());
    }

    @Subscribe
    public void onEventRefresh(List<HomeInfo> infos){
        advList.clear();
        for(HomeInfo info:infos){
            switch (info.getImg_Type()){
                case "0":
                    advList.add(info);
                    break;
                case "1":
                    Picasso.with(getContext()).load(info.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_activity);
                    break;
                case "2":
                    Picasso.with(getContext()).load(info.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_healthy);
                    break;
            }
        }
        ((AdvAdapter)vf_adv.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
