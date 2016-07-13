package com.softtek.lai.module.bodygame2.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame2.adapter.SPPCAdapter;
import com.softtek.lai.module.bodygame2.adapter.SaiKuangAdapter;
import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.module.bodygame2.model.Tips;
import com.softtek.lai.module.bodygame2.present.SPManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.MyGridView;
import com.softtek.lai.widgets.MyListView;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame_sp)
public class BodyGameSPFragment extends LazyBaseFragment implements View.OnClickListener,ObservableScrollView.ScrollViewListener{

    @InjectView(R.id.scroll)
    ObservableScrollView scroll;
    @InjectView(R.id.toolbar)
    RelativeLayout relativeLayout;
    @InjectView(R.id.rl_color)
    RelativeLayout rl_color;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_banner)
    ImageView iv_banner;
    @InjectView(R.id.iv_refresh)
    ImageView iv_refresh;
    @InjectView(R.id.tv_totalperson)
    TextView tv_totalperson;
    @InjectView(R.id.tv_total_loss)
    TextView tv_total_loss;


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
    @InjectView(R.id.mlv)
    MyListView mlv;
    private List<SPPCMoldel> pcModels=new ArrayList<>();
    //adapter
    SPPCAdapter sppcAdapter;
    @InjectView(R.id.mgv)
    MyGridView mgv;
    private List<CompetitionModel> competitionModels=new ArrayList<>();
    SaiKuangAdapter saiKuangAdapter;
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
    //请求
    SPManager manager;

    @Override
    protected void initViews() {
        int status=DisplayUtil.getStatusHeight(getActivity());
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
        //学员点击item
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SPPCMoldel model=pcModels.get(position);
                Intent jumpStudent=new Intent(getContext(),PersonalDataActivity.class);
                jumpStudent.putExtra("userId",(long)model.getAccountId());
                jumpStudent.putExtra("classId",(long)model.getClassId());
                startActivity(jumpStudent);
            }
        });
        //大赛点击item
        mgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompetitionModel model=competitionModels.get(position);
                Intent jumpStudent=new Intent(getContext(),PersonalDataActivity.class);
                jumpStudent.putExtra("userId",(long)model.getAccountId());
                jumpStudent.putExtra("classId",(long)model.getClassId());
                startActivity(jumpStudent);
            }
        });
        scroll.post(new Runnable() {
                    public void run() {
                        scroll.smoothScrollTo(0, 0);
                    }
                });
    }

    @Override
    protected void initDatas() {
        manager=new SPManager();
        sppcAdapter=new SPPCAdapter(getContext(),pcModels);
        saiKuangAdapter=new SaiKuangAdapter(getContext(),competitionModels);
        mlv.setAdapter(sppcAdapter);
        mgv.setAdapter(saiKuangAdapter);

    }


    @Override
    protected void lazyLoad() {
        Log.i("BodyGameSPFragment 加载数据");
        manager.getSPHomeInfo(this);
    }

    public void onloadCompleted(SPBodyGameInfo info){
        if(info!=null){
            String basePath=AddressManager.get("photoHost");
            //首页banner
            if(StringUtils.isNotEmpty(info.getBanner())){
                Picasso.with(getContext()).load(basePath+info.getBanner()).placeholder(R.drawable.default_icon_rect)
                        .error(R.drawable.default_icon_rect).into(iv_banner);
            }
            tv_totalperson.setText(StringUtil.convertValue1(info.getTotalPc()));
            tv_total_loss.setText(StringUtil.convertValue1(info.getTotalLoss()));
            tv_person_num.setText(StringUtil.convertValue1(info.getPcCount()));
            tv_loss_weight.setText(StringUtil.convertValue1(info.getLossTotal()));
            tv_fuce_per.setText(StringUtil.convertValue1(info.getRetest()));
            tv_new_class.setText(StringUtil.convertValue1(info.getNewClass()));
            tv_server_rank.setText(StringUtil.convertValue1(info.getPcNum()));
            tv_loss_rank.setText(StringUtil.convertValue1(info.getLossNum()));
            tv_fuce_rank.setText(StringUtil.convertValue1(info.getRNum()));
            tv_new_student.setText(StringUtil.convertValue1(info.getNewPc()));
            pcModels.clear();
            competitionModels.clear();
            pcModels.addAll(info.getSp_pc_three());
            competitionModels.addAll(info.getCompetition());
            sppcAdapter.notifyDataSetChanged();
            saiKuangAdapter.notifyDataSetChanged();
            tv_video_name.setText(info.getTips_video_name());
            List<Tips> tips=info.getTips_content();
            if(tips==null||tips.isEmpty()){
                ll_tips_content.setVisibility(View.GONE);
            }else{
                ll_tip2.setVisibility(View.INVISIBLE);
                for (int i=0;i<tips.size();i++){
                    Tips tip=tips.get(i);
                    if(i==0){
                        tv_title1.setText(tip.getTips_Title());
                        tv_tag1.setText(tip.getTips_TagTitle());
                        if(StringUtils.isNotEmpty(tip.getTips_Addr())){
                            Picasso.with(getContext()).load(basePath+tip.getTips_Addr()).into(im_icon_tip);
                        }
                    }else if(i==1){
                        ll_tip2.setVisibility(View.VISIBLE);
                        tv_title2.setText(tip.getTips_Title());
                        tv_tag2.setText(tip.getTips_TagTitle());
                        if(StringUtils.isNotEmpty(tip.getTips_Addr())){
                            Picasso.with(getContext()).load(basePath+tip.getTips_Addr()).into(im_icon_tip2);
                        }
                    }
                }
            }



        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                getActivity().finish();
                break;
            case R.id.iv_refresh:
                //刷新
                break;
            case R.id.rl_student_more:
                //我的学员 更多
                break;
            case R.id.ll_tip1:
                //第一个tip
                break;
            case R.id.ll_tip2:
                //第二个tip
                break;
            case R.id.rl_tip:
                //tips更多
                break;
            case R.id.rl_saikuang:
                //大赛更多
                break;
            case R.id.ll_new_student_record:
                //新学员录入
                break;
            case R.id.ll_sp_review:
                //往期回顾
                break;
            case R.id.ll_jindu:
                //当期进度
                break;
            case R.id.ll_honor:
                //荣誉榜
                break;
            case R.id.ll_zhujiao:
                //助教管理
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        float alpha=(1f*y/1000);
        Context context=getContext();
        if(context instanceof BodyGameSPActivity){
            BodyGameSPActivity activity=(BodyGameSPActivity)context;
            activity.setAlpha(alpha);
            rl_color.setAlpha(alpha);
        }

    }
}
