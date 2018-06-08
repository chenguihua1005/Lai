package com.ggx.widgets.view;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry.guan on 11/22/2016.
 */

public class ChooseView extends LinearLayout{

    private OnChooseListener listener;
    private List<RectF> rectFs=new ArrayList<>();
    private List<String> charaset=new ArrayList<>();

    public ChooseView(Context context) {
        super(context);
        initView();
    }

    public ChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setOrientation(VERTICAL);
        setClickable(true);
//        buildCharaset("↑ ");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(0x52000000);
                if(listener!=null){
                    listener.onDown();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX=event.getRawX();
                float rawY=event.getRawY();
                TextView view= (TextView) getChooseView(rawX,rawY);
                if(listener!=null&&view!=null){
//                    if(view.getText().toString().equals("↑ ")){
//                        index=-10;
//                    }
                    listener.chooseView(view.getText().toString(),index);
                }
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0x00000000);
                if(listener!=null){
                    listener.onUp();
                }
                break;
        }
        return true;
    }

    public void buildCharaset(String text){
        if(charaset.contains(text)){
           return;
        }
        charaset.add(text);
        TextView textView=new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(12);

        LinearLayout.LayoutParams params=
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,18,getContext().getResources().getDisplayMetrics()));
        textView.setLayoutParams(params);
        textView.setClickable(false);
        textView.setGravity(Gravity.CENTER);
        addView(textView);

    }

    public void clear(){
        charaset.clear();
        removeAllViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectFs.clear();
        for (int i=0;i<getChildCount();i++){
            View view=getChildAt(i);
            int[] location=new int[2];
            view.getLocationOnScreen(location);
            RectF rectF=new RectF(location[0], location[1], location[0] + view.getWidth(),
                    location[1] + view.getHeight());
            rectFs.add(rectF);
        }
    }

    public void setChooseListener(OnChooseListener listener) {
        this.listener = listener;
    }

    public interface OnChooseListener{
        void onDown();
        void chooseView(String text,int index);
        void onUp();
    }

    int index=0;
    private View getChooseView(float rawX, float rawY){
        for (int i=0;i<rectFs.size();i++){
            RectF rectF=rectFs.get(i);
            if(rectF.contains(rawX,rawY)){
                index=i;
                return getChildAt(i);
            }
        }
        return null;
    }
}
