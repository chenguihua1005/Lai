
package com.softtek.lai.module.laicheng_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng_new.model.GroupModel;

import java.util.ArrayList;
import java.util.List;


public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {

    private List<GroupModel> myItems;
    private FinishListener myListener;
    private Context mContext;

    public GroupRecyclerViewAdapter(List<GroupModel> items, Context context,FinishListener listener) {
        myItems = items;
        myListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_group_name, parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(myItems.get(position));
    }

    public interface FinishListener {
        void setOnFinishListener();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO - Your view members
        public GroupModel item;
        private TextView mGroupName;
        private View mSpace;
        private RecyclerView mRecyclerView;
        private GroupNumberAdapter numberAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            mGroupName = itemView.findViewById(R.id.tv_name);
            mSpace = itemView.findViewById(R.id.space);
            mRecyclerView = itemView.findViewById(R.id.rcv_content);
            itemView.setOnClickListener(this);
            mGroupName.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(GroupModel item) {
            this.item = item;
            if (getAdapterPosition() == myItems.size() - 1){
                mSpace.setVisibility(View.GONE);
            }
            mGroupName.setText(item.getClassName());
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setVisibility(View.GONE);
            numberAdapter = new GroupNumberAdapter(item.getMembers(), new GroupNumberAdapter.ItemListener() {
                @Override
                public void onItemClick(GroupModel.MembersBean item) {
                    VisitorModel model = new VisitorModel();
                    model.setBirthDate(item.getBirthDay());
                    model.setGender(item.getGender());
                    model.setName(item.getUserName());
                    model.setPhoneNo(item.getMobile());
                    model.setHeight(item.getHeight());
                    model.setAge(item.getAge());
                    model.setSuperior(false);
                    model.setVisitorId(item.getAccountId());
                    LocalBroadcastManager.getInstance(LaiApplication.getInstance().getApplicationContext()).
                            sendBroadcast(new Intent().setAction("visitorinfo").putExtra("visitorModel", model));
                    myListener.setOnFinishListener();
                }
            }, mContext);
            mRecyclerView.setAdapter(numberAdapter);
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_name:
                    if (mRecyclerView.getVisibility() == View.GONE){
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mSpace.setVisibility(View.GONE);
                    }else {
                        mRecyclerView.setVisibility(View.GONE);
                        if (getAdapterPosition() == myItems.size() - 1){
                            mSpace.setVisibility(View.GONE);
                        }else {
                            mSpace.setVisibility(View.VISIBLE);
                        }
                    }
            }
        }

    }
}
                                