package com.softtek.lai.module.customermanagement.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;

import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */


@InjectLayout(R.layout.fragment_statistic)
public class StatisticsFragment extends LazyBaseFragment {

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


    }

    @Override
    protected void initDatas() {

    }
}
