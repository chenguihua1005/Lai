package com.softtek.lai.module.bodygame2.view;

import android.support.design.widget.TabLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame)
public class BodyGameActivity extends BaseActivity {

    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    private BodyGameSPFragment bodyGameSPFragment;
    private ContactFragment contactFragment;
    private ChatFragment chatFragment;
    private FuCeFragment fuCeFragment;
    private ClassFragment classFragment;

    private int current=0;

    @Override
    protected void initViews() {

        TabLayout.Tab tab1=tabLayout.newTab().setText("体管赛");
        TabLayout.Tab tab2=tabLayout.newTab().setText("222");
        TabLayout.Tab tab3=tabLayout.newTab().setText("333");
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        bodyGameSPFragment =new BodyGameSPFragment();
        contactFragment =new ContactFragment();
        chatFragment =new ChatFragment();
        fuCeFragment=new FuCeFragment();
        classFragment=new ClassFragment();
        //设置第一个fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bodyGameSPFragment).commit();
        current=0;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tabLayout.getSelectedTabPosition();
                //Log.i("MainActivity","当前位置>>>>"+position);
                if(current==position){
                    return;
                }
                current=position;
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bodyGameSPFragment).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, chatFragment).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactFragment).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fuCeFragment).commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, classFragment).commit();
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
