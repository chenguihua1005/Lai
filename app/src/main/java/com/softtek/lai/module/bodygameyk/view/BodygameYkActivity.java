package com.softtek.lai.module.bodygameyk.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.view.TipsActivity;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame_yk)
public class BodygameYkActivity extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_tipyk)
    LinearLayout ll_tipyk;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_tipyk.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（游客版）");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tipyk:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
