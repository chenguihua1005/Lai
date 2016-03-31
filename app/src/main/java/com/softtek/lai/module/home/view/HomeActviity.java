package com.softtek.lai.module.home.view;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.home.adapter.MainPageAdapter;

import butterknife.InjectView;
import zilla.libcore.lifecircle.exit.AppManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_home_actviity)
public class HomeActviity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.btn_home)
    SimpleButton btn_home;

    @InjectView(R.id.btn_healthy)
    SimpleButton btn_healthy;

    @InjectView(R.id.btn_healthy_record)
    SimpleButton btn_healthy_record;

    @InjectView(R.id.btn_mine)
    SimpleButton btn_mine;

    private int currentId=0;
    private boolean isClick=false;

    Drawable white;
    Drawable green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_home.setOnClickListener(this);
        btn_healthy.setOnClickListener(this);
        btn_healthy_record.setOnClickListener(this);
        btn_mine.setOnClickListener(this);
        white=getResources().getDrawable(R.drawable.bg_white);
        green=getResources().getDrawable(R.drawable.bg_green);
    }

    @Override
    protected void initViews() {
        content.setOffscreenPageLimit(5);
    }

    @Override
    protected void initDatas() {
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i("onPageScrolled");
                if(!isClick){
                    setChildProgress(position,1-positionOffset);
                    setChildProgress(position+1,positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("onPageSelected>>>>>"+position);
                //页面切换了
                isClick=false;
                switch (position){
                    case 0:
                        btn_home.setBackground(green);
                        btn_healthy.setBackground(white);
                        btn_healthy_record.setBackground(white);
                        btn_mine.setBackground(white);
                        break;
                    case 1:
                        btn_home.setBackground(white);
                        btn_healthy.setBackground(green);
                        btn_healthy_record.setBackground(white);
                        btn_mine.setBackground(white);
                        break;
                    case 2:
                        btn_home.setBackground(white);
                        btn_healthy.setBackground(white);
                        btn_healthy_record.setBackground(green);
                        btn_mine.setBackground(white);
                        break;
                    case 3:
                        btn_home.setBackground(white);
                        btn_healthy.setBackground(white);
                        btn_healthy_record.setBackground(white);
                        btn_mine.setBackground(green);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.i("onPageScrollStateChanged>>>>>>"+state);
                currentId=state;
            }
        });

        restoreState();
        btn_home.setProgress(1);
        currentId = 0 ;
        content.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        isClick=true;
        restoreState();
        switch (v.getId()){
            case R.id.btn_home:
                btn_home.setProgress(1);
                currentId=0;
                break;
            case R.id.btn_healthy:
                btn_healthy.setProgress(1);
                currentId=1;
                break;
            case R.id.btn_healthy_record:
                btn_healthy_record.setProgress(1);
                currentId=2;
                break;
            case R.id.btn_mine:
                btn_mine.setProgress(1);
                currentId=3;
                break;
        }
        content.setCurrentItem(currentId, false);
    }

    private  void setChildProgress(int position , float progress){
        switch (position) {
            case 0 :
                btn_home.setProgress(progress);
                break;
            case 1:
                btn_healthy.setProgress(progress);
                break;
            case 2 :
                btn_healthy_record.setProgress(progress);
                break;
            case 3:
                btn_mine.setProgress(progress);
                break;
        }
    }

    private void restoreState(){
        btn_home.setProgress(0);
        btn_healthy.setProgress(0);
        btn_healthy_record.setProgress(0);
        btn_mine.setProgress(0);

    }

    int count=2;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(count==2){
                Util.toastMsg("再按一次,退出应用");
                //5秒中后恢复
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count=2;
                    }
                },5000);
            }
            count--;
            if(count<=0){
                AppManager.getAppManager().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
