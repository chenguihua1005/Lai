package com.softtek.lai.module.bodygame2pc.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame2.adapter.SPPCAdapter;
import com.softtek.lai.module.bodygame2.adapter.SaiKuangAdapter;
import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.MyGridView;
import com.softtek.lai.widgets.MyListView;
import com.softtek.lai.widgets.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
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
    /*@InjectView(R.id.pull)
    SwipeRefreshLayout pull;*/

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
    @InjectView(R.id.rl_tip)
    RelativeLayout rl_tip;





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
        rl_tip.setOnClickListener(this);
        ll_upload_photo.setOnClickListener(this);
        ll_saikuang.setOnClickListener(this);
        ll_chengjidan.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        ll_review.setOnClickListener(this);
        scroll.setScrollViewListener(this);
        fl_video.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

    }
}
