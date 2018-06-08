package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.github.snowdream.android.util.Log;

/**
 * Created by jerry.guan on 2/23/2017.
 */

public class CursorChangeEditText extends EditText{

    private OnCursorChangeListener onCursorChangeListener;

    public CursorChangeEditText(Context context) {
        super(context);
    }

    public CursorChangeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CursorChangeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if(onCursorChangeListener!=null){
            onCursorChangeListener.onChange(selStart,selEnd);
        }
    }

    public OnCursorChangeListener getOnCursorChangeListener() {
        return onCursorChangeListener;
    }

    public void setOnCursorChangeListener(OnCursorChangeListener onCursorChangeListener) {
        this.onCursorChangeListener = onCursorChangeListener;
    }

    public interface OnCursorChangeListener{
        void onChange(int selStart,int selEnd);
    }
}
