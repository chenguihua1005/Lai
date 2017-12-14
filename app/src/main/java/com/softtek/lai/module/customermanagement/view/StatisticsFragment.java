package com.softtek.lai.module.customermanagement.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.StatisAdapter;
import com.softtek.lai.module.customermanagement.model.TimeAxisItemModel;
import com.softtek.lai.module.customermanagement.model.TimeAxisModel;
import com.softtek.lai.module.customermanagement.presenter.TimeAxisPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */


@InjectLayout(R.layout.fragment_statistic)
public class StatisticsFragment extends LazyBaseFragment<TimeAxisPresenter> implements TimeAxisPresenter.TimeAxisCallBack {
    @InjectView(R.id.lv)
    ListView lv;

    private List<TimeAxisItemModel> modelList = new ArrayList<TimeAxisItemModel>();
    private StatisAdapter adapter;
    private static String mobile = "";


    public static Fragment getInstance(String mobileNum) {
        Fragment fragment = new StatisticsFragment();
        mobile = mobileNum;
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        setPresenter(new TimeAxisPresenter(this));
        adapter = new StatisAdapter(getContext(), modelList);
        lv.setAdapter(adapter);

        dialogShow(getString(R.string.loading));
        getPresenter().getTimeAxisOfCustomer("", mobile, 1, 100);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void getTimeAxisOfCustomer(TimeAxisModel model) {
        if (model != null) {
            modelList.clear();
            modelList.addAll(model.getItems());
            adapter.notifyDataSetChanged();
        }
    }
}
