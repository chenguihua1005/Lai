package com.softtek.lai.module.laicheng_new.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewLaiBalanceActivity extends FragmentActivity implements View.OnClickListener{
    private static int PERMISSION_REQUEST_COARSE_LOCATION = 233;
    private FrameLayout mLeftBack;
    private ViewPager mViewPager;
    private List<FragmentModel> fragmentModels = new ArrayList<>();
    private NewSelfFragment selfFragment;
    private NewVisitorFragment vistorFragment;
    private TabLayout mTab;
    private int pageIndex;
    private TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laibalance_new);
        initUi();
    }

    private void initUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NewLaiBalanceActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }

        selfFragment = NewSelfFragment.newInstance(null);
        vistorFragment = NewVisitorFragment.newInstance(null);

        mLeftBack = (FrameLayout)findViewById(R.id.fl_left);
        mLeftBack.setOnClickListener(this);
        mViewPager = (ViewPager)findViewById(R.id.vp_content);
        mTab = (TabLayout)findViewById(R.id.tab_balance);
        mTitle = (TextView)findViewById(R.id.tv_title);
        mTitle.setOnClickListener(this);

        fragmentModels.add(new FragmentModel("给自己测量",selfFragment));
        fragmentModels.add(new FragmentModel("给访客测",vistorFragment));
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new BalanceAdapter(getSupportFragmentManager(),fragmentModels));
        pageIndex = mViewPager.getCurrentItem();
        mTab.setupWithViewPager(mViewPager);
        final TabLayout.Tab tab = mTab.getTabAt(0);
        if (tab != null){
            tab.setCustomView(R.layout.self_tab);
            TextView tv_tab = (TextView) tab.getCustomView().findViewById(R.id.tab_title);
            tv_tab.setText("给自己测");
            @SuppressLint("WrongViewCast")
            CircleImageView civ = (CircleImageView) tab.getCustomView().findViewById(R.id.iv_head);
            Picasso.with(this).load(AddressManager.get("photoHost") + UserInfoModel.getInstance().getUser().getPhoto())
                    .fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageIndex = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //do something
                Log.d("enter bleStateListener", "bleStateListener--------------");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_left:
                finish();
                break;
            case R.id.tv_title:
                finish();
                startActivity(new Intent(this, LaibalanceActivity.class));
        }
    }
}
