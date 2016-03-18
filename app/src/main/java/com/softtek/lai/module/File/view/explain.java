package com.softtek.lai.module.File.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.module.File.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_explain)
public class explain extends Activity implements OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[]dots;
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        //initDots();
    }

    private void initViews(){
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.guideone,null));
        views.add(inflater.inflate(R.layout.guidetwo, null));
        views.add(inflater.inflate(R.layout.guidethree, null));
        views.add(inflater.inflate(R.layout.guidefour, null));
        views.add(inflater.inflate(R.layout.guidefive, null));
       // views.add(inflater.inflate(R.layout.guidesix, null));
        Util.toastMsg(""+views.size());

//        vpAdapter = new ViewPagerAdapter(views, this);
//        vp = (ViewPager) findViewById(R.id.viewpager);
//        vp.setAdapter(vpAdapter);
//        vp.setOnPageChangeListener(this);
    }
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1 || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
    @Override
    public void onPageSelected(int arg0) {
        setCurrentDot(arg0);
    }

}
