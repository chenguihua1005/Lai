package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Scroller;

import com.github.snowdream.android.util.Log;

/**
 * 邮箱 yll@520wcf.com
 * Created by yull on 12/17.
 */
public class YLListView extends ListView implements AbsListView.OnScrollListener {
    private Scroller mScroller; // used for scroll back
    private float mLastY = -1;

    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;

    private final static int SCROLL_DURATION = 200; // 滚动的完成时间
    private final static float OFFSET_RADIO = 6f;//弹性阻尼值
    private final static int REFRESH_THRESHOLD=6;//刷新阈

    // total list items, used to detect is at the bottom of ListView.
    private int mTotalItemCount;
    private View mHeaderView;  // 顶部图片
    private int finalTopHeight;
    private boolean isRefresh;//判断是否可以刷新或者加载
    private OnRefreshListener refreshListener;


    public YLListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public YLListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public YLListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);

        this.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(mHeaderView==null){
                            View view=new View(getContext());
                            addHeaderView(view);
                        }
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isTop(deltaY)) {
                    // the first item is showing, header has shown or pull down.
                    if(deltaY / OFFSET_RADIO>REFRESH_THRESHOLD){
                        isRefresh=true;
                    }
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() <= 1 && getHeaderHeight() > finalTopHeight) {
                    resetHeaderHeight();
                    if (isRefresh){
                        isRefresh=false;
                        if(refreshListener!=null){
                            refreshListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 加载完成
     */
    public void onCompletedRefresh(){
        isRefresh=false;
    }

    /**
     * 判断是否滑倒顶部
     */
    private boolean isTop(float deltaY){
        //Log.i("mHeaderView.getTop() >= 0"+mHeaderView.getTop());
        return getFirstVisiblePosition() == 0 && (mHeaderView.getHeight() > finalTopHeight || deltaY > 0)
                /*&& mHeaderView.getTop() >= 0*/;
    }


    // 计算滑动  当invalidate()后 系统会自动调用
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                setHeaderHeight(mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    // 设置顶部高度
    private void setHeaderHeight(int height) {
        LayoutParams layoutParams = (LayoutParams) mHeaderView.getLayoutParams();
        layoutParams.height = height;
        mHeaderView.setLayoutParams(layoutParams);
    }

    // 获取顶部高度
    public int getHeaderHeight() {
        AbsListView.LayoutParams layoutParams =
                (AbsListView.LayoutParams) mHeaderView.getLayoutParams();
        return layoutParams.height;
    }

    private void resetHeaderHeight() {
        int height = getHeaderHeight();
        if (height == 0) // not visible.
            return;
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalTopHeight - height,
                SCROLL_DURATION);
        invalidate();
    }

    /**
     * 设置顶部高度  如果不设置高度,默认就是布局本身的高度
     * @param height 顶部高度
     */
    public void setFinalTopHeight(int height) {
        this.finalTopHeight = height;
    }
    @Override
    public void addHeaderView(View v) {
        mHeaderView = v;
        super.addHeaderView(mHeaderView);
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(finalTopHeight==0) {
                            finalTopHeight = mHeaderView.getMeasuredHeight();
                        }
                        setHeaderHeight(finalTopHeight);
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    private OnScrollListener mScrollListener; // user's scroll listener

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    private void updateHeaderHeight(float delta) {
        setHeaderHeight((int) (getHeaderHeight()+delta));
        setSelection(0); // scroll to top each time
    }

    public OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface OnRefreshListener{

        public void onRefresh();

    }
}
