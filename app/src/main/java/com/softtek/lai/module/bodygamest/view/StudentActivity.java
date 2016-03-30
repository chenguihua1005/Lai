package com.softtek.lai.module.bodygamest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.model.FuceNum;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student)
public class StudentActivity extends BaseActivity {
    private ITiGuanSai tiGuanSai;
    private FuceNum fuceNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
