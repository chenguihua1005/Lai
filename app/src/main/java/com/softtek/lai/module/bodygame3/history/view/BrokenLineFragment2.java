package com.softtek.lai.module.bodygame3.history.view;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.history.model.HistoryDetailsBean;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 12/10/2016.
 */

public class BrokenLineFragment2 extends Fragment {
    private static final String ARGUMENTS = "BrokenLineFragment2";
    private Chart mBrokeChat;
    private TextView mNoData;

    private List<String> xAsix = new ArrayList<>();
    private List<Entry> data = new ArrayList<>();
    private HistoryDetailsBean historyDetailsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_broken_line, container, false);
        init(view);
        return view;
    }

    public static BrokenLineFragment2 newInstance(@Nullable HistoryDetailsBean detailsBean) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, detailsBean);
        BrokenLineFragment2 fragment = new BrokenLineFragment2();
        fragment.setArguments(arguments);
        return fragment;
    }

    public BrokenLineFragment2(){

    }

    private void init(View view) {
        Bundle arg = getArguments();
        historyDetailsBean = (HistoryDetailsBean) arg.getSerializable(ARGUMENTS);
        mBrokeChat = (Chart) view.findViewById(R.id.broken_chat);
        mNoData = (TextView)view.findViewById(R.id.tv_no_data);
        mBrokeChat.setTitle1("总体重变化比均值");
        mBrokeChat.setTitle2("未复测");
        int radius = DisplayUtil.dip2px(getContext(), 5);
        GradientDrawable green = new GradientDrawable();
        green.setColors(new int[]{0xFF77BA2B, 0xFFA6C225});
        green.setCornerRadius(radius);
        mBrokeChat.setBackground(green);
        float maxValue = 0;
        if (historyDetailsBean.getList_WeightPer() != null && 0 < historyDetailsBean.getList_FatPer().size()) {
            mNoData.setVisibility(View.GONE);
            for (int i = 0; i < historyDetailsBean.getList_WeightPer().size(); i++) {
                xAsix.add(historyDetailsBean.getList_WeightPer().get(i).getGroupName());
                float value =Float.valueOf(historyDetailsBean.getList_WeightPer().get(i).getLossPer().split("%")[0]);
                maxValue = value > maxValue ? value : maxValue;
                Entry entry = new Entry(i,value);
                data.add(entry);
            }
            mBrokeChat.setDate(xAsix,data,maxValue);
            mBrokeChat.setTitle2(historyDetailsBean.getList_WeightPer().get(0).getLossWeight() + "%");
        }else {
            mBrokeChat.setDate(xAsix,data,maxValue);
        }

    }
}
