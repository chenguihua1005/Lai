package com.softtek.lai.module.healthyreport;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.widgets.TextViewExpandable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthyChartFragment extends Fragment {


    public HealthyChartFragment() {
        // Required empty public constructor
    }

    public static HealthyChartFragment newInstance(){
        HealthyChartFragment fragment=new HealthyChartFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_healthy_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextViewExpandable tv= (TextViewExpandable) view.findViewById(R.id.tve);
        tv.setText("哈哈哈哈哈\n体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重体重");
    }
}
