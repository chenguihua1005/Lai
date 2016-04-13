package com.softtek.lai.module.studentbasedate.view;

import android.os.Bundle;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;

import java.io.Serializable;

import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/13/2016.
 */
@InjectLayout(R.layout.fragment_basedate_dynamic)
public class ClassDynamicFragment extends BaseFragment{


    public static ClassDynamicFragment getInstance(String classId){
            ClassDynamicFragment fragment=new ClassDynamicFragment();
            Bundle bundle=new Bundle();
            bundle.putString("classId",classId);
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
