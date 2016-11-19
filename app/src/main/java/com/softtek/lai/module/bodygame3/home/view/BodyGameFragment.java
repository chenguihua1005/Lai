package com.softtek.lai.module.bodygame3.home.view;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame)
public class BodyGameFragment extends LazyBaseFragment {
    //toolbar标题
    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;
    @InjectView(R.id.spinner_title)
    ArrowSpinner2 spinner_title;
    @InjectView(R.id.chart)
    Chart chart;
    private List<String> xAxis = new ArrayList<>();//x轴数据
    private List<Entry> yAxis = new ArrayList<>();//轴数据

    public BodyGameFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentEmpty(false);
                setContentShown(true);
            }
        }, 3000);*/
    }

    @Override
    protected void initViews() {
        //配置列表数据
        final List<String> data = new ArrayList<>();
        data.add("测试数据1测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        final List<String> datas=new ArrayList<>();
        datas.add("体重比");
        datas.add("体脂");
        datas.add("减重比");
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), data, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return data.get(position);
            }

        });

        spinner_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), datas, R.layout.class_title) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_title);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return datas.get(position);
            }

        });

        xAxis.add("10月18");
        xAxis.add("19");
        xAxis.add("20");
        xAxis.add("21");
        xAxis.add("22");
        xAxis.add("23");
        xAxis.add("24");

        yAxis.add(new Entry(0, 3000));
        //yAxis.add(new Entry(1,8000));
        yAxis.add(new Entry(2, 3000));
        yAxis.add(new Entry(3, 1000));
        yAxis.add(new Entry(4, 3000));
        yAxis.add(new Entry(5, 6000));
        yAxis.add(new Entry(6, 8000));
        chart.setDate(xAxis, yAxis, 8000);
    }

    @Override
    protected void initDatas() {

    }

    public void show(View view) {

    }

}
