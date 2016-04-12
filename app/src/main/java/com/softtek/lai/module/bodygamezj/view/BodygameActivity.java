package com.softtek.lai.module.bodygamezj.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.bodygame.view.TipsActivity;
import com.softtek.lai.module.retest.view.RetestActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame)
public class BodygameActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_tipzj)
    LinearLayout ll_tipzj;
    @InjectView(R.id.tv_fucenumzj)
    TextView tv_fucenumzj;
    @InjectView(R.id.iv_advzj)
    ImageView iv_advzj;
    @InjectView(R.id.ll_counselor_fucezj)
    LinearLayout ll_counselor_fucezj;
    @InjectView(R.id.tv_totalzj)
    ImageView tv_totalzj;
    @InjectView(R.id.tv_total_losszj)
    TextView tv_total_losszj;
    @InjectView(R.id.tv_totalpersonzj)
    TextView tv_totalpersonzj;
    private ITiGuanSai iTiGuanSai;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ll_left.setOnClickListener(this);
        ll_tipzj.setOnClickListener(this);
        ll_counselor_fucezj.setOnClickListener(this);
        tv_totalzj.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（助教版）");
        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.getTiGuanSai();
        iTiGuanSai.doGetFuceNum(36);
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
            case R.id.ll_tipzj:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_counselor_fucezj:
                startActivity(new Intent(this, RetestActivity.class));
                break;
            case R.id.tv_totalzj:
                iTiGuanSai.doGetTotal();
                break;
        }
    }
    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+tiGuanSai.getImg_Addr());
        //Picasso.with(this).load().into(iv_adv);
//        Picasso.with(getBaseContext()).load(tiGuanSai.getImg_Addr()).into(iv_adv);
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_advzj);


    }
    @Subscribe
    public void onEvent1(FuceNumModel fuceNum){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+fuceNum.getCount());
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
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+totolModels.get(0).getTotal_loss());
        tv_totalpersonzj.setText(totolModels.get(0).getTotal_person());
        tv_total_losszj.setText(totolModels.get(0).getTotal_loss());
    }
}
