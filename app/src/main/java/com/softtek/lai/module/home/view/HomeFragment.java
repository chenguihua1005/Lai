/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.bodygame.CounselorActivity;
import com.softtek.lai.module.bodygamecc.view.BodyGameCcActivity;
import com.softtek.lai.module.bodygamest.view.StudentActivity;
import com.softtek.lai.module.bodygameyk.view.BodygameYkActivity;
import com.softtek.lai.module.bodygamezj.view.BodygameActivity;
import com.softtek.lai.module.home.adapter.FragementAdapter;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.login.view.RegistActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.RollHeaderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/15/2016.
 *
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

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

    private List<String> advList = new ArrayList<>();
    private RetestPre retestPre;

    @Override
    protected void initViews() {
        tv_left.setVisibility(View.INVISIBLE);
        iv_email.setVisibility(View.VISIBLE);
        page.setAdapter(new FragementAdapter(getFragmentManager()));
        //设置tabLayout和viewpage关联
        tab.setupWithViewPager(page);
        tab.setTabMode(TabLayout.MODE_FIXED);
        appBar.addOnOffsetChangedListener(this);
        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(), 100));
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
        gv_model.setAdapter(new ModelAdapter(getContext()));
        gv_model.setOnItemClickListener(this);
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
    public void onEventRefresh(List<HomeInfoModel> infos) {
        advList.clear();
        for (HomeInfoModel info : infos) {
            switch (info.getImg_Type()) {
                case "0":
                    advList.add(info.getImg_Addr());
                    break;
            }
        }
        rhv_adv.setImgUrlData(advList);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        homeInfoPresenter = new HomeInfoImpl(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float num = Math.abs(1f * Math.abs(verticalOffset) / 1000);
        //toolbar.setAlpha(num);
        /*if(verticalOffset<0){
            toolbar.setVisibility(View.VISIBLE);
        }

        if(num<=0){
            toolbar.setVisibility(View.GONE);
        }*/

        if (verticalOffset >= 0) {
            pull.setEnabled(true);

        } else {
            pull.setEnabled(false);

        }
    }

    @Override
    public void onRefresh() {
        System.out.println("正在加载......");
        homeInfoPresenter.getHomeInfoData(pull);
    }


    /**
     * 功能模块按钮
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserModel user=UserInfoModel.getInstance().getUser();
        switch (position) {
            case 0:
                getIntentByRole(position,Integer.parseInt(user.getUserrole()));
                break;
            case 1:
                startActivity(new Intent(getContext(), StudentActivity.class));
                break;
            case 2:
                //retestPre=new RetestclassImp();
                // retestPre.doPostClient("client_credentials","shhcieurjfn734js","qieow8572jkcv");
                break;
            case 3:
                startActivity(new Intent(getContext(), CounselorActivity.class));
                break;
            case 4:
                startActivity(new Intent(getContext(), WriteActivity.class));
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;


        }
    }

    /**
     * 根据角色获取意图
     * @param position
     * @param role
     * @return
     */
    private Intent getIntentByRole(int position,int role)
    {   Intent intent=null;
        //判断该角色是否拥有此按钮权限
        //....
        //如果有该按钮权限就创建相应的INtent
        if (position==0)
        {
            if (role==5)
            {
                final AlertDialog.Builder information_dialog = new AlertDialog.Builder(getContext());
                information_dialog.setTitle("您当前处于游客模式，需要注册认证").setPositiveButton("现在注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), RegistActivity.class));
                    }
                }).setNegativeButton("稍候", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), BodygameYkActivity.class));

                    }
                }).create().show();
            }
            else if (role==4)
            {
                Util.toastMsg("受邀普通顾客");
            }
            else if (role==3)
            {
                startActivity(new Intent(getContext(), CounselorActivity.class));
            }
            else if (role==2)
            {
                intent=new Intent(getContext(),BodygameActivity.class);
            }
            else if (role==1)
            {
                intent=new Intent(getContext(),StudentActivity.class);
            }
            //未认证的普通顾客
            else if (role==0)
            {
                final AlertDialog.Builder information_dialog = new AlertDialog.Builder(getContext());
                information_dialog.setTitle("参加体管赛需进行身份认证").setPositiveButton("现在认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                    }
                }).setNegativeButton("稍候", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), BodyGameCcActivity.class));

                    }
                }).create().show();

            }
        }


        return intent;
    }
}
