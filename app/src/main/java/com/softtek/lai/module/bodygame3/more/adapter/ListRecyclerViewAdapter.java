
package com.softtek.lai.module.bodygame3.more.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.List;


public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {

    private List<String> myItems;
    private ItemListener myListener;
    private Context mContext;
    private int index = -1;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ListRecyclerViewAdapter(List<String> items, ItemListener listener, Context context) {
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
                .inflate(R.layout.recycler_item_list, parent, false)); // TODO
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
        void onItemClick(String item,View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mClubName;
        private ImageView mImage;

        // TODO - Your view members
        public String item;

        public ViewHolder(View itemView) {
            super(itemView);
            mClubName = itemView.findViewById(R.id.tv_club_name);
            mImage = itemView.findViewById(R.id.iv_choose);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(String item) {
            this.item = item;
            mClubName.setText(item);
            if (getIndex() == getAdapterPosition()){
                mImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.radio_green));
            }else {
                mImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.radio_green_default));
            }
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item,v,getAdapterPosition());
            }
        }
    }


}
                                