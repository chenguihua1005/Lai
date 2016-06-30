package com.softtek.lai.module.pastreview.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_home)
public class HistoryHomeActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_title_date)
    TextView tv_title_date;

    @Override
    protected void initViews() {
        tv_title.setText("08M小队S");
        tv_title_date.setText("2016年10月-2016年12月");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
