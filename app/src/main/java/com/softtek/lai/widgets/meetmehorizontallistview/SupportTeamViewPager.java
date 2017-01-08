package com.softtek.lai.widgets.meetmehorizontallistview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by curry on 2017/1/8.
 */

public class SupportTeamViewPager extends ViewPager {

    private int currentPosition = 0;


    public SupportTeamViewPager(Context context) {
        super(context);
        initView();
    }

    public SupportTeamViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    //不需要加，在外面用下面的方法会触发addOnPageChangeListener监听
//    @Override
//    public void setCurrentItem(int item) {
//        currentPosition = item;
//        super.setCurrentItem(item);
//    }
}
