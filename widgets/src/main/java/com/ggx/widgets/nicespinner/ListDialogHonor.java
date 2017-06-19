package com.ggx.widgets.nicespinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.R;

/**
 * Created by jerry.guan on 11/16/2016.
 */

public class ListDialogHonor extends TextView {

    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";

    private int selectedIndex;
    private Drawable drawable;
    private ListView listView;
    private ArrowSpinnerAdapter adapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private boolean isArrowHide;
    private AlertDialog dialog;

    public ListDialogHonor(Context context) {
        super(context);
        init(context, null);
    }

    public ListDialogHonor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ListDialogHonor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ArrowSpinnerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, selectedIndex);
        return bundle;
    }

    private void init(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListDialog);
        setClickable(true);

        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listView = new ListView(getContext());
        listView.setDivider(new ColorDrawable(Color.parseColor("#c0c0c0")));
        listView.setDividerHeight(2);
        listView.setItemsCanFocus(true);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        container.addView(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedIndex = position;
                String text = adapter.getText(selectedIndex);
                if (!TextUtils.isEmpty(text)) {
                    setText(text);
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }

                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(parent, view, position, id);
                }
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(container);
        dialog = builder.create();

        isArrowHide = typedArray.getBoolean(R.styleable.ListDialog_hide, false);
        if (!isArrowHide) {
            Drawable basicDrawable = ContextCompat.getDrawable(context, R.drawable.drop_arrow_honor);
            if (basicDrawable != null) {
                drawable = DrawableCompat.wrap(basicDrawable);
            }
            if (adapter != null && adapter.getCount() > 1) {
                setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        }

        typedArray.recycle();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }


    public void addOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(@NonNull AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }


    public void attachCustomSource(ArrowSpinnerAdapter adapter) {
        this.adapter = adapter;
        selectedIndex = 0;
        listView.setAdapter(adapter);
        setText(adapter.getText(selectedIndex));
        if (adapter.getCount() > 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }

    }

    public void setHeight(int height) {
        if (adapter != null && adapter.getCount() > 5) {
            int defPopHeightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getContext().getResources().getDisplayMetrics());
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) listView.getLayoutParams();
            params.height = height <= 0 ? defPopHeightValue : height;
            listView.setLayoutParams(params);
        }
    }

    public void setSelected(int index) {
        selectedIndex = index;
        setText(adapter.getText(index));
        if (adapter.getCount() > 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public void notifChange() {
        if (adapter.getCount() > 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!dialog.isShowing()) {
                if (adapter.getCount() > 1) {
                    showDropDown();

                }
            } else {
                dialog.dismiss();
            }
        }
        return super.onTouchEvent(event);
    }


    public void showDropDown() {
        dialog.show();

    }

    public void setTintColor(@ColorRes int resId) {
        if (drawable != null && !isArrowHide) {
            DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), resId));
        }
    }

}
