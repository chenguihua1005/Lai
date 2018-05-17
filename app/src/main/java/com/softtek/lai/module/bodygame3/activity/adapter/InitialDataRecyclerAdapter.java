
package com.softtek.lai.module.bodygame3.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.InitialDataModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class InitialDataRecyclerAdapter extends RecyclerView.Adapter<InitialDataRecyclerAdapter.ViewHolder> {

    private List<InitialDataModel> myItems;
    private ItemListener myListener;
    private Context mContext;

    public InitialDataRecyclerAdapter(List<InitialDataModel> items, ItemListener listener,Context context) {
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
                .inflate(R.layout.recycler_item_initial_data, parent, false)); // TODO
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
        void onItemClick(InitialDataModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mPhoto;
        private TextView mIdentity;
        private TextView mName;
        private TextView mGroup;
        private TextView mNoData;

        public InitialDataModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.civ_trainer_header);
            mIdentity = itemView.findViewById(R.id.tv_identity);
            mName = itemView.findViewById(R.id.tv_name);
            mGroup = itemView.findViewById(R.id.tv_group);
            mNoData = itemView.findViewById(R.id.tv_no_data);
        }

        public void setData(InitialDataModel item) {
            this.item = item;
            if (item == null){
                return;
            }
            Picasso.with(mContext).load(AddressManager.get("photoHost") + item.getPhoto())
                    .fit().error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(mPhoto);
            itemView.setOnClickListener(this);
            switch (item.getClassRole()) {
                case 1:
                    mIdentity.setBackgroundResource(R.drawable.bg_circle_hornor);
                    mIdentity.setText("总");
                    break;
                case 2:
                    mIdentity.setBackgroundResource(R.drawable.bg_circle_hornor);
                    mIdentity.setText("教");
                    break;
                case 3:
                    mIdentity.setBackgroundResource(R.drawable.bg_circle_hornor);
                    mIdentity.setText("助");
                    break;
                case 4:
                    mIdentity.setBackgroundResource(R.drawable.bg_circle_hornor);
                    mIdentity.setText("学");
                    break;
                default:
                    break;
            }
            mName.setText(item.getUserName());
            mGroup.setText("所属小组: " + item.getCGName());
            if (item.isHasInitMeasure()){
                mNoData.setVisibility(View.GONE);
            }else {
                mNoData.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
                                