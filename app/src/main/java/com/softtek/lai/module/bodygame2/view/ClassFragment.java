package com.softtek.lai.module.bodygame2.view;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_class)
public class ClassFragment extends LazyBaseFragment {

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }


    @Override
    protected void lazyLoad() {
        Log.i("ClassFragment 加载数据");
    }
}
