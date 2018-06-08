package com.softtek.lai.module.bodygame3.more.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.more.model.InvitatedContact;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 11/19/2016.
 *
 */

public class InvitatedExpandableAdapter extends BaseExpandableListAdapter {

    private Map<String,List<InvitatedContact>> datas;
    private List<String> groups;
    private Context context;

    public InvitatedExpandableAdapter(Context context, Map<String, List<InvitatedContact>> datas, List<String> groups) {
        this.context=context;
        this.datas = datas;
        this.groups = groups;
    }

    //父项的数量
    @Override
    public int getGroupCount() {
        return datas.size();
    }

    //子项的数量
    @Override
    public int getChildrenCount(int i) {
        return datas.get(groups.get(i)).size();
    }

    //获取某个父项
    @Override
    public Object getGroup(int i) {
        return datas.get(groups.get(i));
    }

    //获取父项的某个子项
    @Override
    public Object getChild(int i, int i1) {
        return datas.get(groups.get(i)).get(i1);
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
        if(parentPos<groups.size()){
            TextView textView= (TextView) view.findViewById(R.id.group_name);
            textView.setText(groups.get(parentPos));
        }
        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            view=LayoutInflater.from(context).inflate(R.layout.expandable_child_item,viewGroup,false);
        }
        InvitatedContact contact=datas.get(groups.get(parentPos)).get(childPos);
        TextView name= (TextView) view.findViewById(R.id.tv_name);
        name.setText(contact.getInviterUserName());
        CircleImageView head_image= (CircleImageView) view.findViewById(R.id.head_image);
        if(!TextUtils.isEmpty(contact.getInviterPhoto())){
            Picasso.with(context).load(AddressManager.get("photoHost")+contact.getInviterPhoto()).fit()
                    .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
        }else {
            Picasso.with(context).load(R.drawable.img_default).into(head_image);
        }
        TextView tv_role= (TextView) view.findViewById(R.id.tv_role);
        int role=contact.getClassRole();
        tv_role.setText(role==1?"(总教练)":role==2?"(教练)":role==3?"(助教)":role==4?"(学员)":"");
        TextView tv_status= (TextView) view.findViewById(R.id.tv_status);
        int status=contact.getInviterStatus();
        tv_status.setText(status==0?"已发送":status==1?"已接受":status==2?"已拒绝":status==3?"删除":"");
        return view;
    }

    //子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
