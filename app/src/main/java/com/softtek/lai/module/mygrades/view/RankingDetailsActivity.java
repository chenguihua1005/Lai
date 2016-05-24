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
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.adapter.RankAdapter;
import com.softtek.lai.module.mygrades.adapter.RankInfoAdapter;
import com.softtek.lai.module.mygrades.adapter.TabContentAdapter;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.module.mygrades.model.RankSelectModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/16/2016.
 * 我的成绩-排名详情
 */
@InjectLayout(R.layout.activity_ranking_details)
public class RankingDetailsActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener,ViewPager.OnPageChangeListener{
    //toobar
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.RL_rungroup)
    LinearLayout RL_rungroup;
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
    String ranking="当前用户未参加跑团";
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private DayRankModel dayRankModel;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iGradesPresenter = new GradesImpl();
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);

        //接口信息：跑团数据1，全国数据0,当前用户所参加的跑团orderRGName
//        getCurrentDateOrder(1);
        getCurrentDateOrder(0);

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
                    RL_rungroup.setVisibility(View.INVISIBLE);
                    ((DayRankFragment)fragments.get(0)).updateDayRankStatus(0);
                    ((WeekRankFragment)fragments.get(1)).updateWeekRankStatus(0);
                    //获取list的值------------
                    tv_rungroupname.setText(ranking);
                    //biaozhi=0;
//                    DayRankFragment dayRankFragment=new DayRankFragment();
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putInt("id",0);
//                    dayRankFragment.setArguments(bundle1);
//                    fragments.add(dayRankFragment);
//                    WeekRankFragment weekRankFragment=new WeekRankFragment();
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putInt("id", 0);
//                    weekRankFragment.setArguments(bundle2);
//                    fragments.add(weekRankFragment);
//                    tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
//                    tab.setupWithViewPager(tab_content);
//                    //tab_content.getCurrentItem();
//
//                    //flag判断是我的日排名还是周排名
//                    Intent intent=getIntent();
//                    int flag=intent.getIntExtra("flag",1);
//                    tab_content.setCurrentItem(flag);


                    //18516262463
                }
                //全国排名
                if (position==1){
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.INVISIBLE);
                    //获取list的值------------
                    tv_rungroupname.setText("全国排名");
                    ((WeekRankFragment)fragments.get(1)).updateWeekRankStatus(1);
                    ((DayRankFragment)fragments.get(0)).updateDayRankStatus(1);
                  //  biaozhi=1;
//                    DayRankFragment dayRankFragment=new DayRankFragment();
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putInt("id",1);
//                    dayRankFragment.setArguments(bundle1);
//                    fragments.add(dayRankFragment);
//                    WeekRankFragment weekRankFragment=new WeekRankFragment();
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putInt("id", 1);
//                    weekRankFragment.setArguments(bundle2);
//                    fragments.add(weekRankFragment);
//
//                    tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
//                    tab.setupWithViewPager(tab_content);
//                    Intent intent=getIntent();
//                    int flag=intent.getIntExtra("flag",0);
//                    tab_content.setCurrentItem(flag);
                }
            }
        });
    }
    //获取当前用户所参加的跑团
    public void getCurrentDateOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentDateOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
            @Override
            public void success(ResponseData<DayRankModel> dayRankModelResponseData, Response response) {
                int status=dayRankModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        if (dayRankModelResponseData.getData().getOrderRGName().isEmpty()){
                            ranking="当前用户未参加跑团";
                            tv_rungroupname.setText(ranking);
                            //Util.toastMsg("跑团排名isEmpty");
                        }else {
                            ranking=dayRankModelResponseData.getData().getOrderRGName();
                            tv_rungroupname.setText(ranking);
                        }
                        //Util.toastMsg("我的日排名--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的日排名--查询出bug");
                        break;
                }
            }
            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    private void init() {
        //RGName 跑团名称
        //RankSelectModel p1 = new RankSelectModel(ranking);

        RankSelectModel p1 = new RankSelectModel(ranking);
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
        //0跑团排名，1全国排名
        manager = getFragmentManager();
        DayRankFragment dayRankFragment=new DayRankFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id",1);
       // Log.i("-----------------------bundle1...biaozhi:"+biaozhi);
        dayRankFragment.setArguments(bundle1);
        fragments.add(dayRankFragment);

        WeekRankFragment weekRankFragment=new WeekRankFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("id", 1);
       // Log.i("----------------------bundle2...biaozhi:"+biaozhi);
        weekRankFragment.setArguments(bundle2);
        fragments.add(weekRankFragment);

        tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
       // tab_content.addOnPageChangeListener(this);
//        tab_content.setOnPageChangeListener(OnCheckedChanged);
        tab.setupWithViewPager(tab_content);
        //模式滚动
        //tab.setTabMode(TabLayout.MODE_SCROLLABLE);
//        设置屏幕页面限制
//        tab_content.setOffscreenPageLimit(9);
        Intent intent=getIntent();
        int flag=intent.getIntExtra("flag",0);
        tab_content.setCurrentItem(flag);
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
                    RL_rungroup.setVisibility(View.INVISIBLE);
                }else if (RL_rungroup.getVisibility()==View.INVISIBLE){
                    RL_rungroup.setVisibility(View.VISIBLE);
                    Iv_fold.setImageResource(R.drawable.retract);
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            Log.i("页面切换到===》"+position);
            switch (position)
            {
                case 0:
                    Util.toastMsg("更新日排名");
//                    ((DayRankFragment)fragments.get(0)).updateDayRankStatus();
                    break;
                case 1:
                    Util.toastMsg("更新周排名");
//                    ((WeekRankFragment)fragments.get(1)).updateWeekRankStatus();
                    break;
            }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
