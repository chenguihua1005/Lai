package com.softtek.lai.module.bodygame.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.counselor.view.AssistantActivity;
import com.softtek.lai.module.counselor.view.CounselorClassListActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.counselor.view.SPHonorActivity;
import com.softtek.lai.module.jingdu.view.JingduActivity;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.retest.view.RetestActivity;
import com.softtek.lai.module.review.view.ReviewActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/17/2016.
 * 体管赛页面
 */
@InjectLayout(R.layout.activity_counselor)
public class CounselorActivity extends BaseActivity implements View.OnClickListener{
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
    @InjectView(R.id.ll_counselor_fuce)
    LinearLayout ll_counselor_fuce;
    //体管赛点击
    @InjectView(R.id.ll_tiguansai)
    LinearLayout ll_tiguansai;
    @InjectView(R.id.im_refresh)
    ImageView im_refresh;
    //新学员录入按钮
    @InjectView(R.id.ll_new_student)
    LinearLayout ll_new_student;
    //往期回顾按钮
    @InjectView(R.id.ll_review)
    LinearLayout ll_review;
    //当前进度按钮
    @InjectView(R.id.ll_process)
    LinearLayout ll_process;
    //荣誉榜按钮
    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    //赛况
    @InjectView(R.id.ll_match)
    LinearLayout ll_match;
    //提示页面
    @InjectView(R.id.ll_tip)
    LinearLayout ll_tip;
    //助教管理
    @InjectView(R.id.ll_assistant)
    LinearLayout ll_assistant;
    @InjectView(R.id.tv_fucenum)
    TextView tv_fucenum;
    @InjectView(R.id.tv_totalperson)
    TextView tv_totalperson;
    @InjectView(R.id.tv_total_loss)
    TextView tv_total_loss;
    private ITiGuanSai iTiGuanSai;
    private TiGuanSaiModel tiGuanSai;
    private FuceNumModel fuceNumModel;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        //初始化事件总线，并注册当前类
        //按钮监听
        ll_counselor_fuce.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_tiguansai.setOnClickListener(this);
        ll_new_student.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        ll_process.setOnClickListener(this);
        ll_review.setOnClickListener(this);
        ll_match.setOnClickListener(this);
        ll_assistant.setOnClickListener(this);
        ll_tip.setOnClickListener(this);
        im_refresh.setOnClickListener(this);
        
    }

    @Override
    protected void onDestroy() {
       EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai){
        iv_adv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_adv);


    }
    @Subscribe
    public void onEvent1(FuceNumModel fuceNum){
        if (Integer.parseInt(fuceNum.getCount())>10)
        {
            tv_fucenum.setVisibility(View.VISIBLE);
            tv_fucenum.setText("10+");
        }
        else if (Integer.parseInt(fuceNum.getCount())!=10&&fuceNum.getCount()!=""){
            tv_fucenum.setVisibility(View.VISIBLE);
            tv_fucenum.setText(fuceNum.getCount());
        }

    }
    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels){
        tv_totalperson.setText(totolModels.get(0).getTotal_person());
        tv_total_loss.setText(totolModels.get(0).getTotal_loss());
    }


    @Override
    protected void initViews() {
        bar_title.setText(R.string.CounselorBarL);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void initDatas() {
        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.getTiGuanSai();
        iTiGuanSai.doGetFuceNum(loginid);
        dialogShow("数据刷新中...");
        iTiGuanSai.doGetTotal(progressDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //复测按钮点击跳转事件
            case R.id.ll_counselor_fuce:
            {
                Intent intent=new Intent(this, RetestActivity.class);
                intent.putExtra("type","1");
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
            case R.id.ll_tiguansai:
            {
                Intent intent = new Intent(this, CounselorClassListActivity.class);
                startActivity(intent);
            }
            break;
            //新学员录入跳转事件
            case R.id.ll_new_student:
            {
                Intent intent = new Intent(this, JoinGameDetailActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
            }
            break;
            case R.id.ll_honor:
            {
                Intent intent = new Intent(this, SPHonorActivity.class);
                startActivity(intent);

            }
            break;
            //当前进度事件跳转
            case R.id.ll_process:
            {
                Intent intent = new Intent(this, JingduActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll_review:
            {
                Intent intent = new Intent(this, ReviewActivity.class);
                startActivity(intent);

            }
            break;
            //大赛赛况事件跳转
            case R.id.ll_match:
            {
                Intent intent=new Intent(this,GameActivity.class);
                startActivity(intent);

            }
            break;
            //提示事件跳转
            case R.id.ll_tip:
            {
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);

            }
            break;
            //助教管理跳转事件
            case R.id.ll_assistant:
            {
                Intent intent=new Intent(this,AssistantActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.im_refresh:
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("数据刷新中...");
                progressDialog.show();
                iTiGuanSai.doGetTotal(progressDialog);
//                final Animation rotate= AnimationUtils.loadAnimation(this,R.anim.rotate);
//                im_refresh.startAnimation(rotate);
//                rotate.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        //做请求操作
//                        iTiGuanSai.doGetTotal();
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        im_refresh.clearAnimation();
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });


                break;

        }
    }

}
