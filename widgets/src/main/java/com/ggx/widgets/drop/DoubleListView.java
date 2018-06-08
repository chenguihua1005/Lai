package com.ggx.widgets.drop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ggx.widgets.R;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2016/11/17
 */

public class DoubleListView<LEFT,RIGHT> extends LinearLayout implements AdapterView.OnItemClickListener{

    private SimpleTextAdapter<LEFT> left_adapter;
    private SimpleTextAdapter<RIGHT> right_adapter;
    private ListView lv_left;
    private ListView lv_right;

    public DoubleListView(Context context) {
        super(context);
        init(context);
    }

    public DoubleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DoubleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.merge_filter_list, this);

        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (ListView) findViewById(R.id.lv_right);
        lv_left.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(lv_left.getFirstVisiblePosition()!=0){
                            lv_left.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        lv_right.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(lv_right.getFirstVisiblePosition()!=0){
                            lv_right.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        lv_left.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv_right.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lv_left.setOnItemClickListener(this);
        lv_right.setOnItemClickListener(this);
    }
    public DoubleListView<LEFT, RIGHT> leftAdapter(SimpleTextAdapter<LEFT> leftAdapter) {
        this.left_adapter = leftAdapter;
        lv_left.setAdapter(leftAdapter);
        return this;
    }

    public DoubleListView<LEFT, RIGHT> rightAdapter(SimpleTextAdapter<RIGHT> rightAdapter) {
        right_adapter = rightAdapter;
        lv_right.setAdapter(rightAdapter);
        return this;
    }

    public void setLeftList(List<LEFT> list, int checkedPosition) {
        left_adapter.setList(list);
        if (checkedPosition != -1) {
//            lv_left.performItemClick(mLeftAdapter.getView(checkedPositoin, null, null), checkedPositoin, mLeftAdapter.getItemId(checkedPositoin));//调用此方法相当于点击.第一次进来时会触发重复加载.
            lv_left.setItemChecked(checkedPosition, true);
        }
    }

    public void setRightList(List<RIGHT> list, int checkedPosition) {
        right_adapter.setList(list);
        if (checkedPosition != -1) {
            lv_right.setItemChecked(checkedPosition, true);
        }
    }

    private OnLeftItemClickListener<LEFT, RIGHT> mOnLeftItemClickListener;
    private OnRightItemClickListener<LEFT, RIGHT> mOnRightItemClickListener;

    public interface OnLeftItemClickListener<LEFT, RIGHT> {
        List<RIGHT> provideRightList(LEFT leftAdapter, int position);
    }

    public interface OnRightItemClickListener<LEFT, RIGHT> {
        void onRightItemClick(LEFT item, RIGHT childItem);
    }

    public DoubleListView<LEFT, RIGHT> onLeftItemClickListener(OnLeftItemClickListener<LEFT, RIGHT> onLeftItemClickListener) {
        this.mOnLeftItemClickListener = onLeftItemClickListener;
        return this;
    }

    public DoubleListView<LEFT, RIGHT> onRightItemClickListener(OnRightItemClickListener<LEFT, RIGHT> onRightItemClickListener) {
        this.mOnRightItemClickListener = onRightItemClickListener;
        return this;
    }

    //========================点击事件===================================
    private int mRightLastChecked;
    private int mLeftLastPosition;
    private int mLeftLastCheckedPosition;

    public static long mLastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        if(isFastDoubleClick()){
            return;
        }
        if (left_adapter == null || right_adapter == null) {
            return;
        }

        if (parent == lv_left) {
            mLeftLastPosition = position;

            if (mOnLeftItemClickListener != null) {
                LEFT item = left_adapter.getItem(position);

                List<RIGHT> rightds = mOnLeftItemClickListener.provideRightList(item, position);
                right_adapter.setList(rightds);

                if (rightds==null||rightds.isEmpty()) {
                    //当前点的就是这个条目
                    mLeftLastCheckedPosition = -1;
                }
            }

            lv_right.setItemChecked(mRightLastChecked, mLeftLastCheckedPosition == position);
        } else {
            mLeftLastCheckedPosition = mLeftLastPosition;
            mRightLastChecked = position;

            if (mOnRightItemClickListener != null) {
                mOnRightItemClickListener.onRightItemClick(left_adapter.getItem(mLeftLastCheckedPosition), right_adapter.getItem(mRightLastChecked));
            }
        }
    }
}
