package com.softtek.lai.module.bodygame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.retest.Retest;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_counselor)
public class Counselor extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.tv_left)
    TextView bar_left;

    @InjectView(R.id.iv_adv)
    ImageView iv_adv;
    @InjectView(R.id.bt_counselor_fuce)
    Button bt_counselor_fuce;

    private ITiGuanSai tiGuanSai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化事件总线，并注册当前类
        EventBus.getDefault().register(this);
//        User user= (User) aCache.getAsObject(Constants.USER_ACACHE_KEY);
//        user.getUserrole();
        bt_counselor_fuce.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
       EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(TiGuanSai tiGuanSai){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+tiGuanSai.getImg_Addr());
        //Picasso.with(this).load().into(iv_adv);
//        Picasso.with(getBaseContext()).load(tiGuanSai.getImg_Addr()).into(iv_adv);
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_adv);


    }


    @Override
    protected void initViews() {
        bar_title.setText(R.string.CounselorBarL);
        bar_left.setBackgroundResource(R.drawable.back_h);
        bar_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,20)));


    }

    @Override
    protected void initDatas() {
        tiGuanSai=new TiGuanSaiImpl();
        tiGuanSai.getTiGuanSai();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_counselor_fuce:
            {
                Intent intent=new Intent(Counselor.this, Retest.class);
                startActivity(intent);

            }
                break;
        }
    }
}
