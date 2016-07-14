package com.softtek.lai.module.bodygame2pc.view;

import android.support.v4.app.Fragment;
import android.view.View;

import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame)
public class BodyGamePCActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.btn_bodygame)
    SimpleButton btn_bodygame;
    @InjectView(R.id.btn_chat)
    SimpleButton btn_chat;
    @InjectView(R.id.btn_contact)
    SimpleButton btn_contact;
    @InjectView(R.id.btn_fuce)
    SimpleButton btn_fuce;
    @InjectView(R.id.btn_class)
    SimpleButton btn_class;
    @InjectView(R.id.nsvp)
    NoSlidingViewPage content;


    private int current=0;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initViews() {

        btn_bodygame.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_fuce.setOnClickListener(this);
        btn_class.setOnClickListener(this);

        fragments.add(new BodyGamePCFragment());
        fragments.add(new BodyGamePCFragment());
        fragments.add(new BodyGamePCFragment());
        fragments.add(new PCFuCeFragment());
        fragments.add(new BodyGamePCFragment());
        content.setOffscreenPageLimit(4);
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
        //设置第一个fragment
        current = 0;
        restoreState();
        btn_bodygame.setProgress(1);
        content.setCurrentItem(current, false);

    }

    @Override
    protected void initDatas() {

    }


  @Override
    public void onClick(View v) {
        restoreState();
      switch (v.getId()) {
          case R.id.btn_bodygame:
              btn_bodygame.setProgress(1);
              if (current == 0) {
                  return;
              }
              current = 0;
              break;
          case R.id.btn_chat:
              btn_chat.setProgress(1);
              if (current == 1) {
                  return;
              }
              current = 1;
              break;
          case R.id.btn_contact:
              btn_contact.setProgress(1);
              if (current == 2) {
                  return;
              }
              current = 2;
              break;
          case R.id.btn_fuce:
              btn_fuce.setProgress(1);
              if (current == 3) {
                  return;
              }
              current = 3;
              break;
          case R.id.btn_class:
              btn_class.setProgress(1);
              if (current == 4) {
                  return;
              }
              current = 4;
              break;
      }
      content.setCurrentItem(current, false);
    }
    private void restoreState() {
        btn_bodygame.setProgress(0);
        btn_chat.setProgress(0);
        btn_contact.setProgress(0);
        btn_fuce.setProgress(0);
        btn_class.setProgress(0);

    }

    public void setAlpha(float alpha){
        tintManager.setStatusBarAlpha(alpha);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
    }

}
