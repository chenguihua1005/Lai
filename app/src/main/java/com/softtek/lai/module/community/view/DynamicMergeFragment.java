package com.softtek.lai.module.community.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.community.presenter.OpenComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 11/9/2017.
 */

@SuppressLint("ValidFragment")
public class DynamicMergeFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabLayout.Tab mTabOne;
    private TabLayout.Tab mTabTwo;
    private DynamicFragment dynamicFragment;
    private FocusFragment focusFragment;
    private MyFragmentAdapter fragmentAdapter;
    private OpenComment openComment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private TextView mMore;

    @SuppressLint("ValidFragment")
    public DynamicMergeFragment(OpenComment openComment){
        this.openComment = openComment;
    }

    public DynamicMergeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_dynamic_merge,null);
        initView();
        return view;
    }

    private void initView(){
        mTabLayout = view.findViewById(R.id.tl_titles);
        mViewPager = view.findViewById(R.id.vp_content);
        mMore = view.findViewById(R.id.tv_more);
        mMore.setOnClickListener(this);

        dynamicFragment = DynamicFragment.getInstance(openComment);
        focusFragment = FocusFragment.getInstance(openComment);
        fragmentList.add(dynamicFragment);
        fragmentList.add(focusFragment);

        mViewPager.setOffscreenPageLimit(1);
        fragmentAdapter = new MyFragmentAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabOne = mTabLayout.getTabAt(0);
        mTabTwo = mTabLayout.getTabAt(1);
        mTabOne.setText("动态");
        mTabTwo.setText("关注");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_more:
                startActivity(new Intent(getActivity(), TopicListActivity.class));
                break;
        }
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return dynamicFragment;
            }
            return focusFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public List<Fragment> getFragmentList(){
        return fragmentList;
    }
}
