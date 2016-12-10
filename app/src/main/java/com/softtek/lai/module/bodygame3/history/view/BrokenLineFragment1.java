package com.softtek.lai.module.bodygame3.history.view;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.history.model.HistoryDetailsBean;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 12/6/2016.
 */

public class BrokenLineFragment1 extends Fragment {
    private static final String ARGUMENTS = "BrokenLineFragment1";
    private Chart mBrokeChat;

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

    public static BrokenLineFragment1 newInstance(@Nullable HistoryDetailsBean detailsBean) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, detailsBean);
        BrokenLineFragment1 fragment = new BrokenLineFragment1();
        fragment.setArguments(arguments);
        return fragment;
    }

    public BrokenLineFragment1() {

    }

    private void init(View view) {
        Bundle arg = getArguments();
        historyDetailsBean = (HistoryDetailsBean) arg.getSerializable(ARGUMENTS);
        mBrokeChat = (Chart) view.findViewById(R.id.broken_chat);
        mBrokeChat.setTitle1("减重均值");
        int radius = DisplayUtil.dip2px(getContext(), 5);
        GradientDrawable orange = new GradientDrawable();
        orange.setColors(new int[]{0xFFFEA003, 0xFFED7460});
        orange.setCornerRadius(radius);
        mBrokeChat.setBackground(orange);
        float maxValue = 0;
        if (historyDetailsBean.getList_Weight() != null) {
            for (int i = 0; i < historyDetailsBean.getList_Weight().size(); i++) {
                xAsix.add(historyDetailsBean.getList_Weight().get(i).getGroupName());
                float value =Float.valueOf(historyDetailsBean.getList_Weight().get(i).getLossPer());
                maxValue = value > maxValue ? value : maxValue;
                Entry entry = new Entry(i,value);
                data.add(entry);
            }
            mBrokeChat.setDate(xAsix,data,maxValue);
            mBrokeChat.setTitle2(historyDetailsBean.getList_Weight().get(0).getLossWeight() + "斤");
        }else {
            mBrokeChat.setDate(xAsix,data,maxValue);
            mBrokeChat.setTitle2("");
        }

//        for (int i = 0; i < 7; i++) {
//            xAsix.add(i + "item");
//            int value = (int) (Math.random() * 1000);
//            Entry entry = new Entry(i, value);
//            data.add(entry);
//        }
//        mBrokeChat.setDate(xAsix, data, maxValue);
//        mBrokeChat.setTitle1("测试图");
//        mBrokeChat.setTitle2("dsadsadas");
    }
}
