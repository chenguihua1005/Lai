package com.softtek.lai.module.bodygame3.home.view;

import android.os.Handler;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment2 {
    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentEmpty(false);
                setContentShown(true);
            }
        }, 3000);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
