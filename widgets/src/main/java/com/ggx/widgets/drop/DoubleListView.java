package com.ggx.widgets.drop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ggx.widgets.R;
import com.ggx.widgets.adapter.EasyAdapter;

/**
 * @author jerry.Guan
 *         created by 2016/11/17
 */

public class DoubleListView<LEFT,RIGHT> extends LinearLayout implements AdapterView.OnItemClickListener{

    private EasyAdapter<LEFT> left_adapter;
    private EasyAdapter<RIGHT> right_adapter;
    private ListView lv_left;
    private ListView lv_right;

    public DoubleListView(Context context) {
        super(context);
    }

    public DoubleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.merge_filter_list, this);

        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (ListView) findViewById(R.id.lv_right);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(isFastDoubleClick()){
            return;
        }

    }
}
