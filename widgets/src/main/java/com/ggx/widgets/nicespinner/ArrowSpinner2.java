package com.ggx.widgets.nicespinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ggx.widgets.R;
import com.ggx.widgets.drop.ArrowRectangleView;

/**
 *
 * Created by jerry.guan on 11/16/2016.
 */

public class ArrowSpinner2 extends LinearLayout{

    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";

    private TextView textView;
    private ImageView imageView;

    private int selectedIndex;
    private Drawable drawable;
    private PopupWindow popupWindow;
    private ListView listView;
    private ArrowSpinnerAdapter adapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private boolean isArrowHide;
    private int textColor;
    private int textSize;


    public ArrowSpinner2(Context context) {
        super(context);
        init(context, null);
    }

    public ArrowSpinner2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArrowSpinner2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if (popupWindow != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, popupWindow.isShowing());
            dismissDropDown();
        }
        return bundle;
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        textView=new TextView(context);
        textView.setMaxEms(10);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxLines(1);
        imageView=new ImageView(context);
        addView(textView);
        addView(imageView);
        LinearLayout.LayoutParams params= (LayoutParams) textView.getLayoutParams();
        params.height=LayoutParams.WRAP_CONTENT;
        params.width=LayoutParams.WRAP_CONTENT;
        params.rightMargin=8;
        textView.setLayoutParams(params);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArrowSpinner2);
        setClickable(true);
        textColor = typedArray.getColor(R.styleable.ArrowSpinner2_arrowTint2, -1);
        textView.setTextColor(textColor);
        textSize= typedArray.getDimensionPixelSize(R.styleable.ArrowSpinner2_textSize2,10);
        textView.setTextSize(textSize);

        View view= LayoutInflater.from(context).inflate(R.layout.drop_list,null);
        ArrowRectangleView arv= (ArrowRectangleView) view.findViewById(R.id.arv);
        arv.setArrowPosition(ArrowRectangleView.RIGHT);
        listView = (ListView) view.findViewById(R.id.lv);
        listView.setDivider(new ColorDrawable(Color.WHITE));
        listView.setSelector(new ColorDrawable(0xFFFFFF));
        listView.setDividerHeight(2);
        listView.setItemsCanFocus(true);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedIndex = position;
                String text=adapter.getText(selectedIndex);
                if(!TextUtils.isEmpty(text)){
                    textView.setText(text);
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
                dismissDropDown();
            }
        });
        popupWindow = new PopupWindow(context);
        int defPopWidthValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 190, getContext().getResources().getDisplayMetrics());
        int pop2Width = typedArray.getDimensionPixelSize(R.styleable.ArrowSpinner2_pop2Width, defPopWidthValue);
        //popupWindow.setAnimationStyle(R.style.mypopupwindow);
        popupWindow.setWidth(pop2Width);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(DEFAULT_ELEVATION);

        }
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_drawable));

        isArrowHide = typedArray.getBoolean(R.styleable.ArrowSpinner2_hideArrow2, false);
        if (!isArrowHide) {
            Drawable basicDrawable = ContextCompat.getDrawable(context, R.drawable.drop_arrow_black);
            if (basicDrawable != null) {
                drawable = DrawableCompat.wrap(basicDrawable);
            }
            imageView.setImageDrawable(drawable);
        }

        typedArray.recycle();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }



    public void addOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void  attachCustomSource(ArrowSpinnerAdapter adapter){
        this.adapter=adapter;
        selectedIndex = 0;
        listView.setAdapter(adapter);
        textView.setText(adapter.getText(selectedIndex));
        if(adapter.getCount()>0){
            setImageVisibility(VISIBLE);
        }else {
            setImageVisibility(GONE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(adapter!=null&&adapter.getCount()>5){
            int defPopWidthValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 215, getContext().getResources().getDisplayMetrics());
            popupWindow.setHeight(defPopWidthValue);
        }else {
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setSelected(int index){
        selectedIndex=index;
        textView.setText(adapter.getText(index));
        listView.setSelection(index);
    }

    public void setImageVisibility(int visibility){
        imageView.setVisibility(visibility);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!popupWindow.isShowing()) {
                if(adapter.getCount()>0){
                    showDropDown();

                }
            } else {
                dismissDropDown();
            }
        }
        return super.onTouchEvent(event);
    }


    public void dismissDropDown() {
        popupWindow.dismiss();
    }

    public void showDropDown() {
        int[] location=new int[2];
        getLocationOnScreen(location);
        //计算差值
        //int cha=popupWindow.getWidth()/2-getWidth()/2;
        int cha=(popupWindow.getWidth()-getWidth())/2;
        //popupWindow.showAtLocation(this,Gravity.NO_GRAVITY,location[0]-,location[1]+getHeight()-35);
        popupWindow.showAsDropDown(this,-cha,-35);
//        popupWindow.showAsDropDown(this);
    }

    public void setText(String str){
        textView.setText(str);
    }
}
