package com.softtek.lai.module.bodygame3.love.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.love.model.LoverModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jessica.zhang on 1/5/2017.
 */

public class LoverMemberAdapter extends BaseAdapter {
    private Context context;
    private List<LoverModel> loverModels;

    public LoverMemberAdapter(Context context, List<LoverModel> loverModels) {
        this.context = context;
        this.loverModels = loverModels;
    }

    public void updateData(List<LoverModel> loverModels) {
        this.loverModels = loverModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return loverModels.size();
    }

    @Override
    public Object getItem(int i) {
        return loverModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LoverModel model = loverModels.get(i);

        ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lover_member_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (model != null) {
            holder.rank_tv.setText(String.valueOf(model.getNum()));

            if (!TextUtils.isEmpty(model.getPhoto())) {
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                Picasso.with(context).load(path + model.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.head_img);
            } else {
                Picasso.with(context).load(R.drawable.img_default).into(holder.head_img);
            }

            holder.name.setText(model.getUserName());
            holder.groupName_tv.setText(model.getCGName());
            holder.count_tv.setText(String.valueOf(model.getCou()));

        }

        return view;
    }

    private final class ViewHolder {
        public ViewHolder(View view) {
            rank_tv = (TextView) view.findViewById(R.id.rank_tv);
            head_img = (CircleImageView) view.findViewById(R.id.head_img);
            name = (TextView) view.findViewById(R.id.name);

            groupName_tv = (TextView) view.findViewById(R.id.groupName_tv);
            count_tv = (TextView) view.findViewById(R.id.count_tv);


        }

        private TextView rank_tv;
        private CircleImageView head_img;
        private TextView name;

        private TextView groupName_tv;
        private TextView count_tv;
    }


}
