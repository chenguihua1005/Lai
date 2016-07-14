package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class ObservablePullScrollView extends PullToRefreshScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservablePullScrollView(Context context) {
        super(context);

    }

    public ObservablePullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservablePullScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(int scrollX, int scrollY, boolean clampedX, boolean clampedY);

    }
}