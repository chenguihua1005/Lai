package com.softtek.lai.module.mygrades.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_grades)
public class MyGradesActivity extends BaseActivity implements View.OnClickListener{

    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    //更新时间
    @InjectView(R.id.tv_update)
    TextView tv_update;
    //总步数
    @InjectView(R.id.tv_totalnumber)
    TextView tv_totalnumber;
    //总公里数
    @InjectView(R.id.tv_totalmileage)
    TextView tv_totalmileage;
    //跑团日排名
    @InjectView(R.id.tv_Rundayrank)
    TextView tv_Rundayrank;
    //跑团日排名人数
    @InjectView(R.id.tv_Rundaypeople)
    TextView tv_Rundaypeople;
    //全国日排名
    @InjectView(R.id.tv_Nationaldayrank)
    TextView tv_Nationaldayrank;
    //全国日排名人数
    @InjectView(R.id.tv_Nationaldaypeople)
    TextView tv_Nationaldaypeople;
    //跑团周排名
    @InjectView(R.id.tv_Runweekrank)
    TextView tv_Runweekrank;
    //跑团周排名人数
    @InjectView(R.id.tv_Runweekpeople)
    TextView tv_Runweekpeople;
    //全国周排名
    @InjectView(R.id.tv_Nationalweekrank)
    TextView tv_Nationalweekrank;
    //全国周排名人数
    @InjectView(R.id.tv_Nationalweekpeople)
    TextView tv_Nationalweekpeople;
    //勋章数量
    @InjectView(R.id.tv_medalnumber)
    TextView tv_medalnumber;

    @InjectView(R.id.chart)
    LineChart chart;
    private IGradesPresenter iGradesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        title.setText("我的成绩");
        tv_right.setText("分享");
        ll_left.setOnClickListener(this);
        //初始化统计图
        //取消统计图整体背景色
        chart.setDrawGridBackground(false);
        //取消描述信息,设置没有数据的时候提示信息
        chart.setDescription("");
        chart.setNoDataTextDescription("暂无数据");
        //启用手势操作
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据

    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        //我的成绩
        iGradesPresenter.getStepCount();
        //当日排名
        //iGradesPresenter.getCurrentDateOrder(0);//0
        //当周排名
        //iGradesPresenter.getCurrentWeekOrder();
        //勋章详情页
        //iGradesPresenter.getStepHonor();
    }

    @Subscribe
    public void onEvent(DayRankModel dayRankModel) {
        System.out.println("全国排名》》》》》》》》》》》》》》" + dayRankModel.getOrderData().get(0).get_order());
        String order=dayRankModel.getOrderData().get(0).get_order();
        String totalnumber = dayRankModel.getOrderData().get(0).getStepCount();
        tv_totalnumber.setText(totalnumber);
        tv_Nationaldayrank.setText(order);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
