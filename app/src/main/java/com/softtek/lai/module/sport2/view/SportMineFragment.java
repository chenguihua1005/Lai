package com.softtek.lai.module.sport2.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.ranking.view.RankingActivity;
import com.softtek.lai.module.sport2.model.SportMineModel;
import com.softtek.lai.module.sport2.presenter.SportManager;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_sport_mine)
public class SportMineFragment extends LazyBaseFragment implements View.OnClickListener,
        PullToRefreshScrollView.OnRefreshListener<ScrollView>,SportManager.SportManagerCallback{


    public SportMineFragment() {

    }

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.pull_sroll)
    PullToRefreshScrollView pull_sroll;
    @InjectView(R.id.header_image)
    CircleImageView header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_step)
    TextView tv_step;
    @InjectView(R.id.tv_calorie)
    TextView tv_calorie;
    @InjectView(R.id.tv_run_count)
    TextView tv_run_count;
    @InjectView(R.id.tv_day_ranking)
    TextView tv_day_ranking;
    @InjectView(R.id.tv_week_ranking)
    TextView tv_week_ranking;
    @InjectView(R.id.tv_medal)
    TextView tv_medal;
    @InjectView(R.id.tv_run_group)
    TextView tv_run_group;
    @InjectView(R.id.tv_message)
    TextView tv_message;
    @InjectView(R.id.tv_juanzen)
    TextView tv_juanzen;

    @InjectView(R.id.rl_day_rank)
    RelativeLayout rl_day_rank;
    @InjectView(R.id.rl_week_rank)
    RelativeLayout rl_week_rank;

    SportManager manager;

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                pull_sroll.setRefreshing();
            }
        }, 300);
    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        pull_sroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pull_sroll.setOnRefreshListener(this);
        ILoadingLayout startLabelse = pull_sroll.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新中");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = pull_sroll.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据中");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示

        rl_day_rank.setOnClickListener(this);
        rl_week_rank.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("我的");
        manager=new SportManager(this);
    }

    @Override
    public void onDestroyView() {
        manager.setCallback(null);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                getActivity().startActivity(new Intent(getActivity(), HomeActviity.class));
                break;
            case R.id.rl_day_rank:
                //日排名
                Intent dayRank=new Intent(getContext(), RankingActivity.class);
                dayRank.putExtra("rank_type",RankingActivity.DAY_RANKING);
                startActivity(dayRank);
                break;
            case R.id.rl_week_rank:
                //周排名
                Intent weekRank=new Intent(getContext(), RankingActivity.class);
                weekRank.putExtra("rank_type",RankingActivity.WEEK_RANKING);
                startActivity(weekRank);
                break;
        }
    }

    @Override
    public void getResult(SportMineModel result) {
        pull_sroll.onRefreshComplete();
        Log.i("加载完成////////////////////////////////////////////");
        if(result!=null){
            tv_name.setText(result.getUsername());
            tv_step.setText(String.valueOf(result.getTodayStepCnt()));
            tv_calorie.setText(String.valueOf(result.getTodayKaluliCnt()));
            SpannableString ss=new SpannableString("大卡");
            ss.setSpan(new AbsoluteSizeSpan(12,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_calorie.append(ss);
            tv_run_count.setText(String.valueOf(result.getMoveCnt()));
            tv_run_count.append("次运动");
            tv_day_ranking.setText("您今日排名为第");
            tv_day_ranking.append(result.getTodayStepOdr());
            tv_day_ranking.append("名");
            tv_week_ranking.setText("您本周排名为第");
            tv_week_ranking.append(result.getWeekStepOrder());
            tv_week_ranking.append("名");
            tv_medal.setText("您已获得");
            tv_medal.append(String.valueOf(result.getMedalCnt()));
            tv_medal.append("枚勋章");
            tv_run_group.setText(result.getRGName());
            tv_message.setText("您有");
            tv_message.append(String.valueOf(result.getUnreadmsg()));
            tv_message.append("条未读消息");
            tv_juanzen.setText("您已向康宝莱公益基金会捐赠");
            tv_juanzen.append(String.valueOf(result.getDonatenNum()));
            tv_juanzen.append("元");
            if(TextUtils.isEmpty(result.getPhoto())){
                Picasso.with(getContext()).load(R.drawable.img_default).into(header_image);
            }else {
                Picasso.with(getContext()).load(AddressManager.get("photoHost") + result.getPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(header_image);
            }
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        Log.i("正在加载");
        String str = DateUtil.getInstance().getCurrentDate() + "," +5000;
        manager.getMineData(UserInfoModel.getInstance().getUserId(),str);
    }
}
