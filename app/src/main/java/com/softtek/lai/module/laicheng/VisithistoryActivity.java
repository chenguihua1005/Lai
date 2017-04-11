package com.softtek.lai.module.laicheng;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visithistory)
public class VisithistoryActivity extends BaseActivity {
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    @Override
    protected void initViews() {
     
    }

    @Override
    protected void initDatas() {

    }
}
