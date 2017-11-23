package com.softtek.lai.module.customermanagement.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.StatisAdapter;
import com.softtek.lai.module.customermanagement.model.StatisticModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */


@InjectLayout(R.layout.fragment_statistic)
public class StatisticsFragment extends LazyBaseFragment {
    @InjectView(R.id.lv)
    ListView lv;

    private List<StatisticModel> modelList = new ArrayList<StatisticModel>();
    private StatisAdapter adapter;

    public static Fragment getInstance() {
        Fragment fragment = new StatisticsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        StatisticModel model = new StatisticModel("2017年11月11日", "添加线索");
        StatisticModel model2 = new StatisticModel("2017年11月12日", "由Tom代注册帐号");
        StatisticModel model3 = new StatisticModel("2017年11月13日", "参加体馆赛：XX班");
        StatisticModel model4 = new StatisticModel("2017年11月15日", "复测");
        StatisticModel model5 = new StatisticModel("2017年11月21日", "身份认证");
        modelList.add(model);
        modelList.add(model2);
        modelList.add(model3);
        modelList.add(model4);
        modelList.add(model5);
        adapter = new StatisAdapter(getContext(),modelList);
        lv.setAdapter(adapter);



    }

    @Override
    protected void initDatas() {

    }
}
