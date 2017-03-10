package com.softtek.lai.module.laiClassroom;


import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;


import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//收藏页面
@InjectLayout(R.layout.fragment_collection)
public class CollectionFragment extends LazyBaseFragment {
    @InjectView(R.id.plv_collect)
    PullToRefreshListView plv_collect;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;


    public CollectionFragment() {
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

    }

}
