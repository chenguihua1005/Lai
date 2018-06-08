
package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.ClubNameModel;

import java.util.List;


public class ChooseClubRecyclerViewAdapter extends RecyclerView.Adapter<ChooseClubRecyclerViewAdapter.ViewHolder> {

    private List<ClubNameModel> myItems;
    private ItemListener myListener;
    private Context mContext;

    public ChooseClubRecyclerViewAdapter(List<ClubNameModel> items, ItemListener listener,Context context) {
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
                .inflate(R.layout.recycler_item_club_choose, parent, false)); // TODO
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
        void onItemClick(ClubNameModel item,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mClubName;
        private ImageView mIsSelect;

        // TODO - Your view members
        public ClubNameModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            mClubName = itemView.findViewById(R.id.tv_club_name);
            mIsSelect = itemView.findViewById(R.id.iv_is_select);
            itemView.setOnClickListener(this);
        }

        public void setData(ClubNameModel item) {
            this.item = item;
            if (item == null){
                return;
            }
            mClubName.setText(item.getClubName());
            if (item.isSelected()){
                mIsSelect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.radio_green));
            }else {
                mIsSelect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.radio_green_default));
            }
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item,getAdapterPosition());
            }
        }
    }


}
                                