package com.softtek.lai.module.bodygamezj.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.bodygame.view.TipsActivity;
import com.softtek.lai.module.counselor.view.CounselorClassListActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.jingdu.view.JingduActivity;
import com.softtek.lai.module.retest.view.RetestActivity;
import com.softtek.lai.module.review.view.ReviewActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame_sr)
public class BodygameSRActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.iv_advzj)
    ImageView iv_advzj;
    @InjectView(R.id.tv_totalzj)
    ImageView tv_totalzj;
    @InjectView(R.id.tv_total_losszj)
    TextView tv_total_losszj;
    @InjectView(R.id.tv_totalpersonzj)
    TextView tv_totalpersonzj;

    @InjectView(R.id.ll_review)
    LinearLayout ll_review;
    @InjectView(R.id.ll_tiguansai)
    LinearLayout ll_tiguansai;
    @InjectView(R.id.ll_process)
    LinearLayout ll_process;
    @InjectView(R.id.ll_assistant)
    LinearLayout ll_assistant;
    @InjectView(R.id.ll_saikuang)
    LinearLayout ll_saikuang;
    @InjectView(R.id.ll_rongyu)
    LinearLayout ll_rongyu;
    @InjectView(R.id.ll_tips)
    LinearLayout ll_tips;
    @InjectView(R.id.tv_fucenumzj)
    TextView tv_fucenumzj;
    @InjectView(R.id.ll_fuce)
    LinearLayout ll_fuce;

    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());


    private ITiGuanSai iTiGuanSai;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ll_left.setOnClickListener(this);
        tv_totalzj.setOnClickListener(this);
        ll_tips.setOnClickListener(this);
        ll_fuce.setOnClickListener(this);
        ll_review.setOnClickListener(this);
        ll_tiguansai.setOnClickListener(this);
        ll_process.setOnClickListener(this);
        ll_saikuang.setOnClickListener(this);
        ll_rongyu.setOnClickListener(this);
        ll_assistant.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（助教版）");
        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.getTiGuanSai();
        iTiGuanSai.doGetFuceNum(loginid);
        iTiGuanSai.doGetTotal();
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tips:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_fuce:
                startActivity(new Intent(this, RetestActivity.class));
                break;
            case R.id.tv_totalzj:
                iTiGuanSai.doGetTotal();
                break;
            case R.id.ll_review:
                startActivity(new Intent(this, ReviewActivity.class));
                break;
            case R.id.ll_tiguansai:
                Intent zhujiao=new Intent(this, CounselorClassListActivity.class);
                startActivity(zhujiao);
                break;
            case R.id.ll_process:
                startActivity(new Intent(this, JingduActivity.class));
                break;
            case R.id.ll_saikuang:
                //大赛赛况
                startActivity(new Intent(this,GameActivity.class));
                break;
            case R.id.ll_rongyu:
                //荣誉榜

                break;
            case R.id.ll_assistant:
                //申请助教
                break;
        }
    }
    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai){
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_advzj);


    }
    @Subscribe
    public void onEvent1(FuceNumModel fuceNum){
        if (Integer.parseInt(fuceNum.getCount())>10)
        {
            tv_fucenumzj.setVisibility(View.VISIBLE);
            tv_fucenumzj.setText("10+");
        }
        else if (Integer.parseInt(fuceNum.getCount())!=10&&fuceNum.getCount()!=""){
            tv_fucenumzj.setVisibility(View.VISIBLE);
            tv_fucenumzj.setText(fuceNum.getCount());
        }

    }
    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels){

        tv_totalpersonzj.setText(totolModels.get(0).getTotal_person());
        tv_total_losszj.setText(totolModels.get(0).getTotal_loss());
    }
}
