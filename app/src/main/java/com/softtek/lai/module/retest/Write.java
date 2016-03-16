package com.softtek.lai.module.retest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_write)
public class Write extends BaseActivity {
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.tv_left)
    TextView bar_left;
    @InjectView(R.id.tv_right)
    TextView bar_rignt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        bar_left.setBackgroundResource(R.drawable.back_h);

        title.setText("复测录入");
        bar_rignt.setText("保存");
        bar_rignt.setTextColor(Color.BLACK);


    }
}
