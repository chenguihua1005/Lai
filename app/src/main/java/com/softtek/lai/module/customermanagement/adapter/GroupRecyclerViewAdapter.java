
package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.List;


public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {

    private List<String> myItems;
    private ItemListener myListener;
    private Context mContext;

    public GroupRecyclerViewAdapter(List<String> items,Context context) {
        myItems = items;
        mContext = context;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_group, parent, false)); // TODO
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
        void onItemClick(String item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mGroupName;
        private ImageView mDelete;

        public String item;

        public ViewHolder(View itemView) {
            super(itemView);
            mGroupName = itemView.findViewById(R.id.tv_group_name);
            mDelete = itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(this);
        }

        public void setData(final String item) {
            this.item = item;
            mGroupName.setText(item);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   myItems.remove(getAdapterPosition());
                   notifyDataSetChanged();
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
                                