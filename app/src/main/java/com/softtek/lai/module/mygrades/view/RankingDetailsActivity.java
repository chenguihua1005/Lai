package com.softtek.lai.module.mygrades.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.mygrades.adapter.RankInfoAdapter;
import com.softtek.lai.module.mygrades.adapter.TabContentAdapter;
import com.softtek.lai.module.mygrades.model.RankSelectModel;
import com.softtek.lai.module.mygrades.model.RunGroupModel;
import com.softtek.lai.module.mygrades.net.GradesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/16/2016.
 * 我的成绩-排名详情
 */
@InjectLayout(R.layout.activity_ranking_details)
public class RankingDetailsActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, ViewPager.OnPageChangeListener {
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

    List<Fragment> fragments = new ArrayList<>();

    private List<RankSelectModel> rankSelectModelList = new ArrayList<RankSelectModel>();
    //private RankSelectModel rankSelectModel;
    public RankInfoAdapter rankInfoAdapter;

   /* private FragmentManager manager;

    private DayRankModel dayRankModel;

    private IGradesPresenter iGradesPresenter;*/
    private GradesService gradesService;

    long accoutid;
    String rungroupname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //iGradesPresenter = new GradesImpl();
        gradesService = ZillaApi.NormalRestAdapter.create(GradesService.class);

        //当前用户所参加的跑团
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accoutid = Long.parseLong(userInfoModel.getUser().getUserid());
        doGetNowRgName(accoutid);
        init();

        rankInfoAdapter = new RankInfoAdapter(this, rankSelectModelList);
        list_group.setAdapter(rankInfoAdapter);
        list_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv1 = (ImageView) parent.getChildAt(0).findViewById(R.id.rbtn_select);
                ImageView iv2 = (ImageView) parent.getChildAt(1).findViewById(R.id.rbtn_select);
                //跑团排名
                if (position == 0) {
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.INVISIBLE);
                    ((DayRankFragment) fragments.get(0)).updateDayRankStatus(1);
                    ((WeekRankFragment) fragments.get(1)).updateWeekRankStatus(1);
                    tv_rungroupname.setText(/*rungroupname*/"跑团排名");
                    iv1.setImageResource(R.drawable.radiosel);
                    iv2.setImageResource(R.drawable.radiocir);
                }
                //全国排名
                if (position == 1) {
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.INVISIBLE);
                    tv_rungroupname.setText("全国排名");
                    ((DayRankFragment) fragments.get(0)).updateDayRankStatus(0);
                    ((WeekRankFragment) fragments.get(1)).updateWeekRankStatus(0);
                    iv1.setImageResource(R.drawable.radiocir);
                    iv2.setImageResource(R.drawable.radiosel);
                }
            }
        });
    }

    //获取当前用户所参加的跑团
    public void doGetNowRgName(long accountid) {
        String token = UserInfoModel.getInstance().getToken();
        gradesService.doGetNowRgName(token, accountid, new Callback<ResponseData<RunGroupModel>>() {
            @Override
            public void success(ResponseData<RunGroupModel> runTeamModelResponseData, Response response) {
                int status = runTeamModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("成功" + runTeamModelResponseData.getData());
                        rungroupname = runTeamModelResponseData.getData().getRgName();
                        tv_rungroupname.setText(/*runTeamModelResponseData.getData().getRgName()*/"跑团排名");
                        break;
                    case 100:
                        break;
                    default:
                        Log.i(runTeamModelResponseData.getMsg());
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
        //1跑团排名，0全国排名,默认是跑团排名
        //manager = getFragmentManager();
        DayRankFragment dayRankFragment = new DayRankFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", 1);
        dayRankFragment.setArguments(bundle1);
        fragments.add(dayRankFragment);

        WeekRankFragment weekRankFragment = new WeekRankFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("id", 1);
        weekRankFragment.setArguments(bundle2);
        fragments.add(weekRankFragment);

        tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(), fragments));
        tab.setupWithViewPager(tab_content);
        //根据标志flag，判断是日排名（0）还是周排名（1），加载fragment
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 1);
        tab_content.setCurrentItem(flag);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.Rl_list:
                if (RL_rungroup.getVisibility() == View.VISIBLE) {
                    Iv_fold.setImageResource(R.drawable.unfold);
                    RL_rungroup.setVisibility(View.INVISIBLE);
                } else if (RL_rungroup.getVisibility() == View.INVISIBLE) {
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
        Log.i("页面切换到===》" + position);
        switch (position) {
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
