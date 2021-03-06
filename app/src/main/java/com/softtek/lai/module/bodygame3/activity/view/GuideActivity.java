/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame3.activity.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.activity.adapter.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_takeshow)
public class GuideActivity extends BaseActivity implements OnPageChangeListener {

    @InjectView(R.id.viewpager)
    ViewPager vp;

    @InjectView(R.id.iv_one)
    ImageView iv_one;

    @InjectView(R.id.iv_two)
    ImageView iv_two;

    @InjectView(R.id.iv_three)
    ImageView iv_three;

    @InjectView(R.id.iv_four)
    ImageView iv_four;

    @InjectView(R.id.iv_five)
    ImageView iv_five;

    @InjectView(R.id.iv_six)
    ImageView iv_six;
    @InjectView(R.id.iv_senven)
    ImageView iv_senven;



    private PagerAdapter vpAdapter;
    private List<View> views = new ArrayList<>();

    // 底部小点图片
    private ImageView[] dots;
    // 记录当前选中位置
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDots();
    }

    @Override
    protected void initViews() {
        tintManager.setStatusBarTintResource(R.drawable.black_draw);
        LayoutInflater inflater = LayoutInflater.from(this);
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.guidewonelayout, null));
        views.add(inflater.inflate(R.layout.guidewtwolayout, null));
        views.add(inflater.inflate(R.layout.guidewthreelayout, null));
        views.add(inflater.inflate(R.layout.guidefourlayout, null));
        views.add(inflater.inflate(R.layout.guidefivelayout, null));
        views.add(inflater.inflate(R.layout.guidesixlayout, null));
        views.add(inflater.inflate(R.layout.guidetenlayout, null));
        vpAdapter = new PagerAdapter(views, this);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);

    }

    @Override
    protected void initDatas() {

    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_clude);

        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);
    }

    //---------------------------------------
    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }
    //------------------------------------------

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }

}
