package com.softtek.lai.module.bodygame2sr.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.net.BodyGameService;
import com.softtek.lai.module.bodygame2.adapter.SPPCAdapter;
import com.softtek.lai.module.bodygame2.adapter.SaiKuangAdapter;
import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.module.bodygame2.model.Tips;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.bodygame2.view.SearchPcActivity;
import com.softtek.lai.module.bodygame2sr.present.SRManager;
import com.softtek.lai.module.counselor.view.ApplyAssistantActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.counselor.view.SPHonorActivity;
import com.softtek.lai.module.jingdu.view.JingduActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message.net.MessageService;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.module.review.view.ReviewActivity;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.view.AskDetailActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.MyGridView;
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

@InjectLayout(R.layout.fragment_bodygame_sr)
public class BodyGameSRFragment extends LazyBaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,ObservableScrollView.ScrollViewListener{

    @InjectView(R.id.scroll)
    ObservableScrollView scroll;
    @InjectView(R.id.toolbar)
    RelativeLayout relativeLayout;
    @InjectView(R.id.rl_color)
    RelativeLayout rl_color;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.ll_right)
    LinearLayout ll_right;
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


    @InjectView(R.id.tv_person_num)
    TextView tv_person_num;
    @InjectView(R.id.tv_loss_weight)
    TextView tv_loss_weight;
    @InjectView(R.id.tv_fuce_per)
    TextView tv_fuce_per;
    @InjectView(R.id.tv_server_rank)
    TextView tv_server_rank;
    @InjectView(R.id.tv_loss_rank)
    TextView tv_loss_rank;
    @InjectView(R.id.tv_fuce_rank)
    TextView tv_fuce_rank;

    @InjectView(R.id.rl_student_more)
    RelativeLayout rl_student_more;
    @InjectView(R.id.mlv)
    ListView mlv;
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
    @InjectView(R.id.ll_shengqing)
    LinearLayout ll_shengqing;
    @InjectView(R.id.ll_sp_review)
    LinearLayout ll_sp_review;
    @InjectView(R.id.ll_jindu)
    LinearLayout ll_jindu;
    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    @InjectView(R.id.et_person)
    EditText et_person;
    @InjectView(R.id.fl_search)
    FrameLayout fl_search;
    //请求
    SRManager manager;

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
        ll_shengqing.setOnClickListener(this);
        ll_sp_review.setOnClickListener(this);
        ll_jindu.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        scroll.setScrollViewListener(this);
        fl_video.setOnClickListener(this);
        fl_search.setOnClickListener(this);
        ll_right.setOnClickListener(this);
        pull.setOnRefreshListener(this);
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
                Intent jumpStudent=new Intent(getContext(),GameActivity.class);
                int zubie=0;
                if("女子140斤以下".equals(model.getGroupName())){
                    zubie=6;
                }else if("女子140斤以上".equals(model.getGroupName())){
                    zubie=5;
                }else if("男子180斤以下".equals(model.getGroupName())){
                    zubie=4;
                }else if("男子180斤以上".equals(model.getGroupName())){
                    zubie=1;
                }
                jumpStudent.putExtra("zubie",zubie);
                startActivity(jumpStudent);
            }
        });
        scroll.post(new Runnable() {
            public void run() {
                scroll.scrollTo(0,0);
            }
        });
    }

    @Override
    protected void initDatas() {
        manager=new SRManager();
        roate= AnimationUtils.loadAnimation(getContext(),R.anim.rotate);
        sppcAdapter=new SPPCAdapter(getContext(),pcModels);
        saiKuangAdapter=new SaiKuangAdapter(getContext(),competitionModels);
        mlv.setAdapter(sppcAdapter);
        mgv.setAdapter(saiKuangAdapter);
        iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.email));
        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(), 100));
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);


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
        if(getContext() instanceof BodyGameSRActivity){
            BodyGameSRActivity activity=(BodyGameSRActivity)getContext();
            activity.setAlpha(0);
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        if(isCreatedView()){
            et_person.setText("");
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
    SPBodyGameInfo info;
    public void onloadCompleted(SPBodyGameInfo info){
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
            tv_person_num.setText(StringUtil.convertValue1(info.getPcCount()));
            tv_loss_weight.setText(StringUtil.convertValue1(info.getLossTotal()));
            tv_fuce_per.setText(StringUtil.convertValue1(info.getRetest()));
            tv_server_rank.setText(StringUtil.convertValue1(info.getPcNum()));
            tv_loss_rank.setText(StringUtil.convertValue1(info.getLossNum()));
            tv_fuce_rank.setText(StringUtil.convertValue1(info.getRNum()));
            pcModels.clear();
            competitionModels.clear();
            pcModels.addAll(info.getSp_pc_three());
            competitionModels.addAll(info.getCompetition());
            sppcAdapter.notifyDataSetChanged();
            ListViewUtil.setListViewHeightBasedOnChildren(mlv);
            saiKuangAdapter.notifyDataSetChanged();
            if(StringUtils.isNotEmpty(info.getTips_video_name())){
                tv_video_name.setText(info.getTips_video_name());
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

    }
    @Override
    public void onResume() {
        super.onResume();
        UserModel model= UserInfoModel.getInstance().getUser();
        if(model==null){
            return;
        }
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {

        } else {
            ZillaApi.NormalRestAdapter.create(MessageService.class).getMessageRead(UserInfoModel.getInstance().getToken(), new Callback<ResponseData>() {
                @Override
                public void success(ResponseData listResponseData, Response response) {
                    int status = listResponseData.getStatus();
                    switch (status) {
                        case 200:
                            iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.has_email));
                            break;
                        default:
                            iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.email));
                            break;
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }
    Animation roate;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                getActivity().finish();
                break;
            case R.id.ll_right:
                startActivity(new Intent(getContext(), MessageActivity.class));
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
                                        iv_refresh.clearAnimation();
                                        if(listResponseData.getStatus()==200){
                                            List<TotolModel> models=listResponseData.getData();
                                            try {
                                                tv_totalperson.setText(models.get(0).getTotal_person());
                                                tv_total_loss.setText(models.get(0).getTotal_loss());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        iv_refresh.clearAnimation();
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
            case R.id.rl_student_more:
                //我的学员 更多
                startActivity(new Intent(getContext(), JingduActivity.class));
                break;
            case R.id.ll_tip1:
                //第一个tip
                Tips tip1=info.getTips_content().get(0);
                AskHealthyModel ask=new AskHealthyModel();
                ask.setTips_Link(tip1.getTips_Link());
                Intent tip1Intent=new Intent(getContext(), AskDetailActivity.class);
                tip1Intent.putExtra("ask",ask);
                startActivity(tip1Intent);
                break;
            case R.id.ll_tip2:
                //第二个tip
                Tips tip2=info.getTips_content().get(1);
                AskHealthyModel ask2=new AskHealthyModel();
                ask2.setTips_Link(tip2.getTips_Link());
                Intent tip2Intent=new Intent(getContext(), AskDetailActivity.class);
                tip2Intent.putExtra("ask",ask2);
                startActivity(tip2Intent);
                break;
            case R.id.rl_tip:
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
            case R.id.rl_saikuang:
                //大赛更多
                startActivity(new Intent(getContext(), GameActivity.class));
                break;
            case R.id.ll_shengqing:
                //申请助教
                startActivity(new Intent(getContext(), ApplyAssistantActivity.class));
                break;
            case R.id.ll_sp_review:
                //往期回顾
                startActivity(new Intent(getContext(), ReviewActivity.class));
                break;
            case R.id.ll_jindu:
                //当期进度
                startActivity(new Intent(getContext(), JingduActivity.class));
                break;
            case R.id.ll_honor:
                //荣誉榜
                Intent intent = new Intent(getContext(), SPHonorActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_search:
                String text=et_person.getText().toString();
                if(StringUtils.isNotEmpty(text)){
                    et_person.setText("");
                    Intent search=new Intent(getContext(),SearchPcActivity.class);
                    search.putExtra("value",text.replaceAll("%","").replaceAll("_",""));
                    startActivity(search);
                }
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y<=0){
            pull.setEnabled(true);
        }else {
            pull.setEnabled(false);
        }
        float alpha=(1f*y/950);
        if(getContext() instanceof BodyGameSPActivity){
            BodyGameSPActivity activity=(BodyGameSPActivity)getContext();
            activity.setAlpha(alpha);
            rl_color.setAlpha(alpha);
        }
    }

    @Override
    public void onRefresh() {
        manager.getSPHomeInfo(this);
    }

}
