package com.softtek.lai.module.home.view;


import android.widget.TextView;
import android.widget.Toolbar;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_my)
public class MineFragment extends BaseFragment{
    @InjectView(R.id.tv_title)
    TextView title;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

       title.setText("我的");

    }
}
