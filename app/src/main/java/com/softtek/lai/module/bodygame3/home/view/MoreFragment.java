package com.softtek.lai.module.bodygame3.home.view;

import android.os.Handler;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment2 {

    @InjectView(R.id.tv)
    TextView tv;

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
        tv.setText("管国祥");
    }

    @Override
    protected void initDatas() {

    }
}
