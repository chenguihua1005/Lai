package com.softtek.lai.module.tips.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_tips)
public class TipsActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    private ITiGuanSai iTiGuanSai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("TIPS");
        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.doGetTips();
        iTiGuanSai.doGetTipsDetail(13);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;
        }

    }
}
