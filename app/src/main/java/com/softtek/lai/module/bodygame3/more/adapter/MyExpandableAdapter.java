package com.softtek.lai.module.bodygame3.more.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.List;
import java.util.Map;

/**
 * Created by jerry.guan on 11/19/2016.
 */

public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private Map<String,List<String>> datas;
    private String[] parentList;
    private Context context;

    public MyExpandableAdapter(Context context,Map<String, List<String>> datas, String[] parentList) {
        this.context=context;
        this.datas = datas;
        this.parentList = parentList;
    }

    //父项的数量
    @Override
    public int getGroupCount() {
        return datas.size();
    }

    //子项的数量
    @Override
    public int getChildrenCount(int i) {
        return datas.get(parentList[i]).size();
    }

    //获取某个父项
    @Override
    public Object getGroup(int i) {
        return datas.get(parentList[i]);
    }

    //获取父项的某个子项
    @Override
    public Object getChild(int i, int i1) {
        return datas.get(parentList[i]).get(i1);
    }

    //获取某个父项的ID
    @Override
    public long getGroupId(int i) {
        return i;
    }

    //获取子项的id
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            view=LayoutInflater.from(context).inflate(R.layout.expandable_parent_item,viewGroup,false);
        }
        TextView textView= (TextView) view.findViewById(R.id.parent_title);
        textView.setText(parentList[parentPos]);
        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            view=LayoutInflater.from(context).inflate(R.layout.expandable_child_item,viewGroup,false);
        }
        TextView textView= (TextView) view.findViewById(R.id.parent_title);
        textView.setText(datas.get(parentList[parentPos]).get(childPos));

        return view;
    }

    //子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
