
package com.softtek.lai.module.laiClassroom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.history.adapter.RecyclerViewInfoAdapter;
import com.softtek.lai.module.laiClassroom.model.SearchModel;

import java.util.List;


public class ChaosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIDEO = 1;
    private static final int ONE_PIC = 2;
    private static final int THREE_PIC = 3;
    private List<SearchModel> myItems;
    private ItemListener myListener;

    public ChaosAdapter(List<SearchModel> items, ItemListener listener) {
        myItems = items;
        myListener = listener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIDEO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ONE_PIC) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one_pic, parent, false);
            return new ViewHolder(view);
        } else if (viewType == THREE_PIC) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_three_pic, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        int itemType = myItems.get(position).getType();
        int isMultiPic = myItems.get(position).getIsMultiPic();
        if (itemType == 1){
            if (isMultiPic == 0){
                result = 2;
            }else {
                result = 3;
            }
        }else if (itemType == 2){
            result = 1;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setData(myItems.get(position));
    }

    public interface ItemListener {
        void onItemClick(SearchModel item,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO - Your view members
        public SearchModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(SearchModel item) {
            this.item = item;
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item,getAdapterPosition());
            }
        }
    }


}
                                