package com.softtek.lai.widgets;

/**
 * Created by jarvis.liu on 5/18/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.softtek.lai.R;

public class SelectPicPopupWindow extends PopupWindow {


    private LinearLayout lin_weixin, lin_circle, lin_sina;
    private ImageView img;
    private View mMenuView;

    public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_view, null);
        lin_weixin = (LinearLayout) mMenuView.findViewById(R.id.lin_weixin);
        lin_circle = (LinearLayout) mMenuView.findViewById(R.id.lin_circle);
        lin_sina = (LinearLayout) mMenuView.findViewById(R.id.lin_sina);
        lin_sina = (LinearLayout) mMenuView.findViewById(R.id.lin_sina);
        img = (ImageView) mMenuView.findViewById(R.id.img);
        //设置按钮监听
        lin_weixin.setOnClickListener(itemsOnClick);
        lin_circle.setOnClickListener(itemsOnClick);
        lin_sina.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(R.id.lin).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

    }

}
