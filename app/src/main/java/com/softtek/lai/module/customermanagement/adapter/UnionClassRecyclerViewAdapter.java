
package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.UnionClassModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class UnionClassRecyclerViewAdapter extends RecyclerView.Adapter<UnionClassRecyclerViewAdapter.ViewHolder> {

    private List<UnionClassModel.ItemsBean> myItems;
    private ItemListener myListener;
    private InviteListener inviteListener;
    private Context mContext;
    private boolean clickable;

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public UnionClassRecyclerViewAdapter(List<UnionClassModel.ItemsBean> items, Context context, InviteListener inviteListener) {
        myItems = items;
        mContext = context;
        this.inviteListener = inviteListener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_union_class, parent, false)); // TODO
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
        void onItemClick(UnionClassModel.ItemsBean item);
    }

    public interface InviteListener {
        void onInviteClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mPhotoView;
        private TextView mClassname;
        private TextView mCreateTime;
        private TextView mStatus;
        private String path = AddressManager.get("photoHost");
        public UnionClassModel.ItemsBean item;

        public ViewHolder(View itemView) {
            super(itemView);
            mPhotoView = itemView.findViewById(R.id.civ_photo);
            mClassname = itemView.findViewById(R.id.tv_class_name);
            mCreateTime = itemView.findViewById(R.id.tv_create_time);
            mStatus = itemView.findViewById(R.id.tv_invite_state);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(UnionClassModel.ItemsBean item) {
            this.item = item;
            if (item == null) {
                return;
            }
            if (isClickable()){
                mStatus.setEnabled(true);
            }
            if (!TextUtils.isEmpty(item.getPhoto())) {
                Picasso.with(mContext).load(path + item.getPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mPhotoView);
            }
            mClassname.setText(item.getClassName());
            mCreateTime.setText("创建于：" + item.getCreateDate());
            if (item.getStatus() == -1) {
                mStatus.setText("可申请");
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_unclick));
            } else if (item.getStatus() == 1) {
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
                mStatus.setText("已同意");
                mStatus.setEnabled(false);
            } else if (item.getStatus() == 0) {
                mStatus.setText("待处理");
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
                mStatus.setEnabled(false);
            }
            mStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inviteListener != null) {
                        inviteListener.onInviteClickListener(view, getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
