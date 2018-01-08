
package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.history.view.ClassInfoActivity;
import com.softtek.lai.module.bodygame3.home.view.BodyGameActivity;
import com.softtek.lai.module.bodygame3.more.model.HistoryClassModel;
import com.softtek.lai.module.customermanagement.model.GymModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class GymRecyclerViewAdapter extends RecyclerView.Adapter<GymRecyclerViewAdapter.ViewHolder> {

    private List<GymModel> myItems;
    private ItemListener myListener;
    private Context mContext;

    public GymRecyclerViewAdapter(List<GymModel> items, ItemListener listener,Context context) {
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
                .inflate(R.layout.recycler_item_gym, parent, false)); // TODO
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
        void onItemClick(GymModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mClassName;
        private TextView mCreateTime;
        private CircleImageView mPhotoView;
        private TextView mMoreInfo;
        private String path = AddressManager.get("photoHost");
        public GymModel item;
        private HistoryClassModel model;

        public ViewHolder(View itemView) {
            super(itemView);
            mClassName = itemView.findViewById(R.id.tv_class_name);
            mCreateTime = itemView.findViewById(R.id.tv_create_time);
            mMoreInfo = itemView.findViewById(R.id.tv_more_info);
            mPhotoView = itemView.findViewById(R.id.civ_photo);
            itemView.setOnClickListener(this);
        }

        public void setData(final GymModel item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getCreatorPhoto())) {
                Picasso.with(mContext).load(path + item.getCreatorPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mPhotoView);
            }
            mClassName.setText(item.getClassName());
            mCreateTime.setText("创建于：" + item.getCreateDate());
            if (item.getStatus().equals("已结束")) {
                model = new HistoryClassModel();
                model.setClassId(item.getClassId());
                model.setClassStart(item.getStartDate());
                model.setClassEnd(item.getEndDate());
                model.setMasterPhoto(item.getCreatorPhoto());
                model.setClassName(item.getClassName());
                model.setMasterName(item.getCreatorName());
                mMoreInfo.setText("往期回顾");
                mMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("classData",model);
                        intent.setClass(mContext, ClassInfoActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }else {
                mMoreInfo.setText("更多");
                mMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, BodyGameActivity.class);
                        intent.putExtra("classId",item.getClassId());
                        intent.putExtra("type",3);
                        mContext.startActivity(intent);
                    }
                });
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
                                