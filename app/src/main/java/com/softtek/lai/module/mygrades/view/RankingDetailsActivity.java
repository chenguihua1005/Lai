package com.softtek.lai.module.mygrades.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.mygrades.adapter.RankAdapter;
import com.softtek.lai.module.mygrades.adapter.RankInfoAdapter;
import com.softtek.lai.module.mygrades.adapter.TabContentAdapter;
import com.softtek.lai.module.mygrades.model.RankSelectModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/16/2016.
 * 我的成绩-排名详情
 */
@InjectLayout(R.layout.activity_ranking_details)
public class RankingDetailsActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener {
    //toobar
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.RL_rungroup)
    RelativeLayout RL_rungroup;
    @InjectView(R.id.list_group)
    ListView list_group;
    @InjectView(R.id.Iv_fold)
    ImageView Iv_fold;

    @InjectView(R.id.Rl_list)
    RelativeLayout Rl_list;

    @InjectView(R.id.tv_rungroupname)
    TextView tv_rungroupname;

    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.tab_content)
    ViewPager tab_content;

    List<Fragment> fragments=new ArrayList<>();

    private List<RankSelectModel> rankSelectModelList = new ArrayList<RankSelectModel>();
    private RankSelectModel rankSelectModel;
    public RankInfoAdapter rankInfoAdapter;

    int biaozhi;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        rankInfoAdapter = new RankInfoAdapter(this,rankSelectModelList);
        list_group.setAdapter(rankInfoAdapter);
        list_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选择图标显示设置
                for(int i=0;i<2;i++){
                    ImageView iv= (ImageView) parent.getChildAt(i).findViewById(R.id.rbtn_select);
                    iv.setImageResource(R.drawable.radiocir);
                }
                ImageView iv= (ImageView) view.findViewById(R.id.rbtn_select);
                iv.setImageResource(R.drawable.radiosel);

                //跑团排名
                if (position==0){
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.GONE);
                    //获取list的值------------
                    tv_rungroupname.setText("跑团排名");

                    biaozhi=0;
                    DayRankFragment dayRankFragment=new DayRankFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("id",biaozhi);
                    dayRankFragment.setArguments(bundle1);
                    fragments.add(dayRankFragment);
                    WeekRankFragment weekRankFragment=new WeekRankFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("id", biaozhi);
                    weekRankFragment.setArguments(bundle2);
                    fragments.add(weekRankFragment);
                    tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
                    tab.setupWithViewPager(tab_content);
                    //flag判断是我的日排名还是周排名
                    Intent intent=getIntent();
                    int flag=intent.getIntExtra("flag",1);
                    tab_content.setCurrentItem(flag);


                    //18516262463
                }
                //全国排名
                if (position==1){
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.GONE);
                    //获取list的值------------
                    tv_rungroupname.setText("全国排名");
                    biaozhi=1;
                    DayRankFragment dayRankFragment=new DayRankFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("id",biaozhi);
                    dayRankFragment.setArguments(bundle1);
                    fragments.add(dayRankFragment);
                    WeekRankFragment weekRankFragment=new WeekRankFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("id", biaozhi);
                    weekRankFragment.setArguments(bundle2);
                    fragments.add(weekRankFragment);

                    tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
                    tab.setupWithViewPager(tab_content);
                    Intent intent=getIntent();
                    int flag=intent.getIntExtra("flag",0);
                    tab_content.setCurrentItem(flag);
                }
            }
        });
    }

    private void init() {
        //RGName 跑团名称
        RankSelectModel p1 = new RankSelectModel("跑团排名");
        rankSelectModelList.add(p1);
        RankSelectModel p2 = new RankSelectModel("全国排名");
        rankSelectModelList.add(p2);
    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("排名详情");
        Rl_list.setOnClickListener(this);
        //根据标志flag，判断是日排名（0）还是周排名（1），加载fragment
//        manager = getFragmentManager();
//        DayRankFragment dayRankFragment=new DayRankFragment();
//        Bundle bundle1 = new Bundle();
//        bundle1.putInt("id",biaozhi);
//        Log.i("------------bundle1...biaozhi:"+biaozhi);
//        dayRankFragment.setArguments(bundle1);
//        fragments.add(dayRankFragment);
//
//        WeekRankFragment weekRankFragment=new WeekRankFragment();
//        Bundle bundle2 = new Bundle();
//        bundle2.putInt("id", biaozhi);
//        Log.i("------------bundle2...biaozhi:"+biaozhi);
//        weekRankFragment.setArguments(bundle2);
//        fragments.add(weekRankFragment);
//
//        tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
//        tab.setupWithViewPager(tab_content);
//        Intent intent=getIntent();
//        int flag=intent.getIntExtra("flag",0);
//        tab_content.setCurrentItem(flag);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.Rl_list:
                if (RL_rungroup.getVisibility()==View.VISIBLE){
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.GONE);
                }else if (RL_rungroup.getVisibility()==View.GONE){
                    RL_rungroup.setVisibility(View.VISIBLE);
                    Iv_fold.setImageResource(R.drawable.retract);
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
