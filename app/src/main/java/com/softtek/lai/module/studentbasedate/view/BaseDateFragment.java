package com.softtek.lai.module.studentbasedate.view;

import android.os.Bundle;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;

import java.io.Serializable;

import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/13/2016.
 */
@InjectLayout(R.layout.fragment_basedate)
public class BaseDateFragment extends BaseFragment{



    public static BaseDateFragment getInstance(Serializable obj){
        BaseDateFragment fragment=new BaseDateFragment();
        if(obj!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("obj",obj);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
