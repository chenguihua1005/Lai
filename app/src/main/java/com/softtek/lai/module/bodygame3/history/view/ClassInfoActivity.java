package com.softtek.lai.module.bodygame3.history.view;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallListModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallslistModel;
import com.softtek.lai.module.bodygame3.history.adapter.MyFragmentPagerAdapter;
import com.softtek.lai.module.bodygame3.history.adapter.RecyclerViewInfoAdapter;
import com.softtek.lai.module.bodygame3.history.net.HistoryService;
import com.softtek.lai.module.bodygame3.more.model.HistoryClassModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.MySwipRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_class_history)
public class ClassInfoActivity extends BaseActivity {
    @InjectView(R.id.iv_left)
    ImageView mLeftBack;
    @InjectView(R.id.tv_title)
    TextView mInfoTitle;
    @InjectView(R.id.vp_broken)
    ViewPager mBrokenViewPager;
    @InjectView(R.id.ll_point)
    LinearLayout mLlPoint;
    @InjectView(R.id.user_set_img)
    ImageView mUserSetImg_1;
    @InjectView(R.id.user_set_img2)
    ImageView mUserSetImg_2;
    @InjectView(R.id.rv_dynamic)
    RecyclerView mRecyclerView;
    @InjectView(R.id.appbar)
    AppBarLayout mAppbar;
    @InjectView(R.id.pull)
    MySwipRefreshView mPull;
    @InjectView(R.id.ll_most_fat)
    LinearLayout mMostFat;
    @InjectView(R.id.tv_most_fat_null)
    TextView mMostFatNull;
    @InjectView(R.id.ll_most_weight)
    LinearLayout mMostWeight;
    @InjectView(R.id.tv_most_weight_null)
    TextView mMostWeightNull;
    @InjectView(R.id.tv_loss_weight_value)
    TextView mLossWeightValue;
    @InjectView(R.id.tv_loss_fat_value)
    TextView mLossFatValue;

    private ArrayList<Fragment> classmates;
    private MyFragmentPagerAdapter mViewpagerAdapter;
    private int brokenIndex = 0;
    private int lastVisitableItem;
    private boolean isLoading = false;

    private RecyclerViewInfoAdapter mInfoAdapter;

    private List<PhotoWallslistModel> wallsList = new ArrayList<>();

    private HistoryClassModel historyClassModel;

    private void init() {
        mInfoTitle.setText("3月班");
        initViewpager();
//        initRecyclerView();
        initAppbarAndPull();
    }

    private void initAppbarAndPull() {
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mPull.setEnabled(true);
                } else {
                    mPull.setEnabled(false);
                }
            }
        });

        mPull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mPull.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
            }
        });
    }

    private void initRecyclerView() {
        final View popView = this.getLayoutInflater().inflate(R.layout.history_popup_window, null);
        mInfoAdapter = new RecyclerViewInfoAdapter(wallsList, new RecyclerViewInfoAdapter.ItemListener() {
            @Override
            public void onItemClick(PhotoWallslistModel item, int pos) {
            }
        }, this, popView);
        mRecyclerView.setAdapter(mInfoAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mInfoAdapter.getItemCount() < 15) {
                    if (!isLoading) {
                        isLoading = true;

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                lastVisitableItem = llm.findLastVisibleItemPosition();

            }
        });
    }

    private void initViewpager() {
        classmates = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BrokenLineFragment fragment = new BrokenLineFragment();
            classmates.add(fragment);
        }
        mViewpagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), classmates);
        mBrokenViewPager.setAdapter(mViewpagerAdapter);
        mBrokenViewPager.setOffscreenPageLimit(2);
        mBrokenViewPager.setCurrentItem(0);
        addPoint();
        mBrokenViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void addPoint() {
        mLlPoint = (LinearLayout) findViewById(R.id.ll_point);
        mLlPoint.removeAllViews();
        int length = DisplayUtil.dip2px(this, 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                length, length);
        params.setMargins(length, 0, 0, 0);
        for (int i = 0; i < 3; i++) {
            ImageView mImageView = new ImageView(this);
            if (i == 0) {
                mImageView.setBackgroundResource(R.drawable.index_chose_point);
            } else {
                mImageView.setBackgroundResource(R.drawable.index_unchose_point);
                mImageView.setLeft(DisplayUtil.dip2px(this, 4));
            }
            mLlPoint.addView(mImageView, params);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private void changePoint() {
            for (int i = 0; i < mLlPoint.getChildCount(); i++) {
                ImageView mImageView = (ImageView) mLlPoint.getChildAt(i);
                if (i == brokenIndex) {
                    mImageView.setBackgroundResource(R.drawable.index_chose_point);
                } else {
                    mImageView.setBackgroundResource(R.drawable.index_unchose_point);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            brokenIndex = position;
            changePoint();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void initViews() {
        init();
    }

    @OnClick(R.id.iv_left)
    public void back() {
        finish();
    }

    private void initFailedView() {
        mMostFat.setVisibility(View.GONE);
        mMostFatNull.setVisibility(View.VISIBLE);
        mMostWeight.setVisibility(View.GONE);
        mMostWeightNull.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mLossFatValue.setVisibility(View.INVISIBLE);
        mLossWeightValue.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initDatas() {
        dialogShow("加载中。。。");
        historyClassModel = (HistoryClassModel) getIntent().getSerializableExtra("classData");
        HistoryService service = ZillaApi.NormalRestAdapter.create(HistoryService.class);
        service.getClassDynamic(
                UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                "00a46548-339f-4768-bd6b-4cca15f57249",
//                model.getClassId(),
                "1",
                "100",
                new Callback<ResponseData<PhotoWallListModel>>() {
                    @Override
                    public void success(ResponseData<PhotoWallListModel> responseData, Response response) {
                        dialogDissmiss();
                        mPull.setRefreshing(false);
                        if (responseData.getStatus() == 200) {
                            String totalPage = responseData.getData().getTotalPage();
                            wallsList = responseData.getData().getPhotoWallslist();
                            initRecyclerView();

                        } else if (responseData.getMsg().equals("暂无数据")) {
                            initFailedView();
                        }
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        mPull.setRefreshing(false);
                        Log.d("historyClassModel-------------", String.valueOf(error));
                        Toast.makeText(ClassInfoActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
