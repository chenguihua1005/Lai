package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_publicwelfare)
public class MyPublicwelfareActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void initViews() {
        tv_title.setText("慈善公益");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.ll_left:
                finish();
                break;
        }
    }
}
