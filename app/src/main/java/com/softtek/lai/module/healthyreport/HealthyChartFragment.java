package com.softtek.lai.module.healthyreport;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.healthyreport.model.HealthyChartModel;
import com.softtek.lai.module.healthyreport.presenter.HealthyChartPresenter;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.StandardLine;
import com.softtek.lai.widgets.TextViewExpandable;
import com.softtek.lai.widgets.chart.Chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy_chart)
public class HealthyChartFragment extends LazyBaseFragment<HealthyChartPresenter> implements RadioGroup.OnCheckedChangeListener,HealthyChartPresenter.HealthyChartView{

    @InjectView(R.id.tve)
    TextViewExpandable textViewExpandable;
    @InjectView(R.id.rl_chart)
    RelativeLayout rl_chart;
    @InjectView(R.id.rg)
    RadioGroup rg;
    @InjectView(R.id.sl)
    StandardLine sl;
    @InjectView(R.id.chart)
    Chart chart;


    private static final int DIRECTION_ORGIN=0;
    private static final int DIRECTION_LEFT=1;
    private static final int DIRECTION_RIGHT=2;


    private boolean isVisitor;
    private int type;
    private String accountId;
    private String recordId;
    private int radioType;

    private String lastDate;
    private String startDate;//最早的开始日期


    public HealthyChartFragment() {

    }

    public static HealthyChartFragment newInstance(Bundle bundle){
        HealthyChartFragment fragment=new HealthyChartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        getPresenter().getLBHistory(recordId,type);
    }

    @OnClick(R.id.bt_left)
    public void doLeftButton(){
        getPresenter().getLBLineChart(accountId,type,radioType, DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate(),DIRECTION_LEFT);
    }

    @OnClick(R.id.bt_right)
    public void doRightButton(){
        getPresenter().getLBLineChart(accountId,type,radioType, DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate(),DIRECTION_RIGHT);
    }

    @Override
    protected void initViews() {
        Bundle bundle=getArguments();
        isVisitor=bundle.getInt("isVisitor",HealthyReportActivity.VISITOR)==HealthyReportActivity.VISITOR;
        type=bundle.getInt("pid");
        accountId=bundle.getString("accountId");
        recordId=bundle.getString("recordId");
        chart.setTitle1(bundle.getString("chartTitle",""));
        if(isVisitor){
            rl_chart.setVisibility(View.GONE);
            rg.setVisibility(View.GONE);
        }else {
            rl_chart.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
        }
        rg.setOnCheckedChangeListener(this);
        GradientDrawable gradient = new GradientDrawable();
        gradient.setColors(new int[]{0xFF77BA2B, 0xFFA6C225});
        gradient.setCornerRadius(DisplayUtil.dip2px(getContext(), 5));
        chart.setBackground(gradient);

    }

    @Override
    protected void initDatas() {
        setPresenter(new HealthyChartPresenter(this));

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        boolean isChange=false;
        switch (checkedId){
            case R.id.week:
                isChange=radioType!=1;
                radioType=1;
                break;
            case R.id.month:
                isChange=radioType!=2;
                radioType=2;
                break;
            case R.id.quarter:
                isChange=radioType!=3;
                radioType=3;
                break;
            case R.id.year:
                isChange=radioType!=4;
                radioType=4;
                break;
        }
        if (isChange)
            getPresenter().getLBLineChart(accountId,type,radioType, DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate(),DIRECTION_ORGIN);

    }


    @Override
    public void getData(HealthyChartModel data) {
        String des=data.getIndexDescription();
        if(TextUtils.isEmpty(des)){
            textViewExpandable.setVisibility(View.GONE);
        }else {
            textViewExpandable.setVisibility(View.VISIBLE);
            textViewExpandable.setText(Html.fromHtml(des));
        }
    }

    @Override
    public void getData2(HealthyChartModel.ChartBean data) {
        chart.setTitle2(data.getUnit());
        startDate=data.getStartDate();
        if(data.getValueList()==null||data.getValueList().isEmpty()){
            return;
        }
        List<HealthyChartModel.ChartBean.ValueList> valueLists=data.getValueList();
        Collections.reverse(data.getValueList());
        //获取最早的一天
        lastDate=valueLists.get(0).getDate();
        List<String> xAxis=new ArrayList<>();
        for (HealthyChartModel.ChartBean.ValueList value:valueLists){

        }
    }


}
