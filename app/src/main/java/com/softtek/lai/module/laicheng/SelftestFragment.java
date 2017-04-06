package com.softtek.lai.module.laicheng;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;

import zilla.libcore.ui.InjectLayout;

public class SelftestFragment extends Fragment {
    private static final String ARGUMENTS = "SelftestFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selftest, container, false);
        initView(view);
        return view;
    }

    public static SelftestFragment newInstance(@Nullable String attrs) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS, attrs);
        SelftestFragment fragment = new SelftestFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private void initView(View view){

    }

}
