package com.softtek.lai.module.bodygame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.grade.view.StudentsActivity;
import com.softtek.lai.module.retest.view.Retest;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
/**
 * Created by lareina.qiao on 3/17/2016.
 * 体管赛页面
 */
@InjectLayout(R.layout.activity_counselor)
public class Counselor extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //banner图片
    @InjectView(R.id.iv_adv)
    ImageView iv_adv;
    //复测按钮
    @InjectView(R.id.bt_counselor_fuce)
    Button bt_counselor_fuce;
    //体管赛按钮
    @InjectView(R.id.bt_tiguansai)
    Button bt_tiguansai;

    private ITiGuanSai tiGuanSai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("d","看见对方的所得税法");
        //初始化事件总线，并注册当前类
        EventBus.getDefault().register(this);
//        User user= (User) aCache.getAsObject(Constants.USER_ACACHE_KEY);
//        user.getUserrole();
        //按钮监听
        bt_counselor_fuce.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        bt_tiguansai.setOnClickListener(this);
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
            //复测按钮点击跳转事件
            case R.id.bt_counselor_fuce:
            {
                Intent intent=new Intent(Counselor.this, Retest.class);
                startActivity(intent);


            }
                break;
            //主题栏返回事件
            case R.id.ll_left:
            {
                finish();

            }
            break;
            //体管赛按钮点击跳转事件
            case R.id.bt_tiguansai:
            {
                Intent intent = new Intent(this, StudentsActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
