
package com.softtek.lai.module.laiClassroom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.model.SearchModel;


public class ChaosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIDEO = 1;
    private static final int ONE_PIC = 2;
    private static final int THREE_PIC = 3;
    private SearchModel myItems;
    private ItemListener myListener;

    public ChaosAdapter(SearchModel items, ItemListener listener) {
        myItems = items;
        myListener = listener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIDEO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
            return new VideoViewHolder(view);
        } else if (viewType == ONE_PIC) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one_pic, parent, false);
            return new OnePicHolder(view);
        } else if (viewType == THREE_PIC) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_three_pic, parent, false);
            return new ThreePicHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;//默认没有
        int itemType = myItems.getArticleList().get(position).getMediaType();
        int isMultiPic = myItems.getArticleList().get(position).getIsMultiPic();
        if (itemType == 1) {
            if (isMultiPic == 0) {
                result = 2;//1图
            } else {
                result = 3;//多图
            }
        } else if (itemType == 2) {
            result = 1;//视频
        }

        return result;
    }

    @Override
    public int getItemCount() {
        if (myItems.getArticleList() != null) {
            return myItems.getArticleList().size();
        }else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            ((VideoViewHolder) holder).setData(myItems, position);
        } else if (getItemViewType(position) == 2) {
            ((OnePicHolder) holder).setData(myItems, position);
        } else if (getItemViewType(position) == 3) {
            ((ThreePicHolder) holder).setData(myItems, position);
        }
    }

    public interface ItemListener {
        void onItemClick(SearchModel item, int position);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO - Your view members
        public SearchModel item;

        public VideoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(SearchModel item, int position) {
            this.item = item;
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item, getAdapterPosition());
            }
        }
    }

    public class OnePicHolder extends RecyclerView.ViewHolder {
        public SearchModel item;

        public OnePicHolder(View view) {
            super(view);
        }

        public void setData(SearchModel item, int position) {
            this.item = item;
            // TODO set data to view
        }
    }

    public class ThreePicHolder extends RecyclerView.ViewHolder {
        public SearchModel item;

        public ThreePicHolder(View view) {
            super(view);
        }

        public void setData(SearchModel item, int position) {
            this.item = item;
            // TODO set data to view
        }
    }


}
                                