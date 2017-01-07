package com.softtek.lai.module.bodygame3.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by lareina.qiao on 1/4/2017.
 */
public class MyExpandableListAdapter implements ExpandableListAdapter {
    Context context;
    Activity activity;
    private String[] groupArray = new String[] { "group1", "group2"};
//    private List<String> groupArray;
    private List<List<String>> childArray;
    private FcStDataModel fcStDataModel;


    public MyExpandableListAdapter(Activity activity,Context context,List<List<String>>childArray,FcStDataModel fcStDataModel)
    {
        this.activity=activity;
        this.context=context;
//        this.groupArray=groupArray;
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
        return groupArray.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray[groupPosition];
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
            holder.group1= (RelativeLayout) view.findViewById(R.id.group1);
            holder.group2= (RelativeLayout) view.findViewById(R.id.group2);

            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }


        switch (groupPosition)
        {
            case 0:
                holder.group2.setVisibility(View.GONE);
                holder.group1.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.group2.setVisibility(View.VISIBLE);
                holder.group1.setVisibility(View.GONE);
                //判断是否已经打开列表
                if(isExpanded){
                    holder.arrow.setBackgroundResource(R.drawable.arrow_up_icon);
                }else{
                    holder.arrow.setBackgroundResource(R.drawable.arrow_down_icon);
                }
//                holder.groupName.setText(groupArray.get(groupPosition));

                break;
        }

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
            holder.tv_danwei= (TextView) view.findViewById(R.id.tv_danwei);
            holder.im_pic= (ImageView) view.findViewById(R.id.im_pic);
            holder.im_pic_icon= (ImageView) view.findViewById(R.id.im_pic_icon);
            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }

//        if(childPosition == 0){
//            holder.divider.setVisibility(View.GONE);
//        }

        holder.im_aciton.setBackgroundResource(R.drawable.action_right);
        switch (groupPosition)
        {
            case 0:
                switch (childPosition)
                {
                    case 0:
                        holder.tv_value.setText(fcStDataModel.getInitWeight());
                        holder.tv_danwei.setText("斤");
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 1:
                        holder.tv_value.setText(fcStDataModel.getWeight());
                        holder.tv_danwei.setText("斤");
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 2:
                        holder.tv_value.setText(fcStDataModel.getPysical());
                        holder.tv_danwei.setText("%");
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 3:
                        holder.tv_value.setText(fcStDataModel.getFat());
                        holder.tv_danwei.setText("%");
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 4:
                        holder.tv_danwei.setVisibility(View.GONE);
                        holder.tv_value.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(fcStDataModel.getImg()))
                        {
                            holder.im_pic.setVisibility(View.VISIBLE);
                            Picasso.with(context).load(AddressManager.get("photoHost")+fcStDataModel.getImg()).fit().centerCrop().placeholder(R.drawable.default_icon_square).into(holder.im_pic);
                        }
                        else if (!TextUtils.isEmpty(fcStDataModel.getImgThumbnail()))
                        {
                            holder.im_pic.setVisibility(View.VISIBLE);
                            Picasso.with(context).load(AddressManager.get("photoHost")+fcStDataModel.getImgThumbnail()).fit().centerCrop().placeholder(R.drawable.default_icon_square).into(holder.im_pic);
                        }
                        else {
                            holder.im_pic_icon.setVisibility(View.VISIBLE);
                        }

                        break;
                    case 5:
                        holder.tv_value.setText(fcStDataModel.getDoLegGirth());
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                }
                break;
            case 1:
                switch (childPosition)
                {
                    case 0:
                        holder.tv_value.setText(fcStDataModel.getCircum());
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 1:
                        holder.tv_value.setText(fcStDataModel.getWaistline());
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 2:
                        holder.tv_value.setText(fcStDataModel.getHiplie());
                        holder.im_pic_icon.setVisibility(View.GONE);
                        holder.im_pic.setVisibility(View.GONE);

                        break;
                    case 3:
                        holder.tv_value.setText(fcStDataModel.getUpArmGirth());
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 4:
                        holder.tv_value.setText(fcStDataModel.getUpLegGirth());
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                    case 5:
                        holder.tv_value.setText(fcStDataModel.getDoLegGirth());
                        holder.im_pic.setVisibility(View.GONE);
                        holder.im_pic_icon.setVisibility(View.GONE);

                        break;
                }
                break;
        }

//
        holder.childName.setText(childArray.get(groupPosition).get(childPosition));
//
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
        public RelativeLayout group1;
        public RelativeLayout group2;
    }

    class ChildHolder{
        public TextView childName;
        public TextView tv_value;
        public TextView tv_danwei;
        public ImageView im_aciton;
        public ImageView im_pic;
        public ImageView im_pic_icon;
        public RelativeLayout re_body;
    }

}
