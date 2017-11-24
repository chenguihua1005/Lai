
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

import java.util.List;

import zilla.libcore.file.AddressManager;


public class InviteRecyclerViewAdapter extends RecyclerView.Adapter<InviteRecyclerViewAdapter.ViewHolder> {

    private List<InviteModel> myItems;
    private ItemListener myListener;
    private Context mContext;

    public InviteRecyclerViewAdapter(List<InviteModel> items, ItemListener listener,Context context) {
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
        void onItemClick(InviteModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mHeadPhoto;
        private TextView mUsername;
        private TextView mState;
        private String path = AddressManager.get("photoHost");

        // TODO - Your view members
        public InviteModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeadPhoto = itemView.findViewById(R.id.civ_head);
            mUsername = itemView.findViewById(R.id.tv_username);
            mState = itemView.findViewById(R.id.tv_invite_state);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        @SuppressLint("SetTextI18n")
        public void setData(InviteModel item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getUserPhoto())) {
                Picasso.with(mContext).load(path + item.getUserPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeadPhoto);
            }
            mUsername.setText(item.getUsername() + "(" + item.getPhoneNumber() + ")");
            if (item.getState() == 0){
                mState.setText("邀 请");
                mState.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_unclick));
            }else if (item.getState() == 1){
                mState.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
                mState.setText("已同意");
            }else if (item.getState() == 2){
                mState.setText("已拒绝");
                mState.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
            }else if (item.getState() == 3){
                mState.setText("待处理");
                mState.setBackground(mContext.getResources().getDrawable(R.drawable.bg_invite_club_clicked));
            }
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
                                