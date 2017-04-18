package com.softtek.lai.module.healthyreport;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.Button;
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
import com.softtek.lai.widgets.chart.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @InjectView(R.id.bt_left)
    Button bt_left;
    @InjectView(R.id.bt_right)
    Button bt_right;



    private static final int DIRECTION_ORGIN=0;
    private static final int DIRECTION_LEFT=1;
    private static final int DIRECTION_RIGHT=2;


    private boolean isVisitor;
    private int type;
    private String accountId;
    private String recordId;
    private int radioType;

    private String leftDate;
    private String rightDate;
    private String startDate;//最早的开始日期
    private String requestDate;//请求时需要的日期


    private SimpleDateFormat sdf;//日期格式化


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
        getPresenter().getLBLineChart(accountId,type,radioType, requestDate,DIRECTION_LEFT);
    }

    @OnClick(R.id.bt_right)
    public void doRightButton(){
        getPresenter().getLBLineChart(accountId,type,radioType, requestDate,DIRECTION_RIGHT);
    }

    @Override
    protected void initViews() {
        Bundle bundle=getArguments();
        isVisitor=bundle.getInt("isVisitor",HealthyReportActivity.VISITOR)==HealthyReportActivity.VISITOR;
        type=bundle.getInt("pid");
        accountId=bundle.getString("accountId");
        recordId=bundle.getString("recordId");
        chart.setTitle1(bundle.getString("chartTitle",""));
        radioType=1;//默认选中在周上
        sdf=new SimpleDateFormat("MM/dd");
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
        if(radioType==4){
            sdf=new SimpleDateFormat("yyyy/MM");
        }else {
            sdf=new SimpleDateFormat("MM/dd");
        }
        if (isChange){
            requestDate=DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();
            getPresenter().getLBLineChart(accountId,type,radioType,requestDate,DIRECTION_ORGIN);
        }

    }


    @Override
    public void getData(HealthyChartModel data) {
        String des=data.getIndexDescription();
        if(TextUtils.isEmpty(des)){
            textViewExpandable.setVisibility(View.GONE);
        }else {
            textViewExpandable.setText(Html.fromHtml(des));
            textViewExpandable.setVisibility(View.VISIBLE);
        }
        //设置指标线
        HealthyChartModel.ColorBar colorBar=data.getColorBar();
        if(colorBar!=null){
            sl.setVisibility(View.VISIBLE);
        }else {
            sl.setVisibility(View.GONE);
        }
        List<Float> valueList=new ArrayList<>();
        List<String> colorList=new ArrayList<>();
        for(int i=0,j=colorBar.getRange().size();i<j;i++){
            HealthyChartModel.ColorBar.Range range=colorBar.getRange().get(i);
            if(i!=0&&i!=j-1)
                valueList.add( range.getValue() );
            if( i!=j-1 )
                colorList.add( "#"+range.getColor() );

        }
        SpannableString unit=new SpannableString(colorBar.getUnit());
        if("kg/m2".equals(colorBar.getUnit())){
            unit.setSpan(new SuperscriptSpan(),unit.length()-1,unit.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        sl.setData(colorBar.getRange().get(0).getValue(), colorBar.getRange().get(colorBar.getRange().size()-1).getValue(),
                colorBar.getValue(), unit, valueList, colorList, "#"+colorBar.getColor(), false);
        //设置曲线图
        getData2(data.getChart());
    }

    @Override
    public void getData2(HealthyChartModel.ChartBean data) {
        chart.setTitle2(data.getUnit());
        startDate=data.getStartDate();

        if(data.getValueList()==null||data.getValueList().isEmpty()){
            return;
        }
        List<HealthyChartModel.ChartBean.ValueList> valueLists=data.getValueList();
        //获取最早的一天
        leftDate=valueLists.get(0).getDate();
        rightDate=valueLists.get(valueLists.size()-1).getDate();
        requestDate=rightDate;
        if(leftDate.compareToIgnoreCase(startDate)>0){
            bt_left.setVisibility(View.VISIBLE);
        }else {
            bt_left.setVisibility(View.GONE);
        }
        String currentDate=DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();
        //如果这段日期的最大时间比当前小就可以往右点
        if(rightDate.compareTo(currentDate)<0){
            bt_right.setVisibility(View.VISIBLE);
        }else {
            bt_right.setVisibility(View.GONE);
        }
        List<String> xAxis=new ArrayList<>();
        List<Entry> entries=new ArrayList<>();
        float max=0;
        for (int i=0,j=valueLists.size();i<j;i++){
            HealthyChartModel.ChartBean.ValueList value=valueLists.get(i);
            max=max<value.getValue()?value.getValue():max;
            xAxis.add(sdf.format(DateUtil.getInstance(DateUtil.yyyy_MM_dd).convert2Date(value.getDate())));
            if(value.getValue()>0){
                entries.add(new Entry(i,value.getValue()));
            }
        }
        chart.setDate(xAxis,entries,max);
    }


}
