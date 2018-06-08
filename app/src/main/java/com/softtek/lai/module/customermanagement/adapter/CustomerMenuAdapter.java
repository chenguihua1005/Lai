package com.softtek.lai.module.customermanagement.adapter;

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

public class CustomerMenuAdapter extends BaseAdapter {
    private Context mContext;

    private int[] menu_icons = {R.drawable.add_customer, R.drawable.register_account, R.drawable.create_act, R.drawable.tiguanban};
    private String[] menu_names = {"添加客户", "注册账户", "创建活动", "体管班"};
    private boolean hasClub;

    public CustomerMenuAdapter(Context mContext, boolean HasClub) {
        this.mContext = mContext;
        this.hasClub = HasClub;
    }

    public void updateData(boolean HasClub) {
        this.hasClub = HasClub;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 4;
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

        if (i == 3) {
            if (!hasClub) {
                holder.menu_icon.setImageResource(R.drawable.tiguanban_gray);
            } else {
                holder.menu_icon.setImageResource(R.drawable.tiguanban);
            }
        } else {
            holder.menu_icon.setImageResource(menu_icons[i]);
        }
        holder.menu_name.setText(menu_names[i]);

        return view;
    }

    private final class ViewHolder {
        public ViewHolder(View view) {
            menu_icon = (ImageView) view.findViewById(R.id.menu_icon);
            menu_name = (TextView) view.findViewById(R.id.menu_name);
            shuxian = view.findViewById(R.id.shuxian);
        }

        private ImageView menu_icon;
        private TextView menu_name;
        private View shuxian;
    }
}
