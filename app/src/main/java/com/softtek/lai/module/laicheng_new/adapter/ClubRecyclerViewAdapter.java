
package com.softtek.lai.module.laicheng_new.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng_new.model.GroupModel;

import java.util.List;


public class ClubRecyclerViewAdapter extends RecyclerView.Adapter<ClubRecyclerViewAdapter.ViewHolder> {

    private List<GroupModel.ClubsBean> myItems;
    private ItemListener myListener;

    public ClubRecyclerViewAdapter(List<GroupModel.ClubsBean> items, ItemListener listener) {
        myItems = items;
        myListener = listener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_club_info, parent, false)); // TODO
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
        void onItemClick(GroupModel.ClubsBean item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mClubName;
        private View mSpace;
        // TODO - Your view members
        public GroupModel.ClubsBean item;

        public ViewHolder(View itemView) {
            super(itemView);
            mClubName = itemView.findViewById(R.id.tv_club_name);
            mSpace = itemView.findViewById(R.id.space);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(GroupModel.ClubsBean item) {
            this.item = item;
            mClubName.setText(item.getClubName());
            if (getAdapterPosition() == myItems.size() - 1){
                mSpace.setVisibility(View.GONE);
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
                                