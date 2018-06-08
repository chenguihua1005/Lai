package com.softtek.lai.widgets;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by jerry.guan on 2/6/2017.
 */

public class CommentTextView extends TextView{

    private int mStart = -1;

    private int mEnd = -1;

    public CommentTextView(Context context) {
        super(context);
    }

    public CommentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= getTotalPaddingLeft();
        y -= getTotalPaddingTop();

        x += getScrollX();
        y += getScrollY();

        Layout layout = getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        CharSequence text = getText();
        if (TextUtils.isEmpty(text) || !(text instanceof Spannable)) {
            return result;
        }

        Spannable buffer = (Spannable) text;
        ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

        if (link.length != 0) {

            if (action == MotionEvent.ACTION_DOWN) {

                mStart = buffer.getSpanStart(link[0]);
                mEnd = buffer.getSpanEnd(link[0]);

                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(0xFFBFBFBF), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Selection.removeSelection(buffer);
                    mStart = -1;
                    mEnd = -1;
                }
            }

            return true;
        } else {
            if (mStart >= 0 && mEnd >= mStart) {
                buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                Selection.removeSelection(buffer);
                mStart = -1;
                mEnd = -1;
            }

            return false;
        }
    }
}
