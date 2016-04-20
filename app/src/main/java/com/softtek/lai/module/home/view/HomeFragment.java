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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.view.CounselorActivity;
import com.softtek.lai.module.bodygamecc.view.BodyGameCcActivity;
import com.softtek.lai.module.bodygamest.view.BodyGamePCActivity;
import com.softtek.lai.module.bodygameyk.view.BodygameYkActivity;
import com.softtek.lai.module.bodygamezj.view.BodygameSRActivity;
import com.softtek.lai.module.counselor.view.SRHonorActivity;
import com.softtek.lai.module.home.adapter.FragementAdapter;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.eventModel.HomeEvent;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.login.view.RegistActivity;
import com.softtek.lai.module.message.view.MessageActivity;
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
public class HomeFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener ,View.OnClickListener{

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
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    private IHomeInfoPresenter homeInfoPresenter;

    private List<String> advList = new ArrayList<>();
    private List<HomeInfoModel> records = new ArrayList<>();
   /* private List<HomeInfoModel> products = new ArrayList<>();
    private List<HomeInfoModel> sales = new ArrayList<>();*/
    private List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void initViews() {
        tv_left.setVisibility(View.INVISIBLE);
        iv_email.setBackgroundResource(R.drawable.email);
        fl_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        ActivityRecordFragment recordFragment=new ActivityRecordFragment();
        /*ProductInfoFragment productInfoFragment=new ProductInfoFragment();
        SaleInfoFragment saleInfoFragment=new SaleInfoFragment();*/
        fragments.add(recordFragment);
        /*fragments.add(productInfoFragment);
        fragments.add(saleInfoFragment);*/
        page.setAdapter(new FragementAdapter(getFragmentManager(),fragments));
        page.setOffscreenPageLimit(3);
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
    public void onEventRefresh(HomeEvent event) {
        advList.clear();
        records.clear();
        /*products.clear();
        sales.clear();*/
        for (HomeInfoModel info : event.getInfos()) {
            switch (info.getImg_Type()) {
                case "0":
                    advList.add(info.getImg_Addr());
                    break;
                case "1":
                    records.add(info);
                    break;
               /* case "2":
                    products.add(info);
                    break;
                case "6":
                    sales.add(info);
                    break;*/
            }
        }
        rhv_adv.setImgUrlData(advList);
        ((ActivityRecordFragment)fragments.get(0)).updateInfo(records);
        /*((ProductInfoFragment)fragments.get(1)).updateInfo(products);
        ((SaleInfoFragment)fragments.get(2)).updateInfo(sales);*/

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
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*startActivity(new Intent(getContext(), HistoryDataActivity.class));
        if(1==1){
            return;
        }*/
        UserInfoModel userInfoModel=UserInfoModel.getInstance();
        int role=Integer.parseInt(userInfoModel.getUser().getUserrole());
        ////判断当前用户是否拥有此按钮权限
        if(userInfoModel.hasPower(position)){
            //如果有则判断更具具体角色进入相应的页面
            switch (position) {
                case Constants.BODY_GAME:
                    intoBodyGamePage(role);
                    break;
                case Constants.LAI_YUNDONG:
                    //startActivity(new Intent(getContext(), StudentActivity.class));
                    new AlertDialog.Builder(getContext()).setMessage("功能开发中敬请期待").create().show();
                    break;
                case Constants.OFFICE:
                    new AlertDialog.Builder(getContext()).setMessage("功能开发中敬请期待").create().show();
                    break;
                case Constants.LAI_EXCLE:
                    new AlertDialog.Builder(getContext()).setMessage("功能开发中敬请期待").create().show();
                    //startActivity(new Intent(getContext(), CounselorActivity.class));
                    break;
                case Constants.LAI_SHOP:
                    new AlertDialog.Builder(getContext()).setMessage("功能开发中敬请期待").create().show();
                    //startActivity(new Intent(getContext(), CreatFlleActivity.class));
                    break;
            }

        }else{
            //如果本身没有该按钮权限则根据不同身份提示用户，进行下一步操作
            AlertDialog.Builder information_dialog=null;
            switch (Integer.parseInt(userInfoModel.getUser().getUserrole())){
                case Constants.VR:
                    //游客若没有此功能，可能是未登录，提示请先登录
                    information_dialog = new AlertDialog.Builder(getContext());
                    information_dialog.setTitle("您当前处于游客模式，请先登录后再试").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                    }).setNegativeButton("稍候", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).create().show();
                    break;
                case Constants.NC:
                case Constants.INC:
                case Constants.PC:
                    information_dialog = new AlertDialog.Builder(getContext());
                    information_dialog.setTitle("请先进行身份认证后在试").setPositiveButton("认证", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到身份认证界面
                            startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                        }
                    }).setNegativeButton("稍候", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).create().show();
                    break;
                case Constants.SR:
                    break;
                case Constants.SP:
                    break;
            }
        }

    }

    /**
     * 根据角色进入相应的体管赛页面
     * @param role
     * @return
     */
    private void intoBodyGamePage(int role){
        //受邀未认证成功就是普通用户，认证成功就是高级用户
        AlertDialog.Builder information_dialog=null;
        if(role== Constants.VR){
            //提示用户让他注册或者直接进入2个功能的踢馆赛模块
            information_dialog = new AlertDialog.Builder(getContext());
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
        }else if(role==Constants.NC){
            //提示用户让他进行身份认证否则进入2个功能的踢馆赛模块
            information_dialog = new AlertDialog.Builder(getContext());
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
        }else if(role==Constants.INC){
            //提示用户让他进行身份认证否则进入2个功能的踢馆赛模块
            Util.toastMsg("受邀普通顾客");
        }else if(role==Constants.PC){
            //直接进入踢馆赛学员版
            startActivity(new Intent(getContext(),BodyGamePCActivity.class));
        }else if(role==Constants.SR){
            //进入踢馆赛助教版
            startActivity(new Intent(getContext(), BodygameSRActivity.class));
        }else if(role==Constants.SP){
            //进入踢馆赛顾问版
            startActivity(new Intent(getContext(), CounselorActivity.class));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_email:
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
        }
    }
}
