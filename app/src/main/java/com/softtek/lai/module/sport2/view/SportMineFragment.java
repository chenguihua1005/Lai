package com.softtek.lai.module.sport2.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.home.view.HomeActviity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_sport_mine)
public class SportMineFragment extends LazyBaseFragment implements View.OnClickListener{


    public SportMineFragment() {

    }

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.pull_sroll)
    PullToRefreshScrollView pull_sroll;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("我的");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                getActivity().startActivity(new Intent(getActivity(), HomeActviity.class));
                break;
        }
    }
}
