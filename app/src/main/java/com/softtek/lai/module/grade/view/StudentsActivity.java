package com.softtek.lai.module.grade.view;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.grade.adapter.TabContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_students)
public class StudentsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener,
        ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener,View.OnClickListener{

    @InjectView(R.id.tabhost)
    FragmentTabHost tabHost;

    @InjectView(R.id.tabcontent)
    ViewPager tabcontent;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    private List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void initViews() {
        //设置viewpage的页面改变监听
        tabcontent.addOnPageChangeListener(this);
        //设置tab改变监听
        tabHost.setOnTabChangedListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("学员列表");
        //设置tab的内容区域
        tabHost.setup(this,getSupportFragmentManager(),R.id.tabcontent);
        //添加标签
        tabHost.addTab(tabHost.newTabSpec("0").setIndicator("按减重斤数"),LossWeightFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("0").setIndicator("按减重百分比"),LossWeightFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("0").setIndicator("按体脂率"),LossWeightFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("0").setIndicator("按腰围变化"),LossWeightFragment.class,null);
        fragments.add(new LossWeightFragment());
        fragments.add(new LossWeightFragment());
        fragments.add(new LossWeightFragment());
        fragments.add(new LossWeightFragment());
        tabcontent.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //获取tabWidget
        TabWidget widget = tabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        tabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //当Tab改变时
    @Override
    public void onTabChanged(String tabId) {
        int tab=tabHost.getCurrentTab();
        tabcontent.setCurrentItem(tab);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
