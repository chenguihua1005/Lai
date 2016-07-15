package com.softtek.lai.module.bodygame2pc.view;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.net.BodyGameService;
import com.softtek.lai.module.bodygame2.adapter.SPPCAdapter;
import com.softtek.lai.module.bodygame2.adapter.SaiKuangAdapter;
import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.MyGridView;
import com.softtek.lai.widgets.MyListView;
import com.softtek.lai.widgets.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
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
    @InjectView(R.id.tv_send_story)
    TextView tv_send_story;//发布减重故事按钮
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





    @Override
    protected void initViews() {
        int status= DisplayUtil.getStatusHeight(getActivity());
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        params.topMargin=status;
        relativeLayout.setLayoutParams(params);
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
        roate= AnimationUtils.loadAnimation(getContext(),R.anim.rotate);


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

    public void onLoadCompleted(){

    }

    Animation roate;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                getContext().fileList();
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

                break;
            case R.id.ll_saikuang:
                break;
            case R.id.ll_chengjidan:
                break;
            case R.id.ll_honor:
                break;
            case R.id.ll_review:
                break;
            case R.id.tv_story_more:
                //减重故事更多
                break;
            case R.id.tv_tip_more:
                //tips更多
                break;
            case R.id.fl_video:
                //视频
                break;
            case R.id.ll_tip1:
                //tips1
                break;
            case R.id.ll_tip2:
                //tips2
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y<=0){
            pull.setEnabled(true);
        }else {
            pull.setEnabled(false);
        }
        float alpha=(1f*y/950);
        if(getContext() instanceof BodyGamePCActivity){
            BodyGameSPActivity activity=(BodyGameSPActivity)getContext();
            activity.setAlpha(alpha);
            rl_color.setAlpha(alpha);
        }
    }
}
