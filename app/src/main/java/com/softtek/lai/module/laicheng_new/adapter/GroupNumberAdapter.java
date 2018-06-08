
package com.softtek.lai.module.laicheng_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng_new.model.GroupModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class GroupNumberAdapter extends RecyclerView.Adapter<GroupNumberAdapter.ViewHolder> {

    private List<GroupModel.MembersBean> myItems;
    private ItemListener myListener;
    private Context mContext;

    public GroupNumberAdapter(List<GroupModel.MembersBean> items, ItemListener listener,Context context) {
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
                .inflate(R.layout.recycler_item_group_number, parent, false)); // TODO
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
        void onItemClick(GroupModel.MembersBean item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO - Your view members
        public GroupModel.MembersBean item;
        private CircleImageView mHeadImage;
        private TextView mName;
        private TextView mGender;
        private TextView mAge;
        private View mSpace;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mHeadImage = itemView.findViewById(R.id.civ_head);
            mName = itemView.findViewById(R.id.tv_name);
            mGender = itemView.findViewById(R.id.tv_gender);
            mAge = itemView.findViewById(R.id.tv_age);
            mSpace = itemView.findViewById(R.id.space);
            // TODO instantiate/assign view members
        }

        public void setData(GroupModel.MembersBean item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getPhoto())) {
                Picasso.with(mContext).load(AddressManager.get("photoHost") + item.getPhoto()).fit().into(mHeadImage);
            } else {
                Picasso.with(mContext).load(R.drawable.img_default).fit().into(mHeadImage);
            }
            if (getAdapterPosition() == myItems.size() - 1){
                mSpace.setVisibility(View.GONE);
            }
            mName.setText(item.getUserName());
            mGender.setText("性别" + (item.getGender() == 0 ? "男" : "女"));
            mAge.setText("年龄" + String.valueOf(item.getAge()));
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
                                