package com.softtek.lai.module.bodygame3.history.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.view.HonorActivity;
import com.softtek.lai.module.bodygame3.history.adapter.MyFragmentPagerAdapter;
import com.softtek.lai.module.bodygame3.history.adapter.RecyclerViewInfoAdapter;
import com.softtek.lai.module.bodygame3.history.model.CommentEvent;
import com.softtek.lai.module.bodygame3.history.model.DynamicBean;
import com.softtek.lai.module.bodygame3.history.model.HistoryDetailsBean;
import com.softtek.lai.module.bodygame3.history.net.HistoryService;
import com.softtek.lai.module.bodygame3.more.model.HistoryClassModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallListModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallslistModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

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
    @InjectView(R.id.tv_loss_weight)
    TextView mLossWeight;
    @InjectView(R.id.tv_loss_fat)
    TextView mLossFat;
    @InjectView(R.id.ll_history)
    RelativeLayout mHistoryView;
    @InjectView(R.id.ll_comment_layout)
    LinearLayout mCommentLayout;
    @InjectView(R.id.et_comment_content)
    TextView mEdtComment;
    @InjectView(R.id.btn_comment_submit)
    Button mCommentSubmit;
    @InjectView(R.id.cl_content)
    CoordinatorLayout mCLContent;
    @InjectView(R.id.tv_recycler_no_data)
    TextView mRecyclerNoData;
    @InjectView(R.id.honors)
    LinearLayout mGotoHonors;

    private ArrayList<Fragment> classmates;
    private MyFragmentPagerAdapter mViewpagerAdapter;
    private int brokenIndex = 0;
    private int lastVisitableItem;
    private boolean isLoading = false;

    private RecyclerViewInfoAdapter mInfoAdapter;

    private List<DynamicBean.PhotoWallslistBean> wallsList = new ArrayList<>();

    private HistoryClassModel historyClassModel;
    private HistoryService service;
    private int page = 1;
    private static final int LOADCOUNT = 6;
    private CoordinatorLayout.Behavior appbarBehavior;

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
        mInfoAdapter = new RecyclerViewInfoAdapter(
                wallsList,
                new RecyclerViewInfoAdapter.ItemListener() {
                    @Override
                    public void onItemClick(DynamicBean.PhotoWallslistBean item, int pos) {
                    }
                },
                new RecyclerViewInfoAdapter.CommentListener() {
                    @Override
                    public void onCommentClick(final CommentEvent event) {
                       doComment(event);
                    }
                },
                this,
                popView);
        mRecyclerView.setAdapter(mInfoAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = mInfoAdapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && count > LOADCOUNT && lastVisitableItem + 1 == count) {
                    //加载更多数据
                    page++;
                    updateRecyclerView(page, 6);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                lastVisitableItem = llm.findLastVisibleItemPosition();

            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftInput(view.getWindowToken());
                mCommentLayout.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void updateRecyclerView(int pageIndex, int pageCount) {
        try {
            service.getClassDynamic(
                    UserInfoModel.getInstance().getToken(),
                    UserInfoModel.getInstance().getUserId(),
                    historyClassModel.getClassId(),
                    pageIndex + "",
                    pageCount + "",
                    new RequestCallback<ResponseData<DynamicBean>>() {
                        @Override
                        public void success(ResponseData<DynamicBean> responseData, Response response) {
                            if (responseData.getStatus() == 200) {
                                if (responseData.getData().getPhotoWallslist() != null) {
                                    if (!responseData.getData().getPhotoWallslist().isEmpty()) {
                                        wallsList.addAll(responseData.getData().getPhotoWallslist());
                                        mInfoAdapter.notifyDataSetChanged();
                                    } else {
                                        page--;
                                    }
                                }

                            } else {
                                if (responseData.getMsg().equals("暂无数据")){
                                    Util.toastMsg("暂无更多数据");
                                    mInfoAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (mPull.isRefreshing()) {
                                mPull.setRefreshing(false);
                            }
                            super.failure(error);
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewpager(HistoryDetailsBean bean) {
        BrokenLineFragment1 fragment1 = BrokenLineFragment1.newInstance(bean);
        BrokenLineFragment2 fragment2 = BrokenLineFragment2.newInstance(bean);
        BrokenLineFragment3 fragment3 = BrokenLineFragment3.newInstance(bean);
        classmates.add(fragment1);
        classmates.add(fragment2);
        classmates.add(fragment3);
        mViewpagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), classmates);
        mBrokenViewPager.setAdapter(mViewpagerAdapter);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.honors)
    public void onClick() {
        HonorActivity.startHonorActivity2(ClassInfoActivity.this,historyClassModel.getClassId(),true);
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
        initAppbarAndPull();
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
        mLossFatValue.setVisibility(View.INVISIBLE);
        mLossWeightValue.setVisibility(View.INVISIBLE);
    }

    private void getHistoryInfo() {
        try {
            service.getHistoryInfo(
                    UserInfoModel.getInstance().getToken(),
                    UserInfoModel.getInstance().getUserId(),
                    historyClassModel.getClassId(),
    //                "C4E8E179-FD99-4955-8BF9-CF470898788B",
                    new RequestCallback<ResponseData<HistoryDetailsBean>>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void success(ResponseData<HistoryDetailsBean> responseData, Response response) {
                            if (responseData.getStatus() == 200) {
                                initViewpager(responseData.getData());
                                if (responseData.getData().getList_Top1() != null) {
                                    if (responseData.getData().getList_Top1().size() > 2) {
                                        initHonor(responseData.getData().getList_Top1());
                                    } else {
                                        initFailedView();
                                    }
                                } else {
                                    initFailedView();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            initViewpager(new HistoryDetailsBean());
                            initFailedView();
                            super.failure(error);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doComment(final CommentEvent event){
        View itemView = event.getView();
        final LinearLayout mPersonCommentLayout = (LinearLayout) itemView.findViewById(R.id.ll_comment_person);
        final View mItemBottom = (View) itemView.findViewById(R.id.item_bottom);
        mCommentLayout.setVisibility(View.VISIBLE);
        mEdtComment.setFocusable(true);
        mEdtComment.setFocusableInTouchMode(true);
        mEdtComment.requestFocus();
        mEdtComment.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftInputUtil.showInputAsView(ClassInfoActivity.this, mEdtComment);
            }
        }, 400);
        mCommentLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] position1 = new int[2];
                mItemBottom.getLocationOnScreen(position1);
                int[] position2 = new int[2];
                mCommentLayout.getLocationOnScreen(position2);
                appbarBehavior = ((CoordinatorLayout.LayoutParams) mAppbar.getLayoutParams()).getBehavior();
                int appbarHigh = mAppbar.getTotalScrollRange();
                int trueHigh = (int) mAppbar.getY() + appbarHigh;
                if (trueHigh > position1[1] - position2[1]) {
                    appbarBehavior.onNestedPreScroll(mCLContent, mAppbar, null, 0, position1[1] - position2[1], new int[]{0, 0});
                } else {
                    appbarBehavior.onNestedPreScroll(mCLContent, mAppbar, null, 0, trueHigh, new int[]{0, 0});
                    mRecyclerView.scrollBy(0, position1[1] - position2[1] - trueHigh);
                }
//                appbarBehavior.onNestedPreScroll(mCLContent, mAppbar, null, 0,, new int[]{0, 0});
//                mRecyclerView.scrollBy(0, position1[1] - position2[1]);
            }
        }, 1000);
        mCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommentLayout.setVisibility(View.GONE);
                hideSoftInput(view.getWindowToken());
                try {
                    service.postCommnents(
                            UserInfoModel.getInstance().getToken(),
                            event.getHealthId(),
                            event.getAccountId(),
                            mEdtComment.getText().toString(),
                            new RequestCallback<ResponseData>() {
                                @Override
                                public void success(ResponseData responseData, Response response) {
                                    if (responseData.getStatus() == 200) {
                                        TextView commentText = new TextView(ClassInfoActivity.this);
                                        String commentName = UserInfoModel.getInstance().getUser().getNickname();
                                        SpannableString ss = new SpannableString(commentName + "：");
                                        ss.setSpan(new ForegroundColorSpan(0xFF576A80), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                        commentText.setText(ss);
                                        commentText.append(mEdtComment.getText().toString());
                                        mPersonCommentLayout.addView(commentText);
                                        mEdtComment.setText("");
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    mEdtComment.setText("");
                                    Log.d("comment---------------", error.toString());
                                    super.failure(error);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initHonor(List<HistoryDetailsBean.ListTop1Bean> list) {
        Picasso.with(ClassInfoActivity.this).load(AddressManager.get("photoHost") + list.get(0).getPhoto()).placeholder(R.drawable.default_icon_rect)
                .error(R.drawable.default_icon_rect).into(mUserSetImg_1);
        Picasso.with(ClassInfoActivity.this).load(AddressManager.get("photoHost") + list.get(1).getPhoto()).placeholder(R.drawable.default_icon_rect)
                .error(R.drawable.default_icon_rect).into(mUserSetImg_2);
        mLossFat.setText(list.get(0).getUserName());
        mLossWeight.setText(list.get(1).getUserName());
        mLossFatValue.setText("减重" + list.get(0).getLoss() + "斤");
        mLossWeightValue.setText("减脂" + list.get(1).getLoss() + "%");
    }

    private void getClassDynamicInfo() {
        service.getClassDynamic(
                UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                historyClassModel.getClassId(),
                "1",
                "6",
                new RequestCallback<ResponseData<DynamicBean>>() {
                    @Override
                    public void success(ResponseData<DynamicBean> responseData, Response response) {
                        mPull.setRefreshing(false);
                        if (responseData.getStatus() == 200) {
                            wallsList.addAll(responseData.getData().getPhotoWallslist());
                            mInfoAdapter.notifyDataSetChanged();

                        } else if (responseData.getMsg().equals("暂无数据")) {
                            initFailedView();
                            mRecyclerNoData.setVisibility(View.VISIBLE);
//                            initRecyclerView();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (mPull.isRefreshing()) {
                            mPull.setRefreshing(false);
                        }
                        mRecyclerNoData.setVisibility(View.VISIBLE);
                        super.failure(error);
                    }
                });
    }

    @Override
    protected void initDatas() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        classmates = new ArrayList<>();
        wallsList.clear();
        initRecyclerView();
        mBrokenViewPager.setOffscreenPageLimit(2);
        service = ZillaApi.NormalRestAdapter.create(HistoryService.class);
        historyClassModel = (HistoryClassModel) getIntent().getSerializableExtra("classData");
        mInfoTitle.setText(historyClassModel.getClassName());
        getClassDynamicInfo();
        getHistoryInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
