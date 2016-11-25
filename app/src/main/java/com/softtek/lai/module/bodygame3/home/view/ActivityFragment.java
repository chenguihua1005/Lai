package com.softtek.lai.module.bodygame3.home.view;


import android.widget.FrameLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.activity.view.ClassedFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment {

    @InjectView(R.id.contain_act)
    FrameLayout contain_act;
    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_act,new ClassedFragment()).commit();

    }

}
