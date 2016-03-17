package com.softtek.lai.module.retest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.utils.DisplayUtil;

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
        bar_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,20)));
        title.setText("复测录入");
        bar_rignt.setText("保存");
        bar_rignt.setTextColor(Color.BLACK);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, HomeActviity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
