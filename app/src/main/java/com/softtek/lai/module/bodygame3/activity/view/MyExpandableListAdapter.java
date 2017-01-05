package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;

import java.util.List;

/**
 * Created by lareina.qiao on 1/4/2017.
 */
public class MyExpandableListAdapter implements ExpandableListAdapter {
    Context context;
    private List<String> groupArray;
    private List<List<String>> childArray;
    private FcStDataModel fcStDataModel;


    public MyExpandableListAdapter(Context context,List<String>groupArray,List<List<String>>childArray,FcStDataModel fcStDataModel)
    {
        this.context=context;
        this.groupArray=groupArray;
        this.childArray=childArray;
        this.fcStDataModel=fcStDataModel;

    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return groupArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if(view == null){
            holder = new GroupHolder();
            view = LayoutInflater.from(context).inflate(R.layout.expandlist_group, null);
            holder.groupName = (TextView)view.findViewById(R.id.tv_group_name);
            holder.arrow = (ImageView)view.findViewById(R.id.arrow);
            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }

        //判断是否已经打开列表
        if(isExpanded){
            holder.arrow.setBackgroundResource(R.drawable.arrow_up_icon);
        }else{
            holder.arrow.setBackgroundResource(R.drawable.arrow_down_icon);
        }

//        holder.groupName.setText(groupArray.get(groupPosition));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if(view == null){
            holder = new ChildHolder();
            view = LayoutInflater.from(context).inflate(R.layout.expandlist_item, null);
            holder.childName = (TextView)view.findViewById(R.id.tv_child_name);
            holder.tv_value = (TextView)view.findViewById(R.id.tv_value);
            holder.im_aciton = (ImageView)view.findViewById(R.id.im_aciton);
            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }

//        if(childPosition == 0){
//            holder.divider.setVisibility(View.GONE);
//        }

        holder.im_aciton.setBackgroundResource(R.drawable.action_right);
        switch (childPosition)
        {
            case 0:
                holder.tv_value.setText(fcStDataModel.getCircum());
                break;
            case 1:
                holder.tv_value.setText(fcStDataModel.getWaistline());
                break;
            case 2:
                holder.tv_value.setText(fcStDataModel.getHiplie());
                break;
            case 3:
                holder.childName.setText("上臂围");
                break;
            case 4:
                holder.childName.setText("大腿围");
                break;
            case 5:
                holder.childName.setText("小腿围");
                break;
        }


        holder.childName.setText(childArray.get(groupPosition).get(childPosition));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(36, 0, 0, 0);
        textView.setTextSize(20);
        return textView;
    }
    class GroupHolder{
        public TextView groupName;
        public ImageView arrow;
    }

    class ChildHolder{
        public TextView childName;
        public TextView tv_value;
        public ImageView im_aciton;
    }

}
