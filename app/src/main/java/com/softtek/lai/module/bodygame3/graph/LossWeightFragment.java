package com.softtek.lai.module.bodygame3.graph;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;

import zilla.libcore.ui.InjectLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@InjectLayout(R.layout.fragment_loss_weight)
public class LossWeightFragment extends LazyBaseFragment2 {


    public LossWeightFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance(long accountId,String classId){
        Fragment fragment=new LossWeightFragment();
        Bundle bundle=new Bundle();
        bundle.putLong("accountId",accountId);
        bundle.putString("classId",classId);
        fragment.setArguments(bundle);
        return  fragment;
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
