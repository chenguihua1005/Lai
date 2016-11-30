package com.softtek.lai.module.bodygame3.conversation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.conversation.model.FriendModel;

import java.util.List;

/**
 * Created by jessica.zhang on 2016/11/30.
 */

public class FriendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<FriendModel> friendslist;

    public FriendAdapter(Context context, List<FriendModel> list) {
        this.inflater = LayoutInflater.from(context);
        this.friendslist = list;
    }

    public void updateData(List<FriendModel> list) {
        this.friendslist = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return friendslist.size();
    }

    @Override
    public FriendModel getItem(int i) {
        return friendslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_newfriend, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final FriendModel friendModel = friendslist.get(position);
//        String photo = friendModel.get();
//        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
//        if ("".equals(photo)) {
//            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(holder.img);
//        } else {
//            Picasso.with(context).load(path + photo).fit().error(R.drawable.img_default).into(holder.img);
//        }
        if (friendModel != null) {
            if (!TextUtils.isEmpty(friendModel.getUserName())) {
                holder.tv_name.setText(friendModel.getUserName());
            }
            holder.tv_role.setText("(" + friendModel.getRole() + ")");
            holder.tv_classname.setText("吃货班 ");

            int status = friendModel.getStatus();
            if (0 == status) {
                holder.tv_status.setText("未处理");
            } else if (1 == status) {
                holder.tv_status.setText("已同意");
            } else if (-1 == status) {
                holder.tv_status.setText("已拒绝");
            }
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    private class ViewHolder {
        private ImageView head_img;//头像图标
        private TextView tv_name;//姓名
        private TextView tv_role;//角色
        private TextView tv_classname;//班级名称
        public TextView tv_status;//是否同意状态

        public ViewHolder(View view) {
            this.head_img = (ImageView) view.findViewById(R.id.head_img);
            this.tv_name = (TextView) view.findViewById(R.id.tv_name);
            this.tv_role = (TextView) view.findViewById(R.id.tv_role);
            this.tv_classname = (TextView) view.findViewById(R.id.tv_classname);
            this.tv_status = (TextView) view.findViewById(R.id.tv_status);
        }
    }
}
