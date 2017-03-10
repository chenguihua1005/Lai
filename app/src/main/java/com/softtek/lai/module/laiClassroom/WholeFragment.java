package com.softtek.lai.module.laiClassroom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.adapter.FilterAdapter;
import com.softtek.lai.module.laiClassroom.presenter.WholePresenter;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//全部页面
@InjectLayout(R.layout.fragment_whole)
public class WholeFragment extends LazyBaseFragment<WholePresenter> implements WholePresenter.WholeView {

    @InjectView(R.id.rv_sort)
    RecyclerView rv_sort;
    @InjectView(R.id.rv_type)
    RecyclerView rv_type;
    @InjectView(R.id.rv_subject)
    RecyclerView rv_subject;

    @InjectView(R.id.ll_panel)
    LinearLayout ll_panel;

    public WholeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        ll_panel.setY(DisplayUtil.dip2px(getContext(),-150f));
    }

    @Override
    protected void initDatas() {
        setPresenter(new WholePresenter(this));
        List<String> strs=new ArrayList<>();
        for (int i=0;i<10;i++){
            strs.add(i+"item");
        }
        rv_sort.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_sort.setAdapter(new FilterAdapter(getContext(),strs));
        rv_type.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_type.setAdapter(new FilterAdapter(getContext(),strs));
        rv_subject.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_subject.setAdapter(new FilterAdapter(getContext(),strs));
    }

}
