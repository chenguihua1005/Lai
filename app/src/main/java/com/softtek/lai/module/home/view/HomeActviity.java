package com.softtek.lai.module.home.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.home.adapter.MainPageAdapter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_home_actviity)
public class HomeActviity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.btn_home)
    SimpleButton btn_home;

    @InjectView(R.id.btn_healthy)
    SimpleButton btn_healthy;

    @InjectView(R.id.btn_contact)
    SimpleButton btn_contact;

    @InjectView(R.id.btn_healthy_record)
    SimpleButton btn_healthy_record;

    @InjectView(R.id.btn_mine)
    SimpleButton btn_mine;

    private int currentId=0;
    private boolean isClick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_home.setOnClickListener(this);
        btn_healthy.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_healthy_record.setOnClickListener(this);
        btn_mine.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

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
                //Log.i("onPageSelected");
                //页面切换了
                isClick=false;

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
            case R.id.btn_contact:
                btn_contact.setProgress(1);
                currentId=2;
                break;
            case R.id.btn_healthy_record:
                btn_healthy_record.setProgress(1);
                currentId=3;
                break;
            case R.id.btn_mine:
                btn_mine.setProgress(1);
                currentId=4;
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
                btn_contact.setProgress(progress);
                break;
            case 3 :
                btn_healthy_record.setProgress(progress);
                break;
            case 4:
                btn_mine.setProgress(progress);
                break;
        }
    }

    private void restoreState(){
        btn_home.setProgress(0);
        btn_healthy.setProgress(0);
        btn_contact.setProgress(0);
        btn_healthy_record.setProgress(0);
        btn_mine.setProgress(0);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_exit))
                    .setMessage(getString(R.string.app_exit) + " " + getResources().getString(R.string.app_name) + " ?")
                    .setPositiveButton(getString(R.string.app_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            dialogBuilder.create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
