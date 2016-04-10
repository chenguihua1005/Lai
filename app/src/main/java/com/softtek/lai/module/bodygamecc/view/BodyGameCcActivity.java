package com.softtek.lai.module.bodygamecc.view;

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

@InjectLayout(R.layout.activity_body_game_cc)
public class BodyGameCcActivity extends BaseActivity implements View.OnClickListener {
    //标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（普通顾客版）");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
        }

    }
}
