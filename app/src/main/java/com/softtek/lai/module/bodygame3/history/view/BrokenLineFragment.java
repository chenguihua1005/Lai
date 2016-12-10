package com.softtek.lai.module.bodygame3.history.view;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 12/6/2016.
 */

public class BrokenLineFragment extends Fragment {
    private Chart mBrokeChat;

    List<String> xAsix = new ArrayList<>();
    List<Entry> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_broken_line, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mBrokeChat = (Chart) view.findViewById(R.id.broken_chat);
        int radius = DisplayUtil.dip2px(getContext(), 5);
        GradientDrawable orange = new GradientDrawable();
        orange.setColors(new int[]{0xFFFEA003, 0xFFED7460});
        orange.setCornerRadius(radius);
        mBrokeChat.setBackground(orange);
        int maxValue = 0;
        for (int i = 0; i < 7; i++) {
            xAsix.add(i + "item");
            int value = (int) (Math.random() * 1000);
            maxValue = value > maxValue ? value : maxValue;
            Entry entry = new Entry(i, value);
            data.add(entry);
        }
        mBrokeChat.setDate(xAsix, data, maxValue);
        mBrokeChat.setTitle1("测试图");
        mBrokeChat.setTitle2("dsadsadas");
    }
}
