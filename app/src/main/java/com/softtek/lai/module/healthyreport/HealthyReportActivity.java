package com.softtek.lai.module.healthyreport;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HealthyReportAdapter;
import com.softtek.lai.module.laicheng.presenter.HealthyReportPresenter;
import com.softtek.lai.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_healthy_report)
public class HealthyReportActivity extends BaseActivity<HealthyReportPresenter> implements HealthyReportPresenter.HealthyReportView{

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.scroll)
    ScrollView scrollView;
    @InjectView(R.id.tv_arrow)
    TextView tv_arrow;
    @InjectView(R.id.rl_expand)
    RelativeLayout rl_expand;
    @InjectView(R.id.tv_des)
    TextView tv_des;
    @InjectView(R.id.list)
    RecyclerView list;

    List<String> items=new ArrayList<>();

    @Override
    protected void initViews() {
        tv_title.setText("健康报告");
    }

    @OnClick(R.id.ll_left)
    public void doBack(){
        finish();
    }

    @Override
    protected void initDatas() {
        for (int i=0;i<100;i++){
            items.add("item"+i);
        }
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        list.setHasFixedSize(true);

        list.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        list.setAdapter(new HealthyReportAdapter(items,this));

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(tv_arrow.getCompoundDrawables()[2], "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv_arrow.setText(!isExpand?"展开":"收起");
            }
        });
        animator.start();
    }
//    boolean doAnimat;
    boolean isExpand;
    @OnClick(R.id.rl_expand)
    public void doExpand(){
//        if(doAnimat){
//            return;
//        }
        int lineCount=tv_des.getLineCount();
        if(!isExpand){
            //展开
            isExpand=true;
            tv_des.setMaxLines(lineCount);
            tv_des.invalidate();
            animateArrow(!isExpand);

        }else {
            isExpand=false;
            //收起
            tv_des.setMaxLines(4);
            tv_des.invalidate();
            animateArrow(!isExpand);
        }
    }

}
