package com.softtek.lai.module.sport.view;

import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.sport.adapter.HistorySportAdapter;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.presenter.SportManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_start_animation_list)
public class StartAnimationActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text)
    TextView text;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        Animation a= AnimationUtils.loadAnimation(this,R.anim.anim_double);
        text.setAnimation(a);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("我的运动");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }

    }
}
