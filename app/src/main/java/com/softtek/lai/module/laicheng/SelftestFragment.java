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
import com.softtek.lai.module.laicheng.model.BleMainData;

import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_selftest)
public class SelftestFragment extends LazyBaseFragment {
    private static final String ARGUMENTS = "mainFragment";
    private VoiceListener listener;

    public interface VoiceListener {
        void onVoiceListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VoiceListener) {
            listener = (SelftestFragment.VoiceListener) context;
        }
    }

    private VoiceListener voiceListener;

    public static SelftestFragment newInstance(@Nullable BleMainData data) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, data);
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

    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (listener != null) {
            listener.onVoiceListener();
        }
    }
}
