package com.softtek.lai.module.healthyreport;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HealthyReportAdapter;
import com.softtek.lai.module.healthyreport.model.HealthyItem;
import com.softtek.lai.module.healthyreport.model.HealthyReport;
import com.softtek.lai.module.laicheng.presenter.HealthyReportPresenter;
import com.softtek.lai.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_healthy_report)
public class HealthyReportActivity extends BaseActivity<HealthyReportPresenter> implements HealthyReportPresenter.HealthyReportView
,HealthyReportAdapter.OnItemClickListener{
    //莱称数据来源
    public static final int SINCE_LAICHEN=1;
    //其他数据来源
    public static final int SINCE_OTHER=2;

    //访客
    public static final int VISITOR=1;
    //非访客
    public static final int NON_VISITOR=2;




    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_user)
    TextView tv_user;
    @InjectView(R.id.tv_time)
    TextView tv_time;

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

    ArrayList<HealthyItem> items=new ArrayList<>();
    HealthyReportAdapter adapter;
    String reportId;
    int since;
    int isVisitor;
    String accountId;

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
        reportId=getIntent().getStringExtra("reportId");
        since=getIntent().getIntExtra("since",SINCE_LAICHEN);
        isVisitor=getIntent().getIntExtra("isVisitor",NON_VISITOR);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        list.setHasFixedSize(true);
        list.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        adapter=new HealthyReportAdapter(items,this);
        adapter.setListener(this);
        list.setAdapter(adapter);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        setPresenter(new HealthyReportPresenter(this));
        getPresenter().healthyReport(reportId);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(tv_arrow.getCompoundDrawables()[2], "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv_arrow.setText(!isExpand?"展开":"收起");
            }
        });
        animator.start();
    }
    boolean isExpand;
    @OnClick(R.id.rl_expand)
    public void doExpand(){
        int lineCount=tv_des.getLineCount();
        if(!isExpand){
            //展开
            isExpand=true;
            animateArrow(!isExpand);
            tv_des.setMaxLines(lineCount);
            tv_des.invalidate();

        }else {
            isExpand=false;
            animateArrow(!isExpand);
            //收起
            tv_des.setMaxLines(4);
            tv_des.invalidate();
        }
    }

    @Override
    public void getData(HealthyReport data) {
        accountId=data.getAccountId();
        tv_user.setText(data.getUsername());
        tv_time.setText(data.getMeasureTime());
        String des=data.getBodyTypeDesc();
        if(TextUtils.isEmpty(des)){
            tv_des.setVisibility(View.GONE);
        }else {
            tv_des.setVisibility(View.VISIBLE);
            String[] split=des.split("<br/>");
            SpannableStringBuilder ssb=new SpannableStringBuilder();
            if(split.length>1){
                SpannableString ss=new SpannableString(split[0]);
                ss.setSpan(new ForegroundColorSpan(0xFFF6BB07),0,ss.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ssb.append(ss);
                ssb.append("\n\n");
                ssb.append(split[1]);
            }else {
                if(des.startsWith(data.getBodyTypeTitle())){
                    //截取开头的字符
                    int lenght=data.getBodyTypeTitle().length();
                    if(des.indexOf(":")==lenght||des.indexOf("：")==lenght){
                        String title=des.substring(0,data.getBodyTypeTitle().length()+1);
                        SpannableString ss=new SpannableString(title);
                        ss.setSpan(new ForegroundColorSpan(0xFFF6BB07),0,ss.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        ssb.append(ss);
                        ssb.append("\n");
                        ssb.append(des.substring(data.getBodyTypeTitle().length()+1));
                    }
                }else {
                    ssb.append(des);
                }
            }
            tv_des.setText(ssb);
            final ViewTreeObserver viewTreeObserver = tv_des.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    viewTreeObserver.removeOnPreDrawListener(this);
                    int lines=tv_des.getLineCount();
                    if(lines>4){
                        rl_expand.setVisibility(View.VISIBLE);
                    }else {
                        rl_expand.setVisibility(View.GONE);
                    }
                    return true;
                }
            });
        }
        items.addAll(data.getItemList());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        //跳转到曲线图
        HealthyItem item=items.get(position);
        Intent intent=new Intent(this, HealthyChartActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("isVisitor",isVisitor);
        bundle.putInt("pid",item.getPid());
        bundle.putString("accountId",accountId);
        bundle.putString("recordId",reportId);
        intent.putExtras(bundle);
        intent.putParcelableArrayListExtra("items",items);
        startActivity(intent);
    }
}
