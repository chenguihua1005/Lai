package com.softtek.lai.module.bodygame3.conversation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;

/**
 * Created by jessica.zhang on 11/24/2016.
 */

public class ContactMenuAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private int count;

    private int[] menu_icons = {R.drawable.group_contact, R.drawable.addfri_contact, R.drawable.msg_contact};
    private String[] menu_names = {"班级群", "新朋友", "群发消息"};

    public ContactMenuAdapter(Context mContext, int count) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void updateCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return menu_names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.contact_menu_itemmy, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.menu_icon.setImageResource(menu_icons[i]);
        holder.menu_name.setText(menu_names[i]);
        if (1 == i) {
            holder.unread_msg_number.setVisibility(View.VISIBLE);
            holder.unread_msg_number.setText(String.valueOf(count));
        }
        if (2 == i) {
            holder.shuxian.setVisibility(View.GONE);
        }

        return view;
    }

    private final class ViewHolder {
        public ViewHolder(View view) {
            menu_icon = (ImageView) view.findViewById(R.id.menu_icon);
            menu_name = (TextView) view.findViewById(R.id.menu_name);
            shuxian = view.findViewById(R.id.shuxian);
            unread_msg_number = (TextView) view.findViewById(R.id.unread_msg_number);
        }

        private ImageView menu_icon;
        private TextView menu_name;
        private View shuxian;
        private TextView unread_msg_number;
    }
}
