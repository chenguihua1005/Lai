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


    @InjectView(R.id.tv_person_num)
    TextView tv_person_num;
    @InjectView(R.id.tv_loss_weight)
    TextView tv_loss_weight;
    @InjectView(R.id.tv_fuce_per)
    TextView tv_fuce_per;
    @InjectView(R.id.tv_new_class)
    TextView tv_new_class;
    @InjectView(R.id.tv_server_rank)
    TextView tv_server_rank;
    @InjectView(R.id.tv_loss_rank)
    TextView tv_loss_rank;
    @InjectView(R.id.tv_fuce_rank)
    TextView tv_fuce_rank;
    @InjectView(R.id.tv_new_student)
    TextView tv_new_student;

    @InjectView(R.id.rl_student_more)
    RelativeLayout rl_student_more;

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
    @InjectView(R.id.rl_saikuang)
    RelativeLayout rl_saikuang;

    //菜单键
    @InjectView(R.id.ll_new_student_record)
    LinearLayout ll_new_student_record;
    @InjectView(R.id.ll_sp_review)
    LinearLayout ll_sp_review;
    @InjectView(R.id.ll_jindu)
    LinearLayout ll_jindu;
    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    @InjectView(R.id.ll_zhujiao)
    LinearLayout ll_zhujiao;



    @Override
    protected void initViews() {
        int status= DisplayUtil.getStatusHeight(getActivity());
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        params.topMargin=status;
        relativeLayout.setLayoutParams(params);
        ll_left.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        rl_student_more.setOnClickListener(this);
        ll_tip2.setOnClickListener(this);
        ll_tip1.setOnClickListener(this);
        rl_tip.setOnClickListener(this);
        rl_saikuang.setOnClickListener(this);
        ll_new_student_record.setOnClickListener(this);
        ll_sp_review.setOnClickListener(this);
        ll_jindu.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        ll_zhujiao.setOnClickListener(this);
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
