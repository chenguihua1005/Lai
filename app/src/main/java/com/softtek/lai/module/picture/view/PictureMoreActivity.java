package com.softtek.lai.module.picture.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.picture.adapter.PictureFragementAdapter;
import com.softtek.lai.widgets.DebugViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_picture_layout)
public class PictureMoreActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {


    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.vp)
    DebugViewPage content;

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> images;
    private PictureFragementAdapter adapter;
    private int position=0;

    @Override
    protected void initViews() {
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        images=getIntent().getStringArrayListExtra("images");
        position=getIntent().getIntExtra("position",0);
        tv_title.setText(position+1+"/"+images.size());

        toggleHideyBar();

    }

    public void toggleHideyBar() {
        View decorView=getWindow().getDecorView();
        int option=View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    @Override
    protected void initDatas() {
        if(images!=null){
            for (int i=0;i<images.size();i++){
                fragments.add(PictureMoreFragment.getInstance(images.get(i)));
            }
        }
        adapter=new PictureFragementAdapter(getSupportFragmentManager(),fragments);
        content.setAdapter(adapter);
        content.setCurrentItem(position);
        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(position+1+"/"+images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
