
package com.softtek.lai.module.customermanagement.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.InviteModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class InviteRecyclerViewAdapter extends RecyclerView.Adapter<InviteRecyclerViewAdapter.ViewHolder> {

    private List<InviteModel.ItemsBean> myItems;
    private ItemListener myListener;
    private InviteListener inviteListener;
    private Context mContext;
    private boolean clickable;

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    private boolean isSearch;

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public InviteRecyclerViewAdapter(List<InviteModel.ItemsBean> items, ItemListener listener, InviteListener inviteListener, Context context) {
        myItems = items;
        myListener = listener;
        mContext = context;
        this.inviteListener = inviteListener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_invite, parent, false)); // TODO
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
        void onItemClick(InviteModel.ItemsBean item);
    }

    public interface InviteListener{
        void onInviteClickListener(View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mHeadPhoto;
        private TextView mUsername;
        private TextView mPhone;
        private TextView mStatus;
        private String path = AddressManager.get("photoHost");

        // TODO - Your view members
        public InviteModel.ItemsBean item;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeadPhoto = itemView.findViewById(R.id.civ_head);
            mUsername = itemView.findViewById(R.id.tv_username);
            mPhone = itemView.findViewById(R.id.tv_phone);
            mStatus = itemView.findViewById(R.id.tv_invite_state);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        @SuppressLint("SetTextI18n")
        public void setData(InviteModel.ItemsBean item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getPhoto())) {
                Picasso.with(mContext).load(path + item.getPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeadPhoto);
            }
            mUsername.setText(item.getUserName());
            mPhone.setText(item.getMobile() + "");
            if (item.getStatus() == 0){
                if (isSearch()) {
                    mStatus.setText("邀 请");
                }else {
                    mStatus.setText("待处理");
                }
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_unclick));
            }else if (item.getStatus() == 1){
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
                mStatus.setText("已同意");
            }else if (item.getStatus() == 2){
                mStatus.setText("已拒绝");
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
            }else if (item.getStatus() == -1){
                mStatus.setText("删 除");
                mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
            }
            mStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inviteListener != null) {
                        if (isClickable()) {
                            inviteListener.onInviteClickListener(view, getAdapterPosition());
                        }
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
                                