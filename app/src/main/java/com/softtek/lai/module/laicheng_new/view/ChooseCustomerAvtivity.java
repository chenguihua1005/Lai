package com.softtek.lai.module.laicheng_new.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.softtek.lai.R;

/**
 * Created by jia.lu on 11/28/2017.
 */

public class ChooseCustomerAvtivity extends FragmentActivity{
    private TabLayout mTab;
    private ViewPager mViewPager;
    private TabLayout.Tab mTabOne;
    private TabLayout.Tab mTabTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_customer);
        initView();
    }

    private void initView(){
        mTab = findViewById(R.id.tl_tab);
        mViewPager = findViewById(R.id.vp_content);
        mTab.setupWithViewPager(mViewPager);
        mTabOne = mTab.getTabAt(0);
        mTabTwo = mTab.getTabAt(1);
        mTabOne.setText("意向客户");
        mTabTwo.setText("市场人员");
    }
}
