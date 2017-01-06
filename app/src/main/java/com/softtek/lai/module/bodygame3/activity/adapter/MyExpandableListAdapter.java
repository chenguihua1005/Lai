package com.softtek.lai.module.bodygame3.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;

import java.util.List;

import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 1/4/2017.
 */
public class MyExpandableListAdapter implements ExpandableListAdapter {
    Context context;
    Activity activity;
    private List<String> groupArray;
    private List<List<String>> childArray;
    private FcStDataModel fcStDataModel;


    public MyExpandableListAdapter(Activity activity,Context context,List<String>groupArray,List<List<String>>childArray,FcStDataModel fcStDataModel)
    {
        this.activity=activity;
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
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if(view == null){
            holder = new ChildHolder();
            view = LayoutInflater.from(context).inflate(R.layout.expandlist_item, null);
            holder.childName = (TextView)view.findViewById(R.id.tv_child_name);
            holder.tv_value = (TextView)view.findViewById(R.id.tv_value);
            holder.im_aciton = (ImageView)view.findViewById(R.id.im_aciton);
            holder.re_body= (RelativeLayout) view.findViewById(R.id.re_body);
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
                holder.tv_value.setText(fcStDataModel.getUpArmGirth());
                break;
            case 4:
                holder.tv_value.setText(fcStDataModel.getUpLegGirth());
                break;
            case 5:
                holder.tv_value.setText(fcStDataModel.getDoLegGirth());
                break;
        }

//
        holder.childName.setText(childArray.get(groupPosition).get(childPosition));
//        holder.re_body.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (childPosition)
//                {
//                    case 0:
//                        Util.toastMsg("点击啦");
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//                    case 5:
//                        break;
//                }
//            }
//        });
        return view;
    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(context);
        final View view =  activity.getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(np1maxvalur);
        np1.setValue(np1value);
        np1.setMinValue(np1minvalue);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(np2maxvalue);
        np2.setValue(np2value);
        np2.setMinValue(np2minvalue);
        np2.setWrapSelectorWheel(false);
        information_dialog.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (num == 0) {
                    ChildHolder childHolder=new ChildHolder();
                    childHolder.tv_value.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
//                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
//                    tv_write_chu_weight.setError(null);
                } else if (num == 1) {
//                    tv_retestWrite_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
//                    tv_retestWrite_nowweight.setError(null);
                } else if (num == 2) {
//                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                } else if (num == 3) {
//                    tv_retestWrite_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();


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
        public RelativeLayout re_body;
    }

}
