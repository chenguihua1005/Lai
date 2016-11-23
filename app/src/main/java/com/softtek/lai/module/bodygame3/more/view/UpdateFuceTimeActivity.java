package com.softtek.lai.module.bodygame3.more.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_update_fuce_time)
public class UpdateFuceTimeActivity extends BaseActivity{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;


    @Override
    protected void initViews() {
        tv_title.setText("修改复测日");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void initDatas() {

    }
}
