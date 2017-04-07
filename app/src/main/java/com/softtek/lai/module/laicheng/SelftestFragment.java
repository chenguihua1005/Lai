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

import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_selftest)
public class SelftestFragment extends LazyBaseFragment {
    private static final String ARGUMENTS = "mainFragment";

    public interface voiceListener {
        void onVoiceLinstener();
    }

    private voiceListener voiceListener;
    public SelftestFragment() {
        // Required empty public constructor
    }

    public static SelftestFragment newInstance(@Nullable String attrs) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS, attrs);
        SelftestFragment fragment = new SelftestFragment();
        fragment.setArguments(arguments);
        return fragment;
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

//    @OnClick(R.id.voice)
}
