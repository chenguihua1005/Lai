
package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.GymModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class GymRecyclerViewAdapter extends RecyclerView.Adapter<GymRecyclerViewAdapter.ViewHolder> {

    private List<GymModel> myItems;
    private ItemListener myListener;
    private Context mContext;

    public GymRecyclerViewAdapter(List<GymModel> items, ItemListener listener,Context context) {
        myItems = items;
        myListener = listener;
        mContext = context;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_gym, parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(myItems.get(position));
    }

    public interface ItemListener {
        void onItemClick(GymModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mClubName;
        private TextView mCreateTime;
        private CircleImageView mPhotoView;
        private TextView mMoreInfo;
        private String path = AddressManager.get("photoHost");
        public GymModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            mClubName = itemView.findViewById(R.id.tv_club_name);
            mCreateTime = itemView.findViewById(R.id.tv_create_time);
            mMoreInfo = itemView.findViewById(R.id.tv_more_info);
            itemView.setOnClickListener(this);
        }

        public void setData(GymModel item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getPhoto())) {
                Picasso.with(mContext).load(path + item.getPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mPhotoView);
            }
            mClubName.setText(item.getClubName());
            mCreateTime.setText(item.getTime());
            if (item.getType().equals("已结束")) {
                mMoreInfo.setText("往期回顾");
            }else {
                mMoreInfo.setText("更多");
            }
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
                                