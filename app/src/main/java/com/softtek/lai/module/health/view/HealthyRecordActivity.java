package com.softtek.lai.module.health.view;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.health.adapter.HealthyRecordFragmentAdapter;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_weight)
public class HealthyRecordActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener,ViewPager.OnPageChangeListener{


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    NoSlidingViewPage tab_content;
    List<Fragment> fragmentList=new ArrayList<>();
    int item;
    int flag=0;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("历史数据");
    }

    @Override
    protected void initDatas() {
        WeightFragment weightFragment=new WeightFragment();
        BodyFatFragment bodyFatFragment=new BodyFatFragment();
        FatFragment fatFragment=new FatFragment();
        WaistlineFragment waistlineFragment=new WaistlineFragment();
        BustFragment bustFragment=new BustFragment();
        HiplineFragment hiplineFragment=new HiplineFragment();
        UpHiplineFragment upHiplineFragment=new UpHiplineFragment();
        LegFragment legFragment=new LegFragment();
        ShinFragment shinFragment=new ShinFragment();
        fragmentList.add(weightFragment);
        fragmentList.add(bodyFatFragment);
        fragmentList.add(fatFragment);
        fragmentList.add(bustFragment);
        fragmentList.add(waistlineFragment);
        fragmentList.add(hiplineFragment);
        fragmentList.add(upHiplineFragment);
        fragmentList.add(legFragment);
        fragmentList.add(shinFragment);
        tab_content.setAdapter(new HealthyRecordFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tab_content.addOnPageChangeListener(this);
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_content.setOffscreenPageLimit(0);
        item=getIntent().getIntExtra("id",0);
        flag=getIntent().getIntExtra("flag",0);
        tab_content.setCurrentItem(item);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(flag==1){
            flag=0;
        }
        else {
            Log.i("页面切换到===》"+position);
            switch (position)
            {
                case 0:
                    ((WeightFragment)fragmentList.get(0)).updateStatus();
                    break;
                case 1:
                    ((BodyFatFragment)fragmentList.get(1)).updateBodyFatStatus();
                    break;
                case 2:
                    ((FatFragment)fragmentList.get(2)).updateFatStatus();
                    break;
                case 3:
                    ((BustFragment)fragmentList.get(3)).updateBustStatus();
                    break;
                case 4:
                    ((WaistlineFragment)fragmentList.get(4)).updateWaistlineStatus();
                    break;
                case 5:
                    ((HiplineFragment)fragmentList.get(5)).updateHiplineStatus();
                    break;
                case 6:
                    ((UpHiplineFragment)fragmentList.get(6)).updateUpHiplineStatus();
                    break;
                case 7:
                    ((LegFragment)fragmentList.get(7)).updateLegStatus();
                    break;
                case 8:
                    ((ShinFragment)fragmentList.get(8)).updateShinStatus();
                    break;

            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
