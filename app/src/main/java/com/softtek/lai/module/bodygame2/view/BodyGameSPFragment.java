package com.softtek.lai.module.bodygame2.view;

import android.widget.RelativeLayout;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame_sp)
public class BodyGameSPFragment extends LazyBaseFragment {

    @InjectView(R.id.toolbar)
    RelativeLayout relativeLayout;

    @Override
    protected void initViews() {
        int status=DisplayUtil.getStatusHeight(getActivity());
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        params.topMargin=status;
        relativeLayout.setLayoutParams(params);
    }

    @Override
    protected void initDatas() {

    }


    @Override
    protected void lazyLoad() {
        Log.i("BodyGameSPFragment 加载数据");
    }
}
