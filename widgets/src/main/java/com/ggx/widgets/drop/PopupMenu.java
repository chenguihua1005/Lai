package com.ggx.widgets.drop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ggx.widgets.R;

public class PopupMenu extends PopupWindow {

    private ListView listView;
    private BaseAdapter adapter;

    private AdapterView.OnItemClickListener onItemClickListener;

    public PopupMenu(Context context,int width) {
        super(LayoutInflater.from(context).inflate(R.layout.drop_list,null), width,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        setAnimationStyle(R.style.mypopupwindow);
        initializeMenuItems();
        // Android API bug: if version <=5.1, must set background otherwise can not be dismissed by touch or back
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void initializeMenuItems() {
        View view=getContentView();
        listView= (ListView) view.findViewById(R.id.lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
                dismiss();
            }
        });
    }

    public void show(View anchor) {
        int[] location=new int[2];
        anchor.getLocationInWindow(location);
        showAtLocation(anchor,Gravity.NO_GRAVITY,location[0], location[1]+anchor.getHeight());
        //super.showAsDropDown(anchor,location[0]/*-anchor.getWidth()/2*/, 0);
    }
    public void show(View anchor,int offsetx,int offsetY) {
        int[] location=new int[2];
        anchor.getLocationInWindow(location);
        super.showAsDropDown(anchor,location[0]-offsetx, offsetY);
    }

    public void dismiss() {
        super.dismiss();
    }

    public boolean isShowing() {
        return super.isShowing();
    }

    public void setOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setAdapter(BaseAdapter adapter){
        this.adapter=adapter;
        listView.setAdapter(adapter);
    }

    public BaseAdapter getAdapter(){
        return adapter;
    }
}
