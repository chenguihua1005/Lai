package com.softtek.lai.module.laicheng_new.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.view.MakiBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 11/28/2017.
 */

public class ChooseCustomerActivity extends MakiBaseActivity implements View.OnClickListener{
    private TabLayout mTab;
    private ViewPager mViewPager;
    private TabLayout.Tab mTabOne;
    private TabLayout.Tab mTabTwo;
    private TabLayout.Tab mTabThree;
    private FrameLayout mBack;
    private List<Fragment> fragments = new ArrayList<>();
    private CustomerIntentionFragment intentionFragment;
    private StaffFragment staffFragment;
    private BodyGameListFragment bodyGameListFragment;
    private int source;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_customer);
        source = getIntent().getIntExtra("source",0);
        initData();
        initView();
    }

    private void initView(){
        mTab = findViewById(R.id.tl_tab);
        mViewPager = findViewById(R.id.vp_content);
        mBack = findViewById(R.id.fl_left);
        mBack.setOnClickListener(this);
        fragments.add(intentionFragment);
        fragments.add(staffFragment);
        fragments.add(bodyGameListFragment);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragments));
        mTab.setupWithViewPager(mViewPager);
        mTabOne = mTab.getTabAt(0);
        mTabTwo = mTab.getTabAt(1);
        mTabThree = mTab.getTabAt(2);
        mTabOne.setText("意向客户");
        mTabTwo.setText("市场人员");
        mTabThree.setText("体管班");
        if (source == 1){
            mViewPager.setCurrentItem(1);
        }else if (source == 2){
            mViewPager.setCurrentItem(2);
        }else {
            mViewPager.setCurrentItem(0);
        }
    }

    private void initData(){
        intentionFragment = new CustomerIntentionFragment();
        staffFragment = new StaffFragment();
        bodyGameListFragment = new BodyGameListFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fl_left:
                finish();
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;

        MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            fragments = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
