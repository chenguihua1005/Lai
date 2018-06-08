package com.softtek.lai.module.sport2.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
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
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.laisportmine.view.MyInformationActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.personalPK.view.PKListMineActivity;
import com.softtek.lai.module.ranking.view.RankingActivity;
import com.softtek.lai.module.sport.view.HistorySportListActivity;
import com.softtek.lai.module.sport2.model.SportMineModel;
import com.softtek.lai.module.sport2.presenter.SportManager;
import com.softtek.lai.module.sportchart.view.ChartActivity;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_sport_mine)
public class SportMineFragment extends LazyBaseFragment<SportManager> implements View.OnClickListener,
        PullToRefreshScrollView.OnRefreshListener<ScrollView>, SportManager.SportManagerCallback, Handler.Callback {


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
    @InjectView(R.id.Re_myrunteam)
    RelativeLayout Re_myrunteam;
    @InjectView(R.id.Re_xunzhang)
    RelativeLayout Re_xunzhang;
    @InjectView(R.id.Re_news)
    RelativeLayout Re_news;
    @InjectView(R.id.rl_dynamic)
    RelativeLayout rl_dynamic;
    @InjectView(R.id.Re_personpk)
    RelativeLayout Re_personpk;
    @InjectView(R.id.ll_step)
    LinearLayout ll_step;
    @InjectView(R.id.ll_calorie)
    LinearLayout ll_calorie;

    private static int currentStep;
    private static int serverStep;
    private static final int REQUEST_DELAY = 3;
    private Handler delayHandler = new Handler(this);
    private static int deviation = 0;
    private Messenger clientMessenger;
    //用来接收服务端发送过来的信息使用
    private Messenger getReplyMessage = new Messenger(delayHandler);

    @Override
    public boolean handleMessage(Message msg) {
        //在这里获取服务端发来的信息
        switch (msg.what) {
            case StepService.MSG_FROM_SERVER:
                //获取数据
                Bundle data = msg.getData();
                currentStep = data.getInt("todayStep", 0);
                serverStep = data.getInt("serverStep", 0);
                //更新显示
                try {
                    if (currentStep == 0) {
                        tv_step.setText("0");
                    } else {
                        tv_step.setText(String.valueOf(currentStep));
                        int kaluli = currentStep / 35;
                        tv_calorie.setText(String.valueOf(kaluli));
                        SpannableString ss = new SpannableString("大卡");
                        ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_calorie.append(ss);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //延迟在一次向服务端请求
                delayHandler.sendEmptyMessageDelayed(REQUEST_DELAY, 800);
                break;
            case REQUEST_DELAY:
                //继续向服务端发送请求获取数据
                Message message = Message.obtain(null, StepService.MSG_FROM_CLIENT);
                //携带服务器上的步数
                if (deviation > 0) {
                    int deviationTemp = deviation;
                    Bundle surplusStep = new Bundle();
                    surplusStep.putInt("surplusStep", deviationTemp);
                    message.setData(surplusStep);
                }
                deviation = 0;
                message.replyTo = getReplyMessage;
                try {
                    clientMessenger.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    //用于绑定服务使用
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当连接开始时向服务端发起一个信使，然后服务端会拿到这个信使并返回一个数据
            clientMessenger = new Messenger(service);
            Message message = Message.obtain(null, StepService.MSG_FROM_CLIENT);
            message.replyTo = getReplyMessage;
            try {
                clientMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void lazyLoad() {
        String str = DateUtil.getInstance().getCurrentDate() + "," + currentStep;
        getPresenter().getMineData(UserInfoModel.getInstance().getUserId(), str, false);
    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        Re_myrunteam.setOnClickListener(this);
        Re_xunzhang.setOnClickListener(this);
        Re_news.setOnClickListener(this);
        rl_dynamic.setOnClickListener(this);
        Re_personpk.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_step.setOnClickListener(this);
        ll_calorie.setOnClickListener(this);
        pull_sroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pull_sroll.setOnRefreshListener(this);
        ILoadingLayout startLabelse = pull_sroll.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        rl_day_rank.setOnClickListener(this);
        rl_week_rank.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        currentStep = SharedPreferenceService.getInstance().get("currentStep", 0);
        tv_title.setText("步数");
        setPresenter(new SportManager(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().bindService(new Intent(getContext(), StepService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getNewMsgRemind();

    }

    @Override
    public void onStop() {
        super.onStop();
        //解绑服务
        deviation = 0;
        delayHandler.removeCallbacksAndMessages(null);
        getActivity().unbindService(connection);


    }

    @Override
    public void onPause() {
        super.onPause();
        StringBuilder buffer = new StringBuilder();
        buffer.append(DateUtil.getInstance().getCurrentDate());
        buffer.append(",");
        buffer.append(tv_step.getText().toString());
        StepNetService stepNetService = ZillaApi.NormalRestAdapter.create(StepNetService.class);
        stepNetService.synStepCount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), buffer.toString(), new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.d("请求成功" + responseData.getMsg());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                getActivity().startActivity(new Intent(getActivity(), HomeActviity.class));
                break;
            case R.id.rl_day_rank:
                //日排名
                Intent dayRank = new Intent(getContext(), RankingActivity.class);
                dayRank.putExtra("rank_type", RankingActivity.DAY_RANKING);
                startActivity(dayRank);
                break;
            case R.id.rl_week_rank:
                //周排名
                Intent weekRank = new Intent(getContext(), RankingActivity.class);
                weekRank.putExtra("rank_type", RankingActivity.WEEK_RANKING);
                startActivity(weekRank);
                break;
            /*跳转跑团资料页*/
            case R.id.Re_myrunteam:
                getActivity().startActivity(new Intent(getActivity(), MyInformationActivity.class));
                break;
            /*跳转勋章页*/
            case R.id.Re_xunzhang:
                getActivity().startActivity(new Intent(getActivity(), MyXuZhangActivity.class));
                break;
            /*跳转消息页*/
            case R.id.Re_news:
                getActivity().startActivity(new Intent(getActivity(), Message2Activity.class));
                if (getContext() instanceof LaiSportActivity) {
                    LaiSportActivity activity = (LaiSportActivity) getContext();
                    activity.updateUnread();
                }
                break;
            /*跳转运动历史页*/
            case R.id.rl_dynamic:
                getActivity().startActivity(new Intent(getActivity(), HistorySportListActivity.class));
                break;
            /*跳转我的pk列表页*/
            case R.id.Re_personpk:
                getActivity().startActivity(new Intent(getActivity(), PKListMineActivity.class));
                break;
            case R.id.ll_step:
            case R.id.ll_calorie:
                Intent intent1 = new Intent(getActivity(), ChartActivity.class);
                intent1.putExtra("isFocusid", UserInfoModel.getInstance().getUser().getUserid());
                intent1.putExtra("step", Integer.parseInt(tv_step.getText() + ""));
                getActivity().startActivity(intent1);
                break;
        }
    }

    @Override
    public void getResult(SportMineModel result) {
        deviation = 0;
        if (result != null) {
            int todayStepCnt = result.getTodayStepCnt();
            int currentTemp = currentStep;
            if (todayStepCnt - currentTemp > 0) {
                //用服务器上的步数减去本地第一次同步的服务器上的步数获取误差值
                deviation = todayStepCnt - serverStep;
                tv_step.setText(String.valueOf(todayStepCnt));
            } else {
                tv_step.setText(String.valueOf(currentStep));
            }
            tv_name.setText(result.getUsername());
            tv_calorie.setText(String.valueOf(result.getTodayKaluliCnt()));
            SpannableString ss = new SpannableString("大卡");
            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            if (result.getUnreadmsg() != 0) {
                SpannableString scolor = new SpannableString(String.valueOf(result.getUnreadmsg()));
                scolor.setSpan(new ForegroundColorSpan(Color.RED), 0, scolor.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_message.append(scolor);
            } else {
                tv_message.append(String.valueOf(result.getUnreadmsg()));
            }
            tv_message.append("条未读消息");
            tv_juanzen.setText("您已向\"康宝莱公益基金会\"捐赠");
            SpannableString sc = new SpannableString(String.valueOf(result.getDonatenNum()));
            sc.setSpan(new ForegroundColorSpan(Color.parseColor("#FFA200")), 0, sc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_juanzen.append(sc);
            tv_juanzen.append("元");
            if (TextUtils.isEmpty(result.getPhoto())) {
                Picasso.with(getContext()).load(R.drawable.img_default).into(header_image);
            } else {
                Picasso.with(getContext()).load(AddressManager.get("photoHost") + result.getPhoto()).resizeDimen(R.dimen.head, R.dimen.head).centerCrop().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(header_image);
            }
        }
    }

    @Override
    public void hidden() {
        pull_sroll.onRefreshComplete();
    }

    @Override
    public void setUnReadMsg(String unRead) {
        tv_message.setText("您有");
        if (TextUtils.isEmpty(unRead) || Integer.parseInt(unRead) == 0) {
            tv_message.append("0");
        } else {
            SpannableString scolor = new SpannableString(unRead);
            scolor.setSpan(new ForegroundColorSpan(Color.RED), 0, scolor.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_message.append(scolor);
        }
        tv_message.append("条未读消息");
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        String str = DateUtil.getInstance().getCurrentDate() + "," + currentStep;
        getPresenter().getMineData(UserInfoModel.getInstance().getUserId(), str, true);
    }
}
