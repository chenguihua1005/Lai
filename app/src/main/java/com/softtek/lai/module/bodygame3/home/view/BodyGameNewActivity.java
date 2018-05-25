package com.softtek.lai.module.bodygame3.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment;
import com.softtek.lai.module.customermanagement.view.MakiBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 5/2/2018.
 */

public class BodyGameNewActivity extends MakiBaseActivity implements View.OnClickListener {
    private FrameLayout mContent;
    private LinearLayout mLlBodygame;
    private ImageView mIvBodygame;
    private TextView mTvBodygame;

    private LinearLayout mLlhonor;
    private ImageView mIvHonor;
    private TextView mTvHonor;

    private LinearLayout mLlFuce;
    private ImageView mIvFuce;
    private TextView mTvFuce;

    private LinearLayout mLlMore;
    private ImageView mIvMore;
    private TextView mTvMore;

    private MoreFragment moreFragment;
    private BodyGameFragment gameFragment;
    private HonorTabFragment honorTabFragment;
    private ActivityFragment activityFragment;
    private List<Fragment> fragmentLists = new ArrayList<>();
    private FragmentManager manager;
    private int page = 0;
    private String classId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodygame4);
        initView();
        initData();
    }

    private void initView() {
        mContent = findViewById(R.id.fl_content);
        mLlBodygame = findViewById(R.id.ll_bodygame);
        mIvBodygame = findViewById(R.id.iv_bodygame);
        mTvBodygame = findViewById(R.id.tv_bodygame);
        mLlBodygame.setOnClickListener(this);

        mLlhonor = findViewById(R.id.ll_honor);
        mIvHonor = findViewById(R.id.iv_honor);
        mTvHonor = findViewById(R.id.tv_honor);
        mLlhonor.setOnClickListener(this);

        mLlFuce = findViewById(R.id.ll_fuce);
        mIvFuce = findViewById(R.id.iv_fuce);
        mTvFuce = findViewById(R.id.tv_fuce);
        mLlFuce.setOnClickListener(this);

        mLlMore = findViewById(R.id.ll_more);
        mIvMore = findViewById(R.id.iv_more);
        mTvMore = findViewById(R.id.tv_more);
        mLlMore.setOnClickListener(this);
    }

    private void initData() {
        classId = getIntent().getStringExtra("classId");
        page = getIntent().getIntExtra("type", 0);
        moreFragment = new MoreFragment();
        gameFragment = new BodyGameFragment();
        honorTabFragment = new HonorTabFragment();
        activityFragment = new ActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("classId", classId);
        if (classId != null) {
            gameFragment.setArguments(bundle);
            honorTabFragment.setArguments(bundle);
            activityFragment.setArguments(bundle);
            moreFragment.setArguments(bundle);
        }
        fragmentLists.add(gameFragment);
        fragmentLists.add(honorTabFragment);
        fragmentLists.add(activityFragment);
        fragmentLists.add(moreFragment);
        manager = getSupportFragmentManager();
        for (Fragment fragment : fragmentLists) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fl_content, fragment);
            transaction.commit();
        }
        changeFragment(page);
        changeBottomIcon(page);
    }

    private void changeBottomIcon(int count) {
        switch (count) {
            case 0:
                mTvBodygame.setTextColor(getResources().getColor(R.color.green));
                mTvHonor.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvFuce.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvMore.setTextColor(getResources().getColor(R.color.gray_pressed));
                mIvBodygame.setImageDrawable(getResources().getDrawable(R.drawable.home));
                mIvHonor.setImageDrawable(getResources().getDrawable(R.drawable.bg_honor));
                mIvFuce.setImageDrawable(getResources().getDrawable(R.drawable.sport2_huodong));
                mIvMore.setImageDrawable(getResources().getDrawable(R.drawable.bg_more));
                break;
            case 1:
                mTvBodygame.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvHonor.setTextColor(getResources().getColor(R.color.green));
                mTvFuce.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvMore.setTextColor(getResources().getColor(R.color.gray_pressed));
                mIvBodygame.setImageDrawable(getResources().getDrawable(R.drawable.rehome));
                mIvHonor.setImageDrawable(getResources().getDrawable(R.drawable.bg_rehonor));
                mIvFuce.setImageDrawable(getResources().getDrawable(R.drawable.sport2_huodong));
                mIvMore.setImageDrawable(getResources().getDrawable(R.drawable.bg_more));
                break;
            case 2:
                mTvBodygame.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvHonor.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvFuce.setTextColor(getResources().getColor(R.color.green));
                mTvMore.setTextColor(getResources().getColor(R.color.gray_pressed));
                mIvBodygame.setImageDrawable(getResources().getDrawable(R.drawable.rehome));
                mIvHonor.setImageDrawable(getResources().getDrawable(R.drawable.bg_honor));
                mIvFuce.setImageDrawable(getResources().getDrawable(R.drawable.sport2_huodong_re));
                mIvMore.setImageDrawable(getResources().getDrawable(R.drawable.bg_more));
                break;
            case 3:
                mTvBodygame.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvHonor.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvFuce.setTextColor(getResources().getColor(R.color.gray_pressed));
                mTvMore.setTextColor(getResources().getColor(R.color.green));
                mIvBodygame.setImageDrawable(getResources().getDrawable(R.drawable.rehome));
                mIvHonor.setImageDrawable(getResources().getDrawable(R.drawable.bg_honor));
                mIvFuce.setImageDrawable(getResources().getDrawable(R.drawable.sport2_huodong));
                mIvMore.setImageDrawable(getResources().getDrawable(R.drawable.bg_remore));
                break;
        }
    }

    private void changeFragment(int index) {
        for (int i = 0; i < fragmentLists.size(); i++) {
            if (index == i) {
                if (fragmentLists.get(i).isHidden()) {
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.show(fragmentLists.get(i));
                    transaction.commit();
                }
            } else {
                if (!fragmentLists.get(i).isHidden()) {
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.hide(fragmentLists.get(i));
                    transaction.commit();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bodygame:
                if (page != 0) {
                    gameFragment.lazyLoad();
                }
                page = 0;
                changeBottomIcon(page);
                changeFragment(page);
                break;
            case R.id.ll_honor:
                if (page != 1) {
                    honorTabFragment.lazyLoad();
                }
                page = 1;
                changeBottomIcon(page);
                changeFragment(page);
                break;
            case R.id.ll_fuce:
                if (page != 2) {
                    activityFragment.lazyLoad();
                }
                page = 2;
                changeBottomIcon(2);
                changeFragment(2);
                break;
            case R.id.ll_more:
                if (page != 3) {
                    moreFragment.lazyLoad();
                }
                page = 3;
                changeBottomIcon(3);
                changeFragment(3);
                break;
        }
    }
}
