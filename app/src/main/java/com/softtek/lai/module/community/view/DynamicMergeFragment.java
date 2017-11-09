package com.softtek.lai.module.community.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;

/**
 * Created by jia.lu on 11/9/2017.
 */

public class DynamicMergeFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_dynamic_merge,null);
        initView();
        return view;
    }

    private void initView(){

    }
}
