package com.softtek.lai.module.grade.view;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 3/21/2016.
 */
@InjectLayout(R.layout.fragment_loss_weight)
public class LossWeightFragment extends BaseFragment{


    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
