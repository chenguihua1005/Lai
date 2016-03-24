package com.softtek.lai.module.home.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.RollHeaderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 3/15/2016.
 *
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment1 extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ScrollView>{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rhv_adv)
    RollHeaderView rhv_adv;

    @InjectView(R.id.gv_model)
    CustomGridView gv_model;

    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    private ACache aCache;

    private IHomeInfoPresenter homeInfoPresenter;

    private List<String> advList=new ArrayList<>();

    @Override
    protected void initViews() {
        tv_left.setVisibility(View.INVISIBLE);
       iv_email.setVisibility(View.VISIBLE);
        /*button.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent = new Intent(getActivity(), Counselor.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "2":
                    {
                        Intent intent = new Intent(getActivity(), Counselor.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "3":
                    {
                        Intent intent = new Intent(getActivity(), Counselor.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "4":
                    {
                        Intent intent = new Intent(getActivity(), Counselor.class);
                        startActivity(intent);
                        Util.toastMsg(user.getUserrole());
                    }
                    break;
                    case "5":
                    {
                        Util.toastMsg("抱歉，未注册验证不能使用此功能");
                    }
                    break;

                }

            }
        });*/

    }

    @Override
    protected void initDatas() {
        tv_title.setText("莱聚+");

        //载入缓存数据
        homeInfoPresenter.loadCacheData();

        //第一次加载自动刷新
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

            }
        }, 1000);
    }


    //下拉刷新回调
    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        System.out.println("正在加载......");
        //homeInfoPresenter.getHomeInfoData(pull);
    }


    @Subscribe
    public void onLoadModelFunction(ModelAdapter adapter){
        gv_model.setAdapter(adapter);
    }

    @Subscribe
    public void onEventRefresh(List<HomeInfo> infos){
        advList.clear();
        for(HomeInfo info:infos){
            switch (info.getImg_Type()){
                case "0":
                    advList.add(info.getImg_Addr());
                    break;
                case "1":
                   // Picasso.with(getContext()).load(info.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_activity);
                    break;
                case "2":
                   // Picasso.with(getContext()).load(info.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_healthy);
                    break;
            }
        }
        rhv_adv.setImgUrlData(advList);

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
}
