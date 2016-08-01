package com.softtek.lai.module.bodygame2pc.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.net.BodyGameService;
import com.softtek.lai.module.bodygame2.model.Tips;
import com.softtek.lai.module.bodygame2pc.model.PCBodyGameInfo;
import com.softtek.lai.module.bodygame2pc.present.PCManager;
import com.softtek.lai.module.bodygamest.view.StudentHonorGridActivity;
import com.softtek.lai.module.bodygamest.view.StudentScoreActivity;
import com.softtek.lai.module.bodygamest.view.UploadPhotoActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.lossweightstory.model.LogStoryModel;
import com.softtek.lai.module.lossweightstory.view.LossWeightStoryActivity;
import com.softtek.lai.module.lossweightstory.view.NewStoryActivity;
import com.softtek.lai.module.message.net.MessageService;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.pastreview.view.ClassListActivity;
import com.softtek.lai.module.studentbasedate.view.DimensionChartFragmentPC;
import com.softtek.lai.module.studentbasedate.view.LossWeightChartFragmentPC;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.view.LogDetailActivity;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.view.AskDetailActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame_pc)
public class BodyGamePCFragment extends LazyBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,ObservableScrollView.ScrollViewListener{
    //头部
    @InjectView(R.id.scroll)
    ObservableScrollView scroll;
    @InjectView(R.id.toolbar)
    RelativeLayout relativeLayout;
    @InjectView(R.id.rl_color)
    RelativeLayout rl_color;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    LinearLayout fl_right;
    @InjectView(R.id.iv_banner)
    ImageView iv_banner;
    @InjectView(R.id.iv_refresh)
    ImageView iv_refresh;
    @InjectView(R.id.tv_totalperson)
    TextView tv_totalperson;
    @InjectView(R.id.tv_total_loss)
    TextView tv_total_loss;
    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;

    //菜单键上面的数据显示
    @InjectView(R.id.tv_loss_weight)
    TextView tv_loss_weight;//减重斤数
    @InjectView(R.id.tv_yaowei)
    TextView tv_yaowei;//腰围变化
    @InjectView(R.id.tv_tizhi_per)
    TextView tv_tizhi_per;//体脂率变化
    @InjectView(R.id.tv_loss_per)
    TextView tv_loss_per;//减重百分比
    @InjectView(R.id.tv_loss_rank)
    TextView tv_loss_rank;//减重班级排名
    @InjectView(R.id.tv_yaowei_rank)
    TextView tv_yaowei_rank;//腰围班级排名
    @InjectView(R.id.tv_tizhi_rank)
    TextView tv_tizhi_rank;//体脂率班级排名
    @InjectView(R.id.tv_loss_per_rank)
    TextView tv_loss_per_rank;//减重百分比班级排名

    //菜单键
    @InjectView(R.id.ll_upload_photo)
    LinearLayout ll_upload_photo;
    @InjectView(R.id.ll_saikuang)
    LinearLayout ll_saikuang;
    @InjectView(R.id.ll_chengjidan)
    LinearLayout ll_chengjidan;
    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    @InjectView(R.id.ll_review)
    LinearLayout ll_review;

    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;//减重前
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;//减重后
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;//减重前斤数
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;//减重后斤数
    @InjectView(R.id.tv_loss_after_tip)
    TextView tv_loss_after_tip;
    @InjectView(R.id.tv_send_story)
    TextView tv_send_story;//发布减重故事按钮
    @InjectView(R.id.ll_story)
    LinearLayout ll_story;//减重故事查看详情
    @InjectView(R.id.tv_day)
    TextView tv_day;//天
    @InjectView(R.id.tv_month)
    TextView tv_month;//月
    @InjectView(R.id.iv_image)
    ImageView iv_image;//减重故事照片
    @InjectView(R.id.tv_content)
    TextView tv_content;//减重故事内容
    @InjectView(R.id.tv_story_more)
    TextView tv_story_more;//查看更多故事
    @InjectView(R.id.tv_tip_more)
    TextView tv_tip_more;//查看更多tips按钮
    //tips内容
    @InjectView(R.id.tv_video_name)
    TextView tv_video_name;
    @InjectView(R.id.tv_title1)
    TextView tv_title1;
    @InjectView(R.id.tv_title2)
    TextView tv_title2;
    @InjectView(R.id.tv_tag1)
    TextView tv_tag1;
    @InjectView(R.id.tv_tag2)
    TextView tv_tag2;
    @InjectView(R.id.ll_tips_content)
    LinearLayout ll_tips_content;
    @InjectView(R.id.fl_video)
    FrameLayout fl_video;
    @InjectView(R.id.ll_tip1)
    LinearLayout ll_tip1;
    @InjectView(R.id.ll_tip2)
    LinearLayout ll_tip2;
    @InjectView(R.id.im_icon_tip)
    ImageView im_icon_tip;
    @InjectView(R.id.im_icon_tip2)
    ImageView im_icon_tip2;
    @InjectView(R.id.iv_video_image)
    ImageView iv_video_image;

    @InjectView(R.id.tablayout)
    TabLayout tab;
    @InjectView(R.id.tabcontent)
    ViewPager tab_content;

    @InjectView(R.id.tv_no_story)
    TextView tv_no_story;

    PCManager manager;
    List<Fragment> fragments=new ArrayList<>();


    @Override
    protected void initViews() {
        if(DisplayUtil.getSDKInt()>18){
            int status=DisplayUtil.getStatusHeight(getActivity());
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
            params.topMargin=status;
            relativeLayout.setLayoutParams(params);
        }
        iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.email));
        ll_left.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        ll_tip2.setOnClickListener(this);
        ll_tip1.setOnClickListener(this);
        ll_upload_photo.setOnClickListener(this);
        ll_saikuang.setOnClickListener(this);
        ll_chengjidan.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        ll_review.setOnClickListener(this);
        fl_video.setOnClickListener(this);
        tv_story_more.setOnClickListener(this);
        tv_tip_more.setOnClickListener(this);
        tv_send_story.setOnClickListener(this);

        fl_right.setOnClickListener(this);

        scroll.setScrollViewListener(this);
        pull.setOnRefreshListener(this);
        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(), 100));
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

    }

    @Override
    protected void initDatas() {
        manager=new PCManager();
        roate= AnimationUtils.loadAnimation(getContext(),R.anim.rotate);
        LossWeightChartFragmentPC lwcf= LossWeightChartFragmentPC.newInstance();
        DimensionChartFragmentPC dcf=DimensionChartFragmentPC.newInstance();
        fragments.add(lwcf);
        fragments.add(dcf);
        tab_content.setAdapter(new StudentDetailFragmentAdapter(getFragmentManager(), fragments));
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setupWithViewPager(tab_content);

    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if(isCreatedView()){
            scroll.post(new Runnable() {
                public void run() {
                    scroll.scrollTo(0,0);
                }
            });
        }
        if(getContext() instanceof BodyGamePCActivity){
            BodyGamePCActivity activity=(BodyGamePCActivity)getContext();
            activity.setAlpha(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserModel model= UserInfoModel.getInstance().getUser();
        if(model==null){
            return;
        }
        String userrole = model.getUserrole();
        if (!String.valueOf(Constants.VR).equals(userrole)) {
            ZillaApi.NormalRestAdapter.create(MessageService.class).getMessageRead(UserInfoModel.getInstance().getToken(), new Callback<ResponseData>() {
                @Override
                public void success(ResponseData listResponseData, Response response) {
                    int status = listResponseData.getStatus();
                    try {
                        switch (status) {
                            case 200:
                                iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.has_email));
                                break;
                            default:
                                iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.email));
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }

    @Override
    protected void lazyLoad() {
        //第一次加载自动刷新
        pull.post(new Runnable() {
            @Override
            public void run() {
                pull.setRefreshing(true);
            }
        });
        onRefresh();
    }
    PCBodyGameInfo info;
    public void onLoadCompleted(PCBodyGameInfo info){
        try {
            pull.setRefreshing(false);
            if(info!=null){
                this.info=info;
                String basePath= AddressManager.get("photoHost");
                //首页banner
                if(StringUtils.isNotEmpty(info.getBanner())){
                    Picasso.with(getContext()).load(basePath+info.getBanner()).placeholder(R.drawable.default_icon_rect)
                            .error(R.drawable.default_icon_rect).into(iv_banner);
                }
                tv_totalperson.setText(StringUtil.convertValue1(info.getTotalPc()));
                tv_total_loss.setText(StringUtil.convertValue1(info.getTotalLoss()));
                tv_loss_weight.setText(StringUtil.convertValue5(info.getPCLoss()));
                tv_yaowei.setText(StringUtil.convertValue5(info.getPCwaistline()));
                tv_tizhi_per.setText(StringUtil.convertValue4(info.getPCPysical()));
                tv_loss_per.setText(StringUtil.convertValue4(info.getPCLossPrecent()));
                tv_loss_rank.setText(info.getPCLossOrde());
                tv_yaowei_rank.setText(info.getPCwaistlineOrder());
                tv_tizhi_rank.setText(info.getPCPysicalOrder());
                tv_loss_per_rank.setText(info.getPCLossPrecentOrder());
                tv_loss_before.setText(StringUtil.getFloatValue(info.getPCLossBefore())+"斤");
                float lossAfter=StringUtil.getFloat(info.getPCLossAfter());
                tv_loss_after.setText(lossAfter==0?"尚未复测":lossAfter+"斤");
                if(lossAfter==0){
                    tv_loss_after_tip.setVisibility(View.GONE);
                }else{
                    tv_loss_after_tip.setVisibility(View.VISIBLE);
                }
                if(StringUtils.isNotEmpty(info.getPCLossBeforeImg())){
                    Picasso.with(getContext()).load(basePath+info.getPCLossBeforeImg()).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(iv_loss_before);
                }
                if(StringUtils.isNotEmpty(info.getPCLossAfterImg())){
                    Picasso.with(getContext()).load(basePath+info.getPCLossAfterImg()).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(iv_loss_after);
                }
                if(StringUtils.isNotEmpty(info.getPCStoryId())){
                    tv_no_story.setVisibility(View.GONE);
                    ll_story.setVisibility(View.VISIBLE);
                    ll_story.setOnClickListener(this);
                    String date=info.getPCStoryDate();
                    int day=DateUtil.getInstance(DateUtil.yyyy_MM_dd).getDay(date);
                    int month=DateUtil.getInstance(DateUtil.yyyy_MM_dd).getMonth(date);
                    tv_day.setText(day+"");
                    tv_month.setText(month+"月");
                    tv_content.setText(info.getPCStoryContent());
                    if(StringUtils.isNotEmpty(info.getPCStoryImg())){
                        Picasso.with(getContext()).load(basePath+info.getPCStoryImg()).placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(iv_image);
                    }
                }else{
                    ll_story.setVisibility(View.GONE);
                    tv_no_story.setVisibility(View.VISIBLE);
                }
                if(StringUtils.isNotEmpty(info.getTips_Video_id())){
                    tv_video_name.setText(info.getTips_video_name());
                    //tv_video_time.setText(StringUtil.convertValue3(info.getTips_video_timelen()));
                    if(StringUtils.isNotEmpty(info.getTips_video_backPicture())){
                        Picasso.with(getContext()).load(basePath+info.getTips_video_backPicture())
                                .fit().placeholder(R.drawable.default_icon_rect)
                                .error(R.drawable.default_icon_rect).into(iv_video_image);
                    }
                    fl_video.setVisibility(View.VISIBLE);
                }else{
                    fl_video.setVisibility(View.GONE);
                }
                List<Tips> tips=info.getTips_content();
                if(tips==null||tips.isEmpty()){
                    ll_tips_content.setVisibility(View.GONE);
                }else{
                    ll_tip2.setVisibility(View.INVISIBLE);
                    for (int i=0;i<tips.size();i++){
                        Tips tip=tips.get(i);
                        String mask=tip.getTips_TagTitle();
                        if(i==0){
                            tv_title1.setText(tip.getTips_Title());
                            tv_tag1.setText(tip.getTips_TagTitle());
                            if("运动健身".equals(mask)){
                                tv_tag1.setTextColor(Color.parseColor("#ffa300"));
                                im_icon_tip.setBackgroundResource(R.drawable.mask_org);
                            }else if("营养养身".equals(mask)){
                                tv_tag1.setTextColor(Color.parseColor("#75ba2b"));
                                im_icon_tip.setBackgroundResource(R.drawable.mask_green);
                            }else if("养身保健知识".equals(mask)){
                                tv_tag1.setTextColor(Color.parseColor("#98dee6"));
                                im_icon_tip.setBackgroundResource(R.drawable.mask_blue);
                            }else if("健康饮食".equals(mask)){
                                tv_tag1.setTextColor(Color.parseColor("#cfdc3d"));
                                im_icon_tip.setBackgroundResource(R.drawable.mask_cyan);
                            }else{
                                tv_tag1.setTextColor(Color.parseColor("#ffa300"));
                                im_icon_tip.setBackgroundResource(R.drawable.mask_org);
                            }
                        }else if(i==1){
                            ll_tip2.setVisibility(View.VISIBLE);
                            tv_title2.setText(tip.getTips_Title());
                            tv_tag2.setText(tip.getTips_TagTitle());
                            if("运动健身".equals(mask)){
                                tv_tag2.setTextColor(Color.parseColor("#ffa300"));
                                im_icon_tip2.setBackgroundResource(R.drawable.mask_org);
                            }else if("营养养身".equals(mask)){
                                tv_tag2.setTextColor(Color.parseColor("#75ba2b"));
                                im_icon_tip2.setBackgroundResource(R.drawable.mask_green);
                            }else if("养身保健知识".equals(mask)){
                                tv_tag2.setTextColor(Color.parseColor("#98dee6"));
                                im_icon_tip2.setBackgroundResource(R.drawable.mask_blue);
                            }else if("健康饮食".equals(mask)){
                                tv_tag2.setTextColor(Color.parseColor("#cfdc3d"));
                                im_icon_tip2.setBackgroundResource(R.drawable.mask_cyan);
                            }else{
                                tv_tag2.setTextColor(Color.parseColor("#ffa300"));
                                im_icon_tip2.setBackgroundResource(R.drawable.mask_org);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Animation roate;
    public static final int SEND_NEW_STORY=1;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                getActivity().startActivity(new Intent(getActivity(), HomeActviity.class));
                break;
            case R.id.fl_right:
                startActivity(new Intent(getContext(), Message2Activity.class));
                break;
            case R.id.iv_refresh:
                //刷新
                iv_refresh.startAnimation(roate);
                roate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ZillaApi.NormalRestAdapter.create(BodyGameService.class).doGetTotal(new Callback<ResponseData<List<TotolModel>>>() {
                                    @Override
                                    public void success(ResponseData<List<TotolModel>> listResponseData, Response response) {
                                        try {
                                            iv_refresh.clearAnimation();
                                            if(listResponseData.getStatus()==200){
                                                List<TotolModel> models=listResponseData.getData();
                                                    tv_totalperson.setText(models.get(0).getTotal_person());
                                                    tv_total_loss.setText(models.get(0).getTotal_loss());
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        try {
                                            iv_refresh.clearAnimation();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }, 500);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.ll_upload_photo:
                if (info!=null&&("2".equals(info.getClassStatus())||"3".equals(info.getClassStatus()))){
                    startActivity(new Intent(getContext(), UploadPhotoActivity.class));
                }
                break;
            case R.id.ll_saikuang:
                startActivity(new Intent(getContext(), GameActivity.class));
                break;
            case R.id.ll_chengjidan:
                if (info!=null&&("2".equals(info.getClassStatus())||"3".equals(info.getClassStatus()))) {
                    startActivity(new Intent(getContext(), StudentScoreActivity.class));
                }
                break;
            case R.id.ll_honor:
                //荣誉榜只能看当前进行中的班级
                if (info!=null&&("2".equals(info.getClassStatus())||"3".equals(info.getClassStatus()))) {
                    startActivity(new Intent(getContext(), StudentHonorGridActivity.class));
                }
                break;
            case R.id.ll_review:
                startActivity(new Intent(getContext(), ClassListActivity.class));
                break;
            case R.id.tv_send_story:
                //发布减重故事
                //如果是正在进行中的班级才可以发布故事
                if(info!=null&&("2".equals(info.getClassStatus())||"3".equals(info.getClassStatus()))){
                    startActivityForResult(new Intent(getContext(),NewStoryActivity.class),SEND_NEW_STORY);
                }
                break;
            case R.id.tv_story_more:
                //减重故事更多
                UserModel model=UserInfoModel.getInstance().getUser();
                if (model!=null){
                    Intent intent=new Intent(getContext(),LossWeightStoryActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_story:
                //点击减重故事查看详情
                if(info!=null&&StringUtils.isNotEmpty(info.getPCStoryId())){
                    Intent intent=new Intent(getContext(),LogDetailActivity.class);
                    if("2".equals(info.getClassStatus())||"3".equals(info.getClassStatus())){
                        //往期0是往期1不是往期
                        intent.putExtra("review",1);
                    }
                    LossWeightLogModel lossWeightLogModel=new LossWeightLogModel();
                    lossWeightLogModel.setLossLogId(info.getPCStoryId());
                    intent.putExtra("log",lossWeightLogModel);
                    startActivity(intent);
                }
                break;
            case R.id.tv_tip_more:
                //tips更多
                startActivity(new Intent(getContext(), TipsActivity.class));
                break;
            case R.id.fl_video:
                //视频
                if(info!=null&&StringUtils.isNotEmpty(info.getTips_video_name())){
                    Uri uri=Uri.parse(AddressManager.get("photoHost")+info.getTips_video_url());
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri,"video/*");
                    startActivity(intent);
                }
                break;
            case R.id.ll_tip1:
                //tips1
                Tips tip1=info.getTips_content().get(0);
                AskHealthyModel ask=new AskHealthyModel();
                ask.setTips_Link(tip1.getTips_Link());
                Intent tip1Intent=new Intent(getContext(), AskDetailActivity.class);
                tip1Intent.putExtra("ask",ask);
                startActivity(tip1Intent);
                break;
            case R.id.ll_tip2:
                //tips2
                Tips tip2=info.getTips_content().get(1);
                AskHealthyModel ask2=new AskHealthyModel();
                ask2.setTips_Link(tip2.getTips_Link());
                Intent tip2Intent=new Intent(getContext(), AskDetailActivity.class);
                tip2Intent.putExtra("ask",ask2);
                startActivity(tip2Intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK&&requestCode==SEND_NEW_STORY){
            tv_no_story.setVisibility(View.GONE);
            ll_story.setVisibility(View.VISIBLE);
            LogStoryModel model= (LogStoryModel) data.getSerializableExtra("story");
            String logId=data.getStringExtra("storyId");
            info.setPCStoryId(logId);
            if(model!=null){
                int day=DateUtil.getInstance().getCurrentDay();
                int month=DateUtil.getInstance().getCurrentMonth();
                tv_day.setText(day+"");
                tv_month.setText(month+"月");
                tv_content.setText(model.getLogContent());
                info.setPCStoryContent(model.getLogContent());
                String photo=model.getPhotoes().split(",")[0];
                info.setPCStoryImg(photo);
                if(StringUtils.isNotEmpty(photo)){
                    Picasso.with(getContext()).load(AddressManager.get("photoHost")+photo).placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(iv_image);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        manager.getSPHomeInfo(this);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y<=0){
            pull.setEnabled(true);
        }else {
            pull.setEnabled(false);
        }
        float alpha=(1f*y/850);
        if(getContext() instanceof BodyGamePCActivity){
            BodyGamePCActivity activity=(BodyGamePCActivity)getContext();
            activity.setAlpha(alpha);
            rl_color.setAlpha(alpha);
        }
    }
}
