package com.softtek.lai.module.bodygamezj.view;

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
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame)
public class BodygameActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_tipzj)
    LinearLayout ll_tipzj;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ll_left.setOnClickListener(this);
        ll_tipzj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tipzj:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
