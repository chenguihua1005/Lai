/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.InjectView;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.adapter.ViewPagerAdapter;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

@InjectLayout(R.layout.activity_explain)
public class ExplainActivity extends BaseActivity implements OnPageChangeListener {

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


    private ViewPagerAdapter vpAdapter;
    private List<View> views = new ArrayList<View>();

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
        //views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.guideone, null));
        views.add(inflater.inflate(R.layout.guidetwo, null));
        views.add(inflater.inflate(R.layout.guidethree, null));
        views.add(inflater.inflate(R.layout.guidefour, null));
        views.add(inflater.inflate(R.layout.guidefive, null));
        views.add(inflater.inflate(R.layout.guidesix, null));
        vpAdapter = new ViewPagerAdapter(views, this);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);

       // iv_one.setBackgroundResource(R.drawable.white_dot);
    }

    @Override
    protected void initDatas() {

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


//    @Override
//    public void onPageSelected(int arg0) {
//        Log.i("当前第几？》》》》" + arg0);
//        switch (arg0) {
//            case 0:
//                iv_one.setBackgroundResource(R.drawable.white_dot);
//                iv_two.setBackgroundResource(R.drawable.dark_dot);
//                iv_three.setBackgroundResource(R.drawable.dark_dot);
//                iv_four.setBackgroundResource(R.drawable.dark_dot);
//                iv_five.setBackgroundResource(R.drawable.dark_dot);
//                iv_six.setBackgroundResource(R.drawable.dark_dot);
//                break;
//            case 1:
//                iv_one.setBackgroundResource(R.drawable.dark_dot);
//                iv_two.setBackgroundResource(R.drawable.white_dot);
//                iv_three.setBackgroundResource(R.drawable.dark_dot);
//                iv_four.setBackgroundResource(R.drawable.dark_dot);
//                iv_five.setBackgroundResource(R.drawable.dark_dot);
//                iv_six.setBackgroundResource(R.drawable.dark_dot);
//                break;
//            case 2:
//                iv_one.setBackgroundResource(R.drawable.dark_dot);
//                iv_two.setBackgroundResource(R.drawable.dark_dot);
//                iv_three.setBackgroundResource(R.drawable.white_dot);
//                iv_four.setBackgroundResource(R.drawable.dark_dot);
//                iv_five.setBackgroundResource(R.drawable.dark_dot);
//                iv_six.setBackgroundResource(R.drawable.dark_dot);
//                break;
//            case 3:
//                iv_one.setBackgroundResource(R.drawable.dark_dot);
//                iv_two.setBackgroundResource(R.drawable.dark_dot);
//                iv_three.setBackgroundResource(R.drawable.dark_dot);
//                iv_four.setBackgroundResource(R.drawable.white_dot);
//                iv_five.setBackgroundResource(R.drawable.dark_dot);
//                iv_six.setBackgroundResource(R.drawable.dark_dot);
//                break;
//            case 4:
//                iv_one.setBackgroundResource(R.drawable.dark_dot);
//                iv_two.setBackgroundResource(R.drawable.dark_dot);
//                iv_three.setBackgroundResource(R.drawable.dark_dot);
//                iv_four.setBackgroundResource(R.drawable.dark_dot);
//                iv_five.setBackgroundResource(R.drawable.white_dot);
//                iv_six.setBackgroundResource(R.drawable.dark_dot);
//                break;
//            case 5:
//                iv_one.setBackgroundResource(R.drawable.dark_dot);
//                iv_two.setBackgroundResource(R.drawable.dark_dot);
//                iv_three.setBackgroundResource(R.drawable.dark_dot);
//                iv_four.setBackgroundResource(R.drawable.dark_dot);
//                iv_five.setBackgroundResource(R.drawable.dark_dot);
//                iv_six.setBackgroundResource(R.drawable.white_dot);
//                break;
//
//        }
//    }

}
