package com.softtek.lai.module.bodygame3.more.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.adapter.FuceAlbumAdapter;
import com.softtek.lai.module.bodygame3.more.model.FuceAlbumModel;
import com.softtek.lai.module.bodygame3.more.model.FuceClassAlbumModel;
import com.softtek.lai.module.bodygame3.more.model.FucePhotoModel;
import com.softtek.lai.module.bodygame3.more.present.FuceAlbumManager;
import com.softtek.lai.widgets.FuCeSelectPicPopupWindow;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

@InjectLayout(R.layout.activity_fuce_album)
public class FuceAlbumActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, FuceAlbumManager.FuceAlbumManagerCallback {
    private static final String TAG = "FuceAlbumActivity";
    //    @InjectView(R.id.refresh)
//    SwipeRefreshLayout refresh;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.empty)
    FrameLayout empty;

    FuCeSelectPicPopupWindow menuWindow;

    String value;
    String url;
    String title_value;


    int pageIndex = 1;
    int totalPage = 0;
    private FuceAlbumManager community;
    private List<FuceClassAlbumModel> fucePhotos = new ArrayList<>();
    private FuceAlbumAdapter adapter;

    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    public static final String UPDATE_PHOTO_NUMBER = "UPDATE_PHOTO_NUMBER";


    @Override
    protected void initViews() {
        tv_title.setText("复测相册");


//        refresh.setOnRefreshListener(this);
//        refresh.setColorSchemeResources(android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);

        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示

        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        registerBroadcastReceiver();//注册

        final Object tag = new Object();
        adapter = new FuceAlbumAdapter(this, fucePhotos, tag);
        ptrlv.setAdapter(adapter);
        ptrlv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {//正在滚动或手指快速一滑
                    Picasso.with(FuceAlbumActivity.this).pauseTag(tag);
                } else if (scrollState == SCROLL_STATE_IDLE) {//停止滑动
                    Picasso.with(FuceAlbumActivity.this).resumeTag(tag);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        pageIndex = 1;
        community = new FuceAlbumManager(this);
        community.getFuceAlbum(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), 1);

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        community.getFuceAlbum(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), 1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            community.getFuceAlbum(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), pageIndex);
        } else {
            pageIndex--;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();

                }
            }, 300);
        }

    }

    static int total = 0;
    static int count = 0;

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATE_PHOTO_NUMBER);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                total = 0;
                count = 0;

                for (int i = 0; i < fucePhotos.size(); i++) {
                    FuceClassAlbumModel classAlbumModel = fucePhotos.get(i);
                    total += classAlbumModel.getPhotoList().size();
                    for (int j = 0; j < classAlbumModel.getPhotoList().size(); j++) {
                        FucePhotoModel fucePhotoModel = classAlbumModel.getPhotoList().get(j);
                        if (fucePhotoModel.isSelect()) {
                            count++;
                        }
                    }

                }
                if (0 != count) {
                    tv_title.setText("已选择" + String.valueOf(count) + "/" + total + "张照片");
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setText("分享");
                } else {
                    tv_title.setText("复测相册");
                    tv_right.setText("");
                    tv_right.setVisibility(View.GONE);
                }

            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(FuceAlbumActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(FuceAlbumActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(FuceAlbumActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(FuceAlbumActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(FuceAlbumActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(FuceAlbumActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }
        }
    };

    int flag = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right: {
                Log.i(TAG, "点击分享按钮。。。。。。。。。。。。。。。。。。 count = " + count);
                if (flag == 0) {
                    if (count != 0) {
                        tv_right.setText("取消");
                        menuWindow = new FuCeSelectPicPopupWindow(FuceAlbumActivity.this, itemsOnClick);
                        //显示窗口
                        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        menuWindow.showAtLocation(FuceAlbumActivity.this.findViewById(R.id.Re_pers_page), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                        flag = 1;

                    }
                } else {// flag = 1;
                    Log.i(TAG, "点击取消按钮。。。。。。。。。。。。。。。。。。");
                    count = 0;
                    tv_title.setText("复测相册");
                    tv_right.setText("");
                    tv_right.setVisibility(View.GONE);
                    if (menuWindow != null && menuWindow.isShowing()) {
                        menuWindow.dismiss();
                    }

                    for (int i = 0; i < fucePhotos.size(); i++) {
                        FuceClassAlbumModel classAlbumModel = fucePhotos.get(i);
                        for (int j = 0; j < classAlbumModel.getPhotoList().size(); j++) {
                            FucePhotoModel fucePhotoModel = classAlbumModel.getPhotoList().get(j);
                            fucePhotoModel.setSelect(false);
                            Log.i(TAG, "model =" + new Gson().toJson(fucePhotoModel));
                        }
                    }
                    adapter.notifyDataSetChanged();

                    flag = 0;

                }
//                if (count != 0) {
//                    tv_right.setText("取消");
//                    menuWindow = new FuCeSelectPicPopupWindow(FuceAlbumActivity.this, itemsOnClick);
//                    //显示窗口
//                    menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    menuWindow.showAtLocation(FuceAlbumActivity.this.findViewById(R.id.Re_pers_page), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//                } else {
//                    Log.i(TAG, "点击取消按钮。。。。。。。。。。。。。。。。。。");
//                    count = 0;
//                    tv_title.setText("复测相册");
//                    tv_right.setText("");
//                    tv_right.setVisibility(View.GONE);
//                    if (menuWindow != null && menuWindow.isShowing()) {
//                        menuWindow.dismiss();
//                    }
//
//                    for (int i = 0; i < fucePhotos.size(); i++) {
//                        FuceClassAlbumModel classAlbumModel = fucePhotos.get(i);
//                        for (int j = 0; j < classAlbumModel.getPhotoList().size(); j++) {
//                            FucePhotoModel fucePhotoModel = classAlbumModel.getPhotoList().get(j);
//                            fucePhotoModel.setSelect(false);
//                            Log.i(TAG, "model =" + new Gson().toJson(fucePhotoModel));
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                }
            }

            break;
        }
    }


    @Override
    public void getFuceAlbum(FuceAlbumModel model) {
        Log.i(TAG, "接口获取数据 = " + new Gson().toJson(model));
        try {
            ptrlv.onRefreshComplete();
            if (model == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (model.getTotalPages() == 0 || model.getPhotos() == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }

            totalPage = model.getTotalPages();

            List<FuceClassAlbumModel> photos = model.getPhotos();
            if (photos == null || photos.isEmpty()) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (pageIndex == 1) {
                this.fucePhotos.clear();
            }
            this.fucePhotos.addAll(photos);
            adapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void onRefresh() {
////        refresh.setRefreshing(true);
////        zilla.libcore.util.Util.toastMsg("哈哈哈");
////        refresh.setRefreshing(false);
//    }
}
