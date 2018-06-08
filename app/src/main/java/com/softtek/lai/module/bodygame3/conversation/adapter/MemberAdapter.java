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
import com.softtek.lai.module.bodygame3.conversation.model.ClassMemberModel;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

public class MemberAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ClassMemberModel> members = new ArrayList<ClassMemberModel>();
    private Context context;

    public MemberAdapter(List<ClassMemberModel> members, Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.members = members;
    }

    public void updateData(List<ClassMemberModel> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public ClassMemberModel getItem(int i) {
        return members.get(i);
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
            convertView = inflater.inflate(R.layout.member_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.group_name = (TextView) convertView.findViewById(R.id.group_name);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ClassMemberModel model = members.get(position);
        String photo = model.getPhoto();
        if (!TextUtils.isEmpty(photo)) {
            int px = DisplayUtil.dip2px(context, 44);
            String path = AddressManager.get("photoHost");
            Picasso.with(context).load(path + photo).resize(px, px).centerCrop().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(R.drawable.img_default).placeholder(R.drawable.img_default).into(holder.img);
        }

        holder.text_name.setText(model.getUserName());

        if (TextUtils.isEmpty(model.getCGName())) {
            holder.group_name.setVisibility(View.GONE);
        } else {
            holder.group_name.setVisibility(View.VISIBLE);
            holder.group_name.setText("(" + model.getCGName() + ")");
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_name;
        public TextView group_name;
        public ImageView img;
    }
}
