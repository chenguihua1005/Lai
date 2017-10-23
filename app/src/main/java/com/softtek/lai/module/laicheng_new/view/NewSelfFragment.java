package com.softtek.lai.module.laicheng_new.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.SelftestFragment;
import com.softtek.lai.module.laicheng.model.BleMainData;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewSelfFragment extends Fragment {
    private static final String ARGUMENTS = "mainFragment";
    private View mView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(getActivity(),R.layout.fragment_self_new,null);
        initView();
        return mView;
    }

    private void initView(){

    }

    public static NewSelfFragment newInstance(@Nullable BleMainData data) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, data);
        NewSelfFragment fragment = new NewSelfFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
}
