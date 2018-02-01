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

public class BrokenLineFragment3 extends Fragment {
    private static final String ARGUMENTS = "BrokenLineFragment3";
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

    public static BrokenLineFragment3 newInstance(@Nullable HistoryDetailsBean detailsBean) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, detailsBean);
        BrokenLineFragment3 fragment = new BrokenLineFragment3();
        fragment.setArguments(arguments);
        return fragment;
    }

    public BrokenLineFragment3(){

    }

    private void init(View view) {
        Bundle arg = getArguments();
        historyDetailsBean = (HistoryDetailsBean) arg.getSerializable(ARGUMENTS);
        mBrokeChat = (Chart) view.findViewById(R.id.broken_chat);
        mNoData = (TextView)view.findViewById(R.id.tv_no_data);
        mBrokeChat.setTitle1("总体脂变化比均值");
        mBrokeChat.setTitle2("未复测");
        int radius = DisplayUtil.dip2px(getContext(), 5);
        GradientDrawable blue = new GradientDrawable();
        blue.setColors(new int[]{0xFF19BC84, 0xFF1899A0});
        blue.setCornerRadius(radius);
        mBrokeChat.setBackground(blue);
        float maxValue = 0;
        if (historyDetailsBean.getList_FatPer() != null && 0 < historyDetailsBean.getList_FatPer().size()) {
            mNoData.setVisibility(View.GONE);
            for (int i = 0; i < historyDetailsBean.getList_FatPer().size(); i++) {
                xAsix.add(historyDetailsBean.getList_FatPer().get(i).getGroupName());
                float value =Float.valueOf(historyDetailsBean.getList_FatPer().get(i).getLossPer().split("%")[0]);
                maxValue = value > maxValue ? value : maxValue;
                Entry entry = new Entry(i,value);
                data.add(entry);
            }
            mBrokeChat.setDate(xAsix,data,maxValue);
            mBrokeChat.setTitle2(historyDetailsBean.getList_FatPer().get(0).getLossWeight() + "%");
        }else {
            mBrokeChat.setDate(xAsix,data,maxValue);
        }

    }
}
