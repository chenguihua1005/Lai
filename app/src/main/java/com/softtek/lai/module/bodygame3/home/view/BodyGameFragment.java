package com.softtek.lai.module.bodygame3.home.view;

import android.os.Handler;
import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame)
public class BodyGameFragment extends LazyBaseFragment2 {

    @InjectView(R.id.chart)
    Chart chart;
    private List<String> xAxis=new ArrayList<>();//x轴数据
    private List<Entry> yAxis=new ArrayList<>();//轴数据
    public BodyGameFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentEmpty(false);
                setContentShown(true);
            }
        }, 3000);
    }

    @Override
    protected void initViews() {
        xAxis.add("10月18");
        xAxis.add("19");
        xAxis.add("20");
        xAxis.add("21");
        xAxis.add("22");
        xAxis.add("23");
        xAxis.add("24");

        yAxis.add(new Entry(0,3000));
        //yAxis.add(new Entry(1,8000));
        yAxis.add(new Entry(2,3000));
        yAxis.add(new Entry(3,1000));
        yAxis.add(new Entry(4,3000));
        yAxis.add(new Entry(5,6000));
        yAxis.add(new Entry(6,8000));
        chart.setDate(xAxis,yAxis,8000);
    }

    @Override
    protected void initDatas() {

    }

    public void show(View view){

    }

}
