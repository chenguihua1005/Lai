package com.softtek.lai.module.bodygame3.activity.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 4/7/2017.
 */

@InjectLayout(R.layout.activity_forstu)
public class FuceForStuActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void initViews() {
        tv_title.setText("为学员复测");
        ll_left.setOnClickListener(this);


    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;

        }
    }
}
